package com.example.trainticker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Train Entity Class
 * 
 * This represents the 'trains' table in our database.
 * Each Train can have multiple Schedules (one-to-many relationship).
 * 
 * Key Annotations:
 * @Entity - Marks this class as a JPA entity (database table)
 * @Table - Specifies the table name in database
 * @Id - Marks the primary key field
 * @GeneratedValue - Auto-generates the ID value
 * @Column - Maps field to database column
 * @OneToMany - Defines relationship with Schedule entity
 */
@Entity
@Table(name = "trains")
public class Train {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "train_number", unique = true, nullable = false, length = 10)
    private String trainNumber;
    
    @Column(name = "train_name", nullable = false, length = 100)
    private String trainName;
    
    @Column(name = "route", nullable = false, length = 200)
    private String route;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    /**
     * One-to-Many relationship with Schedule
     * mappedBy = "train" refers to the 'train' field in Schedule class
     * cascade = CascadeType.ALL means operations on Train will cascade to Schedules
     */
    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Schedule> schedules;
    
    // Default constructor (required by JPA)
    public Train() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor with parameters
    public Train(String trainNumber, String trainName, String route) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.route = route;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<Schedule> getSchedules() {
        return schedules;
    }
    
    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
    
    // toString method for debugging
    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", trainNumber='" + trainNumber + '\'' +
                ", trainName='" + trainName + '\'' +
                ", route='" + route + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
