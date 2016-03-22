package com.lexmark.apollo.api.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DatabaseRedshiftConfig {
    
    @Bean(name = "redshiftDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.ds_primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

}
