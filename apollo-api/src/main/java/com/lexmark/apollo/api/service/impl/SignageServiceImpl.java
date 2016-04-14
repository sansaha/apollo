package com.lexmark.apollo.api.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.lexmark.apollo.api.config.JdbcTemplateConfig;
import com.lexmark.apollo.api.dto.CustomerDemographicProfileDto;
import com.lexmark.apollo.api.dto.DemographicProfileDto;
import com.lexmark.apollo.api.dto.SignageDto;
import com.lexmark.apollo.api.dto.SignageEffectivenessResponseDto;
import com.lexmark.apollo.api.service.SignageService;
import com.lexmark.apollo.api.service.queries.CustomerTrafficQuery;
import com.lexmark.apollo.api.service.queries.CustomerTrafficQueryRowMapper;
import com.lexmark.apollo.api.service.queries.SignageQuery;
import com.lexmark.apollo.api.service.queries.SignageQueryRowMapper;
import com.lexmark.apollo.api.util.ApolloServiceException;
import com.lexmark.apollo.api.util.ApolloServiceHelper;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SignageServiceImpl implements SignageService {

	@Getter
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	@Qualifier("redshiftDataSource")
	private DataSource dataSource;

	@Autowired
	private JdbcTemplateConfig jdbcTemplateConfig;

	@PostConstruct
	public void init() {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.setQueryTimeout(jdbcTemplateConfig.getQueryTimeout());
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(template);
	}

	@Override
	public SignageEffectivenessResponseDto getSignageEffectivenessData(String fromDate, String toDate, int minWatchTime)
			throws ApolloServiceException {

		try {
			ApolloServiceHelper.parseDate(fromDate, null);
		} catch (IllegalArgumentException e) {
			log.error("Invalid formDate : " + fromDate, e);
			throw new IllegalArgumentException("Invalid formDate : " + fromDate, e);
		}

		try {
			ApolloServiceHelper.parseDate(toDate, null);
		} catch (IllegalArgumentException e) {
			log.error("Invalid toDate : " + toDate, e);
			throw new IllegalArgumentException("Invalid toDate : " + toDate, e);
		}

		SignageEffectivenessResponseDto signageEffectivenessResponseDto = new SignageEffectivenessResponseDto();

		MapSqlParameterSource namedParametersForSignage = new MapSqlParameterSource();
		namedParametersForSignage.addValue("startDate", fromDate);
		namedParametersForSignage.addValue("endDate", toDate);

		try {
			List<SignageDto> signages = namedParameterJdbcTemplate.query(
					SignageQuery.SELECT_CUSTOMER_TRAFFIC_BETWEEN_DATES_QUERY, namedParametersForSignage,
					SignageQueryRowMapper.SIGNAGE_BETWEEN_DATES_ROW_MAPPER);

			int totalSignages = signages != null ? signages.size() : 0;

			Date signageEndDate = null;

			// populating end date
			for (int i = totalSignages - 1; i >= 0; i--) {
				SignageDto signageDto = signages.get(i);
				if (signageEndDate == null) {
					signageDto.setEndDateTime(ApolloServiceHelper.parseDate(toDate, null));
					signageEndDate = signageDto.getStartDateTime();
				} else {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(signageEndDate);
					calendar.add(Calendar.DAY_OF_MONTH, -1);
					signageDto.setEndDateTime(calendar.getTime());
					signageEndDate = signageDto.getStartDateTime();
				}
			}

			if (totalSignages > 0) {

				MapSqlParameterSource namedParametersForCustomerTraffic = new MapSqlParameterSource();
				namedParametersForCustomerTraffic.addValue("startDate", fromDate);
				namedParametersForCustomerTraffic.addValue("endDate", toDate);
				namedParametersForCustomerTraffic.addValue("minWatchTime", minWatchTime);

				List<DemographicProfileDto> customerDetails = namedParameterJdbcTemplate.query(
						CustomerTrafficQuery.SELECT_CUSTOMER_BETWEEN_DATES_AND_MIN_ATTENTION_TIME,
						namedParametersForCustomerTraffic,
						CustomerTrafficQueryRowMapper.CUSTOMER_BETWEEN_DATES_AND_MIN_ATTENTION_TIME_ROW_MAPPER);

				Map<SignageDto, List<DemographicProfileDto>> signageDemographyMap = new HashMap<SignageDto, List<DemographicProfileDto>>();
				List<SignageDto> recentSignages = new ArrayList<SignageDto>();
				List<SignageDto> averageSignages = new ArrayList<SignageDto>();
				List<SignageDto> historicalSignages = new ArrayList<SignageDto>();

				Calendar yesterday = Calendar.getInstance();
				yesterday.add(Calendar.DAY_OF_MONTH, -1);
				yesterday.set(Calendar.HOUR_OF_DAY, 0);
				yesterday.set(Calendar.MINUTE, 0);
				yesterday.set(Calendar.SECOND, 0);

				for (DemographicProfileDto demographicProfileDto : customerDetails) {
					SignageDto matchingSignageDto = null;
					for (SignageDto signageDto : signages) {
						Date demographicDateTime = demographicProfileDto.getStartDateTime();
						Date signageStartDateTime = signageDto.getStartDateTime();
						Date signageEndDateTime = signageDto.getEndDateTime();

						if (averageSignages.contains(signageDto) == false && ApolloServiceHelper
								.isBetweenDates(yesterday.getTime(), signageStartDateTime, signageEndDateTime)) {
						    averageSignages.add(signageDto);
						} else if(historicalSignages.contains(signageDto) == false && ApolloServiceHelper
                                .isBetweenDates(yesterday.getTime(), signageStartDateTime, signageEndDateTime) == false){
						    historicalSignages.add(signageDto);
						}

						if (ApolloServiceHelper.isBetweenDates(demographicDateTime, signageStartDateTime,
								signageEndDateTime)) {
							matchingSignageDto = signageDto;
							break;
						}
					}

					if (matchingSignageDto != null) {
						List<DemographicProfileDto> signageSpecificDemographyList = null;
						if (signageDemographyMap.containsKey(matchingSignageDto)) {
							signageSpecificDemographyList = signageDemographyMap.get(matchingSignageDto);
						} else {
							signageSpecificDemographyList = new ArrayList<DemographicProfileDto>();
							signageDemographyMap.put(matchingSignageDto, signageSpecificDemographyList);
						}
						signageSpecificDemographyList.add(demographicProfileDto);
					}
				}

				signageEffectivenessResponseDto.setRecentSignages(recentSignages);
				signageEffectivenessResponseDto.setAverageSignages(averageSignages);
				signageEffectivenessResponseDto.setHistoricalSignages(historicalSignages);

				for (SignageDto signageDto : signages) {
					List<DemographicProfileDto> signageSpecificDemographyList = signageDemographyMap.get(signageDto);
					if (signageSpecificDemographyList != null) {
						CustomerDemographicProfileDto customerDemographicProfileDto = new CustomerDemographicProfileDto(
								signageSpecificDemographyList);
						int totalTraffic = customerDemographicProfileDto.getTotalCount();
						int signageDuration = ApolloServiceHelper.getDurationInDays(signageDto.getStartDateTime(),
								signageDto.getEndDateTime());
						int averageTraffic = totalTraffic / signageDuration;
						signageDto.setViewerCount(averageTraffic);
						customerDemographicProfileDto.popuateAverageCount(signageDuration);
						signageDto.setCustomersDemographicProfile(customerDemographicProfileDto);
					}

					if (ApolloServiceHelper.isBetweenDates(yesterday.getTime(), signageDto.getStartDateTime(),
							signageDto.getEndDateTime())) {
					    
					    SignageDto recentSignageDto = signageDto.copySignage();
					    recentSignages.add(recentSignageDto);
						// have to update the recent demography
						if (signageSpecificDemographyList != null) {
							List<DemographicProfileDto> recentSignageSpecificDemographyList = new ArrayList<DemographicProfileDto>();
							for (DemographicProfileDto demographicProfileDto : signageSpecificDemographyList) {
								String demographicStartDate = ApolloServiceHelper
										.formatDate(demographicProfileDto.getStartDateTime(), null);
								String recentDate = ApolloServiceHelper.formatDate(yesterday.getTime(), null);
								if (demographicStartDate.equals(recentDate)) {
									recentSignageSpecificDemographyList.add(demographicProfileDto);
								}
							}

							CustomerDemographicProfileDto recentCustomerDemographicProfileDto = new CustomerDemographicProfileDto(
									recentSignageSpecificDemographyList);
							int totalTraffic = recentCustomerDemographicProfileDto.getTotalCount();
							recentSignageDto.setViewerCount(totalTraffic);
							recentSignageDto.setCustomersDemographicProfile(recentCustomerDemographicProfileDto);

						}
					}
				}
			}

		} catch (DataAccessException e) {
			log.error("Error occured while processing signage and customer demographic information form: " + fromDate
					+ " to: " + toDate, e);
			throw new ApolloServiceException(
					"Error occured while processing signage and customer demographic information form: " + fromDate
							+ " to: " + toDate,
					e);
		}

		return signageEffectivenessResponseDto;
	}

}
