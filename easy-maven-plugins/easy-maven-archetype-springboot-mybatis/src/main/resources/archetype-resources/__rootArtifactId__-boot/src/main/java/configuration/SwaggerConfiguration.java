package $ import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

{package}.configuration;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-04-19 14:26
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : com.keyo.easy.configuration.SwaggerConfiguration
 */
@Configuration
@Profile({"dev", "test","gld","citest"})
public class SwaggerConfiguration {
    @Bean
    Docket docket() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        ApiInfo apiInfo = apiInfoBuilder.title("")
                .description("熊猫大侠")
                .termsOfServiceUrl("")
                .version("1.0")
                .contact(new Contact("熊猫大侠", "www.easysite.org.cn", "289660870@qq.com"))
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(java.time.LocalDateTime.class, java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, java.util.Date.class);
    }
}
