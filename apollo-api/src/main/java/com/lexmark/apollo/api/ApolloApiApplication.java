package com.lexmark.apollo.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApolloApiApplication {

    private static final Logger log = LoggerFactory.getLogger(ApolloApiApplication.class);

    public static void main(String[] args) {
        log.info("mps-portal-api starting up");
        SpringApplication.run(ApolloApiApplication.class, args);
    }

}
