package com.dandinglong.webframedemo.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(value = "com.dandinglong.webframedemo.dao.slaver",sqlSessionFactoryRef = "slaverSqlSessionFactory")
public class SlaverConfig {
    @Bean(name = "datasourceSlaver")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasourceslaver")
    public DataSource datasourceSlaver(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        return dataSourceBuilder.build();
    }
    @Bean(name = "slaverSqlSessionFactory")
    @Primary
    public SqlSessionFactory slaverSqlSessionFactory(@Qualifier("datasourceSlaver")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappingslaver/*.xml"));
        return bean.getObject();
    }
    @Bean(name = "slaverTransactionManager")
    public DataSourceTransactionManager getSlaverTransactionManager(@Qualifier("datasourceSlaver")DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

}
