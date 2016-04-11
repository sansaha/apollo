package com.lexmark.apollo.api.service.queries;

public class CustomerTrafficQuery {
       
    public static final String SELECT_CUSTOMER_TRAFFIC_BETWEEN_DATES_QUERY = "select start_time::date,sum(out_count) from lexmark_prod.quividi_gate where location_id=20345 and"
            + " start_time::date between :startDate and :endDate group by start_time::date";
    
    public static final String SELECT_PROJECTED_CUSTOMER_TRAFFIC_BETWEEN_DATES_QUERY = "select projection_date,expected_traffic from lexmark_prod.quividi_traffic_projection where"
            + " projection_date between :startDate and :endDate order by projection_date";
    
    public static final String SELECT_DEMOGRAPHIC_PROFILES_BETWEEN_DATES_QUERY = "select gender,age,sum(watcher_count) from lexmark_prod.quividi_watcher where start_time::date between :startDate and :endDate group by gender,age";
    
    /*public static final String SELECT_CUSTOMER_DEMOGRAPHY_DURING_SIGNAGE = "select qwd.description,qw.location_id,qw.age,qw.gender,sum(watcher_count) from lexmark_prod.quividi_watcher as qw INNER join  lexmark_prod.quividi_watcher_desc as qwd on qw.location_id = qwd.location_id" 
    		+" where qwd.start_time = ( select max(qwd2.start_time) from lexmark_prod.quividi_watcher_desc as qwd2  where qwd2.location_id = qw.location_id  and qwd2.start_time < qw.start_time )"
    		+" and qw.start_time::date between :startDate and :endDate and attention_time >= :minWatchTime group by qwd.description,qw.location_id,qw.age,qw.gender order by qwd.description,qw.age,qw.gender";
    
    public static final String SELECT_CUSTOMER_DEMOGRAPHY_BEFORE_SIGNAGE = "select qwd.description,qw.location_id,qw.age,qw.gender,sum(watcher_count) from lexmark_prod.quividi_watcher as qw INNER join  lexmark_prod.quividi_watcher_desc as qwd on qw.location_id = qwd.location_id" 
    		+" where qw.location_id in(:locationIds) and qw.start_time::date between :startDate and :endDate and attention_time >= :minWatchTime group by qwd.description,qw.location_id,qw.age,qw.gender order by qwd.description,qw.age,qw.gender";*/
    
    public static final String SELECT_CUSTOMER_BETWEEN_DATES_AND_MIN_ATTENTION_TIME = "select location_id,gender,age,start_time,watcher_count from lexmark_prod.quividi_watcher where start_time::date between :startDate and :endDate and attention_time >= :minWatchTime order by start_time";
    
}