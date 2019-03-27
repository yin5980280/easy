package cn.org.easysite.spring.boot.distributed.lock.core.provider.api;

import cn.org.easysite.spring.boot.distributed.lock.annotation.DLock;
import cn.org.easysite.spring.boot.distributed.lock.model.LockInfo;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Created by panda on 2017/12/29.
 */
public interface LockInfoProvider {

    /**
     * 获取上锁详情
     * @param joinPoint
     * @param lock
     * @return
     */
    LockInfo get(ProceedingJoinPoint joinPoint, DLock lock);
}
