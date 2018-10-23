package com.venusz.admin.common;

import com.venusz.admin.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Http请求前后监听
 */
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {
    /**
     * 拦截器开始之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url= request.getRequestURI().toString();
        Map parameterMap = request.getParameterMap();
        log.info(" HttpInterceptor preHandle  url:{},param:{}",url, JsonUtils.obj2String(parameterMap));
        return true;
    }

    /**
     * 拦截器正常时
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String url= request.getRequestURI().toString();
        Map parameterMap = request.getParameterMap();
        log.info(" HttpInterceptor postHandle  url:{},param:{}",url, JsonUtils.obj2String(parameterMap));
    }

    /**
     * 拦截器一定会执行  类似finally
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url= request.getRequestURI().toString();
        Map parameterMap = request.getParameterMap();
        log.info(" HttpInterceptor afterCompletion  url:{},param:{}",url, JsonUtils.obj2String(parameterMap));

    }
}
