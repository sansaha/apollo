package com.lexmark.apollo.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lexmark.apollo.api.dto.SignageEffectivenessResponseDto;
import com.lexmark.apollo.api.service.SignageService;
import com.lexmark.apollo.api.util.ApolloServiceException;

@RestController
public class SignageController {
	
	@Autowired
    private SignageService signageService;
    
    @RequestMapping(value = "/signage-effectiveness", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public SignageEffectivenessResponseDto getSalesReport(@RequestParam String startDate,@RequestParam String endDate,@RequestParam Integer minWatchTime){

        try {
            return signageService.getSignageEffectivenessData(startDate, endDate, minWatchTime);
        } catch (ApolloServiceException e) {
            e.printStackTrace();
        }
        return null;
   }

}
