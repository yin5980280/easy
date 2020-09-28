/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.spring.boot.wr.separation.invoker;

import org.aopalliance.intercept.Joinpoint;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.annotation.Transactional;

import static cn.org.easysite.spring.boot.wr.separation.invoker.TransactionalInvoker.doInvoke;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/9/28 8:40 上午
 * @link : cn.org.easysite.spring.boot.wr.separation.invoker.TransactionalMethodInterceptor
 */
public class TransactionalMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Transactional transactional = methodInvocation.getMethod().getAnnotation(Transactional.class);
        transactional = transactional == null ? methodInvocation.getThis().getClass().getAnnotation(Transactional.class) : transactional;
        return doInvoke(Joinpoint::proceed, methodInvocation, transactional);
    }
}
