/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.spring.boot.wr.separation.invoker;

import org.springframework.transaction.annotation.Transactional;

import cn.org.easysite.spring.boot.wr.separation.datasource.DynamicRoutingDataSource;
import cn.org.easysite.spring.boot.wr.separation.processor.Processor;
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
     * @param <In>
     * @return
     * @throws Throwable
     */
    public static <In> Object doInvoke(Processor<In> processor, In in, Transactional transactional) throws Throwable {

        //方法优先只读
        boolean isRead = transactional != null && transactional.readOnly();
        if (isRead) {
            DynamicRoutingDataSource.setReadOnly();
        } else {
            DynamicRoutingDataSource.setWriteRead();
        }
        //真正执行方法
        try {
            return processor.invoke(in);
        } finally {
            DynamicRoutingDataSource.reset();
        }
    }
}
