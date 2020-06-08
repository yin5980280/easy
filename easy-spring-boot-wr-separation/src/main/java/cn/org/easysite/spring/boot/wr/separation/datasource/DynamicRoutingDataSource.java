package cn.org.easysite.spring.boot.wr.separation.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author : 盘达天神
 * @version : 1.0
 * @date : 2020/4/5 6:31 下午
 * @Description :
 * @Copyright : Copyright (c) 2019
 * @Company : EasySite Technology 阿富汗 Co. Ltd.
 * @link : cn.org.easysite.spring.boot.wr.separation.datasource.DynamicRoutingDataSource
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
