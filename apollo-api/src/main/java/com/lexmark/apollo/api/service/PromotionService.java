package com.lexmark.apollo.api.service;

import java.util.List;

import com.lexmark.apollo.api.dto.PromotionDto;
import com.lexmark.apollo.api.util.ApolloServiceException;

public interface PromotionService {
    
    public List<PromotionDto> retrievePromotions(String startDate,String endDate) throws ApolloServiceException;

}
