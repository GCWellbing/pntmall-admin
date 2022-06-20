package com.pntmall.admin.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.pntmall.common.RefreshableSqlSessionFactoryBean;

@Configuration
@EnableTransactionManagement
@MapperScan(value={"com.pntmall.admin.service"})
public class DatabaseConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
    	return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        RefreshableSqlSessionFactoryBean sessionFactoryBean = new RefreshableSqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setTypeAliasesPackage("com.pntmall.admin.domain,com.pntmall.common.type");
        sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));

        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:com/pntmall/admin/service/*.xml");
        sessionFactoryBean.setMapperLocations(res);
        sessionFactoryBean.afterPropertiesSet();

        return sessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sst(SqlSessionFactory sqlSessionFactory) {
        final SqlSessionTemplate sst = new SqlSessionTemplate(sqlSessionFactory);
        return sst;
    }

    @Bean 
    public PlatformTransactionManager transactionManager(DataSource dataSource) throws Exception { 
    	return new DataSourceTransactionManager(dataSource); 
    }
}
