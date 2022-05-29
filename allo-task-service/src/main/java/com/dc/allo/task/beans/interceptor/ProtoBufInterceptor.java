package com.dc.allo.task.beans.interceptor;


import com.dc.allo.common.annotation.AutoPbTrans;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.constants.Constant;
import com.dc.allo.common.utils.LanguageUtils;
import com.dc.allo.common.utils.StringUtils;
import com.erban.main.proto.PbCommon;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class ProtoBufInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        //获取语言信息
        String language = request.getHeader("Accept-Language");
        LanguageUtils.setLanguage(language);
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AutoPbTrans autoPbTrans = method.getAnnotation(AutoPbTrans.class);
        //只处理带ProtoBuf注解的请求
        if (autoPbTrans == null || StringUtils.isEmpty(autoPbTrans.paramClassName())) {
            return true;
        }

        PbCommon.PbHttpBizReq pbHttpBizReq = PbCommon.PbHttpBizReq.parseFrom(request.getInputStream());
        if (pbHttpBizReq == null) {
            log.error("ProtoBufInterceptor pbHttpBizReq is null");
            writeResponse(response, BusiStatus.BUSIERROR);
            return false;
        }

        log.info("ProtoBufInterceptor pbHttpBizReq info:{}",pbHttpBizReq.toString());

        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        httpServletRequest.setAttribute(Constant.proto_interceptor_attr_name,pbHttpBizReq);

        return true;
    }

    /**
     * 返回提示内容
     *
     * @param response
     * @param code
     * @param message
     * @throws IOException
     */
    protected void writeResponse(HttpServletResponse response, int code, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write("{\n" + "\"code\": " + code + ",\n" +"\"message\": \"" + message + "\"\n" +"}");
        printWriter.flush();
        printWriter.close();
    }

    /**
     * 根据提示码返回内容
     *
     * @param response
     * @param busiStatus
     * @throws IOException
     */
    protected void writeResponse(HttpServletResponse response, BusiStatus busiStatus) throws IOException {
        this.writeResponse(response, busiStatus.value(), busiStatus.getReasonPhrase());
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
