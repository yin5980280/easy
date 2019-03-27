package cn.org.easysite.spring.boot.distributed.lock.spi.redisson.impl;

import cn.org.easysite.spring.boot.distributed.lock.model.LockInfo;
import cn.org.easysite.spring.boot.distributed.lock.spi.redisson.AbstractRedissonLock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            if (lockInfo.isTried() ? rLock.tryLock(lockInfo.getWaitTime(), lockInfo.getLeaseTime(), TimeUnit.SECONDS) : rLock.tryLock()) {
                return true;
            }
            return super.required();
        } catch (InterruptedException e) {
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
