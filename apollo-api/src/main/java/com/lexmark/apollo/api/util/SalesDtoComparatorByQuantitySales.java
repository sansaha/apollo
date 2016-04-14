package com.lexmark.apollo.api.util;

import java.util.Comparator;

import com.lexmark.apollo.api.dto.SalesDto;

public class SalesDtoComparatorByQuantitySales implements Comparator<SalesDto>{

    @Override
    public int compare(SalesDto o1, SalesDto o2) {
        int result = o1.getItem().compareTo(o2.getItem());
        
        if(result == 0){
            result = o1.getDate().compareTo(o2.getDate());
        }
        
        if(result == 0){
            result = Integer.valueOf(o1.getQuantitySold()).compareTo(o2.getQuantitySold());
        }
        
        return 0;
    }

}
