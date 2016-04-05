package com.lexmark.apollo.api.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;

@ToString
public class CustomerDemographicProfileDto {
    
    //0: unknown, 1: child, 2: young adult, 3: adult, 4: senior
    //0 - unknown, 1 - male, 2 - female
	
	@Getter
	private int totalCount;
    
    public CustomerDemographicProfileDto(List<DemographicProfileDto> demographicProfiles){
        
        for(DemographicProfileDto demographicProfile : demographicProfiles){
        	totalCount = totalCount + demographicProfile.getCount();
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

	