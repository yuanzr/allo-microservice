package com.dc.allo.rpc.aspect.autolog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dc.allo.common.component.threadpool.DCCommonThreadPool;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.constants.Constant;
import com.dc.allo.rpc.api.sysconf.DcSysConfService;
import com.dc.allo.rpc.domain.sysconf.SysConf;
import com.dc.allo.rpc.proto.AlloResp;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.dc.allo.rpc.aspect.SystemCommonPointCut.*;

/**
 * 自动日志切面
 * Created by zhangzhenjun on 2020/3/22.
 */

//@Component
//@Aspect
public class AutoLogAspect {

    @Reference
    DcSysConfService dcSysConfService;

    @Autowired
    DCCommonThreadPool dcCommonThreadPool;

    private static final Logger logger = LoggerFactory.getLogger(AutoLogAspect.class);

    private static final String POINT_CUT = COMMON_POINT_CUT_PKG + ALL_CONTROLLER
            + "||" + COMMON_POINT_CUT_PKG + ALL_SERVICE
            + "||" + COMMON_POINT_CUT_PKG + KAFKA_LISTENER_SENDER
            + "||" + COMMON_POINT_CUT_PKG + MQ_LISTENER_SENDER
            + "||" + COMMON_POINT_CUT_PKG + MYBATIS_MAPPER
            + "||" + COMMON_POINT_CUT_PKG + NEED_AUTO_LOG;

    /**
     * 允许自动打印日志方法集合的内存缓存
     * 写入1分钟后，如果有query，异步refresh
     */
    private LoadingCache<String, AllowAutoLogMethods> allowAutoLogMethodsCache = CacheBuilder.newBuilder()
            .maximumSize(200)
            .refreshAfterWrite(1, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<String, AllowAutoLogMethods>() {
                        public AllowAutoLogMethods load(String key) {
                            return getAllowAutoLogMethodsConfig();
                        }

                        public ListenableFuture<AllowAutoLogMethods> reload(final String key, AllowAutoLogMethods prevGraph) {
                            ListenableFutureTask<AllowAutoLogMethods> task = ListenableFutureTask.create(
                                    () -> getAllowAutoLogMethodsConfig());
                            dcCommonThreadPool.execute(task);
                            return task;
                        }
                    });

    /**
     * 自动日志切面
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(POINT_CUT)
    public Object autoLog(ProceedingJoinPoint pjp) throws Throwable {
        Signature sig = pjp.getSignature();
        if (!(sig instanceof MethodSignature)) {
            return executeWithoutLog(pjp);
        }
        String targetFullClassName = sig.getDeclaringTypeName();
        if (StringUtils.isBlank(targetFullClassName)) {
            targetFullClassName = "unknownClass";
        }
        String targetMethodName = sig.getName();
        if (StringUtils.isBlank(targetMethodName)) {
            targetMethodName = "unknownMethod";
        }
        boolean allowAutoLog = allowAutoLog(targetFullClassName, targetMethodName);
        if (allowAutoLog) {
            return executeWithLog(pjp, targetFullClassName, targetMethodName);
        } else {
            return executeWithoutLog(pjp);
        }
    }

    /**
     * 获取入参的类型名称集合
     *
     * @param args
     * @return
     */
    private String[] getArgsClassName(Object[] args) {
        if (args == null) {
            return null;
        }
        String[] argsClassName = null;
        try {
            argsClassName = new String[args.length];
            int index = 0;
            for (Object arg : args) {
                argsClassName[index++] = arg == null ? "null" : arg.getClass().getSimpleName();
            }

        } catch (Exception e) {
            logger.error("[AutoLog] inner error of getArgsClassName. args:{}", (Object) args, e);
        }
        return argsClassName;
    }

