package com.vartime.easy.spring.boot.distributed.lock.spi.redisson.impl;

import com.vartime.easy.spring.boot.distributed.lock.model.LockInfo;
import com.vartime.easy.spring.boot.distributed.lock.spi.redisson.AbstractRedissonLock;

import org.redisson.api.RReadWriteLock;

import java.util.concurrent.TimeUnit;

public class DReadLock extends AbstractRedissonLock {

    private static volatile RReadWriteLock rLock;

    public DReadLock(LockInfo lockInfo) {
        super(lockInfo);
    }

    @Override
    public boolean acquire() {
        try {
            LockInfo lockInfo = getLockInfo();
            rLock = getRedissonClient().getReadWriteLock(lockInfo.getName());
            return rLock.readLock().tryLock(lockInfo.getWaitTime(), lockInfo.getLeaseTime(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void release() {
        if (rLock.readLock().isHeldByCurrentThread()) {
            rLock.readLock().unlockAsync();
        }
    }
}
