package com.teacheron.sales.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring REST Sample with ignite")
                .description("Spring REST Sample with ignite")
                .license("Apache License Version 2.0")
                .version("2.0")
                .build();
    }

    @Bean
    public Docket personApi() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true)
                .groupName("teacheron")
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/api.*"))
                .build();
    }
}
