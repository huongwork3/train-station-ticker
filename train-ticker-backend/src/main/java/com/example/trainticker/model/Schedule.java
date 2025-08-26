package com.example.trainticker.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Schedule Entity Class
 * 
 * This represents the 'schedules' table in our database.
 * Each Schedule belongs to one Train (many-to-one relationship).
 * 
 * Key Annotations:
 * @Entity - Marks this class as a JPA entity
 * @Table - Specifies the table name
 * @ManyToOne - Defines relationship with Train entity
 * @JoinColumn - Specifies the foreign key column
 * @Enumerated - Maps Java enum to database enum
 */
@Entity
@Table(name = "schedules")
public class Schedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Many-to-One relationship with Train
     * Many schedules can belong to one train
     * @JoinColumn specifies the foreign key column name
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;
    
    @Column(nullable = false, length = 100)
    private String destination;
    
    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;
    
    @Column(name = "arrival_time", nullable = false)
    private LocalTime arrivalTime;
    
    @Column(nullable = false, length = 5)
    private String platform;
    
    /**
     * Enum for train status
     * @Enumerated(EnumType.STRING) stores the enum name as string in database
     */
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Column(name = "delay_minutes")
    private Integer delayMinutes;
    
    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    /**
     * Enum defining possible train statuses
     * This matches the ENUM in our database schema
     */
    public enum Status {
        ON_TIME, DELAYED, CANCELLED
    }
    
    // Default constructor (required by JPA)
    public Schedule() {
        this.createdAt = LocalDateTime.now();
        this.status = Status.ON_TIME;
        this.delayMinutes = 0;
    }
    
    // Constructor with parameters
    public Schedule(Train train, String destination, LocalTime departureTime, 
                   LocalTime arrivalTime, String platform, LocalDate scheduleDate) {
        this.train = train;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.platform = platform;
        this.scheduleDate = scheduleDate;
        this.status = Status.ON_TIME;
        this.delayMinutes = 0;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Train getTrain() {
        return train;
    }
    
    public void setTrain(Train train) {
        this.train = train;
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
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Integer getDelayMinutes() {
        return delayMinutes;
    }
    
    public void setDelayMinutes(Integer delayMinutes) {
        this.delayMinutes = delayMinutes;
    }
    
    public LocalDate getScheduleDate() {
        return scheduleDate;
    }
    
    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    // toString method for debugging
    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", platform='" + platform + '\'' +
                ", status=" + status +
                ", delayMinutes=" + delayMinutes +
                ", scheduleDate=" + scheduleDate +
                '}';
    }
}
