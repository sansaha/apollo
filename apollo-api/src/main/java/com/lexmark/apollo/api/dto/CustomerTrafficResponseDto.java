package com.lexmark.apollo.api.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lexmark.apollo.api.serializer.CustomerDemographicProfileSerializer;
import com.lexmark.apollo.api.serializer.JsonDateSerializer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private CustomerDemographicProfile customerDemographicProfile;
    
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
    
    public static CustomerDemographicProfile createCustomerDemographicProfile(List<DemographicProfile> demographicProfiles){
        return new CustomerTrafficResponseDto.CustomerDemographicProfile(demographicProfiles);
    }
    
    @ToString
    public static class CustomerDemographicProfile {
        
        //0: unknown, 1: child, 2: young adult, 3: adult, 4: senior
        //0 - unknown, 1 - male, 2 - female
        
        public CustomerDemographicProfile(List<DemographicProfile> demographicProfiles){
            
            for(DemographicProfile demographicProfile : demographicProfiles){
                if(demographicProfile.getAge() == 0 || (demographicProfile.getAge() == 3 && demographicProfile.getGender() == 0)){
                    Integer unknownCount = valueMap.get(CustmerDemographyEnum.UNKNOWN);
                    if(unknownCount == null){
                        unknownCount = 0;
                    }
                    unknownCount = unknownCount + demographicProfile.getCount();
                    valueMap.put(CustmerDemographyEnum.UNKNOWN, unknownCount);
                    continue;
                }
                if(demographicProfile.getAge() == 1){
                    Integer childCount = valueMap.get(CustmerDemographyEnum.CHILDREN);
                    if(childCount == null){
                        childCount = 0;
                    }
                    childCount = childCount + demographicProfile.getCount();
                    valueMap.put(CustmerDemographyEnum.CHILDREN, childCount);
                    continue;
                }
                if(demographicProfile.getAge() == 2 && demographicProfile.getGender() == 0){
                    Integer youthCount = demographicProfile.getCount();
                    valueMap.put(CustmerDemographyEnum.YOUTH, youthCount);
                    continue;
                }
                if(demographicProfile.getAge() == 2 && demographicProfile.getGender() == 1){
                    Integer youngMaleCount = demographicProfile.getCount();
                    valueMap.put(CustmerDemographyEnum.YOUNG_MALE, youngMaleCount);
                    continue;
                }
                if(demographicProfile.getAge() == 2 && demographicProfile.getGender() == 2){
                    Integer youngFemaleCount = demographicProfile.getCount();
                    valueMap.put(CustmerDemographyEnum.YOUNG_FEMALE, youngFemaleCount);
                    continue;
                }
                
                if(demographicProfile.getAge() == 3 && demographicProfile.getGender() == 1){
                    Integer adultMaleCount = demographicProfile.getCount();
                    valueMap.put(CustmerDemographyEnum.ADULT_MALE, adultMaleCount);
                    continue;
                }
                if(demographicProfile.getAge() == 3 && demographicProfile.getGender() == 2){
                    Integer adultFemaleCount = demographicProfile.getCount();
                    valueMap.put(CustmerDemographyEnum.ADULT_FEMALE, adultFemaleCount);
                    continue;
                }
                if(demographicProfile.getAge() == 4){
                    Integer seniorCount = valueMap.get(CustmerDemographyEnum.SENIOR);
                    if(seniorCount == null){
                        seniorCount = 0;
                    }
                    seniorCount = seniorCount + demographicProfile.getCount();
                    valueMap.put(CustmerDemographyEnum.SENIOR, seniorCount);
                }
            }
        }

        
      /*  private Integer unknownCount = 0;//(0,*) || (3,0)
        
        private Integer childCount = 0;//(1,*)
        
        private Integer youthCount = 0;//(2,0)
        
        private Integer youngMaleCount = 0;//(2,1)
        
        private Integer youngFemaleCount = 0;//(2,2)
        
        private Integer adultMaleCount = 0;//(3,1)
        
        private Integer adultFemaleCount = 0;//(3,2)
        
        private Integer seniorCount = 0;//(4,*)
*/        
        @Getter
        private Map<CustmerDemographyEnum,Integer> valueMap = new HashMap<CustmerDemographyEnum,Integer>(8);
        
    }
    
    public static DemographicProfile createDemographicProfile(){
        return new CustomerTrafficResponseDto.DemographicProfile();
    }
    
    public static class DemographicProfile {
        @Getter
        @Setter
        private Integer age;
        @Getter
        @Setter
        private Integer gender;
        @Getter
        @Setter
        private Integer count;
    }
    

}
