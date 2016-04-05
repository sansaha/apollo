package com.lexmark.apollo.api.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SignageDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8534977083811301886L;
	
	private Integer locationId;
	private String description;
	private Date startDateTime;
	private Date endDateTime;
	private Integer recentViewerCount;
	private Integer averageViewerCount;
	
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
