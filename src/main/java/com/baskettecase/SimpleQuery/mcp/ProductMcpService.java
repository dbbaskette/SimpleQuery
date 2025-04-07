package com.baskettecase.SimpleQuery.mcp;

import com.baskettecase.SimpleQuery.entity.Product;
import com.baskettecase.SimpleQuery.service.ProductService;
import org.springframework.ai.tool.annotation.Tool; // Using user suggested import
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductMcpService {

    private final ProductService productService;

    public ProductMcpService(ProductService productService) {
        this.productService = productService;
    }

    @Tool(name = "getAllProducts", description = "Retrieves a list of all products.")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @Tool(name = "getProductById", description = "Retrieves a specific product by its ID.")
    public Optional<Product> getProductById(Long id) {
        return productService.getProductById(id);
    }

    // Note: For create and update, the input 'product' needs to be provided as a JSON object
    // when calling the tool via MCP. The framework handles the deserialization.

    @Tool(name = "createProduct", description = "Creates a new product. Requires product details (name, description, price, stockQuantity) as input.")
    public Product createProduct(Product product) {
        // Ensure ID is null for creation
        product.setId(null);
        // ProductService uses saveProduct for creation when ID is null
        return productService.saveProduct(product);
    }

    @Tool(name = "updateProduct", description = "Updates an existing product by ID. Requires the product ID and updated product details (name, description, price, stockQuantity) as input.")
    public Optional<Product> updateProduct(Long id, Product productDetails) {
        // Fetch the existing product
        return productService.getProductById(id).map(existingProduct -> {
            // Update fields from productDetails (null checks might be needed depending on desired behavior)
            existingProduct.setName(productDetails.getName());
            existingProduct.setDescription(productDetails.getDescription());
            existingProduct.setPrice(productDetails.getPrice());
            existingProduct.setStockQuantity(productDetails.getStockQuantity());
            // Let saveProduct handle setting the update timestamp
            return productService.saveProduct(existingProduct);
        });
    }

    @Tool(name = "deleteProduct", description = "Deletes a product by its ID.")
    public boolean deleteProduct(Long id) {
        productService.deleteProduct(id);
        // Return true if deletion was attempted. ProductService might throw an exception if not found,
        // which the MCP framework should handle and report as an error.
        return true;
    }
}
