package com.lexmark.apollo.api.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lexmark.apollo.api.serializer.CustomerDemographicProfileSerializer;
import com.lexmark.apollo.api.serializer.JsonDateSerializer;

import lombok.Getter;
import lombok.Setter;

public class CustomerTrafficResponseDto {
    
    private static final String ITEM_ACTUAL_TRAFFIC = "Actual Traffic";
    private static final String ITEM_PROJECTED_TRAFFIC = "Projected Traffic";
    private static final String ITEM_TRAFFIC_DIFFERENCE = "Traffic Difference";
    
    @Getter
    @Setter
    @JsonIgnore
    private List<CustomerTraffic> customerTraffics;
    
    @Getter
    private List<CustomerTrafficSegment> actualTraffics;
    
    @Getter
    private List<CustomerTrafficSegment> projectedTraffics;
    
    @Getter
    private List<CustomerTrafficSegment> trafficsDifferences;
    
    @Getter
    @Setter
    @JsonSerialize(using = CustomerDemographicProfileSerializer.class)
    private CustomerDemographicProfileDto customerDemographicProfile;
    
    public void addCustomerTraffic(CustomerTraffic customerTraffic){
        if(customerTraffic != null ){
            if(customerTraffics == null){
                customerTraffics = new ArrayList<CustomerTrafficResponseDto.CustomerTraffic>();
            }
            customerTraffics.add(customerTraffic);
        }  
    }
    
    public void sortCustomerTraffic(){
        if (customerTraffics != null && customerTraffics.isEmpty() == false){
            Collections.sort(customerTraffics);
        }
    }
    
    public void populateTrafficSegments(){
        if (customerTraffics != null && customerTraffics.isEmpty() == false){
            List<CustomerTrafficSegment> actual = new ArrayList<CustomerTrafficSegment>(customerTraffics.size());
            List<CustomerTrafficSegment> projected = new ArrayList<CustomerTrafficSegment>(customerTraffics.size());
            List<CustomerTrafficSegment> difference = new ArrayList<CustomerTrafficSegment>(customerTraffics.size());
            for(CustomerTraffic customerTraffic : customerTraffics){
                CustomerTrafficSegment segmentActual = new CustomerTrafficResponseDto.CustomerTrafficSegment();
                CustomerTrafficSegment segmentProjected = new CustomerTrafficResponseDto.CustomerTrafficSegment();
                CustomerTrafficSegment segmentDifference = new CustomerTrafficResponseDto.CustomerTrafficSegment();
                
                segmentActual.setDate(customerTraffic.getDate());
                segmentProjected.setDate(customerTraffic.getDate());
                segmentDifference.setDate(customerTraffic.getDate());
                
                segmentActual.setItem(ITEM_ACTUAL_TRAFFIC);
                segmentProjected.setItem(ITEM_PROJECTED_TRAFFIC);
                segmentDifference.setItem(ITEM_TRAFFIC_DIFFERENCE);
                
                segmentActual.setValue(customerTraffic.getActualTraffic());
                segmentProjected.setValue(customerTraffic.getProjectedTraffic());
                segmentDifference.setValue(customerTraffic.getTrafficDifference());
                
                actual.add(segmentActual);
                projected.add(segmentProjected);
                difference.add(segmentDifference);
            }
            
           this.actualTraffics = actual;
           this.projectedTraffics = projected;
           this.trafficsDifferences = difference;
        }
    }
    
    public static CustomerTraffic createCustomerTrafic(){
        return new CustomerTrafficResponseDto.CustomerTraffic();
    }
    
    public static class CustomerTraffic implements Comparable<CustomerTraffic>{
        
        @Getter
        @Setter
        @JsonSerialize(using=JsonDateSerializer.class)
        private Date date;
        @Getter
        @Setter
        private Integer actualTraffic;
        @Getter
        @Setter
        private Integer projectedTraffic = 0;
        
        
        public int compareTo(CustomerTraffic o) {
            return this.getDate().compareTo(o.getDate());
        }

        public Integer getTrafficDifference() {
            Integer trafficDifference = null;
            if(actualTraffic != null && projectedTraffic != null){
                trafficDifference = actualTraffic - projectedTraffic;
            }
            return trafficDifference;
        }
        
        
    }
    
    public static class CustomerTrafficSegment {
        @Getter
        @Setter
        private String item;
        @Getter
        @Setter
        private Integer value;
        @Getter
        @Setter
        @JsonSerialize(using=JsonDateSerializer.class)
        private Date date;
    }
        

}
