package com.algorithmvisualizer.repository;

import com.algorithmvisualizer.model.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    
    List<UserProgress> findByUserId(Long userId);
    List<UserProgress> findByAlgorithmId(Long algorithmId);
    Optional<UserProgress> findByUserIdAndAlgorithmId(Long userId, Long algorithmId);
    List<UserProgress> findByMasteryLevel(UserProgress.MasteryLevel masteryLevel);
    
    @Query("SELECT up FROM UserProgress up WHERE up.user.id = :userId ORDER BY up.bestScore DESC")
    List<UserProgress> findUserBestProgress(@Param("userId") Long userId);
    
    @Query("SELECT up FROM UserProgress up WHERE up.completedAttempts > 0 ORDER BY up.bestTime ASC")
    List<UserProgress> findFastestCompletions();
    
    @Query("SELECT COUNT(up) FROM UserProgress up WHERE up.user.id = :userId AND up.completedAttempts > 0")
    Long countCompletedAlgorithms(@Param("userId") Long userId);
}