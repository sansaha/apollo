package com.lexmark.apollo.api.service.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
import com.lexmark.apollo.api.dto.SalesComparisonDto;
import com.lexmark.apollo.api.dto.SalesDto;
import com.lexmark.apollo.api.service.SalesService;
import com.lexmark.apollo.api.service.queries.SalesQuery;
import com.lexmark.apollo.api.service.queries.SalesQueryRowMapper;
import com.lexmark.apollo.api.util.ApolloServiceException;
import com.lexmark.apollo.api.util.ApolloServiceHelper;
import com.lexmark.apollo.api.util.SalesDtoComparatorByGrossSales;
import com.lexmark.apollo.api.util.SalesDtoComparatorByQuantitySales;

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
        
        ApolloServiceHelper.populateSalePercentage(salesDtoList);
        
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
                    SalesQueryRowMapper.GROSS_SALES_BETWEEN_DATES_ROW_MAPPER);
        } catch (DataAccessException e) {
            log.error("Error occured while processing top sales information based on gross sale form: "+startDate+" to: "+endDate,e);
            throw new ApolloServiceException("Error occured while processing top sales information based on gross sale form: "+startDate+" to: "+endDate,e);
        }
        
        if(salesDtoList != null && salesDtoList.isEmpty() == false){
            ApolloServiceHelper.popolateZeroSalesForMissingdates(salesDtoList, ApolloServiceHelper.parseDate(startDate, null), ApolloServiceHelper.parseDate(endDate, null));
            Collections.sort(salesDtoList, new SalesDtoComparatorByGrossSales());
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
                    SalesQueryRowMapper.GROSS_SALES_BETWEEN_DATES_ROW_MAPPER);
        } catch (DataAccessException e) {
            log.error("Error occured while processing sales information based on gross sales change form: "+startDate+" to: "+endDate,e);
            throw new ApolloServiceException("Error occured while processing sales information based on gross sales change form: "+startDate+" to: "+endDate,e);
        }
        
        if(salesDtoList != null && salesDtoList.isEmpty() == false){
            ApolloServiceHelper.popolateZeroSalesForMissingdates(salesDtoList, ApolloServiceHelper.parseDate(startDate, null), ApolloServiceHelper.parseDate(endDate, null));
            Collections.sort(salesDtoList, new SalesDtoComparatorByGrossSales());
        }
        
        return salesDtoList;
    }
    
    @Override
    public List<SalesDto> getGrossSalesUserSelectedData(String startDate, String endDate, String[] items) throws ApolloServiceException {
        
        validateDateRange(startDate, endDate);
        
        if(items == null || items.length == 0){
            throw new IllegalArgumentException("Invalid items : "+items);
        }
        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("startDate", startDate);
        namedParameters.addValue("endDate", endDate);
        namedParameters.addValue("items", Arrays.asList(items));
        
        List<SalesDto> salesDtoList = null;
        
        try {
            salesDtoList = namedParameterJdbcTemplate.query(
                    SalesQuery.QUERY_GROSS_SALES_FOR_ITEMS_BETWEEN_DATES, namedParameters, 
                    SalesQueryRowMapper.GROSS_SALES_BETWEEN_DATES_ROW_MAPPER);
        } catch (DataAccessException e) {
            log.error("Error occured while processing gross sales information based on input items form: "+startDate+" to: "+endDate,e);
            throw new ApolloServiceException("Error occured while processing gross sales information based on input items form: "+startDate+" to: "+endDate,e);
        }
        
        if(salesDtoList != null && salesDtoList.isEmpty() == false){
            ApolloServiceHelper.popolateZeroSalesForMissingdates(salesDtoList, ApolloServiceHelper.parseDate(startDate, null), ApolloServiceHelper.parseDate(endDate, null));
            Collections.sort(salesDtoList, new SalesDtoComparatorByGrossSales());
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
                    SalesQueryRowMapper.QUANTITY_SOLD_BETWEEN_DATES_ROW_MAPPER);
        } catch (DataAccessException e) {
            log.error("Error occured while processing top sales information based on quantity form: "+startDate+" to: "+endDate,e);
            throw new ApolloServiceException("Error occured while processing top sales information based on quantity form: "+startDate+" to: "+endDate,e);
        }
        
        if(salesDtoList != null && salesDtoList.isEmpty() == false){
            ApolloServiceHelper.popolateZeroSalesForMissingdates(salesDtoList, ApolloServiceHelper.parseDate(startDate, null), ApolloServiceHelper.parseDate(endDate, null));
            Collections.sort(salesDtoList, new SalesDtoComparatorByQuantitySales());
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
                    SalesQueryRowMapper.QUANTITY_SOLD_BETWEEN_DATES_ROW_MAPPER);
        } catch (DataAccessException e) {
            log.error("Error occured while processing sales information based on most sold quantity change form: "+startDate+" to: "+endDate,e);
            throw new ApolloServiceException("Error occured while processing sales information based on most sold quantity change form: "+startDate+" to: "+endDate,e);
        }
        
        if(salesDtoList != null && salesDtoList.isEmpty() == false){
            ApolloServiceHelper.popolateZeroSalesForMissingdates(salesDtoList, ApolloServiceHelper.parseDate(startDate, null), ApolloServiceHelper.parseDate(endDate, null));
            Collections.sort(salesDtoList, new SalesDtoComparatorByQuantitySales());
        }
        
        return salesDtoList;
    }
    
    @Override
    public List<SalesDto> getQuantitySoldUserSelectData(String startDate, String endDate, String[] items) throws ApolloServiceException {
        
        validateDateRange(startDate, endDate);
        
        if(items == null || items.length == 0){
            throw new IllegalArgumentException("Invalid items : "+items);
        }
        
        log.info("input items:: "+items.length);
        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("startDate", startDate);
        namedParameters.addValue("endDate", endDate);
        namedParameters.addValue("items", Arrays.asList(items));
        
        List<SalesDto> salesDtoList = null;
        
        try {
            salesDtoList = namedParameterJdbcTemplate.query(
                    SalesQuery.QUERY_QUANTITY_SOLD_FOR_ITEMS_BETWEEN_DATES, namedParameters, 
                    SalesQueryRowMapper.QUANTITY_SOLD_BETWEEN_DATES_ROW_MAPPER);
        } catch (DataAccessException e) {
            log.error("Error occured while processing quantity sales information based on input items form: "+startDate+" to: "+endDate,e);
            throw new ApolloServiceException("Error occured while processing quantity sales information based on input items form: "+startDate+" to: "+endDate,e);
        }
        
        if(salesDtoList != null && salesDtoList.isEmpty() == false){
            ApolloServiceHelper.popolateZeroSalesForMissingdates(salesDtoList, ApolloServiceHelper.parseDate(startDate, null), ApolloServiceHelper.parseDate(endDate, null));
            Collections.sort(salesDtoList, new SalesDtoComparatorByQuantitySales());
        }
        
        return salesDtoList;
    }
    
    @Override
	public SalesComparisonDto getItemSalesEffectivenessForPromotion(String item, String startDate, String endDate,
			String promoStartDate, String promoEndDate) throws ApolloServiceException {
		
    	validateDateRange(startDate, endDate);
    	
    	Date promoStDt = null;
    	
    	try {
    		promoStDt = ApolloServiceHelper.parseDate(promoStartDate, null);
        } catch (IllegalArgumentException e) {
            log.error("Invalid Promotion Start Date : "+promoStartDate, e);
            throw new IllegalArgumentException("Invalid Promotion Start Date : "+promoStartDate, e);
        }
    	
    	Date promoEndDt = null;
    	
    	if(promoEndDate != null){
    		try {
    			promoEndDt = ApolloServiceHelper.parseDate(promoEndDate, null);
            } catch (IllegalArgumentException e) {
                log.error("Invalid Promotion End Date : "+promoEndDate, e);
                throw new IllegalArgumentException("Invalid Promotion End Date : "+promoEndDate, e);
            }
    	}
    	
    	//sales for promotion period
    		Date userSelectionStDt = ApolloServiceHelper.parseDate(startDate, null);
    		Date userSelectionEndDt = ApolloServiceHelper.parseDate(endDate, null);
    		
    		Date withinPromoQueryStartDate = null;
    		Date withinPromoQueryEndDate = null;
    		
    		if(promoStDt.before(userSelectionStDt)){
    			withinPromoQueryStartDate = userSelectionStDt;
    		} else if (promoStDt.after(userSelectionStDt) || promoStDt.equals(userSelectionStDt)){
    			withinPromoQueryStartDate = promoStDt;
    		}
    	
    		if(promoEndDt == null){
    			withinPromoQueryEndDate = userSelectionEndDt;
    		} else if (promoEndDt.before(userSelectionEndDt) || promoEndDt.equals(userSelectionEndDt)){
    			withinPromoQueryEndDate = promoEndDt;
    		}
    		
    		SalesComparisonDto salesComparisonDto = new SalesComparisonDto();
    		
    		log.info("Within Promo Query Start Date:: "+withinPromoQueryStartDate);
    		log.info("Within Promo Query End Date:: "+withinPromoQueryEndDate);
    		
    		List<SalesDto> salesWithinPromotion = getItemSalesDetailsByDate(item, ApolloServiceHelper.formatDate(withinPromoQueryStartDate, null), ApolloServiceHelper.formatDate(withinPromoQueryEndDate, null));
    	
    		salesComparisonDto.setCurrentSales(salesWithinPromotion);
    		
    		int effectivePromoDurationInDays = ApolloServiceHelper.getDurationInDays(withinPromoQueryStartDate, withinPromoQueryEndDate);
    		
    		//sales for other period
    		Date withoutPromoQueryStartDate = null;
    		Date withoutPromoQueryEndDate = null;
    		
    		Calendar withoutPromoQueryCal = Calendar.getInstance();
    		withoutPromoQueryCal.setTime(promoStDt);
    		
    		if(effectivePromoDurationInDays >= 7){
    			withoutPromoQueryCal.add(Calendar.DAY_OF_MONTH, -1);
        		withoutPromoQueryEndDate = withoutPromoQueryCal.getTime();
        		
    			withoutPromoQueryCal.add(Calendar.DAY_OF_MONTH, (-1*(effectivePromoDurationInDays)));
    			withoutPromoQueryStartDate = withoutPromoQueryCal.getTime();
    		}else {
    			withoutPromoQueryCal.add(Calendar.DAY_OF_MONTH, -7);
    			withoutPromoQueryStartDate = withoutPromoQueryCal.getTime();
    			
    			withoutPromoQueryCal.add(Calendar.DAY_OF_MONTH, (effectivePromoDurationInDays));
    			withoutPromoQueryEndDate = withoutPromoQueryCal.getTime();
    		}
    		
    		log.info("Without Promo Query Start Date:: "+withoutPromoQueryStartDate);
    		log.info("Without Promo Query End Date:: "+withoutPromoQueryEndDate);
    		
    		List<SalesDto> salesWithoutPromotion = getItemSalesDetailsByDate(item, ApolloServiceHelper.formatDate(withoutPromoQueryStartDate, null), ApolloServiceHelper.formatDate(withoutPromoQueryEndDate, null));
        	
    		salesComparisonDto.setPreviousSales(salesWithoutPromotion);

		return salesComparisonDto;
	}
    
    private List<SalesDto> getItemSalesDetailsByDate(String itemName,String startDate,String endDate)throws ApolloServiceException {
    	
    	MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("startDate", startDate);
        namedParameters.addValue("endDate", endDate);
        namedParameters.addValue("itemName", itemName);
        
        List<SalesDto> salesDtoList = null;
        
        try {
            salesDtoList = namedParameterJdbcTemplate.query(
                    SalesQuery.QUERY_ITEM_SALES_DETAILS_BETWEEN_DATES, namedParameters, 
                    SalesQueryRowMapper.ITEM_SALES_DETAILS_BETWEEN_DATES_ROW_MAPPER);
        } catch (DataAccessException e) {
            log.error("Error occured while processing item sales information form: "+startDate+" to: "+endDate,e);
            throw new ApolloServiceException("Error occured while processing item sales information form: "+startDate+" to: "+endDate,e);
        }
        
        return salesDtoList;
    }
    
    private void validateDateRange(String startDate, String endDate){
        try {
            ApolloServiceHelper.parseDate(startDate, null);
        } catch (IllegalArgumentException e) {
            log.error("Invalid startDate : "+startDate, e);
            throw new IllegalArgumentException("Invalid startDate : "+startDate, e);
        }
        
        try {
            ApolloServiceHelper.parseDate(endDate, null);
        } catch (IllegalArgumentException e) {
            log.error("Invalid endDate : "+endDate, e);
            throw new IllegalArgumentException("Invalid endDate : "+endDate, e);
        }
    }


	
}
