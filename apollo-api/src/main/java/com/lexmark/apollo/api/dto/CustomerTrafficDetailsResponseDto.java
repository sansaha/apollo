package com.lexmark.apollo.api.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lexmark.apollo.api.serializer.JsonDateTimeSerializer;

import lombok.Getter;
import lombok.Setter;

public class CustomerTrafficDetailsResponseDto {
    
    public static final String ITEM_ACTUAL_TRAFFIC = "Actual Traffic";
    public static final String ITEM_PROJECTED_TRAFFIC = "Projected Traffic";
    public static final String ITEM_TRAFFIC_DIFFERENCE = "Traffic Difference";
    
    
    @Getter
    @Setter
    private List<CustomerTrafficDetailsSegment> actualTraffics;
    
    @Getter
    @Setter
    private List<CustomerTrafficDetailsSegment> projectedTraffics;
    
    @Getter
    @Setter
    private List<CustomerTrafficDetailsSegment> trafficsDifferences;
    
    
    public void addTrafficsDifferenceSegment(CustomerTrafficDetailsSegment customerTrafficSegment){
        if(customerTrafficSegment != null ){
            if(trafficsDifferences == null){
                trafficsDifferences = new ArrayList<CustomerTrafficDetailsSegment>();
            }
            trafficsDifferences.add(customerTrafficSegment);
        }  
    }
    
    public void sortTrafficsDifferenceSegments(){
        if (trafficsDifferences != null && trafficsDifferences.isEmpty() == false){
            Collections.sort(trafficsDifferences);
        }
    }
    
    
    public static CustomerTrafficDetailsSegment createCustomerTrafficDetailsSegment(){
        return new CustomerTrafficDetailsResponseDto.CustomerTrafficDetailsSegment();
    }
    
    public static class CustomerTrafficDetailsSegment implements Comparable<CustomerTrafficDetailsSegment>{
        @Getter
        @Setter
        private String item;
        @Getter
        @Setter
        private Integer value;
        @Getter
        @Setter
        @JsonSerialize(using=JsonDateTimeSerializer.class)
        private Date date;
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((date == null) ? 0 : date.hashCode());
            return result;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            CustomerTrafficDetailsSegment other = (CustomerTrafficDetailsSegment) obj;
            if (date == null) {
                if (other.date != null)
                    return false;
            } else if (!date.equals(other.date))
                return false;
            return true;
        }
        
        @Override
        public int compareTo(CustomerTrafficDetailsSegment o) {
            return this.getDate().compareTo(o.getDate());
        }
        
        
        
    }

}
