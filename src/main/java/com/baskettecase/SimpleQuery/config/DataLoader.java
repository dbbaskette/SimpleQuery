package com.baskettecase.SimpleQuery.config;

import com.baskettecase.SimpleQuery.entity.Product;
import com.baskettecase.SimpleQuery.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataLoader {

    private final ProductRepository productRepository;

    @Bean
    @Profile("dev") // Only run in development profile
    public CommandLineRunner loadData() {
        return args -> {
            log.info("Loading sample data...");
            
            // Create sample products
            List<Product> products = List.of(
                Product.builder()
                    .name("Laptop")
                    .description("High-performance laptop with 16GB RAM and 512GB SSD")
                    .price(new BigDecimal("1299.99"))
                    .stockQuantity(10)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build(),
                Product.builder()
                    .name("Smartphone")
                    .description("Latest smartphone with 128GB storage and 5G capability")
                    .price(new BigDecimal("899.99"))
                    .stockQuantity(15)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build(),
                Product.builder()
                    .name("Headphones")
                    .description("Wireless noise-cancelling headphones")
                    .price(new BigDecimal("249.99"))
                    .stockQuantity(20)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build(),
                Product.builder()
                    .name("Tablet")
                    .description("10-inch tablet with 64GB storage")
                    .price(new BigDecimal("499.99"))
                    .stockQuantity(8)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build(),
                Product.builder()
                    .name("Smart Watch")
                    .description("Fitness tracker with heart rate monitor")
                    .price(new BigDecimal("199.99"))
                    .stockQuantity(12)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build()
            );
            
            productRepository.saveAll(products);
            log.info("Sample data loaded successfully. Added {} products.", products.size());
        };
    }
}
