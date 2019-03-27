package cn.org.easysite.spring.boot.configuration;


import cn.org.easysite.spring.boot.filters.RequestContextFilter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashSet;
import java.util.Set;

/**
 * 通用配置
 *
 * @author panda
 */
@Configuration
public class ResourcesConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** swagger配置 */
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //将日志记录替换为Filter记录
        //registry.addInterceptor(new LoggingInterceptor());
    }

    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean requestFilter() {
        RequestContextFilter filter = new RequestContextFilter();
        Set<String> set = new HashSet<>();
        set.add("*.icon");
        set.add("*.ico");
        set.add("*.html");
        set.add("/swagger*");
        filter.setExcludeUris(set);
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("requestFilter");
        return registrationBean;
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addFormatter(dateFormatter());
    }

    @Bean
    public DateFormatter dateFormatter() {
        return new DateFormatter();
    }

}
