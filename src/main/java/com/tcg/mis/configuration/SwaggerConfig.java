package com.tcg.mis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.classmate.TypeResolver;

import java.util.Arrays;
import java.util.Collections;

import javax.ws.rs.core.MediaType;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Description: Swagger config <br/>
 *
 * @author Eddie
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tcg.mis.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalOperationParameters(
                        Arrays.asList(new ParameterBuilder()
                         .name("Authorization")
                         .parameterType("header")
                         .type(new TypeResolver().resolve(String.class))
                         .modelRef(new ModelRef("string"))
                         .defaultValue("push-token-here")
                         .build()))
                .apiInfo(apiInfo())
                //                .globalOperationParameters(getHeaders())
                .produces(Collections.singleton(MediaType.APPLICATION_JSON));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "REST API Document for MIS",
                "Backend Use",
                "V2 API",
                "Terms of service",
                new Contact("TRD", "", ""),
                "", "", Collections.<VendorExtension>emptyList());
    }
}
