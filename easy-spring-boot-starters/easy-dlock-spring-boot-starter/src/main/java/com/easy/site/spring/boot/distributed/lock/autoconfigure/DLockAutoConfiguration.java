package com.easy.site.spring.boot.distributed.lock.autoconfigure;

import com.easy.site.spring.boot.distributed.lock.core.aspect.DLockAspectHandler;
import com.easy.site.spring.boot.distributed.lock.core.config.DLockConfig;
import com.easy.site.spring.boot.distributed.lock.core.provider.api.BusinessKeyProvider;
import com.easy.site.spring.boot.distributed.lock.core.provider.api.LockInfoProvider;
import com.easy.site.spring.boot.distributed.lock.core.provider.impl.DefaultBusinessKeyProvider;
import com.easy.site.spring.boot.distributed.lock.core.provider.impl.DefaultLockInfoProvider;
import com.easy.site.spring.boot.distributed.lock.spi.redisson.factory.DLockFactory;
import com.easy.site.spring.boot.distributed.lock.spi.redisson.factory.LockFactory;

import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureAfter(RedissonAutoConfiguration.class)
@EnableConfigurationProperties(DLockConfig.class)
@Import({DLockAspectHandler.class})
public class DLockAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LockInfoProvider lockInfoProvider(){
        return new DefaultLockInfoProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public BusinessKeyProvider businessKeyProvider(){
        return new DefaultBusinessKeyProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public LockFactory lockFactory(){
        return new DLockFactory();
    }
}
