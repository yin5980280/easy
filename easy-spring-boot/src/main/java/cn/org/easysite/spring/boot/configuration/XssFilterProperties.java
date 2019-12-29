package cn.org.easysite.spring.boot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author : 盘达天神
 * @version : 1.0
 * @date : 2019-12-29 13:21
 * @Description :
 * @Copyright : Copyright (c) 2019
 * @Company : EasySite Technology 阿富汗 Co. Ltd.
 * @link : cn.org.easysite.spring.boot.configuration.XssFilterProperties
 */
@ConfigurationProperties("easy.xss.filter")
@Data
public class XssFilterProperties {
    private String enabled = "false";

    private String urlPatterns = "/*";

    private String urlExcludes = "/*";
}
