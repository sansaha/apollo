package com.lexmark.apollo.api.service.queries;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.lexmark.apollo.api.dto.SalesDto;

public class SalesQueryRowMapper {
    
    public static final RowMapper<SalesDto> SALES_BETWEEN_DATES_ROW_MAPPER = new RowMapper<SalesDto>(){

        @Override
        public SalesDto mapRow(ResultSet rs, int arg1) throws SQLException {
            SalesDto salesDto = new SalesDto();
            salesDto.setItem(rs.getString(1));
            salesDto.setFamilyGroup(rs.getString(2));
            salesDto.setMajorGroup(rs.getString(3));
            salesDto.setGrossSales(rs.getDouble(4));
            salesDto.setItemDiscounts(rs.getDouble(5));
            salesDto.setSalesLessItemDiscounts(rs.getDouble(6));
            salesDto.setAvaragePrice(rs.getDouble(7));
            salesDto.setQuantitySold(rs.getInt(8));

            return salesDto;
        }
        
    };
    
    public static final RowMapper<SalesDto> ITEM_SALES_DETAILS_BETWEEN_DATES_ROW_MAPPER = new RowMapper<SalesDto>(){

        @Override
        public SalesDto mapRow(ResultSet rs, int arg1) throws SQLException {
            SalesDto salesDto = new SalesDto();
            salesDto.setItem(rs.getString(1));
            salesDto.setGrossSales(rs.getDouble(2));
            salesDto.setQuantitySold(rs.getInt(3));
            salesDto.setDate(rs.getString(4));

            return salesDto;
        }
        
    };
    
    public static final RowMapper<SalesDto> GROSS_SALES_TOP_N_BETWEEN_DATES_ROW_MAPPER = new RowMapper<SalesDto>(){

        @Override
        public SalesDto mapRow(ResultSet rs, int arg1) throws SQLException {
            SalesDto salesDto = new SalesDto();
            salesDto.setItem(rs.getString(1));
            salesDto.setGrossSales(rs.getDouble(2));
            salesDto.setDate(rs.getString(3));
            return salesDto;
        }
        
    };
    
    public static final RowMapper<SalesDto> GROSS_SALES_MOST_CHANGED_BETWEEN_DATES_ROW_MAPPER = new RowMapper<SalesDto>(){

        @Override
        public SalesDto mapRow(ResultSet rs, int arg1) throws SQLException {
            SalesDto salesDto = new SalesDto();
            salesDto.setItem(rs.getString(1));
            salesDto.setGrossSales(rs.getDouble(2));
            salesDto.setDate(rs.getString(3));
            return salesDto;
        }
        
    };    
    
    public static final RowMapper<SalesDto> QUANTITY_SOLD_TOP_N_BETWEEN_DATES_ROW_MAPPER = new RowMapper<SalesDto>(){

        @Override
        public SalesDto mapRow(ResultSet rs, int arg1) throws SQLException {
            SalesDto salesDto = new SalesDto();
            salesDto.setItem(rs.getString(1));
            salesDto.setQuantitySold(rs.getInt(2));
            salesDto.setDate(rs.getString(3));
            return salesDto;
        }
        
    };
    
    public static final RowMapper<SalesDto> QUANTITY_SOLD_MOST_CHANGED_BETWEEN_DATES_ROW_MAPPER = new RowMapper<SalesDto>(){

        @Override
        public SalesDto mapRow(ResultSet rs, int arg1) throws SQLException {
            SalesDto salesDto = new SalesDto();
            salesDto.setItem(rs.getString(1));
            salesDto.setQuantitySold(rs.getInt(2));
            salesDto.setDate(rs.getString(3));
            return salesDto;
        }
        
    };

}
