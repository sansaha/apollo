package com.lexmark.apollo.api.service;

import com.lexmark.apollo.api.dto.SalesResponseDto;
import com.lexmark.apollo.api.util.ApolloServiceException;

public interface SalesService {
    
    public SalesResponseDto getSalesData(String startDate,String endDate) throws ApolloServiceException;

}
