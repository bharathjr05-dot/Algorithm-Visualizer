package com.algorithmvisualizer.repository;

import com.algorithmvisualizer.model.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {
    
    List<Algorithm> findByDifficultyLevel(Algorithm.DifficultyLevel difficultyLevel);
    List<Algorithm> findByCategoryId(Long categoryId);
    Algorithm findByName(String name);
    
    @Query("SELECT a FROM Algorithm a ORDER BY a.successRate DESC")
    List<Algorithm> findBySuccessRateDesc();
    
    @Query("SELECT a FROM Algorithm a ORDER BY a.totalAttempts DESC")
    List<Algorithm> findMostPopular();
    
    @Query(value = "SELECT * FROM algorithm_difficulty_analysis ORDER BY success_rate DESC", nativeQuery = true)
    List<Object[]> getAlgorithmDifficultyAnalysis();
}