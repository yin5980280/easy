package com.vartime.easy.spring.boot.distributed.lock.annotation;


import com.vartime.easy.spring.boot.distributed.lock.model.LockType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加锁注解
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface DLock {

    /**
     * 锁的名称
     * @return
     */
    String name() default "";

    /**
     * 锁类型，默认可重入锁
     * @return
     */
    LockType lockType() default LockType.Reentrant;

    /**
     * 是否等待到必须执行为止默认为true, false将抛出异常。
     * @return
     */
    boolean isTry() default true;

    /**
     * 尝试加锁，最多等待时间
     * @return
     */
    long waitTime() default Long.MIN_VALUE;

    /**
     * 上锁以后xxx秒自动解锁
     * @return
     */
    long leaseTime() default Long.MIN_VALUE;

    /**
     * 自定义业务key
     * @return
     */
     String [] keys() default {};
}
