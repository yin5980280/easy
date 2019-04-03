package cn.org.easysite.spring.boot.autoconfigure;


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
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import cn.org.easysite.framework.utils.SpringApplicationUtils;
import cn.org.easysite.spring.boot.configuration.GlobalReturnValuePathProperties;
import cn.org.easysite.spring.boot.configuration.HttpConnectionProperties;
import cn.org.easysite.spring.boot.converter.GlobalMessageConverter;
import cn.org.easysite.spring.boot.converter.TextPlainMappingJackson2HttpMessageConverter;
import cn.org.easysite.spring.boot.interceptor.ClientHttpRequestInterceptorImpl;
import cn.org.easysite.spring.boot.interceptor.DefaultExceptionHandler;
import cn.org.easysite.spring.boot.interceptor.ResponseBodyWrapFactoryBean;
import cn.org.easysite.spring.boot.interceptor.ValidateInterceptor;

/**
 * Spring MVC 配置
 *
 * @author trang
 */
@Configuration
@EnableAspectJAutoProxy
@EnableConfigurationProperties({GlobalReturnValuePathProperties.class, HttpConnectionProperties.class})
public class SpringMvcConfiguration {

    @ConditionalOnMissingBean
    @Bean
    ClientHttpRequestFactory clientHttpRequestFactory(HttpConnectionProperties httpConnectionProperties) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(httpConnectionProperties.getConnectionTimeOut());
        requestFactory.setReadTimeout(httpConnectionProperties.getReadTimeOut());
        return requestFactory;
    }

    @ConditionalOnMissingBean
    @Bean
    RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
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

    @Bean
    @ConditionalOnMissingBean
    public ValidateInterceptor validateInterceptor() {
        return new ValidateInterceptor();
    }

}
