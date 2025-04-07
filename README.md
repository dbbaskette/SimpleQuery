# SimpleQuery

A Spring Boot application that demonstrates Spring Data JPA integration with SQL databases, using environment variables for configuration.

## Features

- Spring Boot 3.4.4 with Java 21
- Spring Data JPA for database operations
- Multiple database support (H2 for development, PostgreSQL for production)
- Environment variable-based configuration
- RESTful API endpoints
- Sample entity, repository, service, and controller implementation
- Profile-specific configurations (dev, prod)

## Project Structure

```
src/main/java/com/baskettecase/SimpleQuery/
├── SimpleQueryApplication.java       # Main application class
├── config/                           # Configuration classes
│   ├── DatabaseConfig.java           # Database configuration
│   └── DataLoader.java               # Sample data loader (dev profile only)
├── controller/                       # REST controllers
│   └── ProductController.java        # Product API endpoints
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
| SPRING_PROFILES_ACTIVE | Active profile (dev or prod) | dev |
| DB_USERNAME | Database username | sa (H2), postgres (PostgreSQL) |
| DB_PASSWORD | Database password | (empty for H2) |
| DB_HOST | Database host (prod only) | localhost |
| DB_PORT | Database port (prod only) | 5432 |
| DB_NAME | Database name (prod only) | simplequery |
| DB_DDL_AUTO | Hibernate DDL auto setting | update |
| DB_SHOW_SQL | Show SQL statements | true |
| DB_FORMAT_SQL | Format SQL statements | true |
| SERVER_PORT | Application server port | 8080 |
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

## H2 Console (Development Only)

When running in development mode, you can access the H2 console at:

```
http://localhost:8080/h2-console
```

JDBC URL: `jdbc:h2:mem:testdb`  
Username: `sa`  
Password: (leave empty)
