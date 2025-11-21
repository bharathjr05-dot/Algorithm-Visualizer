package com.algorithmvisualizer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_sessions")
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "algorithm_id")
    private Algorithm algorithm;
    
    @Column(name = "session_start")
    private LocalDateTime sessionStart = LocalDateTime.now();
    
    @Column(name = "session_end")
    private LocalDateTime sessionEnd;
    
    @Column(name = "input_array", columnDefinition = "TEXT")
    private String inputArray;
    
    @Column(name = "array_size")
    private Integer arraySize;
    
    @Column(name = "total_steps")
    private Integer totalSteps;
    
    @Column(name = "time_taken")
    private Integer timeTaken;
    
    private Boolean completed = false;
    
    private Integer score = 0;
    
    @Column(name = "mistakes_count")
    private Integer mistakesCount = 0;
    
    // Constructors
    public UserSession() {}
    
    public UserSession(User user, Algorithm algorithm, String inputArray, Integer arraySize) {
        this.user = user;
        this.algorithm = algorithm;
        this.inputArray = inputArray;
        this.arraySize = arraySize;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Algorithm getAlgorithm() { return algorithm; }
    public void setAlgorithm(Algorithm algorithm) { this.algorithm = algorithm; }
    
    public LocalDateTime getSessionStart() { return sessionStart; }
    public void setSessionStart(LocalDateTime sessionStart) { this.sessionStart = sessionStart; }
    
    public LocalDateTime getSessionEnd() { return sessionEnd; }
    public void setSessionEnd(LocalDateTime sessionEnd) { this.sessionEnd = sessionEnd; }
    
    public String getInputArray() { return inputArray; }
    public void setInputArray(String inputArray) { this.inputArray = inputArray; }
    
    public Integer getArraySize() { return arraySize; }
    public void setArraySize(Integer arraySize) { this.arraySize = arraySize; }
    
    public Integer getTotalSteps() { return totalSteps; }
    public void setTotalSteps(Integer totalSteps) { this.totalSteps = totalSteps; }
    
    public Integer getTimeTaken() { return timeTaken; }
    public void setTimeTaken(Integer timeTaken) { this.timeTaken = timeTaken; }
    
    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }
    
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    
    public Integer getMistakesCount() { return mistakesCount; }
    public void setMistakesCount(Integer mistakesCount) { this.mistakesCount = mistakesCount; }
}