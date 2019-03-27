package com.easy.site.spring.boot.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2018/11/9 1:54 PM
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : com.vpay.exchange.core.base.validate.Validate
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validate {
    //需要校验的对象在目标方法参数列表中的序号，从0开始
    int reqParamIndex() default 0;

    Class<?>[] groups() default {};
}
