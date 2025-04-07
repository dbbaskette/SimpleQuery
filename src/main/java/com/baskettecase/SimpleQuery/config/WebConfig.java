package com.baskettecase.SimpleQuery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("https://simplequery.apps.tas-ndc.kuhn-labs.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("X-Forwarded-For", "X-Forwarded-Proto", "CF-Connecting-IP", "CF-IPCountry", "CF-RAY")
                .allowCredentials(true);
    }
} 