package com.tcg.mis.configuration;

import com.tcg.mis.common.mybatis.interceptor.PageInterceptor;

import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Description: database config
 */
@Configuration
@EnableTransactionManagement
@MapperScan(sqlSessionFactoryRef = "sqlSessionFactoryBean", basePackages = {"com.tcg.mis.mapper"})
public class DatabaseConfig {

    @Value("${db.jndi}")
    private String jndiName;

    private PageInterceptor pageInterceptor;

    @Autowired
    public void setDatabaseConfig(PageInterceptor pageInterceptor) {
        this.pageInterceptor = pageInterceptor;
    }

    /**
     * destroy method is disabled for Weblogic update app ability
     */
    @Bean(name = "dataSource", destroyMethod = "")
    public DataSource dataSource() {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        lookup.setResourceRef(true);
        return lookup.getDataSource(jndiName);
    }

    @Bean
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(this.sqlSessionFactoryBean());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPlugins(new Interceptor[]{pageInterceptor});
        sessionFactory.setTypeAliasesPackage("com.tcg.mis.model");
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:com/tcg/*/mapper/*Mapper.xml"));

        SqlSessionFactory sqlSessionFactory = sessionFactory.getObject();
        sqlSessionFactory.getConfiguration().setCacheEnabled(false);
        sqlSessionFactory.getConfiguration().setLogImpl(Slf4jImpl.class);
        sqlSessionFactory.getConfiguration().setLazyLoadingEnabled(false);
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);// 開啟駝峰映射
        sqlSessionFactory.getConfiguration().setDefaultFetchSize(100);
        return sqlSessionFactory;
    }
}
