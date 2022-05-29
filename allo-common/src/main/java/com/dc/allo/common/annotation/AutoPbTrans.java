package com.dc.allo.common.annotation;

import java.lang.annotation.*;

/**
 * 该注解仅用于Controller层
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface AutoPbTrans {
    int module();
    String clzFullName();
    String paramClassName() default "";
}
