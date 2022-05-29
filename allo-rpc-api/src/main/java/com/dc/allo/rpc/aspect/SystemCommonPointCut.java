package com.dc.allo.rpc.aspect;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by zhangzhenjun on 2020/3/22.
 */
public class SystemCommonPointCut {
    public final static String COMMON_POINT_CUT_PKG = "com.dc.allo.rpc.aspect.SystemCommonPointCut.";
    public final static String ALL_CONTROLLER = "allControllerMethod()";
    public final static String ALL_SERVICE = "allServiceMethod()";
    public final static String NEED_AUTO_LOG = "needAutoLogMethod()";
    public final static String KAFKA_LISTENER_SENDER = "kafka()";
    public final static String MQ_LISTENER_SENDER = "mq()";
    public final static String MYBATIS_MAPPER = "mapper()";



    /**
     * 切所有com.dc.allo..controller包下的所有方法(仅限于被spring-aop机制执行的方法 self-Invocation是切不到的)
     */
    @Pointcut("within(com.dc.allo..*.controller..*)")
    public void allControllerMethod() {}

    /**
     * 切所有com.dc.allo..service包下的所有方法(仅限于被spring-aop机制执行的方法 self-Invocation是切不到的)
     */
    @Pointcut("within(com.dc.allo..*.service..*)")
    public void allServiceMethod() {}

    /**
     * 切所有@AutoLog注解的方法(仅限于被spring-aop机制执行的方法 self-Invocation是切不到的)
     * 非controller和非service包下的方法，需要用该注解才能auto log
     */
    @Pointcut("@annotation(com.dc.allo.rpc.aspect.autolog.AutoLog)")
    public void needAutoLogMethod() {}

    /**
     * 切所有kafka生产者和消费者中的方法(仅限于被spring-aop机制执行的方法 self-Invocation是切不到的)
     */
    @Pointcut("within(com.dc.allo..*.kafka..*)")
    public void kafka() {}

    /**
     * 切所有mq生产者和消费者的方法(仅限于被spring-aop机制执行的方法 self-Invocation是切不到的)
     */
    @Pointcut("within(com.dc.allo..*.mq..*)")
    public void mq() {}

    /**
     * 切所有mybatis mapper的方法（mybatis mapper的接口都是被代理的，所以都能切到）
     */
    @Pointcut("within(com.dc.allo..*.mapper..*)")
    public void mapper() {}

}
