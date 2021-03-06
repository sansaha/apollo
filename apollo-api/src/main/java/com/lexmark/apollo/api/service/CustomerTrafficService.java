package com.lexmark.apollo.api.service;

import com.lexmark.apollo.api.dto.CustomerTrafficResponseDto;
import com.lexmark.apollo.api.util.ApolloServiceException;

public interface CustomerTrafficService {
    
    public CustomerTrafficResponseDto getCustomerTrafficData(String fromDate, String toDate) throws ApolloServiceException;

}
