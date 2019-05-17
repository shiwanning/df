package com.tcg.mis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.client.RestTemplate;

/**
 * Description: ApplicationConfig Description
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.tcg.mis.controller", "com.tcg.mis.service", "com.tcg.mis.mapper", "com.tcg.mis.client", "com.tcg.mis.intercept"})
@Import({SwaggerConfig.class, CommonConfig.class, DatabaseConfig.class, MirrorConfig.class, OSSConfig.class})
@PropertySource({"classpath:application.properties", "classpath:environment.properties"})
public class ApplicationConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
