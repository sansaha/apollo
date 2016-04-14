package com.lexmark.apollo.api.dto;

import lombok.Data;

@Data
public class SalesDto implements Comparable<SalesDto>{

    private String item;
    private String familyGroup;
    private String majorGroup;
    private double grossSales;
    private double itemDiscounts;
    private double salesLessItemDiscounts;
    private double salePercentage;
    private double avaragePrice;
    private String date;
    private int quantitySold;
    
   
    
    public SalesDto copyBasicSales(){
        SalesDto salesDto = new SalesDto();
        salesDto.setDate(date);
        salesDto.setItem(item);
        
        return salesDto;
    }



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalesDto other = (SalesDto) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		return result;
	}



	@Override
	public int compareTo(SalesDto o) {
		int result = getItem().compareTo(o.getItem());
        
        if(result == 0){
            result = getDate().compareTo(o.getDate());
        }
        
        return result;
	}
}
