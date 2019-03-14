package com.vartime.easy.spring.boot.distributed.lock.model;

/**
 * Created by kl on 2017/12/29.
 * Content :锁类型
 */
public enum LockType {

    /**
     * 可重入锁
     */
    REENTRANT,

    /**
     * 公平锁
     */
    FAIR,

    /**
     * 读锁
     */
    READ,

    /**
     * 写锁
     */
    WRITE;

    LockType() {
    }

}
