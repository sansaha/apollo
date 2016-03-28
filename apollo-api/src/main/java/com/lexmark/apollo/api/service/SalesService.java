package com.lexmark.apollo.api.service;

import java.util.List;

import com.lexmark.apollo.api.dto.SalesDto;
import com.lexmark.apollo.api.util.ApolloServiceException;

public interface SalesService {

    List<SalesDto> getSalesData(String startDate, String endDate) throws ApolloServiceException;
    List<SalesDto> getGrossSalesTopNData(String startDate, String endDate, int topN) throws ApolloServiceException;
    List<SalesDto> getGrossSalesMostChangedData(String startDate, String endDate, int mostChanged) throws ApolloServiceException;
    List<SalesDto> getQuantitySoldTopNData(String startDate, String endDate, int topN) throws ApolloServiceException;
    List<SalesDto> getQuantitySoldMostChangedData(String startDate, String endDate, int mostChanged) throws ApolloServiceException;
    
}
