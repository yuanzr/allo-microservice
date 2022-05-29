package com.dc.allo.user.beans.interceptor;

import com.dc.allo.common.utils.LanguageUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @ClassName: CommonInterceptor
 * @Description: 通用拦截器
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2019/9/27/14:18
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String language = request.getHeader("Accept-Language");//获取语言信息
        LanguageUtils.setLanguage(language);//设置语言环境
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}