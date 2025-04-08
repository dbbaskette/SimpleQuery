package com.baskettecase.SimpleQuery.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
public class PropertyLogger {

    @Autowired
    private Environment environment;

    @EventListener
    public void handleApplicationStarted(ApplicationStartedEvent event) {
        StandardEnvironment env = (StandardEnvironment) environment;
        
        // Get all property sources
        for (PropertySource<?> propertySource : env.getPropertySources()) {
            String sourceName = propertySource.getName();
            
            // Skip system environment and system properties to avoid sensitive data
            if (sourceName.contains("systemEnvironment") || 
                sourceName.contains("systemProperties") ||
                sourceName.contains("servletConfigInitParams") ||
                sourceName.contains("servletContextInitParams")) {
                continue;
            }

            // Get all property names from this source
            if (propertySource.getSource() instanceof org.springframework.core.env.MapPropertySource) {
                org.springframework.core.env.MapPropertySource mapSource = 
                    (org.springframework.core.env.MapPropertySource) propertySource;
                
                // Log each property, excluding passwords
                mapSource.getSource().forEach((key, value) -> {
                    if (!key.toLowerCase().contains("password") && 
                        !key.toLowerCase().contains("secret") &&
                        !key.toLowerCase().contains("key")) {
                        System.out.println("Property: " + key + " = " + value);
                    }
                });
            }
        }

        // Log active profiles
        System.out.println("Active profiles: " + 
            Arrays.stream(env.getActiveProfiles())
                .collect(Collectors.joining(", ")));
    }
} 