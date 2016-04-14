package com.lexmark.apollo.api.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lexmark.apollo.api.serializer.JsonDateSerializer;

import lombok.Data;

@Data
public class PromotionDto implements Serializable, Comparable<PromotionDto> {

    private static final long serialVersionUID = 4938565982679611336L;

    private String name;
    private String item;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date endDate;
    private String type;

    @Override
    public int compareTo(PromotionDto o) {

        int result = this.getName().compareTo(o.getName());

        if (result == 0) {

            result = this.getItem().compareTo(o.getItem());
        }
        
        if (result == 0) {

            result = o.getStartDate().compareTo(this.getStartDate());
        }
        
        

        return result;
    }

}
