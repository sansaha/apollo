package com.lexmark.apollo.api.service.impl;

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
import com.lexmark.apollo.api.dto.CustomerTrafficResponseDto;
import com.lexmark.apollo.api.dto.CustomerTrafficResponseDto.CustomerDemographicProfile;
import com.lexmark.apollo.api.dto.CustomerTrafficResponseDto.CustomerTraffic;
import com.lexmark.apollo.api.service.CustomerTrafficService;
import com.lexmark.apollo.api.service.queries.CustomerTrafficQuery;
import com.lexmark.apollo.api.service.queries.CustomerTrafficQueryRowMapper;
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
            ApolloServiceHelper.validateDate(fromDate, null);
        } catch (IllegalArgumentException e) {
            log.error("Invalid formDate : "+fromDate, e);
            throw new IllegalArgumentException("Invalid formDate : "+fromDate, e);
        }
        
        try {
            ApolloServiceHelper.validateDate(toDate, null);
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
            
            List<CustomerTrafficResponseDto.DemographicProfile> demographicProfiles = namedParameterJdbcTemplate.query(
                    CustomerTrafficQuery.SELECT_DEMOGRAPHIC_PROFILES_BETWEEN_DATES_QUERY, namedParameters, 
                    CustomerTrafficQueryRowMapper.DEMOGRAPHIC_PROFILE_ROW_MAPPER);
            
            if(demographicProfiles != null){
                CustomerDemographicProfile customerDemographicProfile = CustomerTrafficResponseDto.createCustomerDemographicProfile(demographicProfiles);
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
    
    

}
