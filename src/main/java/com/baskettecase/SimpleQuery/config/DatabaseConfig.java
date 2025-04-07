package com.baskettecase.SimpleQuery.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
@Slf4j
@Getter
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.username}")
    private String databaseUsername;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Value("${spring.jpa.show-sql}")
    private boolean showSql;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Database Configuration:");
        log.info("Active Profile: {}", activeProfile);
        log.info("Database URL: {}", databaseUrl);
        log.info("Database Username: {}", databaseUsername);
        log.info("Hibernate DDL Auto: {}", ddlAuto);
        log.info("Show SQL: {}", showSql);
    }
}
