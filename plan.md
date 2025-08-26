# Detailed Plan for Train Station Ticker Website with MySQL Database

This project will combine a Java Spring Boot backend (with MySQL database) and Next.js frontend for a modern, scrolling train ticker display. Perfect for learning full-stack development!

---

## Learning Objectives
- Java Spring Boot framework and REST APIs
- MySQL database design and integration
- React/Next.js frontend development
- Full-stack application architecture
- Database operations (CRUD)

---

## 1. Database Setup (MySQL)

### a. Database Schema Design
- **Database Name:** `train_station_db`
- **Tables:**
  - `trains` - Store train information
  - `schedules` - Store train schedules with timestamps

### b. SQL Scripts to Create
- **File:** `database/schema.sql`
  ```sql
  CREATE DATABASE IF NOT EXISTS train_station_db;
  USE train_station_db;

  CREATE TABLE trains (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      train_number VARCHAR(10) NOT NULL UNIQUE,
      train_name VARCHAR(100) NOT NULL,
      route VARCHAR(200) NOT NULL,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
  );

  CREATE TABLE schedules (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      train_id BIGINT NOT NULL,
      destination VARCHAR(100) NOT NULL,
      departure_time TIME NOT NULL,
      arrival_time TIME NOT NULL,
      platform VARCHAR(5) NOT NULL,
      status ENUM('ON_TIME', 'DELAYED', 'CANCELLED') DEFAULT 'ON_TIME',
      delay_minutes INT DEFAULT 0,
      schedule_date DATE NOT NULL,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (train_id) REFERENCES trains(id) ON DELETE CASCADE
  );
  ```

- **File:** `database/sample_data.sql`
  ```sql
  USE train_station_db;

  INSERT INTO trains (train_number, train_name, route) VALUES
  ('T101', 'Express Boston', 'New York - Boston'),
  ('T202', 'Metro Chicago', 'Boston - Chicago'),
  ('T303', 'Coastal Express', 'New York - Miami'),
  ('T404', 'Mountain View', 'Chicago - Denver'),
  ('T505', 'City Connector', 'Boston - Philadelphia');

  INSERT INTO schedules (train_id, destination, departure_time, arrival_time, platform, status, delay_minutes, schedule_date) VALUES
  (1, 'Boston', '08:30:00', '12:30:00', 'A1', 'ON_TIME', 0, CURDATE()),
  (2, 'Chicago', '09:15:00', '15:45:00', 'B2', 'DELAYED', 15, CURDATE()),
  (3, 'Miami', '10:00:00', '18:30:00', 'A3', 'ON_TIME', 0, CURDATE()),
  (4, 'Denver', '11:30:00', '19:15:00', 'C1', 'ON_TIME', 0, CURDATE()),
  (5, 'Philadelphia', '12:00:00', '14:30:00', 'B1', 'DELAYED', 5, CURDATE());
  ```

---

## 2. Java Spring Boot Backend with MySQL

### a. Project Structure
```
train-ticker-backend/
├── pom.xml
├── src/main/java/com/example/trainticker/
│   ├── TrainTickerApplication.java
│   ├── config/
│   │   └── DatabaseConfig.java
│   ├── model/
│   │   ├── Train.java
│   │   └── Schedule.java
│   ├── repository/
│   │   ├── TrainRepository.java
│   │   └── ScheduleRepository.java
│   ├── service/
│   │   └── TrainService.java
│   ├── controller/
│   │   └── TrainController.java
│   └── dto/
│       └── TrainScheduleDTO.java
└── src/main/resources/
    └── application.properties
```

### b. Maven Dependencies (pom.xml)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>train-ticker-backend</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
        <relativePath/>
    </parent>

    <dependencies>
        <!-- Spring Boot Web Starter -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
        <!-- Spring Boot Data JPA -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        
        <!-- MySQL Connector -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version>
        </dependency>
        
        <!-- Spring Boot DevTools (for development) -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

### c. Application Configuration
- **File:** `src/main/resources/application.properties`
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/train_station_db
spring.datasource.username=root
spring.datasource.password=your_password_here
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Server Configuration
server.port=8080

# CORS Configuration
spring.web.cors.allowed-origins=http://localhost:3000
spring.web.cors.allowed-methods=GET,POST,PUT,DELETE
spring.web.cors.allowed-headers=*
```

### d. Entity Classes (Models)
- **File:** `src/main/java/com/example/trainticker/model/Train.java`
- **File:** `src/main/java/com/example/trainticker/model/Schedule.java`

### e. Repository Interfaces (Data Access Layer)
- **File:** `src/main/java/com/example/trainticker/repository/TrainRepository.java`
- **File:** `src/main/java/com/example/trainticker/repository/ScheduleRepository.java`

### f. Data Transfer Object (DTO)
- **File:** `src/main/java/com/example/trainticker/dto/TrainScheduleDTO.java`

### g. Service Layer (Business Logic)
- **File:** `src/main/java/com/example/trainticker/service/TrainService.java`

### h. Controller Layer (REST API)
- **File:** `src/main/java/com/example/trainticker/controller/TrainController.java`

### i. Main Application Class
- **File:** `src/main/java/com/example/trainticker/TrainTickerApplication.java`

---

## 3. Next.js Frontend Updates

### a. Environment Configuration
- **File:** `.env.local`
```
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

### b. Train Ticker Component
- **File:** `src/components/TrainTicker.tsx`

### c. Main Ticker Page
- **File:** `src/app/train-ticker/page.tsx`

### d. Update Main Page Navigation
- **File:** `src/app/page.tsx`

---

## 4. Step-by-Step Setup Guide

### Prerequisites Installation:
1. **Install Java 17+**: Download from Oracle or use OpenJDK
2. **Install MySQL**: Download MySQL Community Server
3. **Install Maven**: For building Java project
4. **Install Node.js 18+**: For Next.js frontend

### Database Setup:
1. Start MySQL service
2. Create database and tables using provided SQL scripts
3. Insert sample data

### Backend Setup:
1. Create new directory `train-ticker-backend`
2. Create `pom.xml` with dependencies
3. Create Java package structure
4. Implement all Java classes as specified
5. Configure `application.properties` with your MySQL credentials
6. Run: `mvn spring-boot:run`

### Frontend Setup:
1. Update existing Next.js project with new components
2. Install any missing dependencies
3. Create environment file with API URL
4. Run: `npm run dev`

### Testing:
1. Test backend API: `curl http://localhost:8080/api/trains`
2. Test frontend: Visit `http://localhost:3000/train-ticker`
3. Verify database connectivity and data display

---

## 5. Learning Resources and Next Steps

### Key Concepts Learned:
- **Database Design**: Entity relationships, foreign keys
- **Spring Boot**: Dependency injection, JPA repositories
- **REST APIs**: HTTP methods, JSON responses
- **React Hooks**: useState, useEffect for data fetching
- **Full-Stack Integration**: Frontend-backend communication

### Potential Enhancements:
- Add train booking functionality
- Implement real-time WebSocket updates
- Add user authentication
- Create admin panel for managing schedules
- Add mobile responsive design
- Implement caching for better performance

This comprehensive plan provides a solid foundation for learning full-stack development with Java, MySQL, and React!
