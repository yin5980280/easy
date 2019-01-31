package com.vartime.easy.spring.boot.configuration;


import com.vartime.easy.framework.utils.SpringApplicationUtils;
import com.vartime.easy.spring.boot.converter.GlobalMessageConverter;
import com.vartime.easy.spring.boot.converter.TextPlainMappingJackson2HttpMessageConverter;
import com.vartime.easy.spring.boot.interceptor.ClientHttpRequestInterceptorImpl;
import com.vartime.easy.spring.boot.interceptor.DefaultExceptionHandler;
import com.vartime.easy.spring.boot.interceptor.ResponseBodyWrapFactoryBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring MVC 配置
 *
 * @author trang
 */
@Configuration
@EnableAspectJAutoProxy
@EnableConfigurationProperties({GlobalReturnValuePathProperties.class, HttpConnectionProperties.class})
public class SpringMvcConfiguration {

    @Autowired
    private HttpConnectionProperties httpConnectionProperties;

    @ConditionalOnMissingBean
    @Bean
    ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(httpConnectionProperties.getConnectionTimeOut());
        requestFactory.setReadTimeout(httpConnectionProperties.getReadTimeOut());
        return requestFactory;
    }

    @ConditionalOnMissingBean
    @Bean
    RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new TextPlainMappingJackson2HttpMessageConverter());
        List<ClientHttpRequestInterceptor> interceptors=new ArrayList<>();
        interceptors.add(new ClientHttpRequestInterceptorImpl());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        return new HttpMessageConverters(converter());
    }

    @Bean
    @ConditionalOnMissingBean
    public MappingJackson2HttpMessageConverter converter() {
        return new GlobalMessageConverter();
    }

    @Bean
    public SpringApplicationUtils springUtils() {
        return new SpringApplicationUtils();
    }

    @ConditionalOnMissingBean
    @Bean
    public DefaultExceptionHandler defaultExceptionHandler() {
        return new DefaultExceptionHandler();
    }

    @Bean
    @ConditionalOnProperty(prefix = "easy.global.json.return.value", name = "enable", matchIfMissing = true, havingValue = "true")
    public ResponseBodyWrapFactoryBean responseBodyWrapFactoryBean() {
        return new ResponseBodyWrapFactoryBean();
    }

}
