package com.algorithmvisualizer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "algorithms")
public class Algorithm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private AlgorithmCategory category;
    
    private String description;
    
    @Column(name = "time_complexity")
    private String timeComplexity;
    
    @Column(name = "space_complexity")
    private String spaceComplexity;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level")
    private DifficultyLevel difficultyLevel = DifficultyLevel.EASY;
    
    @Column(name = "average_completion_time")
    private Integer averageCompletionTime = 0;
    
    @Column(name = "total_attempts")
    private Integer totalAttempts = 0;
    
    @Column(name = "success_rate")
    private Double successRate = 0.0;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public enum DifficultyLevel {
        EASY, MEDIUM, HARD
    }
    
    // Constructors
    public Algorithm() {}
    
    public Algorithm(String name, String description, String timeComplexity, String spaceComplexity) {
        this.name = name;
        this.description = description;
        this.timeComplexity = timeComplexity;
        this.spaceComplexity = spaceComplexity;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public AlgorithmCategory getCategory() { return category; }
    public void setCategory(AlgorithmCategory category) { this.category = category; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getTimeComplexity() { return timeComplexity; }
    public void setTimeComplexity(String timeComplexity) { this.timeComplexity = timeComplexity; }
    
    public String getSpaceComplexity() { return spaceComplexity; }
    public void setSpaceComplexity(String spaceComplexity) { this.spaceComplexity = spaceComplexity; }
    
    public DifficultyLevel getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(DifficultyLevel difficultyLevel) { this.difficultyLevel = difficultyLevel; }
    
    public Integer getAverageCompletionTime() { return averageCompletionTime; }
    public void setAverageCompletionTime(Integer averageCompletionTime) { this.averageCompletionTime = averageCompletionTime; }
    
    public Integer getTotalAttempts() { return totalAttempts; }
    public void setTotalAttempts(Integer totalAttempts) { this.totalAttempts = totalAttempts; }
    
    public Double getSuccessRate() { return successRate; }
    public void setSuccessRate(Double successRate) { this.successRate = successRate; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}