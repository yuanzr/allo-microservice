package com.dc.allo.rpc.aspect.autolog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenjun on 2020/3/31.
 */
//@Component
//@RefreshScope
public class AllowMethods {

//    @Value(value = "${nacos.config.autolog}")
    String allowMethods;

    public String getAllowMethods() {
        return allowMethods;
    }

    public void setAllowMethods(String allowMethods) {
        this.allowMethods = allowMethods;
    }
}
