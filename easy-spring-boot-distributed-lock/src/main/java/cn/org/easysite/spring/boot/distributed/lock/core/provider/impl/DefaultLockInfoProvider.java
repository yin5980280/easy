package cn.org.easysite.spring.boot.distributed.lock.core.provider.impl;

import cn.org.easysite.spring.boot.distributed.lock.annotation.DLock;
import cn.org.easysite.spring.boot.distributed.lock.model.LockInfo;
import cn.org.easysite.spring.boot.distributed.lock.model.LockType;
import cn.org.easysite.spring.boot.distributed.lock.core.config.DLockConfig;
import cn.org.easysite.spring.boot.distributed.lock.core.provider.api.BusinessKeyProvider;
import cn.org.easysite.spring.boot.distributed.lock.core.provider.api.LockInfoProvider;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by panda on 2017/12/29.
 */
public class DefaultLockInfoProvider implements LockInfoProvider {

    public static final String LOCK_NAME_PREFIX = "lock";

    public static final String LOCK_NAME_SEPARATOR = ".";

    @Autowired
    private DLockConfig lockConfig;

    @Autowired
    private BusinessKeyProvider businessKeyProvider;

    @Override
    public LockInfo get(ProceedingJoinPoint joinPoint, DLock lock) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LockType type = lock.lockType();
        String businessKeyName = businessKeyProvider.getKeyName(joinPoint, lock);
        String lockName = LOCK_NAME_PREFIX + LOCK_NAME_SEPARATOR + getName(lock.name(), signature)+businessKeyName;
        long waitTime = getWaitTime(lock);
        long leaseTime = getLeaseTime(lock);
        return new LockInfo(type, lock.tried(), lock.required(), lockName, waitTime, leaseTime);
    }

    private String getName(String annotationName, MethodSignature signature) {
        if (annotationName.isEmpty()) {
            return String.format("%s.%s", signature.getDeclaringTypeName(), signature.getMethod().getName());
        } else {
            return annotationName;
        }
    }

    private long getWaitTime(DLock lock) {
        return lock.waitTime() == Long.MIN_VALUE ? lockConfig.getWaitTime() : lock.waitTime();
    }

    private long getLeaseTime(DLock lock) {
        return lock.leaseTime() == Long.MIN_VALUE ? lockConfig.getLeaseTime() : lock.leaseTime();
    }
}
