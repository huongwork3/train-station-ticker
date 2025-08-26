package com.example.trainticker.repository;

import com.example.trainticker.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Schedule Repository Interface
 * 
 * This interface provides data access methods for Schedule entities.
 * It includes both Spring Data JPA generated methods and custom queries.
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    
    /**
     * Find all schedules for a specific date, ordered by departure time
     * This is perfect for our train ticker display
     */
    List<Schedule> findByScheduleDateOrderByDepartureTime(LocalDate date);
    
    /**
     * Find schedules by date and status
     * Useful for filtering only on-time trains or delayed trains
     */
    List<Schedule> findByScheduleDateAndStatusOrderByDepartureTime(LocalDate date, Schedule.Status status);
    
    /**
     * Find schedules departing after a specific time today
     * Useful for showing only upcoming departures
     */
    @Query("SELECT s FROM Schedule s WHERE s.scheduleDate = :date AND s.departureTime >= :time ORDER BY s.departureTime")
    List<Schedule> findUpcomingSchedules(@Param("date") LocalDate date, @Param("time") LocalTime time);
    
    /**
     * Custom query to get today's schedule with train information
     * JOIN FETCH loads the train data in the same query (avoids N+1 problem)
     * This is more efficient than lazy loading when we know we need the train data
     */
    @Query("SELECT s FROM Schedule s JOIN FETCH s.train t WHERE s.scheduleDate = :date ORDER BY s.departureTime")
    List<Schedule> findTodaysScheduleWithTrainInfo(@Param("date") LocalDate date);
    
    /**
     * Find schedules by destination (case-insensitive)
     * Useful for passengers looking for trains to specific destinations
     */
    List<Schedule> findByDestinationContainingIgnoreCaseOrderByDepartureTime(String destination);
    
    /**
     * Find schedules by platform
     * Useful for displaying departures from specific platforms
     */
    List<Schedule> findByPlatformOrderByDepartureTime(String platform);
    
    /**
     * Find delayed schedules for a specific date
     * Useful for maintenance and customer service
     */
    @Query("SELECT s FROM Schedule s JOIN FETCH s.train t WHERE s.scheduleDate = :date AND s.status = 'DELAYED' ORDER BY s.delayMinutes DESC")
    List<Schedule> findDelayedSchedules(@Param("date") LocalDate date);
    
    /**
     * Count schedules by status for a specific date
     * Useful for generating reports
     */
    long countByScheduleDateAndStatus(LocalDate date, Schedule.Status status);
    
    /**
     * Find schedules within a time range for a specific date
     * Useful for showing departures in specific time windows (e.g., morning, afternoon)
     */
    @Query("SELECT s FROM Schedule s JOIN FETCH s.train t WHERE s.scheduleDate = :date AND s.departureTime BETWEEN :startTime AND :endTime ORDER BY s.departureTime")
    List<Schedule> findSchedulesInTimeRange(@Param("date") LocalDate date, 
                                          @Param("startTime") LocalTime startTime, 
                                          @Param("endTime") LocalTime endTime);
}
