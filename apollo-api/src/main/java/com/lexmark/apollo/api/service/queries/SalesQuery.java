package com.lexmark.apollo.api.service.queries;

public class SalesQuery {
    
    public static final String QUERY_SALES_DATA_BETWEEN_DATES = "select item,family_group, major_group,sum(gross_sales),sum(item_discounts),sum(sales_less_item_disc),round(avg(average_price), 2),sum(qty_sold)"
            + " from lexmark_prod.lexmark_pos where date::date between :startDate and :endDate group by item, family_group, major_group order by item, family_group, major_group";
    
    public static final String QUERY_ITEM_SALES_DETAILS_BETWEEN_DATES = "select item,gross_sales,qty_sold,date::date"
            + " from lexmark_prod.lexmark_pos where date::date between :startDate and :endDate and item = :itemName order by date::date";

    public static final String QUERY_GROSS_SALES_TOP_N_BETWEEN_DATES = "select item, gross_sales, date::date from lexmark_prod.lexmark_pos where item in (select item from lexmark_prod.lexmark_pos where date::date between :startDate and :endDate group by item order by sum(gross_sales) desc limit :recordCount)"
            + " and date::date between :startDate and :endDate order by item, date, gross_sales desc";


    public static final String QUERY_GROSS_SALES_MOST_CHANGED_BETWEEN_DATES = "select item, gross_sales, date::date from lexmark_prod.lexmark_pos where item in (select item"
                +" from lexmark_prod.lexmark_pos where date::date between :startDate and :endDate group by item having stddev(gross_sales) > 0 order by stddev(gross_sales) desc limit :recordCount)" 
                +" and date::date between :startDate and :endDate order by item, date::date, gross_sales desc";

    
    public static final String QUERY_QUANTITY_SOLD_TOP_N_BETWEEN_DATES = "select item, qty_sold, date::date from lexmark_prod.lexmark_pos where item in ( select item from lexmark_prod.lexmark_pos where" 
                +" date::date between :startDate and :endDate group by item order by sum(qty_sold) desc limit :recordCount) and date::date between :startDate and :endDate order by item, date, qty_sold desc;";


    public static final String QUERY_QUANTITY_SOLD_MOST_CHANGED_BETWEEN_DATES = "select item, qty_sold, date::date from lexmark_prod.lexmark_pos where item in (select item from lexmark_prod.lexmark_pos where"
                +" date::date between :startDate and :endDate group by item having stddev(qty_sold) > 0 order by stddev(qty_sold) desc limit :recordCount) and date::date between :startDate and :endDate" 
                +" order by item, date, qty_sold desc";

}
