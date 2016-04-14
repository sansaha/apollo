package com.lexmark.apollo.api.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.lexmark.apollo.api.dto.SalesDto;

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
    
    public static boolean isBetweenDates(Date inputDate,Date startDate,Date endDate){
    	boolean match = false;
    	if(startDate.equals(inputDate) || 
				(startDate.before(inputDate) && endDate.after(inputDate)) ||
				endDate.equals(inputDate)){
			match = true;
		}
    	return match;
    }
    
    public static void populateSalePercentage(List<SalesDto> salesDtoList){
    	if(salesDtoList != null && salesDtoList.isEmpty() == false){
    		double totalSales = 0;
    		for(SalesDto salesDto : salesDtoList){
    			totalSales = totalSales + salesDto.getSalesLessItemDiscounts();
    		}
    		
    		for(SalesDto salesDto : salesDtoList){
    			DecimalFormat decimalFormat = new DecimalFormat("##.00");
    			double salesPercentage = (salesDto.getSalesLessItemDiscounts()*100.0)/totalSales;
    			salesDto.setSalePercentage(Double.parseDouble(decimalFormat.format(salesPercentage)));
    		}
    	}
    }
    
    public static Double calculatePercentage(int total,int input){
        String percentageString = "0";
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        percentageString = decimalFormat.format((input*100.0)/total);
        return Double.parseDouble(percentageString);
    }
    
    
    public static void popolateZeroSalesForMissingdates(List<SalesDto> salesDtoList,Date startDate,Date endDate){
        Map<String,List<SalesDto>> salesDataMap = new HashMap<String,List<SalesDto>>();
        
        for(SalesDto salesDto:salesDtoList){
            
            List<SalesDto> subList = salesDataMap.get(salesDto.getItem());
            if(subList == null){
                subList = new ArrayList<SalesDto>();
                salesDataMap.put(salesDto.getItem(), subList);
            }
            subList.add(salesDto);
            
        }
        
        int duration = getDurationInDays(startDate, endDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        
        //SalesDto salesDtoDummy = new SalesDto();
        //Map<Date,SalesDto> dummySalesMap = new HashMap<Date,SalesDto>();
        List<String> dateList = new ArrayList<String>();
        for(int i = 0;i < duration;i++){
            dateList.add(formatDate(cal.getTime(),null));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        for(String date:dateList){
            
            SalesDto salesDtoDummy = new SalesDto();
            salesDtoDummy.setDate(date);
            
            for(String item:salesDataMap.keySet()){
                List<SalesDto> subList = salesDataMap.get(item);
                salesDtoDummy.setFamilyGroup(subList.get(0).getFamilyGroup());
                salesDtoDummy.setItem(item);
                salesDtoDummy.setMajorGroup(subList.get(0).getMajorGroup());
                
                if(subList.contains(salesDtoDummy) == false){
                    SalesDto salesDto = salesDtoDummy.copyBasicSales();
                    salesDtoList.add(salesDto);
                }
                
            }
            
        } 
        
    }
        
    

}
