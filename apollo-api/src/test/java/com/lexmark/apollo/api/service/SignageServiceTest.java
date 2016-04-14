package com.lexmark.apollo.api.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.lexmark.apollo.api.ApolloApiApplication;
import com.lexmark.apollo.api.dto.SignageEffectivenessResponseDto;
import com.lexmark.apollo.api.util.ApolloServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApolloApiApplication.class)
@WebAppConfiguration
@Ignore
public class SignageServiceTest {
	
	@Autowired
	private SignageService signageService;
	
	@Test
	public void testSgnageEffectiveness(){
		
		SignageEffectivenessResponseDto responseDto = null;
		try {
			responseDto = signageService.getSignageEffectivenessData("2015-09-15", "2016-03-21", 10);
		} catch (ApolloServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("responseDto:: "+responseDto);
		Assert.assertNotNull(responseDto);
	}

}
