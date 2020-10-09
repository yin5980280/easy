package cn.org.easysite.spring.boot.distributed.lock.core.provider.api;

import org.aspectj.lang.ProceedingJoinPoint;

import cn.org.easysite.spring.boot.distributed.lock.annotation.DLock;

/**
 * Created by panda on 2018/1/24.
 * Content : 获取用户定义业务key
 * @author 潘多拉
 */
public interface BusinessKeyProvider {

    /**
     * 获取用户定义业务key
     * @param joinPoint
     * @param lock
     * @return
     */
    String getKeyName(ProceedingJoinPoint joinPoint, DLock lock);
}
