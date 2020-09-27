/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.spring.boot.wr.separation.invoker;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/9/27 8:15 下午
 * @link : cn.org.easysite.spring.boot.wr.separation.invoker.Processor
 */
@FunctionalInterface
public interface Processor<In> {
    /**
     * Applies this function to the given argument.
     *
     * @param in the function argument
     * @return the function result
     */
    Object invoke(In in) throws Throwable;
}
