package com.baskettecase.SimpleQuery.repository;

import com.baskettecase.SimpleQuery.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find products by name containing the given string (case insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Find products with price less than the given value
    List<Product> findByPriceLessThan(BigDecimal price);

    // Find products with stock quantity greater than the given value
    List<Product> findByStockQuantityGreaterThan(Integer stockQuantity);

    // Custom query to find products by price range
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice ORDER BY p.price ASC")
    List<Product> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
}
