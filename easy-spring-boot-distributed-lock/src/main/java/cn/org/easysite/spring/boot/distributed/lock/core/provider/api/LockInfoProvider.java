package cn.org.easysite.spring.boot.distributed.lock.core.provider.api;

import org.aspectj.lang.ProceedingJoinPoint;

import cn.org.easysite.spring.boot.distributed.lock.annotation.DLock;
import cn.org.easysite.spring.boot.distributed.lock.model.LockInfo;

/**
 * Created by panda on 2017/12/29.
 * @author 潘多拉
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
