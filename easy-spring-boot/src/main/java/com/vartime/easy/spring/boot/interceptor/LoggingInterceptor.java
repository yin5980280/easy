package com.vartime.easy.spring.boot.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2018/11/10 9:13 AM
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : com.vpay.exchange.core.interceptors.LoggingInterceptor
 */
@Slf4j
public class LoggingInterceptor extends HandlerInterceptorAdapter {

    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        startTime.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex == null) {
            log.info("请求处理方法 [{}], 返回响应状态码 [{}], 该请求共花费时间 [{}] ms",
                    handler.toString(),
                    response.getStatus(),
                    System.currentTimeMillis() - startTime.get());
        }
        removeData();
    }

    private void removeData() {
        startTime.remove();
    }
}
