package com.example.trainticker.repository;

import com.example.trainticker.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Train Repository Interface
 * 
 * This interface extends JpaRepository which provides:
 * - Basic CRUD operations (Create, Read, Update, Delete)
 * - Pagination and sorting capabilities
 * - Custom query methods
 * 
 * JpaRepository<Train, Long> means:
 * - Train: The entity type this repository manages
 * - Long: The type of the entity's primary key
 * 
 * Spring Data JPA automatically implements this interface at runtime
 */
@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    
    /**
     * Find train by train number
     * Spring Data JPA automatically generates the query based on method name:
     * SELECT * FROM trains WHERE train_number = ?
     */
    Optional<Train> findByTrainNumber(String trainNumber);
    
    /**
     * Find trains by route containing specific text (case-insensitive)
     * Useful for searching trains by route
     */
    List<Train> findByRouteContainingIgnoreCase(String route);
    
    /**
     * Find trains by train name containing specific text (case-insensitive)
     * Useful for searching trains by name
     */
    List<Train> findByTrainNameContainingIgnoreCase(String trainName);
    
    /**
     * Custom query to find all trains with their schedules
     * @Query annotation allows us to write custom JPQL (Java Persistence Query Language)
     * This is useful when method name queries become too complex
     */
    @Query("SELECT DISTINCT t FROM Train t LEFT JOIN FETCH t.schedules")
    List<Train> findAllTrainsWithSchedules();
    
    /**
     * Check if a train exists by train number
     * Returns true if train exists, false otherwise
     */
    boolean existsByTrainNumber(String trainNumber);
}
