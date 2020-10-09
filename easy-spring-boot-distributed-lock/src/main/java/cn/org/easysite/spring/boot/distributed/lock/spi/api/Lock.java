package cn.org.easysite.spring.boot.distributed.lock.spi.api;

/**
 * 锁操作定义
 * @author 潘多拉
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
