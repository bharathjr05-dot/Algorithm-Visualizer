package com.algorithmvisualizer.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_progress")
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "algorithm_id")
    private Algorithm algorithm;
    
    private Integer attempts = 0;
    
    @Column(name = "completed_attempts")
    private Integer completedAttempts = 0;
    
    @Column(name = "best_time")
    private Integer bestTime;
    
    @Column(name = "best_score")
    private Integer bestScore = 0;
    
    @Column(name = "average_time")
    private BigDecimal averageTime;
    
    @Column(name = "last_attempt")
    private LocalDateTime lastAttempt;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "mastery_level")
    private MasteryLevel masteryLevel = MasteryLevel.NOVICE;
    
    public enum MasteryLevel {
        NOVICE, LEARNING, PROFICIENT, EXPERT
    }
    
    // Constructors
    public UserProgress() {}
    
    public UserProgress(User user, Algorithm algorithm) {
        this.user = user;
        this.algorithm = algorithm;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Algorithm getAlgorithm() { return algorithm; }
    public void setAlgorithm(Algorithm algorithm) { this.algorithm = algorithm; }
    
    public Integer getAttempts() { return attempts; }
    public void setAttempts(Integer attempts) { this.attempts = attempts; }
    
    public Integer getCompletedAttempts() { return completedAttempts; }
    public void setCompletedAttempts(Integer completedAttempts) { this.completedAttempts = completedAttempts; }
    
    public Integer getBestTime() { return bestTime; }
    public void setBestTime(Integer bestTime) { this.bestTime = bestTime; }
    
    public Integer getBestScore() { return bestScore; }
    public void setBestScore(Integer bestScore) { this.bestScore = bestScore; }
    
    public BigDecimal getAverageTime() { return averageTime; }
    public void setAverageTime(BigDecimal averageTime) { this.averageTime = averageTime; }
    
    public LocalDateTime getLastAttempt() { return lastAttempt; }
    public void setLastAttempt(LocalDateTime lastAttempt) { this.lastAttempt = lastAttempt; }
    
    public MasteryLevel getMasteryLevel() { return masteryLevel; }
    public void setMasteryLevel(MasteryLevel masteryLevel) { this.masteryLevel = masteryLevel; }
}