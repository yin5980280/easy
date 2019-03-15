package com.vartime.easy.spring.boot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-01-30 19:06
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : com.vartime.easy.spring.boot.configuration.IgnoredGlobalReturnValuePath
 */
@Configuration
@ConfigurationProperties("easy.global.json.value.path")
@Data
public class GlobalReturnValuePathProperties {

    private String[] ignored;

    private String[] notStartWith;

    private String[] startWith;
}
