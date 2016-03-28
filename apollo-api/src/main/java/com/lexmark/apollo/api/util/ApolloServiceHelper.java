package com.lexmark.apollo.api.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.util.StringUtils;

public class ApolloServiceHelper {
    
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    
    public static void validateDate(String dateString,String dateFormat){
        
        if(StringUtils.isEmpty(dateString)){
            throw new IllegalArgumentException("NULL or Empty Date");
        }
        
        DateFormat format = null;
        
        if(StringUtils.isEmpty(dateFormat) == false){
            format = new SimpleDateFormat(dateFormat);
        } else {
            format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        }
        
        try {
            format.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date :"+dateString,e);
        }
        
    }

}
