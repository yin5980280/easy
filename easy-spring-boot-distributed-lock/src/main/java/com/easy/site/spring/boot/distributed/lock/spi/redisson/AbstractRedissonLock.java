package com.easy.site.spring.boot.distributed.lock.spi.redisson;

import com.easy.site.framework.utils.SpringApplicationUtils;
import com.easy.site.spring.boot.distributed.lock.core.exception.DLockRequiredException;
import com.easy.site.spring.boot.distributed.lock.model.LockInfo;
import com.easy.site.spring.boot.distributed.lock.spi.api.Lock;

import org.redisson.api.RedissonClient;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-03-14 19:42
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : com.easy.site.spring.boot.distributed.lock.spi.redisson.AbstractRedissonLock
 */
public abstract class AbstractRedissonLock implements Lock {

    private final LockInfo lockInfo;

    public AbstractRedissonLock(final LockInfo lockInfo) {
        this.lockInfo = lockInfo;
    }

    protected RedissonClient getRedissonClient() {
        return SpringApplicationUtils.getBean(RedissonClient.class);
    }

    protected LockInfo getLockInfo() {
        return this.lockInfo;
    }

    /**
     * 是否必须上锁
     * @return
     */
    protected boolean required() {
        if (!lockInfo.isRequired()) {
            return false;
        }
        throw new DLockRequiredException("当前执行业务被中断,分布式锁[" + lockInfo.getName() + "]是必须的，当前线程未获取到锁");
    }
}
