package com.tcg.mis.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by ivan.julius on 2/21/2017.
 */
@Configuration
@PropertySource("classpath:environment.properties")
public class OSSConfig {
    @Value("${oss.internal.host}")
    private String ossHost;
    @Value("${oss.internal.address}")
    private String ossServicePath;

    public String getOssHost() {
        return ossHost;
    }

    public void setOssHost(String ossHost) {
        this.ossHost = ossHost;
    }

    public String getOssServicePath() {
        return ossServicePath;
    }

    public void setOssServicePath(String ossServicePath) {
        this.ossServicePath = ossServicePath;
    }

    public String getOSSUrl() {
        return ossHost + ossServicePath;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
