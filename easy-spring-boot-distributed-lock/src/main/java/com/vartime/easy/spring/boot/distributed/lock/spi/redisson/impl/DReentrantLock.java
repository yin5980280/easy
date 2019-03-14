package com.vartime.easy.spring.boot.distributed.lock.spi.redisson.impl;

import com.vartime.easy.spring.boot.distributed.lock.model.LockInfo;
import com.vartime.easy.spring.boot.distributed.lock.spi.redisson.AbstractRedissonLock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

public class DReentrantLock extends AbstractRedissonLock {

    private static volatile RLock rLock;

    public DReentrantLock(LockInfo lockInfo) {
        super(lockInfo);
    }

    @Override
    public boolean acquire() {
        try {
            LockInfo lockInfo = getLockInfo();
            rLock = getRedissonClient().getLock(lockInfo.getName());
            return rLock.tryLock(lockInfo.getWaitTime(), lockInfo.getLeaseTime(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void release() {
        if (rLock.isHeldByCurrentThread()) {
            rLock.unlockAsync();
        }

    }
}
