package com.tcg.mis.test;

import com.tcg.mis.configuration.ApplicationConfig;
import com.tcg.mis.configuration.WebConfig;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ContextConfiguration(classes = {JndiConfig.class, WebConfig.class, ApplicationConfig.class})
@WebAppConfiguration
public @interface IntegrationTest {
    
}
