package com.lexmark.apollo.api.service.queries;

public class CustomerTrafficQuery {
       
    public static final String SELECT_CUSTOMER_TRAFFIC_BETWEEN_DATES_QUERY = "select start_time::date,sum(out_count) from lexmark_prod.quividi_gate where location_id=20345 and"
            + " start_time::date between :startDate and :endDate group by start_time::date";
    
    public static final String SELECT_PROJECTED_CUSTOMER_TRAFFIC_BETWEEN_DATES_QUERY = "select projection_date,expected_traffic from lexmark_prod.quividi_traffic_projection where"
            + " projection_date between :startDate and :endDate order by projection_date";
    
    public static final String SELECT_DEMOGRAPHIC_PROFILES_BETWEEN_DATES_QUERY = "select gender,age,sum(watcher_count) from lexmark_prod.quividi_watcher where start_time::date between :startDate and :endDate group by gender,age";
    
}