package com.lexmark.apollo.api.service.queries;

public class SignageQuery {
	
	public static final String SELECT_CUSTOMER_TRAFFIC_BETWEEN_DATES_QUERY = "select location_id,description,start_time from lexmark_prod.quividi_watcher_desc"
			+ " where start_time::date between :startDate and :endDate order by start_time";

}
