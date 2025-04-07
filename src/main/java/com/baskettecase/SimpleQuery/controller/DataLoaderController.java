package com.baskettecase.SimpleQuery.controller;

import com.baskettecase.SimpleQuery.config.BicycleDataLoader;
import com.baskettecase.SimpleQuery.entity.Product;
import com.baskettecase.SimpleQuery.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class DataLoaderController {

    private final ProductRepository productRepository;
    private final BicycleDataLoader bicycleDataLoader;

    @PostMapping("/load-sample-data")
    public ResponseEntity<String> loadSampleData() {
        if (productRepository.count() > 0) {
            return ResponseEntity.badRequest()
                    .body("Database already contains data. Please clear the database first.");
        }

        try {
            bicycleDataLoader.run();
            return ResponseEntity.ok("Sample data loaded successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error loading sample data: " + e.getMessage());
        }
    }

    @PostMapping("/clear-data")
    public ResponseEntity<String> clearData() {
        try {
            productRepository.deleteAll();
            return ResponseEntity.ok("All data cleared successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error clearing data: " + e.getMessage());
        }
    }
} 