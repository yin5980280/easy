package cn.org.easysite.spring.boot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-01-30 19:06
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.spring.boot.configuration.IgnoredGlobalReturnValuePath
 */
@ConfigurationProperties("easy.global.json.value.path")
@Data
public class GlobalReturnValuePathProperties {

    /**
     * 过滤掉的url
     */
    private String[] excludes;
}
