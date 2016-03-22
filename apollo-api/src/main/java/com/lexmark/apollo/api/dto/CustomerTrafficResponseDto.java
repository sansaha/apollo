package com.lexmark.apollo.api.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class CustomerTrafficResponseDto {
    
    @Getter
    @Setter
    private List<CustomerTraffic> customerTraffics;
    
    @Getter
    @Setter
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
    
    public static CustomerTraffic createCustomerTrafic(){
        return new CustomerTrafficResponseDto.CustomerTraffic();
    }
    
    public static class CustomerTraffic implements Comparable<CustomerTraffic>{
        @Getter
        @Setter
        @JsonFormat(pattern="yyyy-MM-dd")
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
                    this.unknownCount = unknownCount + demographicProfile.getCount();
                    continue;
                }
                if(demographicProfile.getAge() == 1){
                    this.childCount = childCount + demographicProfile.getCount();
                    continue;
                }
                if(demographicProfile.getAge() == 2 && demographicProfile.getGender() == 0){
                    this.youthCount = youthCount + demographicProfile.getCount();
                    continue;
                }
                if(demographicProfile.getAge() == 2 && demographicProfile.getGender() == 1){
                    this.youngMaleCount = youngMaleCount + demographicProfile.getCount();
                    continue;
                }
                if(demographicProfile.getAge() == 2 && demographicProfile.getGender() == 2){
                    this.youngFemaleCount = youngFemaleCount + demographicProfile.getCount();
                    continue;
                }
                
                if(demographicProfile.getAge() == 3 && demographicProfile.getGender() == 1){
                    this.adultMaleCount = adultMaleCount + demographicProfile.getCount();
                    continue;
                }
                if(demographicProfile.getAge() == 3 && demographicProfile.getGender() == 2){
                    this.adultFemaleCount = adultFemaleCount + demographicProfile.getCount();
                    continue;
                }
                if(demographicProfile.getAge() == 4){
                    this.seniorCount = seniorCount + demographicProfile.getCount();
                }
            }
        }

        @Getter
        private Integer unknownCount = 0;//(0,*) || (3,0)
        @Getter
        private Integer childCount = 0;//(1,*)
        @Getter
        private Integer youthCount = 0;//(2,0)
        @Getter
        private Integer youngMaleCount = 0;//(2,1)
        @Getter
        private Integer youngFemaleCount = 0;//(2,2)
        @Getter
        private Integer adultMaleCount = 0;//(3,1)
        @Getter
        private Integer adultFemaleCount = 0;//(3,2)
        @Getter
        private Integer seniorCount = 0;//(4,*)
 
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
