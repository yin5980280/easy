/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */

package cn.org.easysite.spring.boot.wr.separation.processor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import cn.org.easysite.spring.boot.wr.separation.aop.TransactionalMethodInterceptor;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/9/27 8:15 下午
 * @link : cn.org.easysite.spring.boot.wr.separation.processor.Processor
 */
public class TransactionalAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {

    private final Advice advice;

    private final Pointcut pointcut;

    /**
     * AOP 方法环绕执行器
     */
    private TransactionalMethodInterceptor transactionalMethodInterceptor;

    /**
     * Create a new {@code AsyncAnnotationAdvisor} for the given task executor.
     * <p>
     */
    public TransactionalAdvisor(TransactionalMethodInterceptor transactionalMethodInterceptor, Class<? extends Annotation> annotationClass) {
        this.transactionalMethodInterceptor = transactionalMethodInterceptor;
        Set<Class<? extends Annotation>> annotationTypes = new LinkedHashSet<Class<? extends Annotation>>(2);
        annotationTypes.add(annotationClass);
        this.advice = buildAdvice();
        this.pointcut = buildPointcut(annotationTypes);
    }

    /**
     * Set the {@code BeanFactory} to be used when looking up executors by qualifier.
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    protected Advice buildAdvice() {
        return transactionalMethodInterceptor;
    }

    /**
     * Calculate a pointcut for the given async annotation types, if any.
     *
     * @param asyncAnnotationTypes the async annotation types to introspect
     * @return the applicable Pointcut object, or {@code null} if none
     */
    protected Pointcut buildPointcut(Set<Class<? extends Annotation>> asyncAnnotationTypes) {
        ComposablePointcut result = null;

        for (Class<? extends Annotation> asyncAnnotationType : asyncAnnotationTypes) {
            Pointcut cpc = new AnnotationMatchingPointcut(asyncAnnotationType, true);
            Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(asyncAnnotationType);
            if (result == null) {
                result = new ComposablePointcut(cpc).union(mpc);
            } else {
                result.union(cpc).union(mpc);
            }
        }
        return result;
    }
}
