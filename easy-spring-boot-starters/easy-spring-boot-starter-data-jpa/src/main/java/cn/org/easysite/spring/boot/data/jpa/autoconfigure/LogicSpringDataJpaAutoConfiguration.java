package cn.org.easysite.spring.boot.data.jpa.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import cn.org.easysite.spring.boot.jpa.factory.bean.LogicJpaRepositoryFactoryBean;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-05-16 18:14
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.spring.boot.data.jpa.autoconfigure.LogicSpringDataJpaAutoConfiguration
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.data.jpa.logic.repositories", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableJpaRepositories(repositoryBaseClass = LogicJpaRepositoryFactoryBean.class)
public class LogicSpringDataJpaAutoConfiguration {
}
