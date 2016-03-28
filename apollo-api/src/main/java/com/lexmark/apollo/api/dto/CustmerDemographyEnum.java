package com.lexmark.apollo.api.dto;

public enum CustmerDemographyEnum {

    UNKNOWN("Unknown"), CHILDREN("Children"), YOUTH("Youth"), YOUNG_MALE("Young Male"), 
    YOUNG_FEMALE("Young Female"), ADULT_MALE("Adult Male"), ADULT_FEMALE("Adult Female"), SENIOR("Senior");
    
    private String description;
    
    private CustmerDemographyEnum(String description){
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
