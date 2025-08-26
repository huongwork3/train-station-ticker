package com.example.trainticker.service;

import com.example.trainticker.dto.TrainScheduleDTO;
import com.example.trainticker.model.Schedule;
import com.example.trainticker.repository.ScheduleRepository;
import com.example.trainticker.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Train Service Class
 * 
 * The Service layer contains business logic and acts as a bridge between
 * the Controller (web layer) and Repository (data layer).
 * 
 * Key Annotations:
 * @Service - Marks this as a Spring service component
 * @Transactional - Ensures database operations are wrapped in transactions
 * @Autowired - Tells Spring to inject dependencies automatically
 */
@Service
@Transactional(readOnly = true) // Default to read-only transactions for better performance
public class TrainService {
    
    private final ScheduleRepository scheduleRepository;
    private final TrainRepository trainRepository;
    
    /**
     * Constructor-based dependency injection (recommended over field injection)
     * Spring will automatically inject the repository beans
     */
    @Autowired
    public TrainService(ScheduleRepository scheduleRepository, TrainRepository trainRepository) {
        this.scheduleRepository = scheduleRepository;
        this.trainRepository = trainRepository;
    }
    
    /**
     * Get today's train schedule
     * This is the main method used by our ticker display
     */
    public List<TrainScheduleDTO> getTodaysSchedule() {
        LocalDate today = LocalDate.now();
        
        // Get schedules with train information in a single query (efficient)
        List<Schedule> schedules = scheduleRepository.findTodaysScheduleWithTrainInfo(today);
        
        // Convert entities to DTOs using Java 8 Streams
        return schedules.stream()
                .map(TrainScheduleDTO::new)  // Convert each Schedule to TrainScheduleDTO
                .collect(Collectors.toList());
    }
    
    /**
     * Get schedule for a specific date
     * Useful for viewing future or past schedules
     */
    public List<TrainScheduleDTO> getScheduleByDate(LocalDate date) {
        List<Schedule> schedules = scheduleRepository.findTodaysScheduleWithTrainInfo(date);
        
        return schedules.stream()
                .map(TrainScheduleDTO::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get only upcoming departures (trains that haven't left yet)
     * This is useful for real-time displays
     */
    public List<TrainScheduleDTO> getUpcomingDepartures() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        
        List<Schedule> upcomingSchedules = scheduleRepository.findUpcomingSchedules(today, now);
        
        return upcomingSchedules.stream()
                .map(TrainScheduleDTO::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get schedules by destination
     * Useful for passengers looking for trains to specific cities
     */
    public List<TrainScheduleDTO> getSchedulesByDestination(String destination) {
        List<Schedule> schedules = scheduleRepository
                .findByDestinationContainingIgnoreCaseOrderByDepartureTime(destination);
        
        return schedules.stream()
                .map(TrainScheduleDTO::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get schedules by platform
     * Useful for platform-specific displays
     */
    public List<TrainScheduleDTO> getSchedulesByPlatform(String platform) {
        List<Schedule> schedules = scheduleRepository.findByPlatformOrderByDepartureTime(platform);
        
        return schedules.stream()
                .map(TrainScheduleDTO::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get only delayed trains for today
     * Useful for maintenance and customer service
     */
    public List<TrainScheduleDTO> getDelayedTrains() {
        LocalDate today = LocalDate.now();
        List<Schedule> delayedSchedules = scheduleRepository.findDelayedSchedules(today);
        
        return delayedSchedules.stream()
                .map(TrainScheduleDTO::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get schedules within a specific time range
     * Useful for showing morning, afternoon, or evening departures
     */
    public List<TrainScheduleDTO> getSchedulesInTimeRange(LocalTime startTime, LocalTime endTime) {
        LocalDate today = LocalDate.now();
        List<Schedule> schedules = scheduleRepository
                .findSchedulesInTimeRange(today, startTime, endTime);
        
        return schedules.stream()
                .map(TrainScheduleDTO::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get schedule statistics for today
     * Returns a summary of on-time, delayed, and cancelled trains
     */
    public ScheduleStats getTodaysStats() {
        LocalDate today = LocalDate.now();
        
        long onTimeCount = scheduleRepository.countByScheduleDateAndStatus(today, Schedule.Status.ON_TIME);
        long delayedCount = scheduleRepository.countByScheduleDateAndStatus(today, Schedule.Status.DELAYED);
        long cancelledCount = scheduleRepository.countByScheduleDateAndStatus(today, Schedule.Status.CANCELLED);
        
        return new ScheduleStats(onTimeCount, delayedCount, cancelledCount);
    }
    
    /**
     * Inner class to represent schedule statistics
     * This is a simple data holder class
     */
    public static class ScheduleStats {
        private final long onTimeCount;
        private final long delayedCount;
        private final long cancelledCount;
        private final long totalCount;
        
        public ScheduleStats(long onTimeCount, long delayedCount, long cancelledCount) {
            this.onTimeCount = onTimeCount;
            this.delayedCount = delayedCount;
            this.cancelledCount = cancelledCount;
            this.totalCount = onTimeCount + delayedCount + cancelledCount;
        }
        
        // Getters
        public long getOnTimeCount() { return onTimeCount; }
        public long getDelayedCount() { return delayedCount; }
        public long getCancelledCount() { return cancelledCount; }
        public long getTotalCount() { return totalCount; }
        
        // Utility methods
        public double getOnTimePercentage() {
            return totalCount > 0 ? (double) onTimeCount / totalCount * 100 : 0;
        }
        
        public double getDelayedPercentage() {
            return totalCount > 0 ? (double) delayedCount / totalCount * 100 : 0;
        }
    }
}
