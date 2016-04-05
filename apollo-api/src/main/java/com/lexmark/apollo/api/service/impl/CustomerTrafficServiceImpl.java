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
import com.lexmark.apollo.api.dto.CustomerTrafficResponseDto;
import com.lexmark.apollo.api.dto.CustomerTrafficResponseDto.CustomerTraffic;
import com.lexmark.apollo.api.dto.DemographicProfileDto;
import com.lexmark.apollo.api.dto.SignageDto;
import com.lexmark.apollo.api.dto.SignageEffectivenessResponseDto;
import com.lexmark.apollo.api.dto.SignageEffectivenessResponseDto.SignageEffectiveness;
import com.lexmark.apollo.api.service.CustomerTrafficService;
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
public class CustomerTrafficServiceImpl implements CustomerTrafficService {
    
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
    
    public CustomerTrafficResponseDto getCustomerTrafficData(String fromDate, String toDate) throws ApolloServiceException {
        
        try {
            ApolloServiceHelper.parseDate(fromDate, null);
        } catch (IllegalArgumentException e) {
            log.error("Invalid formDate : "+fromDate, e);
            throw new IllegalArgumentException("Invalid formDate : "+fromDate, e);
        }
        
        try {
            ApolloServiceHelper.parseDate(toDate, null);
        } catch (IllegalArgumentException e) {
            log.error("Invalid toDate : "+toDate, e);
            throw new IllegalArgumentException("Invalid toDate : "+toDate, e);
        }
        
        CustomerTrafficResponseDto customerTrafficResponseDto = new CustomerTrafficResponseDto();
        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("startDate", fromDate);
        namedParameters.addValue("endDate", toDate);

        try {
            List<CustomerTrafficResponseDto.CustomerTraffic> customerTraffics = namedParameterJdbcTemplate.query(
                    CustomerTrafficQuery.SELECT_CUSTOMER_TRAFFIC_BETWEEN_DATES_QUERY, namedParameters, 
                    CustomerTrafficQueryRowMapper.CUSTOMER_TRAFFIC_ACTUAL_ROW_MAPPER);
            
            
            if(customerTraffics != null){
                customerTrafficResponseDto.setCustomerTraffics(customerTraffics);
            }
            
            customerTrafficResponseDto.sortCustomerTraffic();
            
            List<CustomerTrafficResponseDto.CustomerTraffic> projectedCustomerTraffics = namedParameterJdbcTemplate.query(
                    CustomerTrafficQuery.SELECT_PROJECTED_CUSTOMER_TRAFFIC_BETWEEN_DATES_QUERY, namedParameters, 
                    CustomerTrafficQueryRowMapper.CUSTOMER_TRAFFIC_PROJECTED_ROW_MAPPER);
            
            mergeProjectedTraffic(customerTraffics, projectedCustomerTraffics);
            
            customerTrafficResponseDto.populateTrafficSegments();
            
            List<DemographicProfileDto> demographicProfiles = namedParameterJdbcTemplate.query(
                    CustomerTrafficQuery.SELECT_DEMOGRAPHIC_PROFILES_BETWEEN_DATES_QUERY, namedParameters, 
                    CustomerTrafficQueryRowMapper.DEMOGRAPHIC_PROFILE_ROW_MAPPER);
            
            if(demographicProfiles != null){
                CustomerDemographicProfileDto customerDemographicProfile = new CustomerDemographicProfileDto(demographicProfiles);
                customerTrafficResponseDto.setCustomerDemographicProfile(customerDemographicProfile);
            }
        } catch (DataAccessException e) {
            log.error("Error occured while processing customer traffic information form: "+fromDate+" to: "+toDate,e);
            throw new ApolloServiceException("Error occured while processing customer traffic information form: "+fromDate+" to: "+toDate,e);
        }
        

        return customerTrafficResponseDto;
    }
    
    private void mergeProjectedTraffic(List<CustomerTrafficResponseDto.CustomerTraffic> customerTraffics,List<CustomerTrafficResponseDto.CustomerTraffic> projectedCustomerTraffics){
        
        Map<Date,CustomerTraffic> projectedTrafficMap = new HashMap<Date,CustomerTraffic>();
        for(CustomerTrafficResponseDto.CustomerTraffic projectedCustomerTraffic : projectedCustomerTraffics){
            projectedTrafficMap.put(projectedCustomerTraffic.getDate(), projectedCustomerTraffic);
        }
        
        for(CustomerTrafficResponseDto.CustomerTraffic customerTraffic : customerTraffics){
            CustomerTraffic projectedTraffic = projectedTrafficMap.get(customerTraffic.getDate());
            if(projectedTraffic != null){
                customerTraffic.setProjectedTraffic(projectedTraffic.getProjectedTraffic());
            }
        }
    }
    
