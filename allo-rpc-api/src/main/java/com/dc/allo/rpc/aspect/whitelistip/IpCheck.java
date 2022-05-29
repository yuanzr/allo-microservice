package com.dc.allo.rpc.aspect.whitelistip;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangzhenjun on 2020/5/18.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IpCheck {

    /*
    注意：添加该注解的方法，需要含有 HttpServletRequest 类型参数，否则限制器无效，请求默认通过
     */

}