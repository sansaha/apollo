package com.lexmark.apollo.api.service.queries;

public class CustomerTrafficQuery {
       
    public static final String SELECT_CUSTOMER_TRAFFIC_BETWEEN_DATES_QUERY = "select start_time::date,sum(out_count) from lexmark_prod.quividi_gate where location_id=20345 and"
            + " start_time::date between :startDate and :endDate group by start_time::date";
    
    public static final String SELECT_PROJECTED_CUSTOMER_TRAFFIC_BETWEEN_DATES_QUERY = "select pred_time::date,round(sum(people)) from lexmark_prod.cafeteria_prediction_data"
    		+ " where pred_time::date between :startDate and :endDate group by pred_time::date order by pred_time";
    
    public static final String SELECT_DEMOGRAPHIC_PROFILES_BETWEEN_DATES_QUERY = "select gender,age,sum(watcher_count) from lexmark_prod.quividi_watcher where start_time::date between :startDate and :endDate group by gender,age";
   
    public static final String SELECT_CUSTOMER_BETWEEN_DATES_AND_MIN_ATTENTION_TIME = "select location_id,gender,age,start_time,watcher_count from lexmark_prod.quividi_watcher where start_time::date between :startDate and :endDate and attention_time >= :minWatchTime order by start_time";
    
}