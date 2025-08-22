# URL Shortener

A Spring Boot-based URL shortening service with caching, click tracking, and optional expiration dates. Built with Java 17, MySQL, and Caffeine caching for high performance.

## 🚀 Tech Stack

- **Backend**: Spring Boot 3.2.5 (Java 17)
- **Database**: MySQL 8.0+
- **ORM**: Spring Data JPA with Hibernate
- **Caching**: Caffeine (in-memory cache)
- **Validation**: Spring Boot Validation
- **API**: RESTful endpoints
- **Frontend**: Vanilla HTML/CSS/JavaScript

## ✨ Features

- **URL Shortening**: Generate short, unique codes for long URLs
- **Click Tracking**: Monitor how many times each shortened URL is accessed
- **Expiration Dates**: Optional automatic expiration for shortened URLs
- **Caching**: High-performance URL resolution with Caffeine cache
- **REST API**: Full CRUD operations via REST endpoints
- **Web Interface**: Simple web UI for creating and managing shortened URLs
- **Input Validation**: Server-side validation for URL format and required fields

## 🏗️ Architecture

### Database Schema
```sql
CREATE TABLE short_urls (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(16) NOT NULL UNIQUE,
    target VARCHAR(2048) NOT NULL,
    click_count BIGINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NULL
);
```

### Key Components
- **ShortUrl Model**: JPA entity with validation and indexing
- **ShortenerService**: Business logic with caching and code generation
- **ShortenerController**: REST API endpoints
- **ShortUrlRepository**: Data access layer

## 🛠️ Setup & Installation

### Prerequisites
- Java 17 or higher
- MySQL 8.0+
- Maven 3.6+

### Database Setup
1. Create a MySQL database:
```sql
CREATE DATABASE shortener;
```

2. Update `application.properties` with your database credentials:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Running the Application

#### Local Development (Recommended for testing)
1. Clone the repository
2. Navigate to the project directory
3. Run the application with development profile:

**Windows:**
```bash
run-local.bat
```

**Unix/Linux/Mac:**
```bash
chmod +x run-local.sh
./run-local.sh
```

**Manual:**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

The application will start on `http://localhost:8080` with H2 in-memory database.

#### Production (with PostgreSQL)
1. Set up PostgreSQL database
2. Configure environment variables
3. Run with production profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=production
```

## 📚 API Documentation

### Base URL
```
http://localhost:8082/api
```

### Endpoints

#### 1. Create Short URL
```http
POST /api/shorten
Content-Type: application/json

{
    "url": "https://example.com/very-long-url",
    "expiresInDays": 30
}
```

**Response:**
```json
{
    "code": "abc1234",
    "shortUrl": "/r/abc1234"
}
```

#### 2. Redirect to Original URL
```http
GET /api/r/{code}
```

**Response:** 302 Redirect to the original URL

#### 3. List All URLs
```http
GET /api/links
```

**Response:**
```json
[
    {
        "id": 1,
        "code": "abc1234",
        "target": "https://example.com/very-long-url",
        "clickCount": 5,
        "createdAt": "2024-01-15T10:30:00Z",
        "expiresAt": "2024-02-14T10:30:00Z"
    }
]
```

#### 4. Delete URL
```http
DELETE /api/links/{id}
```

**Response:** 204 No Content (success) or 404 Not Found

## 🌐 Web Interface

Access the web interface at `http://localhost:8082` to:
- Create new shortened URLs
- View recent URLs and their click counts
- Delete URLs
- Copy shortened URLs to clipboard

## ⚙️ Configuration

### Application Properties
```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/shortener
spring.datasource.username=root
spring.datasource.password=root

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Caching
spring.cache.type=caffeine

# Server
server.port=8082
```

### Customization Options
- **Code Length**: Modify the `generateUniqueCode()` method in `ShortenerService`
- **Cache Configuration**: Add Caffeine cache configuration for custom cache settings
- **URL Validation**: Extend validation rules in the controller
- **Expiration Logic**: Modify expiration handling in the service layer

## 🔧 Development

### Project Structure
```
src/
├── main/
│   ├── java/com/example/shortener/
│   │   ├── model/
│   │   │   └── ShortUrl.java
│   │   ├── repo/
│   │   │   └── ShortUrlRepository.java
│   │   ├── service/
│   │   │   └── ShortenerService.java
│   │   ├── web/
│   │   │   └── ShortenerController.java
│   │   └── UrlShortenerApplication.java
│   └── resources/
│       ├── application.properties
│       └── static/
│           ├── index.html
│           ├── main.js
│           └── styles.css
```

### Key Features Implementation

#### Code Generation
- Generates 7-character alphanumeric codes
- Ensures uniqueness through database constraints
- Uses random generation with collision detection

#### Caching Strategy
- Caches resolved URLs for faster redirects
- Uses Caffeine for high-performance in-memory caching
- Cache eviction on URL updates

#### Click Tracking
- Atomic increment of click counts
- Tracks clicks per shortened URL
- Available through API and web interface

## 🧪 Testing

Run tests with:
```bash
mvn test
```

## 🚀 Deployment

### Docker (Recommended)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/url-shortener-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Traditional Deployment
1. Build the JAR: `mvn clean package`
2. Run: `java -jar target/url-shortener-0.0.1-SNAPSHOT.jar`

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📞 Support

For questions or issues, please open an issue on the repository.