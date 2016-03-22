package com.lexmark.apollo.api.service.queries;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.lexmark.apollo.api.dto.CustomerTrafficResponseDto;

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
    
    public static final RowMapper<CustomerTrafficResponseDto.DemographicProfile> DEMOGRAPHIC_PROFILE_ROW_MAPPER = new RowMapper<CustomerTrafficResponseDto.DemographicProfile>(){

        @Override
        public CustomerTrafficResponseDto.DemographicProfile mapRow(ResultSet rs, int arg1) throws SQLException {
            CustomerTrafficResponseDto.DemographicProfile demographicProfileDto = CustomerTrafficResponseDto.createDemographicProfile();
            demographicProfileDto.setGender(rs.getInt(1));
            demographicProfileDto.setAge(rs.getInt(2));
            demographicProfileDto.setCount(rs.getInt(3));
            return demographicProfileDto;
        }
        
    };

}
