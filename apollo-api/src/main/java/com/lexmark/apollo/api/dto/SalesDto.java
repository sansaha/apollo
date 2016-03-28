package com.lexmark.apollo.api.dto;

import lombok.Data;

@Data
public class SalesDto {

    private String item;
    private String familyGroup;
    private String majorGroup;
    private double grossSales;
    private double itemDiscounts;
    private double salesLessItemDiscounts;
    private double avaragePrice;
    private String date;
    private int quantitySold;
}
