package com.lexmark.apollo.api.service.queries;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.lexmark.apollo.api.dto.SignageDto;

public class SignageQueryRowMapper {
	
    public static final RowMapper<SignageDto> SIGNAGE_BETWEEN_DATES_ROW_MAPPER = new RowMapper<SignageDto>(){

        @Override
        public SignageDto mapRow(ResultSet rs, int arg1) throws SQLException {
        	SignageDto signageDto = new SignageDto();
        	signageDto.setLocationId(rs.getInt(1));
        	signageDto.setDescription(rs.getString(2));
        	signageDto.setStartDateTime(rs.getDate(3));
            return signageDto;
        }
        
    };

}
