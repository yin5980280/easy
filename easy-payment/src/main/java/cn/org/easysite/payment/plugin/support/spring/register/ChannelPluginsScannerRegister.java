/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.payment.plugin.support.spring.register;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.org.easysite.payment.plugin.spi.annotation.Channel;
import cn.org.easysite.payment.plugin.support.spring.annotation.EnablePaymentPluginRegistries;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/12/27 3:22 下午
 * @link : cn.org.easysite.payment.plugin.support.spring.register.ChannelPluginsScannerRegister
 */
@Slf4j
public class ChannelPluginsScannerRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanClassLoaderAware, EnvironmentAware {

    private ResourceLoader resourceLoader;

    private ClassLoader classLoader;

    private Environment environment;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        Set<String> basePackages = getBasePackages(importingClassMetadata);
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Channel.class));

        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
            for (BeanDefinition candidateComponent : candidateComponents) {
                if (candidateComponent instanceof AnnotatedBeanDefinition) {
                    AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                    AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                    this.registerPluginBean(registry, annotationMetadata);
                }
            }
        }
    }

    /**
     * 注册bean
     * @param registry
     * @param annotationMetadata
     */
    private void registerPluginBean(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata) {
        try {
            String className = annotationMetadata.getClassName();
            Class<?> beanClazz = Class.forName(className);
            if (!beanClazz.isAnnotationPresent(Channel.class)) {
                throw new RuntimeException("@Channel is required!");
            }
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
            BeanDefinition definition = builder.getRawBeanDefinition();
            registry.registerBeanDefinition(beanClazz.getSimpleName(), definition);
        } catch (ClassNotFoundException e) {
            log.error("Could not register target class: " + annotationMetadata.getClassName(), e);
        }
    }

    protected Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnablePaymentPluginRegistries.class.getCanonicalName());

        Set<String> basePackages = new HashSet<>();

        for (String pkg : (String[]) attributes.get("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }

        for (Class<?> clazz : (Class[]) attributes.get("basePackageClasses")) {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }

        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return basePackages;
    }

    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {

            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (
                            beanDefinition.getMetadata().isInterface()
                            && beanDefinition.getMetadata().getInterfaceNames().length == 1
                            && Annotation.class.getName().equals(beanDefinition.getMetadata().getInterfaceNames()[0])) {
                        try {
                            Class<?> target = ClassUtils.forName(
                                    beanDefinition.getMetadata().getClassName(),
                                    ChannelPluginsScannerRegister.this.classLoader);
                            return !target.isAnnotation();
                        } catch (Exception ex) {
                            this.logger.error("Could not load target class: " + beanDefinition.getMetadata().getClassName(), ex);
                        }
                    }
                    return true;
                }
                return false;
            }
        };
    }
}
