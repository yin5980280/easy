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

    private static final ThreadLocal<String> DATASOURCE_CONTEXT_HOLDER = new ThreadLocal<>();

    public enum JdbcDatasourceType {
        /**
         * 读或写
         */
        READONLY,

        WRITE;
    }

    public static void setJdbcType(String jdbcType) {
        DATASOURCE_CONTEXT_HOLDER.set(jdbcType);
    }

    public static void setReadOnly() {
        DATASOURCE_CONTEXT_HOLDER.set(JdbcDatasourceType.READONLY.name());
    }

    public static void setWriteRead() {
        DATASOURCE_CONTEXT_HOLDER.set(JdbcDatasourceType.WRITE.name());
    }

    public static void reset() {
        DATASOURCE_CONTEXT_HOLDER.set(JdbcDatasourceType.WRITE.name());
    }

    public static String getJdbcType() {
        return DATASOURCE_CONTEXT_HOLDER.get();
    }

    @Override
    protected Object determineCurrentLookupKey() {
         String jdbcType = getJdbcType();
         if (jdbcType == null) {
             return JdbcDatasourceType.WRITE.name();
         }
         return jdbcType;
    }
}
