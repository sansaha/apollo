package com.lexmark.apollo.api.serializer;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lexmark.apollo.api.dto.CustmerDemographyEnum;
import com.lexmark.apollo.api.dto.CustomerDemographicProfileDto;
import com.lexmark.apollo.api.util.ApolloServiceHelper;

public class CustomerDemographicProfileSerializer extends JsonSerializer<CustomerDemographicProfileDto> {

    @Override
    public void serialize(CustomerDemographicProfileDto arg0, JsonGenerator arg1, SerializerProvider arg2)
            throws IOException, JsonProcessingException {

        arg1.writeStartArray();

        Map<CustmerDemographyEnum, Integer> valueMap = arg0.getValueMap();
        for (CustmerDemographyEnum custmerDemographyEnum : CustmerDemographyEnum.values()) {
            if (valueMap.get(custmerDemographyEnum) != null) {
                arg1.writeStartObject();
                arg1.writeStringField("key", custmerDemographyEnum.getDescription());
                Integer value = valueMap.get(custmerDemographyEnum);
                arg1.writeNumberField("value", value);
                int total = arg0.getAverageCount()  >0?arg0.getAverageCount():arg0.getTotalCount();
                double percentage = ApolloServiceHelper.calculatePercentage(total, value);
                arg1.writeNumberField("percentage", percentage);
                arg1.writeEndObject();
            }
        }

        arg1.writeEndArray();

    }

}
