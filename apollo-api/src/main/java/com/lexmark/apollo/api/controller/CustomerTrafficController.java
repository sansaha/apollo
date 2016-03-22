package com.lexmark.apollo.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lexmark.apollo.api.dto.CustomerTrafficResponseDto;
import com.lexmark.apollo.api.dto.CustomerTrafficResponseDto.CustomerTraffic;
import com.lexmark.apollo.api.service.CustomerTrafficService;
import com.lexmark.apollo.api.util.ApolloServiceException;

@RestController
public class CustomerTrafficController {
    
    @Autowired
    private CustomerTrafficService customerTrafficService;
    
    @RequestMapping(value = "/customer-traffic", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public CustomerTrafficResponseDto getCustomerTraffic(@RequestParam String startDate,@RequestParam String endDate){
        
        CustomerTrafficResponseDto customerTrafficResponseDto = null;
        
        try {
            customerTrafficResponseDto = customerTrafficService.getCustomerTrafficData(startDate, endDate);
        } catch (ApolloServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //return populateCustomerTrafficResponseDto();
        return customerTrafficResponseDto;
   }
    
    private CustomerTrafficResponseDto populateCustomerTrafficResponseDto(){
        
        CustomerTrafficResponseDto customerTrafficResponseDto = new CustomerTrafficResponseDto();
        
        CustomerTraffic customerTrafic1 = CustomerTrafficResponseDto.createCustomerTrafic();
        CustomerTraffic customerTrafic2 = CustomerTrafficResponseDto.createCustomerTrafic();
        CustomerTraffic customerTrafic3 = CustomerTrafficResponseDto.createCustomerTrafic();
        CustomerTraffic customerTrafic4 = CustomerTrafficResponseDto.createCustomerTrafic();
        CustomerTraffic customerTrafic5 = CustomerTrafficResponseDto.createCustomerTrafic();
        CustomerTraffic customerTrafic6 = CustomerTrafficResponseDto.createCustomerTrafic();
        CustomerTraffic customerTrafic7 = CustomerTrafficResponseDto.createCustomerTrafic();
        CustomerTraffic customerTrafic8 = CustomerTrafficResponseDto.createCustomerTrafic();
        CustomerTraffic customerTrafic9 = CustomerTrafficResponseDto.createCustomerTrafic();
        CustomerTraffic customerTrafic10 = CustomerTrafficResponseDto.createCustomerTrafic();
        CustomerTraffic customerTrafic11 = CustomerTrafficResponseDto.createCustomerTrafic();
        CustomerTraffic customerTrafic12 = CustomerTrafficResponseDto.createCustomerTrafic();
        CustomerTraffic customerTrafic13 = CustomerTrafficResponseDto.createCustomerTrafic();
        CustomerTraffic customerTrafic14 = CustomerTrafficResponseDto.createCustomerTrafic();
        CustomerTraffic customerTrafic15 = CustomerTrafficResponseDto.createCustomerTrafic();
        
        customerTrafic1.setActualTraffic(676);
        customerTrafic1.setProjectedTraffic(700);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_MONTH, -17);
        customerTrafic1.setDate(calendar1.getTime());
        
        customerTrafic2.setActualTraffic(677);
        customerTrafic2.setProjectedTraffic(700);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_MONTH, -16);
        customerTrafic2.setDate(calendar2.getTime());
        
        customerTrafic3.setActualTraffic(760);
        customerTrafic3.setProjectedTraffic(850);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.add(Calendar.DAY_OF_MONTH, -15);
        customerTrafic3.setDate(calendar3.getTime());
        
        customerTrafic4.setActualTraffic(608);
        customerTrafic4.setProjectedTraffic(610);
        Calendar calendar4 = Calendar.getInstance();
        calendar4.add(Calendar.DAY_OF_MONTH, -14);
        customerTrafic4.setDate(calendar4.getTime());
        
        customerTrafic5.setActualTraffic(0);
        customerTrafic5.setProjectedTraffic(10);
        Calendar calendar5 = Calendar.getInstance();
        calendar5.add(Calendar.DAY_OF_MONTH, -13);
        customerTrafic5.setDate(calendar5.getTime());
        
        customerTrafic6.setActualTraffic(18);
        customerTrafic6.setProjectedTraffic(25);
        Calendar calendar6 = Calendar.getInstance();
        calendar6.add(Calendar.DAY_OF_MONTH, -12);
        customerTrafic6.setDate(calendar6.getTime());
        
        customerTrafic7.setActualTraffic(658);
        customerTrafic7.setProjectedTraffic(750);
        Calendar calendar7 = Calendar.getInstance();
        calendar7.add(Calendar.DAY_OF_MONTH, -11);
        customerTrafic7.setDate(calendar7.getTime());
        
        customerTrafic8.setActualTraffic(652);
        customerTrafic8.setProjectedTraffic(800);
        Calendar calendar8 = Calendar.getInstance();
        calendar8.add(Calendar.DAY_OF_MONTH, -10);
        customerTrafic8.setDate(calendar8.getTime());
        
        customerTrafic9.setActualTraffic(662);
        customerTrafic9.setProjectedTraffic(750);
        Calendar calendar9 = Calendar.getInstance();
        calendar9.add(Calendar.DAY_OF_MONTH, -9);
        customerTrafic9.setDate(calendar9.getTime());
        
        customerTrafic10.setActualTraffic(677);
        customerTrafic10.setProjectedTraffic(650);
        Calendar calendar10 = Calendar.getInstance();
        calendar10.add(Calendar.DAY_OF_MONTH, -8);
        customerTrafic10.setDate(calendar10.getTime());
        
        customerTrafic11.setActualTraffic(215);
        customerTrafic11.setProjectedTraffic(275);
        Calendar calendar11 = Calendar.getInstance();
        calendar11.add(Calendar.DAY_OF_MONTH, -7);
        customerTrafic11.setDate(calendar11.getTime());
        
        customerTrafic12.setActualTraffic(2);
        customerTrafic12.setProjectedTraffic(25);
        Calendar calendar12 = Calendar.getInstance();
        calendar12.add(Calendar.DAY_OF_MONTH, -6);
        customerTrafic12.setDate(calendar12.getTime());
        
        customerTrafic13.setActualTraffic(0);
        customerTrafic13.setProjectedTraffic(10);
        Calendar calendar13 = Calendar.getInstance();
        calendar13.add(Calendar.DAY_OF_MONTH, -5);
        customerTrafic13.setDate(calendar13.getTime());
        
        customerTrafic14.setActualTraffic(311);
        customerTrafic14.setProjectedTraffic(450);
        Calendar calendar14 = Calendar.getInstance();
        calendar14.add(Calendar.DAY_OF_MONTH, -4);
        customerTrafic14.setDate(calendar14.getTime());
        
        customerTrafic15.setActualTraffic(275);
        customerTrafic15.setProjectedTraffic(400);
        Calendar calendar15 = Calendar.getInstance();
        calendar15.add(Calendar.DAY_OF_MONTH, -3);
        customerTrafic15.setDate(calendar15.getTime());
        
        customerTrafficResponseDto.addCustomerTraffic(customerTrafic1);
        customerTrafficResponseDto.addCustomerTraffic(customerTrafic2);
        customerTrafficResponseDto.addCustomerTraffic(customerTrafic3);
        customerTrafficResponseDto.addCustomerTraffic(customerTrafic4);
        customerTrafficResponseDto.addCustomerTraffic(customerTrafic5);
        customerTrafficResponseDto.addCustomerTraffic(customerTrafic6);
        customerTrafficResponseDto.addCustomerTraffic(customerTrafic7);
        customerTrafficResponseDto.addCustomerTraffic(customerTrafic8);
        customerTrafficResponseDto.addCustomerTraffic(customerTrafic9);
        customerTrafficResponseDto.addCustomerTraffic(customerTrafic10);
        customerTrafficResponseDto.addCustomerTraffic(customerTrafic11);
        customerTrafficResponseDto.addCustomerTraffic(customerTrafic12);
        customerTrafficResponseDto.addCustomerTraffic(customerTrafic13);
        customerTrafficResponseDto.addCustomerTraffic(customerTrafic14);
        customerTrafficResponseDto.addCustomerTraffic(customerTrafic15);
        
        customerTrafficResponseDto.sortCustomerTraffic();
        
        /*CustomerDemographicProfile customerDemographicProfile = CustomerTrafficResponseDto.createCustomerDemographicProfile();
        customerDemographicProfile.setAdultFemaleCount(9643);
        customerDemographicProfile.setAdultMaleCount(27363);
        customerDemographicProfile.setChildCount(2373);
        customerDemographicProfile.setSeniorCount(642);
        customerDemographicProfile.setUnknownCount(3231);
        customerDemographicProfile.setYoungFemaleCount(10489);
        customerDemographicProfile.setYoungMaleCount(22706);
        customerDemographicProfile.setYouthCount(0);
        
        customerTrafficResponseDto.setCustomerDemographicProfile(customerDemographicProfile);*/
        
        return customerTrafficResponseDto;
    }

}
