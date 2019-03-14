package com.vartime.easy.spring.boot.distributed.lock.core.provider.api;

import com.vartime.easy.spring.boot.distributed.lock.annotation.DLock;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Created by panda on 2018/1/24.
 * Content :获取用户定义业务key
 */
public interface BusinessKeyProvider {

    /**
     * 获取用户定义业务key
     * @param joinPoint
     * @param lock
     * @return
     */
    String getKeyName(ProceedingJoinPoint joinPoint, DLock lock);
}
