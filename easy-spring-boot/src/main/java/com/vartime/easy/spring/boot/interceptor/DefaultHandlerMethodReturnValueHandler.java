package com.vartime.easy.spring.boot.interceptor;

import com.alibaba.fastjson.JSON;
import com.vartime.easy.commons.base.Response;
import com.vartime.easy.framework.utils.SpringApplicationUtils;
import com.vartime.easy.spring.boot.configuration.GlobalReturnValuePathProperties;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.spring.web.json.Json;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-01-30 16:29
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : KeRuYun Technology(Beijing) Chengdu Co. Ltd.
 * @link : com.vartime.easy.spring.boot.interceptor.DefaultHandlerMethodReturnValueHandler
 */
@Slf4j
public class DefaultHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    public DefaultHandlerMethodReturnValueHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

        if (this.needGlobal(returnValue, returnType, mavContainer, webRequest)) {
            Response res = Response.build(returnValue);
            if (log.isDebugEnabled()) {
                log.debug("接口返回数据结果[{}]", JSON.toJSONString(res));
            }
            delegate.handleReturnValue(res, returnType, mavContainer, webRequest);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("接口返回数据结果[{}]", JSON.toJSONString(returnValue));
            }
            delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
        }
    }

    private boolean needGlobal(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) {
        String uri = webRequest.getNativeRequest(HttpServletRequest.class).getServletPath();
        GlobalReturnValuePathProperties pathProperties = SpringApplicationUtils.getBean(GlobalReturnValuePathProperties.class);
        boolean need = true;
        if (pathProperties.getStartWith() != null && pathProperties.getStartWith().length > 0) {
            need = startWith(pathProperties.getStartWith(), uri);
        }
        if (need && pathProperties.getNotStartWith() != null && pathProperties.getNotStartWith().length > 0) {
            need = !startWith(pathProperties.getNotStartWith(), uri);
        }
        if (need && pathProperties.getIgnored() != null && pathProperties.getIgnored().length > 0) {
            need = !ArrayUtils.contains(pathProperties.getIgnored(), uri);
        }
        if (need) {
            return !(returnValue instanceof Json || returnValue instanceof Response);
        }
        return false;
    }

    private boolean startWith(String[] paths, String path) {
        for (String p : paths) {
            if (path.startsWith(p)) {
                return true;
            }
        }
        return false;
    }
}
