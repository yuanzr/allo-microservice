package com.dc.allo.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by zhangzhenjun on 2020/3/15.
 */
public class JsonObjectMapper {

    private static final ObjectMapper objectMapper;

    private JsonObjectMapper() {
    }

    public static final ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    static {
        objectMapper = (new ObjectMapper()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

}
