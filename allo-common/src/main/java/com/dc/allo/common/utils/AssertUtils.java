package com.dc.allo.common.utils;

import com.dc.allo.common.exception.DCException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * Created by zhangzhenjun on 2020/3/15.
 */
public class AssertUtils {
    public static void failed(int code, String message) {
        throw new DCException(code, message);
    }

    /** 为空 */
    public static void isNull(Object object, int code, String message) {
        if (object == null) {
            throw new DCException(code, message);
        }
        if (object instanceof String && StringUtils.isBlank((String) object)) {
            throw new DCException(code, message);
        }
    }

    /** 包含空格 */
    public static void containsWhitespace(String str, int code, String message) {
        if (StringUtils.containsWhitespace(str)) {
            throw new DCException(code, message);
        }
    }

    /** 不是数值，当numeric不为空且非正整数时抛出异常，注意控制不抛异常 */
    public static void notNumeric(String numeric, int code, String message) {
        if (StringUtils.isNotBlank(numeric) && !StringUtils.isNumeric(numeric)) {
            throw new DCException(code, message);
        }
    }

    /** 不以 prefix 开头 */
    public static void notStartWith(String str, String prefix, int code, String message) {
        isNull(str, code, message);
        if (!str.startsWith(prefix)) {
            throw new DCException(code, message);
        }
    }

    /** 字符串长度大于等于 */
    public static void lengthGreaterThan (String str, int length, int code, String message) {
        isNull(str, code, message);
        if (str.length() > length) {
            throw new DCException(code, message);
        }
    }

    /** 字符串长度小于 */
    public static void lengthLessThan (String str, int length, int code, String message) {
        isNull(str, code, message);
        if (str.length() < length) {
            throw new DCException(code, message);
        }
    }

    /** 非正数 */
    public static void notPositiveNumber(long val, int code, String message) {
        if (val <= 0) {
            throw new DCException(code, message);
        }
    }

    public static void lessEqualsThan(long val, long thanVal, int code, String message) {
        if (val <= thanVal) {
            throw new DCException(code, message);
        }
    }

    public static void isEmpty(Collection c, int code, String message){
        if(CollectionUtils.isEmpty(c)){
            throw new DCException(code,message);
        }
    }
}
