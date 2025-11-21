package com.algorithmvisualizer.repository;

import com.algorithmvisualizer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findBySkillLevel(User.SkillLevel skillLevel);
    List<User> findByIsActiveTrue();
    
    @Query("SELECT u FROM User u WHERE u.totalAlgorithmsCompleted >= :minCompleted ORDER BY u.totalAlgorithmsCompleted DESC")
    List<User> findTopPerformers(@Param("minCompleted") Integer minCompleted);
    
    @Query("SELECT u FROM User u WHERE u.totalTimeSpent >= :minTime ORDER BY u.totalTimeSpent DESC")
    List<User> findMostActiveUsers(@Param("minTime") Integer minTime);
    
    @Query("SELECT u.username, u.totalAlgorithmsCompleted, u.totalTimeSpent, u.skillLevel FROM User u WHERE u.totalAlgorithmsCompleted >= :minAlgorithms ORDER BY u.totalAlgorithmsCompleted DESC")
    List<Object[]> getUserPerformanceSummary(@Param("minAlgorithms") Integer minAlgorithms);
}