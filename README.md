# SimpleQuery

A Spring Boot application that demonstrates Spring Data JPA integration with SQL databases, using environment variables for configuration.

## Features

- Spring Boot 3.4.4 with Java 21
- Spring Data JPA for database operations
- Multiple database support (H2 for development, PostgreSQL for production)
- Environment variable-based configuration
- RESTful API endpoints
- Sample entity, repository, service, and controller implementation
- Profile-specific configurations (dev, prod, cloud)
- Cloud Foundry deployment support with java-cfenv
- Sample bicycle shop data with manual loading capability
- CORS and Cloudflare configuration for secure API access

## Project Structure

```
src/main/java/com/baskettecase/SimpleQuery/
├── SimpleQueryApplication.java       # Main application class
├── config/                           # Configuration classes
│   ├── DatabaseConfig.java           # Database configuration
│   ├── DataLoader.java               # Sample data loader (dev profile only)
│   └── BicycleDataLoader.java        # Bicycle shop data loader
├── controller/                       # REST controllers
│   ├── ProductController.java        # Product API endpoints
│   └── DataLoaderController.java     # Data management endpoints
├── entity/                           # JPA entities
│   └── Product.java                  # Product entity
├── repository/                       # Spring Data repositories
│   └── ProductRepository.java        # Product repository
└── service/                          # Service layer
    └── ProductService.java           # Product service
```

## Environment Variables

The application uses the following environment variables:

| Variable | Description | Default Value |
|----------|-------------|---------------|
| SPRING_PROFILES_ACTIVE | Active profile (dev, prod, or cloud) | dev |
| DB_USERNAME | Database username | sa (H2), postgres (PostgreSQL) |
| DB_PASSWORD | Database password | (empty for H2) |
| DB_HOST | Database host (prod only) | localhost |
| DB_PORT | Database port (prod only) | 5432 |
| DB_NAME | Database name (prod only) | simplequery |
| DB_DDL_AUTO | Hibernate DDL auto setting | update |
| DB_SHOW_SQL | Show SQL statements | true |
| DB_FORMAT_SQL | Format SQL statements | true |
| PORT | Application server port | 8080 |
| DB_MAX_POOL_SIZE | Connection pool max size (prod only) | 10 |
| DB_MIN_IDLE | Connection pool min idle (prod only) | 5 |
| DB_IDLE_TIMEOUT | Connection idle timeout in ms (prod only) | 30000 |

## Running the Application

### Development Mode (H2 Database)

```bash
# Run with default settings (dev profile)
./mvnw spring-boot:run

# Or explicitly set the profile
export SPRING_PROFILES_ACTIVE=dev
./mvnw spring-boot:run
```

### Production Mode (PostgreSQL)

```bash
# Set environment variables for PostgreSQL
export SPRING_PROFILES_ACTIVE=prod
export DB_USERNAME=postgres
export DB_PASSWORD=your_password
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=simplequery

# Run the application
./mvnw spring-boot:run
```

### Cloud Foundry Deployment

The application uses java-cfenv for automatic service binding in Cloud Foundry. To deploy:

1. Create a PostgreSQL service:
```bash
cf create-service postgresql standard postgres-db
```

2. Build the application:
```bash
./mvnw clean package
```

3. Deploy to Cloud Foundry:
```bash
cf push
```

The application will automatically:
- Use the cloud profile
- Disable auto-reconfiguration
- Bind to the PostgreSQL service
- Configure the database connection using service credentials

### Using Docker (PostgreSQL)

If you want to run PostgreSQL in Docker:

```bash
# Start PostgreSQL container
docker run --name postgres-db -e POSTGRES_PASSWORD=your_password -e POSTGRES_DB=simplequery -p 5432:5432 -d postgres

# Run the application with PostgreSQL
export SPRING_PROFILES_ACTIVE=prod
export DB_USERNAME=postgres
export DB_PASSWORD=your_password
./mvnw spring-boot:run
```

## API Endpoints

### Product Management

| Method | URL | Description |
|--------|-----|-------------|
| GET | /api/products | Get all products |
| GET | /api/products/{id} | Get product by ID |
| GET | /api/products/search?name={name} | Search products by name |
| GET | /api/products/price-range?min={min}&max={max} | Get products in price range |
| POST | /api/products | Create a new product |
| PUT | /api/products/{id} | Update a product |
| PATCH | /api/products/{id}/stock?quantity={quantity} | Update product stock |
| DELETE | /api/products/{id} | Delete a product |

### Data Management

| Method | URL | Description |
|--------|-----|-------------|
| POST | /api/admin/load-sample-data | Load sample bicycle shop data |
| POST | /api/admin/clear-data | Clear all product data |

Example usage:
```bash
# Clear existing data
curl -X POST http://localhost:8080/api/admin/clear-data

# Load sample bicycle shop data
curl -X POST http://localhost:8080/api/admin/load-sample-data
```

The sample data includes:
- Bikes (Mountain, Road, Hybrid)
- Safety equipment (Helmets)
- Accessories (Locks, Lights, Water Bottles)
- Tools (Pumps, Repair Kits)
- Clothing (Gloves)

## H2 Console (Development Only)

When running in development mode, you can access the H2 console at:

```
http://localhost:8080/h2-console
```

JDBC URL: `jdbc:h2:mem:testdb`  
Username: `sa`  
Password: (leave empty)

## CORS and Cloudflare Configuration

The application includes configuration for handling requests through Cloudflare:

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("https://simplequery.apps.tas-ndc.kuhn-labs.com")
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
  https://simplequery.apps.tas-ndc.kuhn-labs.com/api/products/1
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.
