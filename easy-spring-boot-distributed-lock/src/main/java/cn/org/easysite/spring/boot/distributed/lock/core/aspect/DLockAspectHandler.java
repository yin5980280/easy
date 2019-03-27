package cn.org.easysite.spring.boot.distributed.lock.core.aspect;

import cn.org.easysite.spring.boot.distributed.lock.annotation.DLock;
import cn.org.easysite.spring.boot.distributed.lock.spi.api.Lock;
import cn.org.easysite.spring.boot.distributed.lock.spi.redisson.factory.LockFactory;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class DLockAspectHandler {

    @Autowired
    LockFactory lockFactory;

    /**
     * 切面环绕通知
     * @param joinPoint
     * @param dlock
     * @return
     * @throws Throwable
     */
    @Around(value = "@annotation(dlock)")
    public Object around(ProceedingJoinPoint joinPoint, DLock dlock) throws Throwable {
        Lock lock = lockFactory.getLock(joinPoint, dlock);
        boolean currentThreadLock = false;
        try {
            currentThreadLock = lock.acquire();
            //执行业务，但是lock.acquire可能会上锁失败导致不能执行业务，这里需要业务自己去处理
            return joinPoint.proceed();
        } finally {
            //判断业务是否上锁成功，执行完业务后解锁
            if (currentThreadLock) {
                lock.release();
            }
        }
    }
}
