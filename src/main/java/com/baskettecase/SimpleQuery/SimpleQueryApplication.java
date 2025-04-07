package com.baskettecase.SimpleQuery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baskettecase.SimpleQuery.mcp.ProductMcpService;

@SpringBootApplication
public class SimpleQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleQueryApplication.class, args);
	}

	@Bean
    public ToolCallbackProvider simplequeryToolsProvider(ProductMcpService productMCPService) {
        // This leverages your @Tool annotations directly
        return MethodToolCallbackProvider.builder()
                .toolObjects(productMCPService)
                .build();
    }


}
