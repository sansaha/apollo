package com.lexmark.apollo.api.service.queries;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.lexmark.apollo.api.dto.CustomerTrafficResponseDto;
import com.lexmark.apollo.api.dto.DemographicProfileDto;

public class CustomerTrafficQueryRowMapper {
        
    public static final RowMapper<CustomerTrafficResponseDto.CustomerTraffic> CUSTOMER_TRAFFIC_ACTUAL_ROW_MAPPER = new RowMapper<CustomerTrafficResponseDto.CustomerTraffic>(){

        @Override
        public CustomerTrafficResponseDto.CustomerTraffic mapRow(ResultSet rs, int arg1) throws SQLException {
            CustomerTrafficResponseDto.CustomerTraffic customerTrafficDto = CustomerTrafficResponseDto.createCustomerTrafic();
            customerTrafficDto.setDate(rs.getDate(1));
            customerTrafficDto.setActualTraffic(rs.getInt(2));
            return customerTrafficDto;
        }
        
    };
    
    public static final RowMapper<CustomerTrafficResponseDto.CustomerTraffic> CUSTOMER_TRAFFIC_PROJECTED_ROW_MAPPER = new RowMapper<CustomerTrafficResponseDto.CustomerTraffic>(){

        @Override
        public CustomerTrafficResponseDto.CustomerTraffic mapRow(ResultSet rs, int arg1) throws SQLException {
            CustomerTrafficResponseDto.CustomerTraffic customerTrafficDto = CustomerTrafficResponseDto.createCustomerTrafic();
            customerTrafficDto.setDate(rs.getDate(1));
            customerTrafficDto.setProjectedTraffic(rs.getInt(2));
            return customerTrafficDto;
        }
        
    };
    
    public static final RowMapper<DemographicProfileDto> DEMOGRAPHIC_PROFILE_ROW_MAPPER = new RowMapper<DemographicProfileDto>(){

        @Override
        public DemographicProfileDto mapRow(ResultSet rs, int arg1) throws SQLException {
        	DemographicProfileDto demographicProfileDto = new DemographicProfileDto();
            demographicProfileDto.setGender(rs.getInt(1));
            demographicProfileDto.setAge(rs.getInt(2));
            demographicProfileDto.setCount(rs.getInt(3));
            return demographicProfileDto;
        }
        
    };
    
    /*public static final RowMapper<DemographicProfileDto> SIGNAGE_SPECIFIC_CUSTOMER_DEMOGRAPHY_ROW_MAPPER = new RowMapper<DemographicProfileDto>(){

        @Override
        public DemographicProfileDto mapRow(ResultSet rs, int arg1) throws SQLException {
        	DemographicProfileDto demographicProfileDto = new DemographicProfileDto();
        	demographicProfileDto.setDescription(rs.getString(1));
        	demographicProfileDto.setLocationId(rs.getInt(2));
        	demographicProfileDto.setAge(rs.getInt(3));
            demographicProfileDto.setGender(rs.getInt(4));
            demographicProfileDto.setCount(rs.getInt(5));
            return demographicProfileDto;
        }
        
    };*/
    
    public static final RowMapper<DemographicProfileDto> CUSTOMER_BETWEEN_DATES_AND_MIN_ATTENTION_TIME_ROW_MAPPER = new RowMapper<DemographicProfileDto>(){
        @Override
        public DemographicProfileDto mapRow(ResultSet rs, int arg1) throws SQLException {
        	DemographicProfileDto demographicProfileDto = new DemographicProfileDto();
        	demographicProfileDto.setLocationId(rs.getInt(1));
        	demographicProfileDto.setGender(rs.getInt(2));
        	demographicProfileDto.setAge(rs.getInt(3));
        	demographicProfileDto.setStartDateTime(rs.getDate(4));
            demographicProfileDto.setCount(rs.getInt(5));
            return demographicProfileDto;
        }
        
    };

}
