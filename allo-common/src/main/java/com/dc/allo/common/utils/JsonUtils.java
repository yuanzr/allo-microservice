package com.dc.allo.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/3/15.
 */
@Slf4j
public class JsonUtils {

    public static final ObjectMapper MAPPER = new ObjectMapper();
    public static final ObjectMapper UNICODE_MAPPER = new ObjectMapper();
    public static final String EMPTY_JSON = "{}";

    public JsonUtils() {
    }

    public static final <T> T fromJson(String json, Class<T> type) {
        return fromJson(json, type, false);
    }

    public static final <T> T fromJson(String json, Class<T> type, boolean newInstance) {
        return fromJson(MAPPER, json, type, newInstance);
    }

    public static final <T> T fromJson(ObjectMapper mapper, String json, Class<T> type) {
        return fromJson(mapper, json, type, false);
    }

    public static final <T> T fromJson(ObjectMapper mapper, String json, Class<T> type, boolean newInstance) {
        try {
            if(StringUtils.isBlank(json)) {
                return newInstance?type.newInstance():null;
            } else {
                T result = mapper.readValue(json, type);
                return result == null && newInstance?type.newInstance():result;
            }
        } catch (Exception var5) {
            log.warn("[fromJson] Could not parse json -> {}, to type -> {}", json, type, var5);
            return null;
        }
    }

    public static final <T> T fromJson(String json, TypeReference<T> type) {
        return fromJson(MAPPER, json, type, false);
    }

    public static final <T> T fromJson(String json, TypeReference<T> type, boolean newInstance) {
        return fromJson(MAPPER, json, type, newInstance);
    }

    public static final <T> T fromJson(ObjectMapper mapper, String json, TypeReference<T> type, boolean newInstance) {
        if(type == null) {
            return null;
        } else {
            try {
                if(StringUtils.isBlank(json)) {
                    return newInstance? (T) type.getType().getClass().newInstance() :null;
                } else {
                    T result = mapper.readValue(json, type);
                    return result == null && newInstance? (T) type.getType().getClass().newInstance() :result;
                }
            } catch (Exception var5) {
                log.warn("[fromJson] Could not parse json -> {}, to type -> {}", json, type, var5);
                return null;
            }
        }
    }

    public static final String toJsonUnicode(Object obj) {
        return toJson(UNICODE_MAPPER, obj);
    }

    public static final String toJson(Object obj) {
        return toJson(MAPPER, obj);
    }

    public static final String toJson(ObjectMapper mapper, Object obj) {
        if(obj == null) {
            return "{}";
        } else {
            try {
                return MAPPER.writeValueAsString(obj);
            } catch (Exception var3) {
                log.warn("[toJson] Object[{}] could not parse to json", obj, var3);
                return "{}";
            }
        }
    }

    public static boolean isJson(String json) {
        try {
            JsonObjectMapper.getObjectMapper().readTree(json);
            return true;
        } catch (IOException var2) {
            return false;
        }
    }

    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        UNICODE_MAPPER.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        UNICODE_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static void main(String[] args) {
        Map<String, Object> obj1 = fromJson(MAPPER, "{\"a\":1,\"b\":2}", new TypeReference<Map<String, Object>>() {}, true);
        System.out.println(obj1);

        List<Map<String, Object>> obj2 = fromJson(MAPPER, "[{\"a\":1,\"b\":2}]", new TypeReference<List<Map<String, Object>>>() {
        }, true);
        System.out.println(obj2);
    }
}
