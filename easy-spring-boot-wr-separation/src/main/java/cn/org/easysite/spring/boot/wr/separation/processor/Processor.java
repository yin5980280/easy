/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.spring.boot.wr.separation.processor;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/9/27 8:15 下午
 * @link : cn.org.easysite.spring.boot.wr.separation.processor.Processor
 */
@FunctionalInterface
public interface Processor<In> {

    /**
     * 代理方法执行器
     * @param in
     * @return 执行结果
     * @throws Throwable
     */
    Object invoke(In in) throws Throwable;
}
