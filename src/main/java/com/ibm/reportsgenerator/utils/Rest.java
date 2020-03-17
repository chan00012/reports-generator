package com.ibm.reportsgenerator.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.math.BigDecimal;

public class Rest {

    private static Logger logger = LoggerFactory.getLogger(Rest.class);

    public static String toJsonString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.setTimeZone(TimeZone.getTimeZone("UTC"));

        String jsonFormat = "";
        try {
            jsonFormat = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.info("JSON PARSING ERROR: {} object not parseable", object.getClass().getName());
            e.printStackTrace();
        }

        return jsonFormat;
    }

    public static boolean isJsonType(String contentType) {
        return (MediaType.APPLICATION_JSON_VALUE.equals(contentType)) ? true : false;
    }

    public static boolean isXmlType(String contentType) {
        return (MediaType.APPLICATION_XML_VALUE.equals(contentType)) ? true : false;
    }

    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    public static class BigDecimalSerializer extends JsonSerializer<BigDecimal>{


        @Override
        public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            BigDecimal d = new BigDecimal(bigDecimal.toString());
            jsonGenerator.writeNumber(d.toPlainString());
        }
    }

}