    public SignageEffectivenessResponseDto getSignageEffectivenessData(String fromDate, String toDate, int minWatchTime) throws ApolloServiceException {
        
        try {
            ApolloServiceHelper.parseDate(fromDate, null);
        } catch (IllegalArgumentException e) {
            log.error("Invalid formDate : "+fromDate, e);
            throw new IllegalArgumentException("Invalid formDate : "+fromDate, e);
        }
        
        try {
            ApolloServiceHelper.parseDate(toDate, null);
        } catch (IllegalArgumentException e) {
            log.error("Invalid toDate : "+toDate, e);
            throw new IllegalArgumentException("Invalid toDate : "+toDate, e);
        }
        
        SignageEffectivenessResponseDto signageEffectivenessResponseDto = new SignageEffectivenessResponseDto();
        
        MapSqlParameterSource namedParametersForSignage = new MapSqlParameterSource();
        namedParametersForSignage.addValue("startDate", fromDate);
        namedParametersForSignage.addValue("endDate", toDate);
        //namedParametersForSignage.addValue("minWatchTime", minWatchTime);

        try {
            List<SignageDto> signages = namedParameterJdbcTemplate.query(
                    SignageQuery.SELECT_CUSTOMER_TRAFFIC_BETWEEN_DATES_QUERY, namedParametersForSignage, 
                    SignageQueryRowMapper.SIGNAGE_BETWEEN_DATES_ROW_MAPPER);
            
            int totalSignages = signages != null?signages.size():0;
            
            Date signageEndDate = null;
            
            //populating end date
            for(int i = totalSignages - 1; i > 0;i--){
            	SignageDto signageDto = signages.get(i);
            	if(signageEndDate == null){
            		signageDto.setEndDateTime(ApolloServiceHelper.parseDate(toDate, null));
            		signageEndDate = signageDto.getStartDateTime();
            	} else {
            		signageDto.setEndDateTime(signageEndDate);
            		signageEndDate = signageDto.getStartDateTime();
            	}
            }
            
            if(totalSignages > 0){
            	
            	MapSqlParameterSource namedParametersForCustomerTraffic = new MapSqlParameterSource();
            	namedParametersForCustomerTraffic.addValue("startDate", fromDate);
            	namedParametersForCustomerTraffic.addValue("endDate", toDate);
                namedParametersForCustomerTraffic.addValue("minWatchTime", minWatchTime);
                
                List<DemographicProfileDto> customerDetails = namedParameterJdbcTemplate.query(CustomerTrafficQuery.SELECT_CUSTOMER_BETWEEN_DATES_AND_MIN_ATTENTION_TIME, 
                		namedParametersForCustomerTraffic,CustomerTrafficQueryRowMapper.CUSTOMER_BETWEEN_DATES_AND_MIN_ATTENTION_TIME_ROW_MAPPER);
                
                Map<SignageDto,List<DemographicProfileDto>> signageDemographyMap = new HashMap<SignageDto,List<DemographicProfileDto>>();
                List<SignageDto> resentSignages = new ArrayList<SignageDto>();
                
                Calendar yesterday = Calendar.getInstance();
                yesterday.add(Calendar.DAY_OF_MONTH, -1);
                yesterday.set(Calendar.HOUR_OF_DAY, 0);
                yesterday.set(Calendar.MINUTE, 0);
                yesterday.set(Calendar.SECOND, 0);
                
                for(DemographicProfileDto demographicProfileDto : customerDetails){
                	SignageDto matchingSignageDto = null;
                	for(SignageDto signageDto : signages){
                		Date demographicDateTime = demographicProfileDto.getStartDateTime();
                		Date signageStartDateTime = signageDto.getStartDateTime();
                		Date signageEndDateTime = signageDto.getEndDateTime();
                		
                		if(resentSignages.contains(signageDto) == false && ApolloServiceHelper.isBetweenDates(yesterday.getTime(), signageStartDateTime, signageEndDateTime)){
                			resentSignages.add(signageDto);
                		}
                		
                		if(ApolloServiceHelper.isBetweenDates(demographicDateTime, signageStartDateTime, signageEndDateTime)){
                			matchingSignageDto = signageDto;
                			break;
                		}
                	}
                	
                	if(matchingSignageDto != null){
                		List<DemographicProfileDto> signageSpecificDemographyList = null;
                		if(signageDemographyMap.containsKey(matchingSignageDto)){
                			signageSpecificDemographyList = signageDemographyMap.get(matchingSignageDto);
                		}else{
                			signageSpecificDemographyList = new ArrayList<DemographicProfileDto>();
                			signageDemographyMap.put(matchingSignageDto, signageSpecificDemographyList);
                		}
                		signageSpecificDemographyList.add(demographicProfileDto);
                	}
                }
                
                
                
                
            }
            
            
            
            
            /*Map<String,List<DemographicProfileDto>> signageDemographyMap = new HashMap<String,List<DemographicProfileDto>>();
            Set<Integer> locationSet = new HashSet<Integer>();
            for(DemographicProfileDto demographicProfileDto : demographicProfileDuringSignage){
            	locationSet.add(demographicProfileDto.getLocationId());
            	if(signageDemographyMap.containsKey(demographicProfileDto.getDescription())){
            		signageDemographyMap.get(demographicProfileDto.getDescription()).add(demographicProfileDto);
            	}else{
            		List<DemographicProfileDto> demographicProfileSubList = new ArrayList<DemographicProfileDto>();
            		demographicProfileSubList.add(demographicProfileDto);
            		signageDemographyMap.put(demographicProfileDto.getDescription(), demographicProfileSubList);
            	}
            }*/
            
            /*if(signageDemographyMap.isEmpty() == false){
            	for(String signage : signageDemographyMap.keySet()){
            		List<DemographicProfileDto> demographicProfileSubList = signageDemographyMap.get(signage);
            		CustomerDemographicProfileDto customerDemographicProfileDto = new CustomerDemographicProfileDto(demographicProfileSubList);
            		SignageEffectiveness signageEffectiveness = signageEffectivenessResponseDto.createSignageEffectiveness();
            		signageEffectiveness.setName(signage);
            		signageEffectiveness.setTrafficDuringSignage(customerDemographicProfileDto.getTotalCount());
            		signageEffectiveness.setCustomerDemographicDuringSignage(customerDemographicProfileDto);
            	}
            }*/
            
            
           
        } catch (DataAccessException e) {
            log.error("Error occured while processing customer traffic information form: "+fromDate+" to: "+toDate,e);
            throw new ApolloServiceException("Error occured while processing customer traffic information form: "+fromDate+" to: "+toDate,e);
        }
        
//TODO
        return null;
    }
    
    

}
