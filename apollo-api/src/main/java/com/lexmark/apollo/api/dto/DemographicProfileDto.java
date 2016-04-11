package com.lexmark.apollo.api.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class DemographicProfileDto implements Serializable{
	

	private static final long serialVersionUID = -5199137913948152997L;
	
    private Integer age;
    private Integer gender;
    private Integer count;
    private Integer locationId;
    private String description;
    private Date startDateTime;
    
}
