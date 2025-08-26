package com.example.trainticker.controller;

import com.example.trainticker.dto.TrainScheduleDTO;
import com.example.trainticker.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Train Controller Class
 * 
 * This is the REST API layer that handles HTTP requests and responses.
 * It acts as the entry point for our web application.
 * 
 * Key Annotations:
 * @RestController - Combines @Controller and @ResponseBody
 * @RequestMapping - Maps HTTP requests to handler methods
 * @CrossOrigin - Enables CORS (Cross-Origin Resource Sharing)
 * @GetMapping - Maps HTTP GET requests
 * @PathVariable - Extracts values from URL path
 * @RequestParam - Extracts query parameters
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8000"}) // Allow frontend access
public class TrainController {
    
    private final TrainService trainService;
    
    /**
     * Constructor-based dependency injection
     */
    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }
    
    /**
     * GET /api/trains
     * Returns today's train schedule
     * This is the main endpoint used by our ticker display
     */
    @GetMapping("/trains")
    public ResponseEntity<List<TrainScheduleDTO>> getTodaysTrains() {
        try {
            List<TrainScheduleDTO> trains = trainService.getTodaysSchedule();
            
            // Return 200 OK with the train data
            return ResponseEntity.ok(trains);
            
        } catch (Exception e) {
            // Log the error (in production, use proper logging framework)
            System.err.println("Error fetching today's trains: " + e.getMessage());
            e.printStackTrace();
            
            // Return 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/trains/{date}
     * Returns train schedule for a specific date
     * Date format: YYYY-MM-DD (e.g., 2024-01-15)
     * 
     * Example: GET /api/trains/2024-01-15
     */
    @GetMapping("/trains/{date}")
    public ResponseEntity<List<TrainScheduleDTO>> getTrainsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<TrainScheduleDTO> trains = trainService.getScheduleByDate(date);
            return ResponseEntity.ok(trains);
            
        } catch (Exception e) {
            System.err.println("Error fetching trains for date " + date + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/trains/upcoming
     * Returns only upcoming departures (trains that haven't left yet)
     * Useful for real-time displays
     */
    @GetMapping("/trains/upcoming")
    public ResponseEntity<List<TrainScheduleDTO>> getUpcomingTrains() {
        try {
            List<TrainScheduleDTO> trains = trainService.getUpcomingDepartures();
            return ResponseEntity.ok(trains);
            
        } catch (Exception e) {
            System.err.println("Error fetching upcoming trains: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/trains/destination/{destination}
     * Returns trains going to a specific destination
     * 
     * Example: GET /api/trains/destination/Boston
     */
    @GetMapping("/trains/destination/{destination}")
    public ResponseEntity<List<TrainScheduleDTO>> getTrainsByDestination(@PathVariable String destination) {
        try {
            List<TrainScheduleDTO> trains = trainService.getSchedulesByDestination(destination);
            return ResponseEntity.ok(trains);
            
        } catch (Exception e) {
            System.err.println("Error fetching trains to " + destination + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/trains/platform/{platform}
     * Returns trains departing from a specific platform
     * 
     * Example: GET /api/trains/platform/A1
     */
    @GetMapping("/trains/platform/{platform}")
    public ResponseEntity<List<TrainScheduleDTO>> getTrainsByPlatform(@PathVariable String platform) {
        try {
            List<TrainScheduleDTO> trains = trainService.getSchedulesByPlatform(platform);
            return ResponseEntity.ok(trains);
            
        } catch (Exception e) {
            System.err.println("Error fetching trains from platform " + platform + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/trains/delayed
     * Returns only delayed trains for today
     * Useful for maintenance and customer service
     */
    @GetMapping("/trains/delayed")
    public ResponseEntity<List<TrainScheduleDTO>> getDelayedTrains() {
        try {
            List<TrainScheduleDTO> trains = trainService.getDelayedTrains();
            return ResponseEntity.ok(trains);
            
        } catch (Exception e) {
            System.err.println("Error fetching delayed trains: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/trains/time-range
     * Returns trains departing within a specific time range
     * Query parameters: startTime and endTime in HH:mm format
     * 
     * Example: GET /api/trains/time-range?startTime=08:00&endTime=12:00
     */
    @GetMapping("/trains/time-range")
    public ResponseEntity<List<TrainScheduleDTO>> getTrainsInTimeRange(
            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime startTime,
            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime endTime) {
        try {
            List<TrainScheduleDTO> trains = trainService.getSchedulesInTimeRange(startTime, endTime);
            return ResponseEntity.ok(trains);
            
        } catch (Exception e) {
            System.err.println("Error fetching trains in time range " + startTime + "-" + endTime + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/trains/stats
     * Returns statistics about today's schedule
     * Shows count of on-time, delayed, and cancelled trains
     */
    @GetMapping("/trains/stats")
    public ResponseEntity<TrainService.ScheduleStats> getTodaysStats() {
        try {
            TrainService.ScheduleStats stats = trainService.getTodaysStats();
            return ResponseEntity.ok(stats);
            
        } catch (Exception e) {
            System.err.println("Error fetching train statistics: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/health
     * Simple health check endpoint
     * Returns 200 OK if the service is running
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Train Ticker API is running! 🚂");
    }
    
    /**
     * Exception handler for this controller
     * Catches any unhandled exceptions and returns a proper error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        System.err.println("Unhandled exception in TrainController: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while processing your request");
    }
}
