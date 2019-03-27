package cn.org.easysite.spring.boot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-01-31 09:36
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.spring.boot.configuration.HttpConnectionProperties
 */
@Configuration
@ConfigurationProperties(prefix = "easy.http.connection")
@Data
public class HttpConnectionProperties {

    private int readTimeOut = 5000;

    private int connectionTimeOut = 1000;
}
