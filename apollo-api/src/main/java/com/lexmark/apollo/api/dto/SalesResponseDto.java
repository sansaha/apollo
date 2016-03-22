package com.lexmark.apollo.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SalesResponseDto {
        
    private List<SalesRecordDto> salesRecords = null;

    public List<SalesRecordDto> getSalesRecords() {
        return salesRecords;
    }

    public void setSalesRecords(List<SalesRecordDto> salesRecords) {
        this.salesRecords = salesRecords;
    }
    
    public void addSalesRecord(SalesRecordDto salesRecord){
        if(salesRecord != null){
            if (salesRecords == null){
                salesRecords = new ArrayList<SalesRecordDto>();
            }
            salesRecords.add(salesRecord);
        }
    }
    
    public static SalesRecordDto createNewSalesRecordDto(){
        return new SalesResponseDto.SalesRecordDto();
    }
    
    public static class SalesRecordDto {
                
        private String itemName;
        private String familyGroup;
        private String majorGroup;
        private Integer quantitySold;
        private BigDecimal discountPrice;
        private BigDecimal grossPrice;
 
        private BigDecimal salesAmountPercentage;
        private BigDecimal salesQuantityPercentage;
        
        public String getItemName() {
            return itemName;
        }
        public void setItemName(String itemName) {
            this.itemName = itemName;
        }
        public String getFamilyGroup() {
            return familyGroup;
        }
        public void setFamilyGroup(String familyGroup) {
            this.familyGroup = familyGroup;
        }
        public String getMajorGroup() {
            return majorGroup;
        }
        public void setMajorGroup(String majorGroup) {
            this.majorGroup = majorGroup;
        }
 
        public BigDecimal getDiscountPrice() {
            return discountPrice;
        }
        public void setDiscountPrice(BigDecimal discountPrice) {
            this.discountPrice = discountPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        public BigDecimal getGrossPrice() {
            return grossPrice;
        }
        public void setGrossPrice(BigDecimal grossPrice) {
            this.grossPrice = grossPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        public BigDecimal getNetPrice() {
            return grossPrice.add(discountPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        public BigDecimal getSalesAmountPercentage() {
            return salesAmountPercentage;
        }
        public BigDecimal getSalesQuantityPercentage() {
            return salesQuantityPercentage;
        }
        public Integer getQuantitySold() {
            return quantitySold;
        }
        public void setQuantitySold(Integer quantitySold) {
            this.quantitySold = quantitySold;
        }
        public BigDecimal getAveragePrice() {
            return getNetPrice().divide(new BigDecimal(quantitySold),2, BigDecimal.ROUND_HALF_UP);
        }
        
        public void populatePercentages(Integer totalQuantitySold,Double totalSalesAmount){
            salesQuantityPercentage = new BigDecimal(quantitySold).multiply(BigDecimal.valueOf(100)).divide(new BigDecimal(totalQuantitySold),2, BigDecimal.ROUND_HALF_UP);
            salesAmountPercentage = getNetPrice().multiply(BigDecimal.valueOf(100)).divide(new BigDecimal(totalSalesAmount),2, BigDecimal.ROUND_HALF_UP);
            
        }

    }

}
