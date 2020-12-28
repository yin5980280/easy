package cn.org.easysite.payment.plugin.spi.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019/7/23 7:00 PM
 * @Description : 支付渠道注解
 * @Copyright : Copyright (c) 2018
 * @Company : IOT Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.payment.plugin.spi.annotation.PayChannel
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Channel {

    /**
     * 支付渠道CODE 支付渠道在系统启动时需要获取CODE
     * @return
     */
    String value() default "";
}
