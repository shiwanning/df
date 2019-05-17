package com.tcg.mis.configuration;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import com.google.common.collect.Maps;
import com.tcg.mis.common.util.JsonUtils;
import com.tcg.mis.to.PathMapping;

@Configuration
public class MirrorConfig {
    
    @Bean
    @DependsOn("objectMapper")
    public Map<String, PathMapping> pathMappings() throws IOException {
        Map<String, PathMapping> result = Maps.newHashMap();
        String json = StreamUtils.copyToString( new ClassPathResource("pathMapping.json").getInputStream(), Charset.forName("UTF-8"));
        for(PathMapping pm : JsonUtils.fromJsonToList(json, PathMapping.class)) {
            result.put(pm.getKey(), pm);
        }
        return result;
    }
	
}
