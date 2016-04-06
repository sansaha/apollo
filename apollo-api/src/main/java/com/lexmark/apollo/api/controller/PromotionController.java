package com.lexmark.apollo.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lexmark.apollo.api.dto.PromotionDto;
import com.lexmark.apollo.api.service.PromotionService;
import com.lexmark.apollo.api.util.ApolloServiceException;

@RestController
@CrossOrigin
public class PromotionController {
    
    @Autowired
    private PromotionService promotionService;
    
    @RequestMapping(value = "/promotions", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public List<PromotionDto> getPromitions(@RequestParam String startDate,@RequestParam String endDate){
        try {
            return promotionService.retrievePromotions(startDate, endDate);
        } catch (ApolloServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

}
