package com.dc.allo.rpc.aspect.autolog;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangzhenjun on 2020/3/22.
 */
@Data
@ToString
public class AllowAutoLogMethods implements Serializable {

    private Map<String, Set<String>> methodSets = new ConcurrentHashMap<>();

    private static final String ALL = "*";

    public boolean contains(String className, String methodName) {
        if (methodSets == null || StringUtils.isBlank(className) || StringUtils.isBlank(methodName)) {
            return false;
        }
        Set<String> methodSet = methodSets.get(className);
        if (CollectionUtils.isEmpty(methodSet)) {
            return false;
        }
        return methodSet.contains(ALL) || methodSet.contains(methodName);

    }
}
