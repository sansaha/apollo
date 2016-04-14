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
    	
    	valueMap.put(CustmerDemographyEnum.UNKNOWN, 0);
    	valueMap.put(CustmerDemographyEnum.CHILDREN, 0);
    	valueMap.put(CustmerDemographyEnum.YOUTH, 0);
    	valueMap.put(CustmerDemographyEnum.YOUNG_MALE, 0);
    	valueMap.put(CustmerDemographyEnum.YOUNG_FEMALE, 0);
    	valueMap.put(CustmerDemographyEnum.ADULT_MALE, 0);
    	valueMap.put(CustmerDemographyEnum.ADULT_FEMALE, 0);
    	valueMap.put(CustmerDemographyEnum.SENIOR, 0);
        
        for(DemographicProfileDto demographicProfile : demographicProfiles){
        	totalCount = totalCount + demographicProfile.getCount();
            if(demographicProfile.getAge() == 0 || (demographicProfile.getAge() == 3 && demographicProfile.getGender() == 0)){
                Integer unknownCount = valueMap.get(CustmerDemographyEnum.UNKNOWN);
                unknownCount = unknownCount + demographicProfile.getCount();
                valueMap.put(CustmerDemographyEnum.UNKNOWN, unknownCount);
                continue;
            }
            if(demographicProfile.getAge() == 1){
                Integer childCount = valueMap.get(CustmerDemographyEnum.CHILDREN);
                
                childCount = childCount + demographicProfile.getCount();
                valueMap.put(CustmerDemographyEnum.CHILDREN, childCount);
                continue;
            }
            if(demographicProfile.getAge() == 2 && demographicProfile.getGender() == 0){
            	Integer youthCount = valueMap.get(CustmerDemographyEnum.YOUTH);
                youthCount = youthCount + demographicProfile.getCount();
                valueMap.put(CustmerDemographyEnum.YOUTH, youthCount);
                continue;
            }
            if(demographicProfile.getAge() == 2 && demographicProfile.getGender() == 1){
            	Integer youngMaleCount = valueMap.get(CustmerDemographyEnum.YOUNG_MALE);
                youngMaleCount = youngMaleCount + demographicProfile.getCount();
                valueMap.put(CustmerDemographyEnum.YOUNG_MALE, youngMaleCount);
                continue;
            }
            if(demographicProfile.getAge() == 2 && demographicProfile.getGender() == 2){
            	Integer youngFemaleCount = valueMap.get(CustmerDemographyEnum.YOUNG_FEMALE);
                youngFemaleCount = youngFemaleCount + demographicProfile.getCount();
                valueMap.put(CustmerDemographyEnum.YOUNG_FEMALE, youngFemaleCount);
                continue;
            }
            
            if(demographicProfile.getAge() == 3 && demographicProfile.getGender() == 1){
            	Integer adultMaleCount = valueMap.get(CustmerDemographyEnum.ADULT_MALE);
                adultMaleCount = adultMaleCount + demographicProfile.getCount();
                valueMap.put(CustmerDemographyEnum.ADULT_MALE, adultMaleCount);
                continue;
            }
            if(demographicProfile.getAge() == 3 && demographicProfile.getGender() == 2){
            	Integer adultFemaleCount = valueMap.get(CustmerDemographyEnum.ADULT_FEMALE);
                adultFemaleCount = adultFemaleCount + demographicProfile.getCount();
                valueMap.put(CustmerDemographyEnum.ADULT_FEMALE, adultFemaleCount);
                continue;
            }
            if(demographicProfile.getAge() == 4){
                Integer seniorCount = valueMap.get(CustmerDemographyEnum.SENIOR);
                seniorCount = seniorCount + demographicProfile.getCount();
                valueMap.put(CustmerDemographyEnum.SENIOR, seniorCount);
            }
        }
    }
    
    public void popuateAverageCount(int duration){
    	if(duration > 0){
    		Integer unknownCount = valueMap.get(CustmerDemographyEnum.UNKNOWN);
    		Integer childCount = valueMap.get(CustmerDemographyEnum.CHILDREN);
    		Integer youthCount = valueMap.get(CustmerDemographyEnum.YOUTH);
    		Integer youngMaleCount = valueMap.get(CustmerDemographyEnum.YOUNG_MALE);
    		Integer youngFemaleCount = valueMap.get(CustmerDemographyEnum.YOUNG_FEMALE);
    		Integer adultMaleCount = valueMap.get(CustmerDemographyEnum.ADULT_MALE);
    		Integer adultFemaleCount = valueMap.get(CustmerDemographyEnum.ADULT_FEMALE);
    		Integer seniorCount = valueMap.get(CustmerDemographyEnum.SENIOR);
    		
    		if(unknownCount > 0){
    			unknownCount = unknownCount / duration;
    			valueMap.put(CustmerDemographyEnum.UNKNOWN, unknownCount);
    		}
    		
			if(childCount > 0){
			    childCount = childCount / duration;
                valueMap.put(CustmerDemographyEnum.CHILDREN, childCount);	
			}
			
			if(youthCount > 0){
			    youthCount = youthCount / duration;
				valueMap.put(CustmerDemographyEnum.YOUTH, youthCount);
			}
			
			if(youngMaleCount > 0){
			    youngMaleCount = youngMaleCount / duration;
			    valueMap.put(CustmerDemographyEnum.YOUNG_MALE, youngMaleCount);
				
			}
			
			if(youngFemaleCount > 0){
			    youngFemaleCount = youngFemaleCount / duration;
				valueMap.put(CustmerDemographyEnum.YOUNG_FEMALE, youngFemaleCount);
			}
			
			if(adultMaleCount > 0){
			    adultMaleCount = adultMaleCount / duration;
				valueMap.put(CustmerDemographyEnum.ADULT_MALE, adultMaleCount);
			}
			
			if(adultFemaleCount > 0){
			    adultFemaleCount = adultFemaleCount / duration;
				valueMap.put(CustmerDemographyEnum.ADULT_FEMALE, adultFemaleCount);
			}
			
			if(seniorCount > 0){
			    seniorCount = seniorCount / duration;
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

	