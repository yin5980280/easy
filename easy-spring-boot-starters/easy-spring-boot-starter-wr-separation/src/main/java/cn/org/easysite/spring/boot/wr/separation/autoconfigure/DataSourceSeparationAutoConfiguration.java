/*
 *  @Copyright : Copyright (c) 2020
 *  @Company : EasySite Technology 阿富汗 Co. Ltd.
 */
package cn.org.easysite.spring.boot.wr.separation.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import cn.org.easysite.spring.boot.wr.separation.datasource.DynamicRoutingDataSource;
import cn.org.easysite.spring.boot.wr.separation.processor.TransactionalBeanPostProcessor;

/**
 * @author : 潘多拉
 * @version : 1.0
 * @date : 2020/10/9 9:02 上午
 * @link : cn.org.easysite.spring.boot.wr.separation.autoconfigure.DatasourceSeparationAutoConfiguration
 */
@Configuration
@EnableAspectJAutoProxy
@Import(TransactionalBeanPostProcessor.class)
public class DataSourceSeparationAutoConfiguration {

    @Bean(name = "transactionManager")
    @ConditionalOnMissingBean(PlatformTransactionManager.class)
    public PlatformTransactionManager platformTransactionManager(DataSource dynamicRoutingDataSource) {
        return new DataSourceTransactionManager(dynamicRoutingDataSource);
    }

    @Bean(name = "dynamicRoutingDataSource")
    @Primary
    public DynamicRoutingDataSource dynamicRoutingDataSource(DataSource writeDataSource, DataSource readDataSource) {
        DynamicRoutingDataSource dynamicDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("writeDataSource", writeDataSource);
        dataSourceMap.put("readDataSource", readDataSource);
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(writeDataSource);
        return dynamicDataSource;
    }
}
