package com.easy.site.spring.boot.distributed.lock.spi.redisson.factory;

import com.easy.site.framework.utils.SpringApplicationUtils;
import com.easy.site.spring.boot.distributed.lock.annotation.DLock;
import com.easy.site.spring.boot.distributed.lock.model.LockInfo;
import com.easy.site.spring.boot.distributed.lock.spi.api.Lock;
import com.easy.site.spring.boot.distributed.lock.core.provider.api.LockInfoProvider;
import com.easy.site.spring.boot.distributed.lock.spi.redisson.impl.DFairLock;
import com.easy.site.spring.boot.distributed.lock.spi.redisson.impl.DReadLock;
import com.easy.site.spring.boot.distributed.lock.spi.redisson.impl.DReentrantLock;
import com.easy.site.spring.boot.distributed.lock.spi.redisson.impl.DWriteLock;

import org.aspectj.lang.ProceedingJoinPoint;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DLockFactory implements LockFactory {

    @Override
    public Lock getLock(ProceedingJoinPoint joinPoint, DLock lock) {
        LockInfo lockInfo = getLockInfoProvider().get(joinPoint, lock);
        switch (lockInfo.getType()) {
            case REENTRANT:
                return new DReentrantLock(lockInfo);
            case FAIR:
                return new DFairLock(lockInfo);
            case READ:
                return new DReadLock(lockInfo);
            case WRITE:
                return new DWriteLock(lockInfo);
            default:
                return new DReentrantLock(lockInfo);
        }
    }

    protected LockInfoProvider getLockInfoProvider() {
        return SpringApplicationUtils.getBean(LockInfoProvider.class);
    }

}
