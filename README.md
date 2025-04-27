# Sparkplug Auth Service

Authentication and authorization service for the Sparkplug platform. Built with Spring Boot 3 and Java 21.

## Features

- User authentication with JWT tokens
  - Issues and signs tokens with private key
  - Provides JWK endpoint (public key) to other services for validation 
- User registration with email or phone number
- Role-based authorization
- Swagger/OpenAPI documentation
- Eureka client integration
- PostgreSQL database with Flyway migrations

## Prerequisites

- Java 21
- Docker and Docker Compose
- Maven 3.9.9 

## Configuration

### Environment Variables

Create a `.env` file in the root directory with the following variables:

```properties
POSTGRES_DB=your_db_name
POSTGRES_USER=your_db_user
POSTGRES_PASSWORD=your_db_password
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5401/your_db_name
SPRING_DATASOURCE_USERNAME=your_db_user
SPRING_DATASOURCE_PASSWORD=your_db_password
```

## Running the Service

1. Start the database:
```bash
docker compose up -d
```

2. Run the application:
```bash
mvn spring-boot:run
```

Or build and run:
```bash
mvn clean package
java -jar target/auth-0.0.1-SNAPSHOT.jar
```

## API Documentation

Once the service is running, access the Swagger UI at:
```
http://localhost:8081/swagger-ui.html
```

## Testing

Run tests with:
```bash
mvn clean test
```

Note: Tests use TestContainers for database integration testing.

## API Endpoints

### Authentication
- `POST /auth/login` - User login
- `POST /auth/register/email` - Register new user with email
- `GET /auth/me` - Get current user information

### User Management
- Update user credentials (email, password, username, phone number)

## Security

- JWT-based authentication
- BCrypt password hashing
- Role-based access control
- Bearer token authorization

## Dependencies

- Spring Boot 3.4.4
- Spring Security
- Spring Cloud Netflix Eureka Client
- PostgreSQL
- Flyway
- Lombok
- SpringDoc OpenAPI (Swagger)
- Nimbus JOSE+JWT
