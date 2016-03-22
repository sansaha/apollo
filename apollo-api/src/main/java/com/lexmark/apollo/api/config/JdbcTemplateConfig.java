package com.lexmark.apollo.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="apollo.jdbc")
public class JdbcTemplateConfig {

    @Getter
    @Setter
    private int queryTimeout;


}
