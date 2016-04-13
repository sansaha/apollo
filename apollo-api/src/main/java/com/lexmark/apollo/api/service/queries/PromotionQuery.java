package com.lexmark.apollo.api.service.queries;

public class PromotionQuery {
    
    public static final String SELECT_PROMOTIONS_BETWEEN_DATES_QUERY = "select * from lexmark_prod.promotions where startdate between :startDate and :endDate union "
            + "select * from lexmark_prod.promotions where startdate < :startDate and enddate is null or enddate between :startDate and :endDate";

}
