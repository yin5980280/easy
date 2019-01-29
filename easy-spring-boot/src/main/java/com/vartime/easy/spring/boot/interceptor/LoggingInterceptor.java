package com.vartime.easy.spring.boot.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2018/11/10 9:13 AM
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : KeRuYun Technology(Beijing) Chengdu Co. Ltd.
 * @link : com.vpay.exchange.core.interceptors.LoggingInterceptor
 */
@Slf4j
public class LoggingInterceptor extends HandlerInterceptorAdapter {

    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        startTime.set(System.currentTimeMillis());
        Map parameterMap = request.getParameterMap();
        log.info("请求URI [{}], 请求参数类型Content-Type [{}], 请求method类型 [{}], 请求处理方法 [{}]", request.getRequestURI(), request.getContentType(), request.getMethod(), handler.toString());
        parameterMap.forEach((k, v) -> log.info("参数名:[{}], 参数值:[{}]", k, v));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("请求处理方法 [{}], 返回响应状态码 [{}], 该请求共花费时间 [{}] ms", handler.toString(),
                    response.getStatus(), System.currentTimeMillis() - startTime.get());
        startTime.remove();
    }
}
