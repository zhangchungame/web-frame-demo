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
@MapperScan(value = "com.dandinglong.webframedemo.dao.master",sqlSessionFactoryRef = "masertSqlSessionFactory")
public class Masterconfig {
    @Bean(name = "datasourceMaster")
    @ConfigurationProperties(prefix = "spring.datasourcemaster")
    public DataSource datasourceMaster(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        return dataSourceBuilder.build();
    }


    @Bean(name = "masertSqlSessionFactory")
    public SqlSessionFactory masertSqlSessionFactory(@Qualifier("datasourceMaster")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappingmaster/*.xml"));
        return bean.getObject();
    }
    @Bean(name = "masterTransactionManager")
    public DataSourceTransactionManager getMasterTransactionManager(@Qualifier("datasourceMaster")DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }


}
