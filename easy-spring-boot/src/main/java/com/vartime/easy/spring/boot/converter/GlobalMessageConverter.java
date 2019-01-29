package com.vartime.easy.spring.boot.converter;

import com.vartime.easy.spring.boot.response.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.spring.web.json.Json;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-01-11 19:02
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : KeRuYun Technology(Beijing) Chengdu Co. Ltd.
 * @link : com.platform.payment.framework.web.interceptor.GlobalMessageConverter
 */
@Slf4j
public class GlobalMessageConverter extends MappingJackson2HttpMessageConverter {

    @Value("${springfox.documentation.swagger.v2.path:v2/api-docs}")
    private String swaggerApiPath;

    private static final String SWAGGER_PATH = "swagger";

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        if (requestURI.contains(SWAGGER_PATH) || requestURI.contains(swaggerApiPath)) {
            super.writeInternal(object, type, outputMessage);
            return;
        }
        if (type instanceof Json || type instanceof Response) {
            super.writeInternal(object, type, outputMessage);
            return;
        }
        super.writeInternal(Response.build(object), Response.class, outputMessage);
    }
}
