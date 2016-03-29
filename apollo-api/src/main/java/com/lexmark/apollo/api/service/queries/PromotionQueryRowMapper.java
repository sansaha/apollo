package com.lexmark.apollo.api.service.queries;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.lexmark.apollo.api.dto.PromotionDto;
import com.lexmark.apollo.api.util.ApolloConstant;

public class PromotionQueryRowMapper {
    
    public static final RowMapper<PromotionDto> PROMOTION_BETWEEN_DATES_ROW_MAPPER = new RowMapper<PromotionDto>(){

        @Override
        public PromotionDto mapRow(ResultSet rs, int arg1) throws SQLException {
            PromotionDto promotionDto = new PromotionDto();
            promotionDto.setType(ApolloConstant.DEFAULT_EMPTY_STRING);
            promotionDto.setName(rs.getString(2));
            promotionDto.setItem(rs.getString(3));
            promotionDto.setStartDate(rs.getDate(5));
            promotionDto.setEndDate(rs.getDate(6));
            
            return promotionDto;
        }
        
    };

}
