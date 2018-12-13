package com.interswitch.urlshortener.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    @Primary
    public DataSource dataSource(@Value("${datasource.driverClassName}") String datasourceDriverClassName,
                                 @Value("${datasource.url}") String datasourceUrl, @Value("${datasource.username}") String datasourceUsername,
                                 @Value("${datasource.password}") String password) {
        final HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(datasourceDriverClassName);
        ds.setJdbcUrl(datasourceUrl);
        ds.setUsername(datasourceUsername);
        ds.setPassword(password);

        return ds;
    }
}
