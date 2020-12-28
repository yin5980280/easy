/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.payment.plugin.support.spring.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.org.easysite.payment.plugin.support.spring.register.ChannelPluginsRegister;
import cn.org.easysite.payment.plugin.support.spring.register.ChannelPluginsScannerRegister;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/12/27 3:21 下午
 * @link : cn.org.easysite.payment.plugin.support.spring.annotation.EnableChannelPlugins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({ChannelPluginsScannerRegister.class, ChannelPluginsRegister.class})
public @interface EnablePaymentPluginRegistries {

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};
}
