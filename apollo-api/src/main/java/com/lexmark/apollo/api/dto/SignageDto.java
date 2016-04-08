package com.lexmark.apollo.api.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lexmark.apollo.api.serializer.CustomerDemographicProfileSerializer;

import lombok.Data;

@Data
public class SignageDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8534977083811301886L;
	
	@JsonIgnore
	private Integer locationId;
	@JsonIgnore
	private Date startDateTime;
	@JsonIgnore
	private Date endDateTime;
	private String description;
	private Integer viewerCount;
	
	@JsonSerialize(using = CustomerDemographicProfileSerializer.class)
	private CustomerDemographicProfileDto customersDemographicProfile;
	
	public SignageDto copySignage(){
	    SignageDto signageDto = new SignageDto();
	    signageDto.setDescription(this.description);
	    signageDto.setEndDateTime(this.endDateTime);
	    signageDto.setStartDateTime(this.startDateTime);
	    signageDto.setLocationId(this.locationId);
	    
	    return signageDto;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SignageDto other = (SignageDto) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endDateTime == null) {
			if (other.endDateTime != null)
				return false;
		} else if (!endDateTime.equals(other.endDateTime))
			return false;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		if (startDateTime == null) {
			if (other.startDateTime != null)
				return false;
		} else if (!startDateTime.equals(other.startDateTime))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endDateTime == null) ? 0 : endDateTime.hashCode());
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
		result = prime * result + ((startDateTime == null) ? 0 : startDateTime.hashCode());
		return result;
	}
	
	

}
