package com.easy.site.spring.boot.distributed.lock.spi.redisson.impl;

import com.easy.site.spring.boot.distributed.lock.model.LockInfo;
import com.easy.site.spring.boot.distributed.lock.spi.redisson.AbstractRedissonLock;

import org.redisson.api.RReadWriteLock;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DWriteLock extends AbstractRedissonLock {

    private static volatile RReadWriteLock rLock;

    public DWriteLock(LockInfo lockInfo) {
        super(lockInfo);
    }

    @Override
    public boolean acquire() {
        try {
            LockInfo lockInfo = getLockInfo();
            rLock = getRedissonClient().getReadWriteLock(lockInfo.getName());
            if (lockInfo.isTried() ? rLock.writeLock().tryLock(lockInfo.getWaitTime(), lockInfo.getLeaseTime(), TimeUnit.SECONDS) : rLock.writeLock().tryLock()) {
                return true;
            }
            return super.required();
        } catch (InterruptedException e) {
            return super.required();
        }
    }

    @Override
    public void release() {
        if (rLock.writeLock().isHeldByCurrentThread()) {
            rLock.writeLock().unlockAsync();
        }
    }
}
