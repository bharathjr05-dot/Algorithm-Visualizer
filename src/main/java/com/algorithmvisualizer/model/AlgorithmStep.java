package com.algorithmvisualizer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "algorithm_steps")
public class AlgorithmStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "session_id")
    private UserSession session;
    
    private String algorithmName;
    private int stepNumber;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(columnDefinition = "TEXT")
    private String arrayState;
    
    @Column(columnDefinition = "TEXT")
    private String highlightedIndices;
    
    @Column(name = "comparison_count")
    private Integer comparisonCount = 0;
    
    @Column(name = "swap_count")
    private Integer swapCount = 0;
    
    @Column(name = "execution_time_ms")
    private Integer executionTimeMs = 0;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public AlgorithmStep() {}
    
    public AlgorithmStep(String algorithmName, int stepNumber, String description, String arrayState, String highlightedIndices) {
        this.algorithmName = algorithmName;
        this.stepNumber = stepNumber;
        this.description = description;
        this.arrayState = arrayState;
        this.highlightedIndices = highlightedIndices;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public UserSession getSession() { return session; }
    public void setSession(UserSession session) { this.session = session; }
    
    public String getAlgorithmName() { return algorithmName; }
    public void setAlgorithmName(String algorithmName) { this.algorithmName = algorithmName; }
    
    public int getStepNumber() { return stepNumber; }
    public void setStepNumber(int stepNumber) { this.stepNumber = stepNumber; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getArrayState() { return arrayState; }
    public void setArrayState(String arrayState) { this.arrayState = arrayState; }
    
    public String getHighlightedIndices() { return highlightedIndices; }
    public void setHighlightedIndices(String highlightedIndices) { this.highlightedIndices = highlightedIndices; }
    
    public Integer getComparisonCount() { return comparisonCount; }
    public void setComparisonCount(Integer comparisonCount) { this.comparisonCount = comparisonCount; }
    
    public Integer getSwapCount() { return swapCount; }
    public void setSwapCount(Integer swapCount) { this.swapCount = swapCount; }
    
    public Integer getExecutionTimeMs() { return executionTimeMs; }
    public void setExecutionTimeMs(Integer executionTimeMs) { this.executionTimeMs = executionTimeMs; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}