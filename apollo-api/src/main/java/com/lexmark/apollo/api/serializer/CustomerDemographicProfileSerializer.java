package com.lexmark.apollo.api.serializer;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lexmark.apollo.api.dto.CustmerDemographyEnum;
import com.lexmark.apollo.api.dto.CustomerTrafficResponseDto.CustomerDemographicProfile;

public class CustomerDemographicProfileSerializer extends JsonSerializer<CustomerDemographicProfile> {

    @Override
    public void serialize(CustomerDemographicProfile arg0, JsonGenerator arg1, SerializerProvider arg2)
            throws IOException, JsonProcessingException {

        arg1.writeStartArray();

        Map<CustmerDemographyEnum, Integer> valueMap = arg0.getValueMap();
        for (CustmerDemographyEnum custmerDemographyEnum : CustmerDemographyEnum.values()) {
            if (valueMap.get(custmerDemographyEnum) != null) {
                arg1.writeStartObject();
                arg1.writeStringField("key", custmerDemographyEnum.toString());
                arg1.writeNumberField("value", valueMap.get(custmerDemographyEnum));
                arg1.writeEndObject();
            }
        }

        arg1.writeEndArray();

    }

}
