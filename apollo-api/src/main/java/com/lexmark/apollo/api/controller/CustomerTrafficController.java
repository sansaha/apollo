package com.lexmark.apollo.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lexmark.apollo.api.dto.CustomerTrafficResponseDto;
import com.lexmark.apollo.api.service.CustomerTrafficService;
import com.lexmark.apollo.api.util.ApolloServiceException;

@RestController
@CrossOrigin
public class CustomerTrafficController {
    
    @Autowired
    private CustomerTrafficService customerTrafficService;
    
    @RequestMapping(value = "/customer-traffic", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public CustomerTrafficResponseDto getCustomerTraffic(@RequestParam String startDate,@RequestParam String endDate){
        
        CustomerTrafficResponseDto customerTrafficResponseDto = null;
        
        try {
            customerTrafficResponseDto = customerTrafficService.getCustomerTrafficData(startDate, endDate);
        } catch (ApolloServiceException e) {
            e.printStackTrace();
        }

        return customerTrafficResponseDto;
   }

}
