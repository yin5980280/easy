package com.vartime.easy.spring.boot.distributed.lock.spi.redisson.factory;

import com.vartime.easy.spring.boot.distributed.lock.annotation.DLock;
import com.vartime.easy.spring.boot.distributed.lock.spi.api.Lock;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-03-14 20:52
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : KeRuYun Technology(Beijing) Chengdu Co. Ltd.
 * @link : com.vartime.easy.spring.boot.distributed.lock.spi.redisson.factory.LockFactory
 */
public interface LockFactory {
    /**
     * Lock工厂
     * @param joinPoint
     * @param lock
     * @return
     */
    Lock getLock(ProceedingJoinPoint joinPoint, DLock lock);
}
