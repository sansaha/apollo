package com.lexmark.apollo.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lexmark.apollo.api.dto.SalesComparisonDto;
import com.lexmark.apollo.api.dto.SalesDto;
import com.lexmark.apollo.api.service.SalesService;
import com.lexmark.apollo.api.util.ApolloServiceException;

@RestController
public class SalesController {
    
    @Autowired
    private SalesService salesService;
    
    @RequestMapping(value = "/sales", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public List<SalesDto> getSalesReport(@RequestParam String startDate,@RequestParam String endDate){

        try {
            return salesService.getSalesData(startDate, endDate);
        } catch (ApolloServiceException e) {
            e.printStackTrace();
        }
        return null;
   }

    @RequestMapping(value = "/sales/grosssales/topn/{topN}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public List<SalesDto> getGrossSalesTopNReport(@RequestParam String startDate,@RequestParam String endDate, @PathVariable int topN){

        try {
            return salesService.getGrossSalesTopNData(startDate, endDate, topN);
        } catch (ApolloServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/sales/grosssales/mostchanged/{mostChanged}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public List<SalesDto> getGrossSalesMostChangedReport(@RequestParam String startDate,@RequestParam String endDate, @PathVariable int mostChanged){

        try {
            return salesService.getGrossSalesMostChangedData(startDate, endDate, mostChanged);
        } catch (ApolloServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/sales/quantitysold/topn/{topN}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public List<SalesDto> getQuantitySoldTopNReport(@RequestParam String startDate,@RequestParam String endDate, @PathVariable int topN){

        try {
            return salesService.getQuantitySoldTopNData(startDate, endDate, topN);
        } catch (ApolloServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/sales/quantitysold/mostchanged/{mostChanged}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public List<SalesDto> getQuantitySoldMostChangedReport(@RequestParam String startDate,@RequestParam String endDate, @PathVariable int mostChanged){

        try {
            return salesService.getQuantitySoldMostChangedData(startDate, endDate, mostChanged);
        } catch (ApolloServiceException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/sales/promotion-effectiveness", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public SalesComparisonDto getPromotionEffictiveness(@RequestParam String promotionItem,@RequestParam String startDate,@RequestParam String endDate,@RequestParam String promoStartDate,@RequestParam (required = false) String promoEndDate){

        try {
            return salesService.getItemSalesEffectivenessForPromotion(promotionItem, startDate, endDate, promoStartDate, promoEndDate);
        } catch (ApolloServiceException e) {
            e.printStackTrace();
        }
    	
        return null;
    }
}
