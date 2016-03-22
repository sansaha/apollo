package com.lexmark.apollo.api.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.lexmark.apollo.api.dto.SalesResponseDto;
import com.lexmark.apollo.api.dto.SalesResponseDto.SalesRecordDto;
import com.lexmark.apollo.api.service.SalesService;
import com.lexmark.apollo.api.util.ApolloServiceException;

@Service
public class SalesServiceImpl implements SalesService {

    public SalesResponseDto getSalesData(String startDate, String endDate) throws ApolloServiceException {
        
        
        //TODO
        return populateDummyResponse();
    }
    
    private SalesResponseDto populateDummyResponse(){
        
        SalesResponseDto salesResponseDto = new SalesResponseDto();
        
        SalesRecordDto salesRecordDto1 = SalesResponseDto.createNewSalesRecordDto();
        SalesRecordDto salesRecordDto2 = SalesResponseDto.createNewSalesRecordDto();
        SalesRecordDto salesRecordDto3 = SalesResponseDto.createNewSalesRecordDto();
        SalesRecordDto salesRecordDto4 = SalesResponseDto.createNewSalesRecordDto();
        SalesRecordDto salesRecordDto5 = SalesResponseDto.createNewSalesRecordDto();
        SalesRecordDto salesRecordDto6 = SalesResponseDto.createNewSalesRecordDto();
        SalesRecordDto salesRecordDto7 = SalesResponseDto.createNewSalesRecordDto();
        SalesRecordDto salesRecordDto8 = SalesResponseDto.createNewSalesRecordDto();
        SalesRecordDto salesRecordDto9 = SalesResponseDto.createNewSalesRecordDto();
        SalesRecordDto salesRecordDto10 = SalesResponseDto.createNewSalesRecordDto();
        
        salesResponseDto.addSalesRecord(salesRecordDto1);
        salesResponseDto.addSalesRecord(salesRecordDto2);
        salesResponseDto.addSalesRecord(salesRecordDto3);
        salesResponseDto.addSalesRecord(salesRecordDto4);
        salesResponseDto.addSalesRecord(salesRecordDto5);
        salesResponseDto.addSalesRecord(salesRecordDto6);
        salesResponseDto.addSalesRecord(salesRecordDto7);
        salesResponseDto.addSalesRecord(salesRecordDto8);
        salesResponseDto.addSalesRecord(salesRecordDto9);
        salesResponseDto.addSalesRecord(salesRecordDto10);
        
        int totalQuantitySold = 0;
        double totalSalesAmount = 0;
        
       
        salesRecordDto1.setFamilyGroup("Deli");
        salesRecordDto1.setMajorGroup("FOOD");
        salesRecordDto1.setItemName("Deli Chips");
        salesRecordDto1.setDiscountPrice(BigDecimal.ZERO);
        salesRecordDto1.setGrossPrice(new BigDecimal(300.25));
        salesRecordDto1.setQuantitySold(24);
        
        totalQuantitySold = totalQuantitySold + salesRecordDto1.getQuantitySold();
        totalSalesAmount = totalSalesAmount + salesRecordDto1.getNetPrice().doubleValue();
        
        salesRecordDto2.setFamilyGroup("Hot Beverage");
        salesRecordDto2.setMajorGroup("BEVERAGE");
        salesRecordDto2.setItemName("Reg Coffee 16oz");
        salesRecordDto2.setDiscountPrice(new BigDecimal(-0.5));
        salesRecordDto2.setGrossPrice(new BigDecimal(120.25));
        salesRecordDto2.setQuantitySold(5);
        
        totalQuantitySold = totalQuantitySold + salesRecordDto2.getQuantitySold();
        totalSalesAmount = totalSalesAmount + salesRecordDto2.getNetPrice().doubleValue();
        
        salesRecordDto3.setFamilyGroup("Healthy GnG");
        salesRecordDto3.setMajorGroup("RETAIL FOOD");
        salesRecordDto3.setItemName("HUMMUS&REDPEPPER");
        salesRecordDto3.setDiscountPrice(BigDecimal.ZERO);
        salesRecordDto3.setGrossPrice(new BigDecimal(897.3));
        salesRecordDto3.setQuantitySold(12);
        
        totalQuantitySold = totalQuantitySold + salesRecordDto3.getQuantitySold();
        totalSalesAmount = totalSalesAmount + salesRecordDto3.getNetPrice().doubleValue();
        
        salesRecordDto4.setFamilyGroup("Continental");
        salesRecordDto4.setMajorGroup("FOOD");
        salesRecordDto4.setItemName("Donut Lg");
        salesRecordDto4.setDiscountPrice(BigDecimal.ZERO);
        salesRecordDto4.setGrossPrice(new BigDecimal(200.58));
        salesRecordDto4.setQuantitySold(2);
        
        totalQuantitySold = totalQuantitySold + salesRecordDto4.getQuantitySold();
        totalSalesAmount = totalSalesAmount + salesRecordDto4.getNetPrice().doubleValue();
        
        salesRecordDto5.setFamilyGroup("Breakfast");
        salesRecordDto5.setMajorGroup("FOOD");
        salesRecordDto5.setItemName("Ham Croissant");
        salesRecordDto5.setDiscountPrice(new BigDecimal(-2.01));
        salesRecordDto5.setGrossPrice(new BigDecimal(400.55));
        salesRecordDto5.setQuantitySold(50);
        
        totalQuantitySold = totalQuantitySold + salesRecordDto5.getQuantitySold();
        totalSalesAmount = totalSalesAmount + salesRecordDto5.getNetPrice().doubleValue();
        
        salesRecordDto6.setFamilyGroup("Cold Beverage");
        salesRecordDto6.setMajorGroup("BEVERAGE");
        salesRecordDto6.setItemName("LgFountDrink");
        salesRecordDto6.setDiscountPrice(new BigDecimal(-5.01));
        salesRecordDto6.setGrossPrice(new BigDecimal(100.35));
        salesRecordDto6.setQuantitySold(15);
        
        totalQuantitySold = totalQuantitySold + salesRecordDto6.getQuantitySold();
        totalSalesAmount = totalSalesAmount + salesRecordDto6.getNetPrice().doubleValue();
        
        salesRecordDto7.setFamilyGroup("Cold Beverage");
        salesRecordDto7.setMajorGroup("RETAIL BEVERAGE");
        salesRecordDto7.setItemName("Pureleaf Sweet");
        salesRecordDto7.setDiscountPrice(BigDecimal.ZERO);
        salesRecordDto7.setGrossPrice(new BigDecimal(406.35));
        salesRecordDto7.setQuantitySold(20);
        
        totalQuantitySold = totalQuantitySold + salesRecordDto7.getQuantitySold();
        totalSalesAmount = totalSalesAmount + salesRecordDto7.getNetPrice().doubleValue();
        
        salesRecordDto8.setFamilyGroup("Grill");
        salesRecordDto8.setMajorGroup("FOOD");
        salesRecordDto8.setItemName("Grill Side");
        salesRecordDto8.setDiscountPrice(new BigDecimal(-10.21));
        salesRecordDto8.setGrossPrice(new BigDecimal(879.25));
        salesRecordDto8.setQuantitySold(25);
        
        totalQuantitySold = totalQuantitySold + salesRecordDto8.getQuantitySold();
        totalSalesAmount = totalSalesAmount + salesRecordDto8.getNetPrice().doubleValue();
        
        salesRecordDto9.setFamilyGroup("Healthy Impulse");
        salesRecordDto9.setMajorGroup("RETAIL FOOD");
        salesRecordDto9.setItemName("NAB BELV BLUEBRY");
        salesRecordDto9.setDiscountPrice(new BigDecimal(-5.09));
        salesRecordDto9.setGrossPrice(new BigDecimal(980.25));
        salesRecordDto9.setQuantitySold(50);
        
        totalQuantitySold = totalQuantitySold + salesRecordDto9.getQuantitySold();
        totalSalesAmount = totalSalesAmount + salesRecordDto9.getNetPrice().doubleValue();
        
        salesRecordDto10.setFamilyGroup("Healthy Cold Bev");
        salesRecordDto10.setMajorGroup("RETAIL BEVERAGE");
        salesRecordDto10.setItemName("GTRADE FROST");
        salesRecordDto10.setDiscountPrice(BigDecimal.ZERO);
        salesRecordDto10.setGrossPrice(new BigDecimal(1250.25));
        salesRecordDto10.setQuantitySold(75);
        
        totalQuantitySold = totalQuantitySold + salesRecordDto10.getQuantitySold();
        totalSalesAmount = totalSalesAmount + salesRecordDto10.getNetPrice().doubleValue();
        
        salesRecordDto1.populatePercentages(totalQuantitySold, totalSalesAmount);
        salesRecordDto2.populatePercentages(totalQuantitySold, totalSalesAmount);
        salesRecordDto3.populatePercentages(totalQuantitySold, totalSalesAmount);
        salesRecordDto4.populatePercentages(totalQuantitySold, totalSalesAmount);
        salesRecordDto5.populatePercentages(totalQuantitySold, totalSalesAmount);
        salesRecordDto6.populatePercentages(totalQuantitySold, totalSalesAmount);
        salesRecordDto7.populatePercentages(totalQuantitySold, totalSalesAmount);
        salesRecordDto8.populatePercentages(totalQuantitySold, totalSalesAmount);
        salesRecordDto9.populatePercentages(totalQuantitySold, totalSalesAmount);
        salesRecordDto10.populatePercentages(totalQuantitySold, totalSalesAmount);
        
        return salesResponseDto;
    }
    
}
