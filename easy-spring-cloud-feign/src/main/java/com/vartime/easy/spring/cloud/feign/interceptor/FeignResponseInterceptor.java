package com.vartime.easy.spring.cloud.feign.interceptor;

import com.alibaba.fastjson.JSON;
import com.vartime.easy.framework.exception.BaseException;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;

import java.io.IOException;
import java.lang.reflect.Type;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;


/**
 * Created by panda @version 1.0
 */
public class FeignResponseInterceptor extends Decoder.Default implements Decoder {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        if (response.status() == 404) {
            return Util.emptyValueOf(type);
        }
        if (response.body() == null) {
            return null;
        }
        if (byte[].class.equals(type)) {
            return Util.toByteArray(response.body().asInputStream());
        }
        String json = Util.toString(response.body().asReader());
        com.vartime.easy.commons.base.Response res = JSON.parseObject(json, com.vartime.easy.commons.base.Response.class);
        if (!res.isSuccess()) {
            throw new BaseException(1015, res.getMsg());
        }
        return new ResponseEntityDecoder(new SpringDecoder(this.messageConverters)).decode(response, type);
    }
}
