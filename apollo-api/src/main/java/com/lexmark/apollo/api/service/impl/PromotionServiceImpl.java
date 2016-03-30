package com.lexmark.apollo.api.service.impl;

import java.util.Collections;
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
import com.lexmark.apollo.api.dto.PromotionDto;
import com.lexmark.apollo.api.service.PromotionService;
import com.lexmark.apollo.api.service.queries.PromotionQuery;
import com.lexmark.apollo.api.service.queries.PromotionQueryRowMapper;
import com.lexmark.apollo.api.util.ApolloServiceException;
import com.lexmark.apollo.api.util.ApolloServiceHelper;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PromotionServiceImpl implements PromotionService {
    
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
    public List<PromotionDto> retrievePromotions(String startDate,String endDate) throws ApolloServiceException {
        
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
        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("startDate", startDate);
        namedParameters.addValue("endDate", endDate);
        
        List<PromotionDto> promotionDtoList = null;
        
        try {
            promotionDtoList = namedParameterJdbcTemplate.query(
                    PromotionQuery.SELECT_PROMOTIONS_BETWEEN_DATES_QUERY, namedParameters, 
                    PromotionQueryRowMapper.PROMOTION_BETWEEN_DATES_ROW_MAPPER);
        } catch (DataAccessException e) {
            log.error("Error occured while fetching promotion data form: "+startDate+" to: "+endDate,e);
            throw new ApolloServiceException("Error occured while fetching promotion data form: "+startDate+" to: "+endDate,e);
        }
        
        if(promotionDtoList != null && promotionDtoList.isEmpty() == false){
            Collections.sort(promotionDtoList);
        }
        
        return promotionDtoList;
        
    }

}
