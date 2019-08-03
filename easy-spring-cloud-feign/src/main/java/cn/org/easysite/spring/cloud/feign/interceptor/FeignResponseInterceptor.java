package cn.org.easysite.spring.cloud.feign.interceptor;

import com.alibaba.fastjson.JSON;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.Type;

import cn.org.easysite.framework.exception.BaseException;
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
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return Util.emptyValueOf(type);
        }
        if (response.body() == null) {
            return null;
        }
        if (byte[].class.equals(type)) {
            return Util.toByteArray(response.body().asInputStream());
        }
        String json = Util.toString(response.body().asReader());
        cn.org.easysite.spring.boot.model.Response res = JSON.parseObject(json, cn.org.easysite.spring.boot.model.Response.class);
        if (!res.isSuccess()) {
            throw new BaseException(1015, res.getMsg());
        }
        return new ResponseEntityDecoder(new SpringDecoder(this.messageConverters)).decode(response, type);
    }
}
