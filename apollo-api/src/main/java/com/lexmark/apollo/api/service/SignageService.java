package com.lexmark.apollo.api.service;

import com.lexmark.apollo.api.dto.SignageEffectivenessResponseDto;
import com.lexmark.apollo.api.util.ApolloServiceException;

public interface SignageService {
	
	public SignageEffectivenessResponseDto getSignageEffectivenessData(String fromDate, String toDate, int minWatchTime)
			throws ApolloServiceException;

}
