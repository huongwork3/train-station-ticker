package com.example.trainticker.dto;

import com.example.trainticker.model.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

/**
 * Train Schedule Data Transfer Object (DTO)
 * 
 * DTOs are used to transfer data between different layers of the application.
 * They help us:
 * 1. Control what data is exposed to the frontend
 * 2. Avoid exposing internal entity structure
 * 3. Combine data from multiple entities into a single response
 * 4. Format data appropriately for the client
 * 
 * This DTO combines Train and Schedule information for the ticker display.
 */
public class TrainScheduleDTO {
    
    private String trainNumber;
    private String trainName;
    private String destination;
    
    /**
     * @JsonFormat ensures time is formatted consistently in JSON response
     * HH:mm format gives us "14:30" instead of "14:30:00"
     */
    @JsonFormat(pattern = "HH:mm")
    private LocalTime departureTime;
    
    @JsonFormat(pattern = "HH:mm")
    private LocalTime arrivalTime;
    
    private String platform;
    private String status;
    private Integer delayMinutes;
    private String route;
    
    // Default constructor (required for JSON serialization)
    public TrainScheduleDTO() {}
    
    /**
     * Constructor that converts Schedule entity to DTO
     * This is a common pattern - create DTO from entity
     */
    public TrainScheduleDTO(Schedule schedule) {
        this.trainNumber = schedule.getTrain().getTrainNumber();
        this.trainName = schedule.getTrain().getTrainName();
        this.route = schedule.getTrain().getRoute();
        this.destination = schedule.getDestination();
        this.departureTime = schedule.getDepartureTime();
        this.arrivalTime = schedule.getArrivalTime();
        this.platform = schedule.getPlatform();
        this.status = schedule.getStatus().toString();
        this.delayMinutes = schedule.getDelayMinutes();
    }
    
    /**
     * Constructor with all parameters
     * Useful when creating DTOs manually
     */
    public TrainScheduleDTO(String trainNumber, String trainName, String route, String destination,
                           LocalTime departureTime, LocalTime arrivalTime, String platform,
                           String status, Integer delayMinutes) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.route = route;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.platform = platform;
        this.status = status;
        this.delayMinutes = delayMinutes;
    }
    
    // Getters and Setters
    public String getTrainNumber() {
        return trainNumber;
    }
    
    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }
    
    public String getTrainName() {
        return trainName;
    }
    
    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }
    
    public String getRoute() {
        return route;
    }
    
    public void setRoute(String route) {
        this.route = route;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public LocalTime getDepartureTime() {
        return departureTime;
    }
    
    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }
    
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }
    
    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    public String getPlatform() {
        return platform;
    }
    
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Integer getDelayMinutes() {
        return delayMinutes;
    }
    
    public void setDelayMinutes(Integer delayMinutes) {
        this.delayMinutes = delayMinutes;
    }
    
    /**
     * Utility method to get formatted status with delay information
     * This makes it easier for the frontend to display status
     */
    public String getFormattedStatus() {
        if ("DELAYED".equals(status) && delayMinutes != null && delayMinutes > 0) {
            return status + " (+" + delayMinutes + " min)";
        }
        return status;
    }
    
    /**
     * Utility method to check if train is delayed
     */
    public boolean isDelayed() {
        return "DELAYED".equals(status);
    }
    
    /**
     * Utility method to check if train is on time
     */
    public boolean isOnTime() {
        return "ON_TIME".equals(status);
    }
    
    // toString method for debugging
    @Override
    public String toString() {
        return "TrainScheduleDTO{" +
                "trainNumber='" + trainNumber + '\'' +
                ", trainName='" + trainName + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime=" + departureTime +
                ", platform='" + platform + '\'' +
                ", status='" + status + '\'' +
                ", delayMinutes=" + delayMinutes +
                '}';
    }
}
