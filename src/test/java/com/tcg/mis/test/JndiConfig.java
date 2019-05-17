package com.tcg.mis.test;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JndiConfig {
    
    static {
        try {
//            BasicDataSource dataSource =  new BasicDataSource();
//            dataSource.setUsername("TCG_ODSDB");
//            dataSource.setPassword("Aa123456");
//            dataSource.setUrl("jdbc:oracle:thin:@10.8.91.55:1521:TCG");
//            dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
//
//            BasicDataSource dataSource2 =  new BasicDataSource();
//            dataSource2.setUsername("TCG_POKERDB");
//            dataSource2.setPassword("Aa123456");
//            dataSource2.setUrl("jdbc:oracle:thin:@10.8.91.55:1521:TCG");
//            dataSource2.setDriverClassName("oracle.jdbc.OracleDriver");
//
//            SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
//            builder.bind("persistence/odsdb", dataSource);
//            builder.bind("persistence/odsdb-reader", dataSource);
//            builder.bind("persistence/odsdb-writer", dataSource2);
//            builder.activate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
