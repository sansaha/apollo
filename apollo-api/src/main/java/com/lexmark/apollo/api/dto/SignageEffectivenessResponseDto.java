package com.lexmark.apollo.api.dto;

import java.util.List;

import lombok.Data;

@Data
public class SignageEffectivenessResponseDto {
	
	private List<SignageDto> recentSignages;
	
	private List<SignageDto> allSignages;
	

}