    /**
     * 用来json转map<string,set>
     *
     * @param json
     * @param keyType
     * @param valueType
     * @param <K>
     * @param <V>
     * @return
     */
    private <K, V> Map<K, V> parseToMap(String json,
                                        Class<String> keyType,
                                        Class<? extends HashSet> valueType) {
        return JSON.parseObject(json,
                new TypeReference<Map<K, V>>(keyType, valueType) {
                });
    }

    /**
     * 获取允许自动打印日志方法列表的系统配置
     *
     * @return
     */
    private AllowAutoLogMethods getAllowAutoLogMethodsConfig() {
        try {
            AlloResp<SysConf> resp = dcSysConfService.getSysConfById(Constant.SysConfId.Auto_Log_Methods);
            if (resp != null && resp.getCode() == BusiStatus.SUCCESS.value() && resp.getData() !=null && StringUtils.isNotBlank(resp.getData().getConfigValue())) {
                Map<String, Set<String>> map = parseToMap(resp.getData().getConfigValue(), String.class, new HashSet<String>().getClass());
                AllowAutoLogMethods methods = new AllowAutoLogMethods();
                methods.setMethodSets(map);
                logger.info("[AutoLog] AllowAutoLogMethods refreshed, new val:{}", methods);
                return methods;
            }

        } catch (Exception e) {
            logger.error("[AutoLog] inner error of getAllowAutoLogMethodsConfig.", e);
        }
        return new AllowAutoLogMethods();
    }

    /**
     * 判断是否当前方法是否允许打印日志
     *
     * @param targetClassName
     * @param targetMethodName
     * @return
     */
    private boolean allowAutoLog(String targetClassName, String targetMethodName) {
        //日志白名单要异步的加载，加载成功与否不能影响逻辑正常执行
        try {
            return allowAutoLogMethodsCache.get("").contains(targetClassName, targetMethodName);
        } catch (ExecutionException e) {
            logger.error("[AutoLog] inner error of needAutoLog targetClassName:{} targetMethodName:{}.", targetClassName, targetMethodName, e);
        }
        //异常或者取到空名单则不
        return false;
    }

    /**
     * 执行并打印入参出参
     *
     * @param pjp
     * @param targetClassName
     * @param targetMethodName
     * @return
     * @throws Throwable
     */
    private Object executeWithLog(ProceedingJoinPoint pjp, String targetClassName, String targetMethodName) throws Throwable {
        String contextUUID = UUID.randomUUID().toString();
        Object[] args = pjp.getArgs();
        //打印入参类型、名字、值
        boolean hasParam = args != null && args.length > 0;
        logger.info("[AutoLog]-[in] class: {} method: {} argTypes: {} argVals: {} context: {}",
                targetClassName, targetMethodName,
                !hasParam ? "EMPTY_TYPES" : (Object) getArgsClassName(args),
                !hasParam ? "EMPTY_VALS" : (Object) args, contextUUID);
        try {
            //执行逻辑
            long start = System.nanoTime();
            Object returnVal = pjp.proceed(args);
            long end = System.nanoTime();
            String cost = String.format("%.3f", (end - start) / 1000000.0);
            //如果有返回值，打印返回值类型、值
            if (returnVal != null) {
                logger.info("[AutoLog]-[out] class: {} method: {} cost: {}ms returnValType: {} returnVal: {} context: {}",
                        targetClassName, targetMethodName, cost,
                        returnVal.getClass().getSimpleName(), returnVal, contextUUID);
            } else {
                logger.info("[AutoLog]-[out] class: {} method: {} cost: {}ms returnValType: return null or no return returnVal: return null or no return context: {}",
                        targetClassName, targetMethodName, cost, contextUUID);
            }
            return returnVal;
        } catch (Throwable throwable) {
            logger.error("[AutoLog]-[exception] class: {} method: {} context: {} msg: {} ",
                    targetClassName, targetMethodName, contextUUID, throwable.getMessage(), throwable);
            throw throwable;
        }
    }

    /**
     * 直接执行
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    private Object executeWithoutLog(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed(pjp.getArgs());
    }

}
