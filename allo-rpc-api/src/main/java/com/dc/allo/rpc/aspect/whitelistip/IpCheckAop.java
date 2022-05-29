package com.dc.allo.rpc.aspect.whitelistip;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rpc.api.utils.HttpUtils;
import com.dc.allo.rpc.proto.AlloResp;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.utils.ConcurrentHashSet;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhenjun on 2020/5/18.
 */
@Aspect
@Slf4j
public class IpCheckAop {

    @Autowired
    private ApplicationContext appContext;

    @Pointcut("@annotation(com.dc.allo.rpc.aspect.whitelistip.IpCheck)")
    public void recordIpCheck() {}

    @Around("recordIpCheck()")
    public Object dealIpCheck(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            final MethodInvocationProceedingJoinPoint joinPoint = (MethodInvocationProceedingJoinPoint) proceedingJoinPoint;
            Object[] args = joinPoint.getArgs();

            boolean isPass = true;
            String ip = "unknow";
            try {
                ip = getIp(args);
                log.info("[IpCheck]getIp:" + ip);
                if (ip != null) {
                    ip = ip.replaceAll(" ", "");
                    String[] ipList = ip.split(",");
                    if (ipList.length > 1) {
                        ip = ipList[0];
                    }
                    log.info("[IpCheck]using ip:" + ip);
                    if (!defaultPass(ip)) {
                        isPass = isPass(ip);
                    }
                }
            } catch (NoSuchBeanDefinitionException nbe) {
                log.info("[IpCheck]找不到实现 AbstractIpCheckAdapter 的bean, 校验失效请求通过, ip:" + ip);
            } catch (Exception e) {
                log.error("[IpCheck]err:" + e.getMessage(), e);
            }

            if (isPass) {
                try {
                    return proceedingJoinPoint.proceed(args);
                } catch (Throwable throwable) {
                    log.error("[IpCheck]" + throwable.getMessage(), throwable);
                    return null;
                }
            } else {
                log.error("[IpCheck]请求被拦截, ip不在白名单内, ip:" + ip);
                return AlloResp.failed(BusiStatus.SERVER_BUSY.value(), "ip不通过", null);
            }

        } catch (Exception e) {
            log.error("[IpCheck]异常" + e.getMessage(), e);
            return null;
        }
    }



    private static ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));
    private LoadingCache<String, IpCheckConfig> cache = CacheBuilder.newBuilder()
            .maximumSize(10)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .refreshAfterWrite(61, TimeUnit.SECONDS)
            .build(new CacheLoader<String, IpCheckConfig>() {
                @SuppressWarnings("NullableProblems")
                public IpCheckConfig load(String key) throws Exception {
                    return getIpCheckConfigResp();
                }
                @Override
                public ListenableFuture<IpCheckConfig> reload(String key, IpCheckConfig oldValue) throws Exception {
                    return executorService.submit(() -> load(key));
                }
            });

    private boolean isPass(String ip) {
        boolean isPass = true;
        AbstractIpCheckAdapter adapter;
        try {
            adapter = appContext.getBean(AbstractIpCheckAdapter.class);
        } catch (BeansException e) {
            adapter = null;
        }
        if (adapter != null) {
            isPass = adapter.isPass(ip);
        } else {

            try {
                IpCheckConfig conf = cache.get("true");
                if (conf.isUsingBlacklist()) {
                    Set<String> blacklist = conf.getBlacklist();
                    if (blacklist != null && blacklist.contains(ip)) {
                        isPass = false;
                    }
                }
                if (conf.isUsingWhitelist()) {
                    Set<String> whitelist = conf.getWhitelist();
                    if (whitelist != null && !whitelist.contains(ip)) {
                        isPass = false;
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return isPass;
    }

    /**
     * 后续可换成配置化
     * @return
     */
    private IpCheckConfig getIpCheckConfigResp() {
        IpCheckConfig def = new IpCheckConfig();
        def.setUsingWhitelist(true);
        def.setUsingBlacklist(false);
        Set<String> whitelist = new ConcurrentHashSet<>();
        whitelist.add("47.91.115.0");
        whitelist.add("47.91.110.137");
        whitelist.add("47.91.106.148");
        whitelist.add("47.91.107.43");
        whitelist.add("47.91.107.45");
        whitelist.add("47.91.124.110");
        def.setWhitelist(whitelist);
        return def;
    }

    private String getIp(Object[] args) {
        HttpServletRequest request = null;
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
            }
        }
        try {
            if (request == null) {
                return null;
            }
            return HttpUtils.getIpAddress(request);
        } catch (Exception e) {
            log.error("[IpCheck] getIpAddress error.", e);
            return null;
        }
    }

    private boolean defaultPass(String ip) {
        if (ip == null) {
            return true;
        }
        ip = ip.trim();
        if ("".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip) || "localhost".equals(ip)) {
            return true;
        } else {
            return false;
        }
    }

}
