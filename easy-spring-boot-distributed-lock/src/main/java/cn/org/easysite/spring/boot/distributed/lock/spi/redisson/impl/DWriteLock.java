package cn.org.easysite.spring.boot.distributed.lock.spi.redisson.impl;

import org.redisson.api.RReadWriteLock;

import java.util.concurrent.TimeUnit;

import cn.org.easysite.spring.boot.distributed.lock.model.LockInfo;
import cn.org.easysite.spring.boot.distributed.lock.spi.redisson.AbstractRedissonLock;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 潘多拉
 */
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
