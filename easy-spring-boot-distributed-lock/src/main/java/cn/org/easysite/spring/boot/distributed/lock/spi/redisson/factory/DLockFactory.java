package cn.org.easysite.spring.boot.distributed.lock.spi.redisson.factory;

import org.aspectj.lang.ProceedingJoinPoint;

import cn.org.easysite.framework.utils.SpringApplicationUtils;
import cn.org.easysite.spring.boot.distributed.lock.annotation.DLock;
import cn.org.easysite.spring.boot.distributed.lock.core.provider.api.LockInfoProvider;
import cn.org.easysite.spring.boot.distributed.lock.model.LockInfo;
import cn.org.easysite.spring.boot.distributed.lock.spi.api.Lock;
import cn.org.easysite.spring.boot.distributed.lock.spi.redisson.impl.DFairLock;
import cn.org.easysite.spring.boot.distributed.lock.spi.redisson.impl.DReadLock;
import cn.org.easysite.spring.boot.distributed.lock.spi.redisson.impl.DReentrantLock;
import cn.org.easysite.spring.boot.distributed.lock.spi.redisson.impl.DWriteLock;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 潘多拉
 */
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
