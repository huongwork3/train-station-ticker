package com.example.trainticker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application Class
 * 
 * @SpringBootApplication annotation combines:
 * - @Configuration: Tags the class as a source of bean definitions
 * - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings
 * - @ComponentScan: Tells Spring to look for other components, configurations, and services
 */
@SpringBootApplication
public class TrainTickerApplication {

    /**
     * Main method - entry point of the application
     * This starts the Spring Boot application
     */
    public static void main(String[] args) {
        SpringApplication.run(TrainTickerApplication.class, args);
        System.out.println("🚂 Train Ticker Backend is running on http://localhost:8080");
        System.out.println("📊 API endpoint: http://localhost:8080/api/trains");
    }
}
