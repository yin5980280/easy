package com.vartime.easy.spring.boot.distributed.lock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 锁基本信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LockInfo {

    /**
     * 锁类型
     */
    private LockType type;

    /**
     * 是否等待 默认 true 如果是true将一直等待到超时为止，否则直接返回抛出异常
     */
    private boolean tried;

    /**
     * 名字
     */
    private String name;

    /**
     * 等待时间
     */
    private long waitTime;

    /**
     * 等待超时时间
     */
    private long leaseTime;

}
