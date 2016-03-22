package com.lexmark.apollo.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lexmark.apollo.api.dto.SalesResponseDto;
import com.lexmark.apollo.api.service.SalesService;
import com.lexmark.apollo.api.util.ApolloServiceException;

@RestController
public class SalesController {
    
    @Autowired
    private SalesService salesService;
    
    @RequestMapping(value = "/sales-report", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public SalesResponseDto getSalesReport(@RequestParam String startDate,@RequestParam String endDate){

        try {
            return salesService.getSalesData(startDate, endDate);
        } catch (ApolloServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
   }
    
   

}
