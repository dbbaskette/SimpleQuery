package com.baskettecase.SimpleQuery.config;

import com.baskettecase.SimpleQuery.entity.Product;
import com.baskettecase.SimpleQuery.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BicycleDataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            loadData();
        }
    }

    public void loadData() {
        log.info("Loading initial bicycle products data...");
        
        List<Product> products = Arrays.asList(
            createProduct("Mountain Bike Pro", "High-performance mountain bike with full suspension", 
                new BigDecimal("1299.99"), 10, "Bikes"),
            createProduct("Road Bike Elite", "Lightweight carbon fiber road bike", 
                new BigDecimal("2499.99"), 5, "Bikes"),
            createProduct("Hybrid Bike", "Versatile bike for city and light trails", 
                new BigDecimal("799.99"), 15, "Bikes"),
            createProduct("Helmet Pro", "Premium safety helmet with MIPS technology", 
                new BigDecimal("149.99"), 20, "Safety"),
            createProduct("Bike Lock", "Heavy-duty U-lock with cable", 
                new BigDecimal("59.99"), 30, "Accessories"),
            createProduct("Bike Pump", "Professional floor pump with pressure gauge", 
                new BigDecimal("49.99"), 25, "Tools"),
            createProduct("Bike Lights Set", "Front and rear LED lights with rechargeable batteries", 
                new BigDecimal("39.99"), 40, "Accessories"),
            createProduct("Bike Repair Kit", "Complete repair kit with tools and patches", 
                new BigDecimal("29.99"), 35, "Tools"),
            createProduct("Bike Water Bottle", "Insulated water bottle with cage", 
                new BigDecimal("19.99"), 50, "Accessories"),
            createProduct("Bike Gloves", "Padded cycling gloves for comfort", 
                new BigDecimal("34.99"), 30, "Clothing")
        );

        productRepository.saveAll(products);
        log.info("Loaded {} bicycle products", products.size());
    }

    private Product createProduct(String name, String description, BigDecimal price, 
                                int stockQuantity, String category) {
        return Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .stockQuantity(stockQuantity)
                .category(category)
                .build();
    }
} 