package com.vartime.easy.spring.boot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-01-31 09:36
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : KeRuYun Technology(Beijing) Chengdu Co. Ltd.
 * @link : com.vartime.easy.spring.boot.configuration.HttpConnectionProperties
 */
@ConfigurationProperties(prefix = "easy.http.connection")
@Data
public class HttpConnectionProperties {

    private int readTimeOut = 5000;

    private int connectionTimeOut = 1000;
}
