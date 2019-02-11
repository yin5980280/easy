package com.vartime.easy.spring.cloud.feign.configuration;

import com.vartime.easy.spring.cloud.feign.interceptor.FeignResponseInterceptor;

import org.springframework.context.annotation.Configuration;

import feign.codec.Decoder;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-02-08 17:18
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : KeRuYun Technology(Beijing) Chengdu Co. Ltd.
 * @link : com.vartime.easy.spring.cloud.feign.configuration.FeignConfiguration
 */
@Configuration
public class FeignConfiguration {
    //@Bean
    public Decoder getFeignResponseInterceptor() {
        return new FeignResponseInterceptor();
    }

}
