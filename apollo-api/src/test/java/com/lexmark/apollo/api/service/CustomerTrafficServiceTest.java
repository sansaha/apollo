package com.lexmark.apollo.api.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.lexmark.apollo.api.ApolloApiApplication;
import com.lexmark.apollo.api.dto.CustomerTrafficResponseDto;
import com.lexmark.apollo.api.dto.CustomerTrafficResponseDto.CustomerTraffic;
import com.lexmark.apollo.api.util.ApolloServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApolloApiApplication.class)
@WebAppConfiguration
@Ignore
public class CustomerTrafficServiceTest {
    
    @Autowired
    private CustomerTrafficService customerTrafficService;
    
    @Test
    public void testCustomerTrafficActualData(){
        
        CustomerTrafficResponseDto customerTrafficResponseDto = null;
        try {
            customerTrafficResponseDto = customerTrafficService.getCustomerTrafficData("2015-09-15", "2016-03-21");
        } catch (ApolloServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Total customertrafficDataList:: "+customerTrafficResponseDto.getCustomerTraffics().size());
        System.out.println(customerTrafficResponseDto.getCustomerDemographicProfile());
        for(CustomerTraffic customerTraffic : customerTrafficResponseDto.getCustomerTraffics()){
            System.out.println(customerTraffic.getDate()+","+customerTraffic.getActualTraffic()+","+customerTraffic.getProjectedTraffic());
        }
        
        
    }

}
