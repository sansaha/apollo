package com.lexmark.apollo.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lexmark.apollo.api.serializer.CustomerDemographicProfileSerializer;

import lombok.Data;
import lombok.Getter;

public class SignageEffectivenessResponseDto {
	
	@Getter
	private List<SignageEffectiveness> signages;
	
	public void addSignage(SignageEffectiveness signageEffectiveness){
		if(signageEffectiveness != null){
			if(signages == null){
				signages = new ArrayList<SignageEffectiveness>();
			}
			signages.add(signageEffectiveness);
		}
	}
	
	public static SignageEffectiveness createSignageEffectiveness(){
		return new SignageEffectivenessResponseDto.SignageEffectiveness();
	}
	
	@Data
	public static class SignageEffectiveness {
		private String name;
		private int trafficDuringSignage;
		private int trafficBeforeSignage;
		@JsonSerialize(using = CustomerDemographicProfileSerializer.class)
		private CustomerDemographicProfileDto customerDemographicDuringSignage;
		@JsonSerialize(using = CustomerDemographicProfileSerializer.class)
		private CustomerDemographicProfileDto customerDemographicAfterSignage;
		
	}

}
