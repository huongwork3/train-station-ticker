-- Train Station Database Schema
-- This creates the database structure for our train ticker system

CREATE DATABASE IF NOT EXISTS train_station_db;
USE train_station_db;

-- Table to store train information
CREATE TABLE trains (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    train_number VARCHAR(10) NOT NULL UNIQUE,
    train_name VARCHAR(100) NOT NULL,
    route VARCHAR(200) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table to store train schedules
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

-- Create indexes for better performance
CREATE INDEX idx_train_number ON trains(train_number);
CREATE INDEX idx_schedule_date ON schedules(schedule_date);
CREATE INDEX idx_departure_time ON schedules(departure_time);
