package com.vartime.easy.spring.boot.distributed.lock.spi.redisson.impl;

import com.vartime.easy.spring.boot.distributed.lock.model.LockInfo;
import com.vartime.easy.spring.boot.distributed.lock.spi.redisson.AbstractRedissonLock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DFairLock extends AbstractRedissonLock {

    private static volatile RLock rLock;

    public DFairLock(LockInfo lockInfo) {
        super(lockInfo);
    }

    @Override
    public boolean acquire() {
        try {
            LockInfo lockInfo = getLockInfo();
            rLock = getRedissonClient().getFairLock(lockInfo.getName());
            if (lockInfo.isTried() ? rLock.tryLock(lockInfo.getWaitTime(), lockInfo.getLeaseTime(), TimeUnit.SECONDS) : rLock.tryLock()) {
                return true;
            }
            return super.required();
        } catch (InterruptedException e) {
            log.warn(e.getMessage(), e);
            return super.required();
        }
    }

    @Override
    public void release() {
        if (rLock.isHeldByCurrentThread()) {
            rLock.unlockAsync();
        }
    }
}
