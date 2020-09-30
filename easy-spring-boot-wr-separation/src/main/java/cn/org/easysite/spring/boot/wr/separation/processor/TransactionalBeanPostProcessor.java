/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */

package cn.org.easysite.spring.boot.wr.separation.processor;

import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;

import cn.org.easysite.spring.boot.wr.separation.aop.TransactionalMethodInterceptor;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/9/27 8:15 下午
 * @link : cn.org.easysite.spring.boot.wr.separation.processor.Processor
 */
public class TransactionalBeanPostProcessor extends AbstractAdvisingBeanPostProcessor implements BeanFactoryAware {

    private Class<? extends Annotation> asyncAnnotationType = Transactional.class;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        TransactionalMethodInterceptor transactionalMethodInterceptor = new TransactionalMethodInterceptor();
        TransactionalAdvisor advisor = new TransactionalAdvisor(transactionalMethodInterceptor, this.asyncAnnotationType);
        advisor.setBeanFactory(beanFactory);
        this.advisor = advisor;
    }

}
