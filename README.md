# SimpleQuery

A Spring Boot application that demonstrates PostgreSQL integration with Cloud Foundry.

## Features

- PostgreSQL database integration
- RESTful API endpoints for product management
- Cloud Foundry service binding
- Sample bicycle shop data with manual loading capability
- CORS and Cloudflare configuration for secure API access

## Project Structure

```
src/main/java/com/baskettecase/SimpleQuery/
├── config/
│   ├── BicycleDataLoader.java (data loader for bicycle shop data)
│   ├── DataLoaderController.java (controller for data management endpoints)
│   └── WebConfig.java (CORS and Cloudflare configuration)
├── controller/
│   └── ProductController.java
├── entity/
│   └── Product.java
├── repository/
│   └── ProductRepository.java
└── SimpleQueryApplication.java
```

## Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL database
- Cloud Foundry CLI (for deployment)

## Configuration

### Local Development

1. Create a PostgreSQL database
2. Update `application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Cloud Foundry Deployment

1. Create a PostgreSQL service instance:
```bash
cf create-service postgresql-db small my-postgresql
```

2. Push the application:
```bash
cf push your-app-name -p target/simplequery-0.0.1-SNAPSHOT.jar
```

3. Bind the service:
```bash
cf bind-service your-app-name my-postgresql
```

4. Restart the application:
```bash
cf restart your-app-name
```

## API Endpoints

### Product Management
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get a specific product
- `POST /api/products` - Create a new product
- `PUT /api/products/{id}` - Update a product
- `DELETE /api/products/{id}` - Delete a product

### Data Management
- `POST /api/admin/load-sample-data` - Load sample bicycle shop data
- `POST /api/admin/clear-data` - Clear all product data

Example curl commands:
```bash
# Clear existing data
curl -X POST http://localhost:8080/api/admin/clear-data

# Load sample bicycle shop data
curl -X POST http://localhost:8080/api/admin/load-sample-data
```

### Sample Data Categories
- Bikes (Road, Mountain, Hybrid)
- Safety Equipment (Helmets, Lights, Locks)
- Accessories (Baskets, Pumps, Bottles)
- Tools (Multi-tools, Repair Kits)
- Clothing (Jerseys, Shorts, Gloves)

## CORS and Cloudflare Configuration

The application includes configuration for handling requests through Cloudflare:

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("https://your-domain.com")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
```

Application properties for Cloudflare:
```properties
server.forward-headers-strategy=framework
server.tomcat.remoteip.remote-ip-header=x-forwarded-for
server.tomcat.remoteip.protocol-header=x-forwarded-proto
server.tomcat.remoteip.internal-proxies=.*
```

When making API requests through Cloudflare, include the following headers:
```bash
curl -X DELETE \
  -H "X-Forwarded-Proto: https" \
  -H "X-Forwarded-For: $(curl -s ifconfig.me)" \
  https://your-domain.com/api/products/1
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.
