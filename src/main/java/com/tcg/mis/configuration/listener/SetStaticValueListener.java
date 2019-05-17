package com.tcg.mis.configuration.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcg.mis.common.log.TcgLogFactory;
import com.tcg.mis.common.util.JsonUtils;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class SetStaticValueListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = TcgLogFactory.getLogger(SetStaticValueListener.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null) {
            LOGGER.debug("setStaticValueListener start");
            JsonUtils.setObjectMapperStatic(objectMapper);
        }
    }

}
