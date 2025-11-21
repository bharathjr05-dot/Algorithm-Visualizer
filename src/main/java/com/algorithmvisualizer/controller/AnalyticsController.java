package com.algorithmvisualizer.controller;

import com.algorithmvisualizer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AlgorithmRepository algorithmRepository;
    
    @Autowired
    private UserSessionRepository userSessionRepository;
    
    @Autowired
    private UserProgressRepository userProgressRepository;
    
    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Basic counts
        stats.put("totalUsers", userRepository.count());
        stats.put("totalAlgorithms", algorithmRepository.count());
        stats.put("totalSessions", userSessionRepository.count());
        stats.put("completedSessions", userSessionRepository.findByCompletedTrue().size());
        
        // Performance metrics
        stats.put("topPerformers", userRepository.findTopPerformers(5));
        stats.put("mostPopularAlgorithms", algorithmRepository.findMostPopular());
        stats.put("expertUsers", userProgressRepository.findByMasteryLevel(
            com.algorithmvisualizer.model.UserProgress.MasteryLevel.EXPERT));
        
        return stats;
    }
    
    @GetMapping("/algorithm-difficulty")
    public List<Object[]> getAlgorithmDifficultyAnalysis() {
        return algorithmRepository.getAlgorithmDifficultyAnalysis();
    }
    
    @GetMapping("/user-performance")
    public List<Object[]> getUserPerformanceAnalysis(@RequestParam(defaultValue = "1") int minAlgorithms) {
        return userRepository.getUserPerformanceSummary(minAlgorithms);
    }
    
    @GetMapping("/algorithm/{algorithmId}/stats")
    public Map<String, Object> getAlgorithmStats(@PathVariable Long algorithmId) {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("sessions", userSessionRepository.findByAlgorithmId(algorithmId));
        stats.put("averageTime", userSessionRepository.getAverageTimeForAlgorithm(algorithmId));
        stats.put("userProgress", userProgressRepository.findByAlgorithmId(algorithmId));
        
        return stats;
    }
    
    @GetMapping("/user/{userId}/detailed-stats")
    public Map<String, Object> getUserDetailedStats(@PathVariable Long userId) {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("sessions", userSessionRepository.findByUserId(userId));
        stats.put("progress", userProgressRepository.findByUserId(userId));
        stats.put("bestProgress", userProgressRepository.findUserBestProgress(userId));
        stats.put("completedAlgorithms", userProgressRepository.countCompletedAlgorithms(userId));
        
        return stats;
    }
    
    @GetMapping("/leaderboard/fastest")
    public List<com.algorithmvisualizer.model.UserProgress> getFastestCompletions() {
        return userProgressRepository.findFastestCompletions();
    }
    
    @GetMapping("/trends/skill-distribution")
    public Map<String, Long> getSkillLevelDistribution() {
        Map<String, Long> distribution = new HashMap<>();
        
        distribution.put("BEGINNER", (long) userRepository.findBySkillLevel(
            com.algorithmvisualizer.model.User.SkillLevel.BEGINNER).size());
        distribution.put("INTERMEDIATE", (long) userRepository.findBySkillLevel(
            com.algorithmvisualizer.model.User.SkillLevel.INTERMEDIATE).size());
        distribution.put("ADVANCED", (long) userRepository.findBySkillLevel(
            com.algorithmvisualizer.model.User.SkillLevel.ADVANCED).size());
        
        return distribution;
    }
}