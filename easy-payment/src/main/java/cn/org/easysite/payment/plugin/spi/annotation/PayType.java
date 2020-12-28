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
 * @Description : 支付方式注解
 * @Copyright : Copyright (c) 2018
 * @Company : IOT Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.payment.plugin.spi.annotation.PayType
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PayType {

    /**
     * 支付方式code
     * @return
     */
    String value() default "";
}
