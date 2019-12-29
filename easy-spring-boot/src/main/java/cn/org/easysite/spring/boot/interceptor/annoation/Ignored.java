package cn.org.easysite.spring.boot.interceptor.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : 盘达天神
 * @version : 1.0
 * @date : 2019-12-29 12:46
 * @Description :
 * @Copyright : Copyright (c) 2019
 * @Company : EasySite Technology 阿富汗 Co. Ltd.
 * @link : cn.org.easysite.spring.boot.interceptor.annoation.Ignored
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Ignored {
}
