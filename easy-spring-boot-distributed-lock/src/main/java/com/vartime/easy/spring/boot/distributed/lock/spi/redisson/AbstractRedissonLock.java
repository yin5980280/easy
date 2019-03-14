package com.vartime.easy.spring.boot.distributed.lock.spi.redisson;

import com.vartime.easy.framework.utils.SpringApplicationUtils;
import com.vartime.easy.spring.boot.distributed.lock.model.LockInfo;
import com.vartime.easy.spring.boot.distributed.lock.spi.api.Lock;

import org.redisson.api.RedissonClient;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-03-14 19:42
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : KeRuYun Technology(Beijing) Chengdu Co. Ltd.
 * @link : com.vartime.easy.spring.boot.distributed.lock.spi.redisson.AbstractRedissonLock
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
}
