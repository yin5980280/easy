/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.spring.boot.wr.separation.invoker;

import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

import cn.org.easysite.spring.boot.wr.separation.datasource.DynamicRoutingDataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/9/27 8:16 下午
 * @link : cn.org.easysite.spring.boot.wr.separation.invoker.TransactionalInvoker
 */
@Slf4j
public class TransactionalInvoker {

    /**
     * 事物执行器
     * @param processor
     * @param in
     * @param bean
     * @param method
     * @param <In>
     * @return
     * @throws Throwable
     */
    public static <In> Object invoke(Processor<In> processor, In in, Object bean, Method method) throws Throwable {
        Transactional clazzMarkTransactional = bean.getClass().getAnnotation(Transactional.class);
        Transactional methodMarkTransactional = method.getAnnotation(Transactional.class);
        //方法优先只读
        boolean isRead = (methodMarkTransactional != null && methodMarkTransactional.readOnly())
                        || (clazzMarkTransactional != null && clazzMarkTransactional.readOnly());
        //如果是只读
        String className = bean.getClass().getCanonicalName();
        if (isRead) {
            if (log.isDebugEnabled()) {
                log.debug("reset jdbc transaction type to read datasource, service method: {}.{} , readOnly={}", className, method.getName(), isRead);
            }
            DynamicRoutingDataSource.setReadOnly();
        } else {
            DynamicRoutingDataSource.setWriteRead();
            if (clazzMarkTransactional == null && methodMarkTransactional == null) {
               if (log.isDebugEnabled()) {
                   log.debug("transactional type not set, default is write datasource, service method: {}.{}, readOnly={}", className, method.getName(), isRead);
               }
            }
        }
        //真正执行方法
        try {
            return processor.invoke(in);
        } finally {
            DynamicRoutingDataSource.reset();
        }
    }
}
