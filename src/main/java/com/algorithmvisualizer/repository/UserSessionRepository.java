package com.algorithmvisualizer.repository;

import com.algorithmvisualizer.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    
    List<UserSession> findByUserId(Long userId);
    List<UserSession> findByAlgorithmId(Long algorithmId);
    List<UserSession> findByUserIdAndAlgorithmId(Long userId, Long algorithmId);
    List<UserSession> findByCompletedTrue();
    
    @Query("SELECT us FROM UserSession us WHERE us.sessionStart BETWEEN :startDate AND :endDate")
    List<UserSession> findSessionsBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT us FROM UserSession us WHERE us.user.id = :userId ORDER BY us.score DESC")
    List<UserSession> findUserBestSessions(@Param("userId") Long userId);
    
    @Query("SELECT AVG(us.timeTaken) FROM UserSession us WHERE us.algorithm.id = :algorithmId AND us.completed = true")
    Double getAverageTimeForAlgorithm(@Param("algorithmId") Long algorithmId);
}