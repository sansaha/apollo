package com.lexmark.apollo.api.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

public class ApolloServiceHelper {
    
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    
    public static Date parseDate(String dateString,String dateFormat){
        
        if(StringUtils.isEmpty(dateString)){
            throw new IllegalArgumentException("NULL or Empty Date");
        }
        
        DateFormat format = null;
        
        if(StringUtils.isEmpty(dateFormat) == false){
            format = new SimpleDateFormat(dateFormat);
        } else {
            format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        }
        
        Date date = null;
        
        try {
        	date = format.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date :"+dateString,e);
        }
        
        return date;
        
    }
    
    public static String formatDate(Date date,String dateFormat){
    	
    	if(date == null){
            throw new IllegalArgumentException("Input Date is null");
        }
        
        DateFormat format = null;
        
        if(StringUtils.isEmpty(dateFormat) == false){
            format = new SimpleDateFormat(dateFormat);
        } else {
            format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        }
        
        String dateString = format.format(date);
        
        return dateString;
    }
    
    public static int getDurationInDays(Date startDate,Date endDate){
    	
    	int difference = (int) ((endDate.getTime() - startDate.getTime())/(24*3600000));

    	return difference;
    }
        
    

}
