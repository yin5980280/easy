package com.vartime.easy.spring.boot.distributed.lock.spi.api;

/**
 * 锁操作定义
 */
public interface Lock {

    /**
     * 获取锁
     * @return
     */
    boolean acquire();

    /**
     * 释放锁
     */
    void release();
}
