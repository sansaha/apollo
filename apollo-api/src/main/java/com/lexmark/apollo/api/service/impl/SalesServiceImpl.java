package com.lexmark.apollo.api.service.impl;

import java.util.List;

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
import com.lexmark.apollo.api.dto.SalesDto;
import com.lexmark.apollo.api.service.SalesService;
import com.lexmark.apollo.api.service.queries.SalesQuery;
import com.lexmark.apollo.api.service.queries.SalesQueryRowMapper;
import com.lexmark.apollo.api.util.ApolloServiceException;
import com.lexmark.apollo.api.util.ApolloServiceHelper;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SalesServiceImpl implements SalesService {
    
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


    public List<SalesDto> getSalesData(String startDate, String endDate) throws ApolloServiceException {
        
        validateDateRange(startDate, endDate);
        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("startDate", startDate);
        namedParameters.addValue("endDate", endDate);
        
        List<SalesDto> salesDtoList = null;
        
        try {
            salesDtoList = namedParameterJdbcTemplate.query(
                    SalesQuery.QUERY_SALES_DATA_BETWEEN_DATES, namedParameters, 
                    SalesQueryRowMapper.SALES_BETWEEN_DATES_ROW_MAPPER);
        } catch (DataAccessException e) {
            log.error("Error occured while processing sales information form: "+startDate+" to: "+endDate,e);
            throw new ApolloServiceException("Error occured while processing sales information form: "+startDate+" to: "+endDate,e);
        }
        
        return salesDtoList;
    }
    

    @Override
    public List<SalesDto> getGrossSalesTopNData(String startDate, String endDate, int topN) throws ApolloServiceException {
        
        validateDateRange(startDate, endDate);
        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("startDate", startDate);
        namedParameters.addValue("endDate", endDate);
        namedParameters.addValue("recordCount", topN);
        
        List<SalesDto> salesDtoList = null;
                
        try {
            salesDtoList = namedParameterJdbcTemplate.query(
                    SalesQuery.QUERY_GROSS_SALES_TOP_N_BETWEEN_DATES, namedParameters, 
                    SalesQueryRowMapper.GROSS_SALES_TOP_N_BETWEEN_DATES_ROW_MAPPER);
        } catch (DataAccessException e) {
            log.error("Error occured while processing top sales information based on gross sale form: "+startDate+" to: "+endDate,e);
            throw new ApolloServiceException("Error occured while processing top sales information based on gross sale form: "+startDate+" to: "+endDate,e);
        }
        
        return salesDtoList;
    }

    @Override
    public List<SalesDto> getGrossSalesMostChangedData(String startDate, String endDate, int mostChanged) throws ApolloServiceException {
        
        validateDateRange(startDate, endDate);
        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("startDate", startDate);
        namedParameters.addValue("endDate", endDate);
        namedParameters.addValue("recordCount", mostChanged);
        
        List<SalesDto> salesDtoList = null;
        
        try {
            salesDtoList = namedParameterJdbcTemplate.query(
                    SalesQuery.QUERY_GROSS_SALES_MOST_CHANGED_BETWEEN_DATES, namedParameters, 
                    SalesQueryRowMapper.GROSS_SALES_MOST_CHANGED_BETWEEN_DATES_ROW_MAPPER);
        } catch (DataAccessException e) {
            log.error("Error occured while processing sales information based on gross sales change form: "+startDate+" to: "+endDate,e);
            throw new ApolloServiceException("Error occured while processing sales information based on gross sales change form: "+startDate+" to: "+endDate,e);
        }
        
        return salesDtoList;
    }

    @Override
    public List<SalesDto> getQuantitySoldTopNData(String startDate, String endDate, int topN) throws ApolloServiceException {
        
        validateDateRange(startDate, endDate);
        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("startDate", startDate);
        namedParameters.addValue("endDate", endDate);
        namedParameters.addValue("recordCount", topN);
        
        List<SalesDto> salesDtoList = null;
        
        try {
            salesDtoList = namedParameterJdbcTemplate.query(
                    SalesQuery.QUERY_QUANTITY_SOLD_TOP_N_BETWEEN_DATES, namedParameters, 
                    SalesQueryRowMapper.QUANTITY_SOLD_TOP_N_BETWEEN_DATES_ROW_MAPPER);
        } catch (DataAccessException e) {
            log.error("Error occured while processing top sales information based on quantity form: "+startDate+" to: "+endDate,e);
            throw new ApolloServiceException("Error occured while processing top sales information based on quantity form: "+startDate+" to: "+endDate,e);
        }
        
        return salesDtoList;
    }

    @Override
    public List<SalesDto> getQuantitySoldMostChangedData(String startDate, String endDate, int mostChanged) throws ApolloServiceException {
        
        validateDateRange(startDate, endDate);
        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("startDate", startDate);
        namedParameters.addValue("endDate", endDate);
        namedParameters.addValue("recordCount", mostChanged);
        
        List<SalesDto> salesDtoList = null;
        
        try {
            salesDtoList = namedParameterJdbcTemplate.query(
                    SalesQuery.QUERY_QUANTITY_SOLD_MOST_CHANGED_BETWEEN_DATES, namedParameters, 
                    SalesQueryRowMapper.QUANTITY_SOLD_MOST_CHANGED_BETWEEN_DATES_ROW_MAPPER);
        } catch (DataAccessException e) {
            log.error("Error occured while processing sales information based on most sold quantity change form: "+startDate+" to: "+endDate,e);
            throw new ApolloServiceException("Error occured while processing sales information based on most sold quantity change form: "+startDate+" to: "+endDate,e);
        }
        
        return salesDtoList;
    }
    
    private void validateDateRange(String startDate, String endDate){
        try {
            ApolloServiceHelper.validateDate(startDate, null);
        } catch (IllegalArgumentException e) {
            log.error("Invalid startDate : "+startDate, e);
            throw new IllegalArgumentException("Invalid startDate : "+startDate, e);
        }
        
        try {
            ApolloServiceHelper.validateDate(endDate, null);
        } catch (IllegalArgumentException e) {
            log.error("Invalid endDate : "+endDate, e);
            throw new IllegalArgumentException("Invalid endDate : "+endDate, e);
        }
    }
}
