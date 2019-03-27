package cn.org.easysite.spring.boot.distributed.lock.annotation;


import cn.org.easysite.spring.boot.distributed.lock.model.LockType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DLock Distributed Lock 分布式锁注解
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
    LockType lockType() default LockType.REENTRANT;

    /**
     * 是否等待到必须执行为止默认为true, false将抛出异常。
     * @return
     */
    boolean tried() default true;

    /**
     * 是否必须上锁成功才继续执行业务 默认为false即不管上锁成功与否都执行，标志为true，上锁不成功会抛出异常
     * @return
     */
    boolean required() default false;

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
     * 自定义业务key，支持表达式
     * @return
     */
     String [] keys() default {};
}
