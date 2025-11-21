package com.algorithmvisualizer.service;

import com.algorithmvisualizer.model.*;
import com.algorithmvisualizer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlgorithmSessionService {
    
    @Autowired
    private UserSessionRepository userSessionRepository;
    
    @Autowired
    private UserProgressRepository userProgressRepository;
    
    @Autowired
    private AlgorithmRepository algorithmRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public UserSession startSession(Long userId, Long algorithmId, String inputArray, Integer arraySize) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Algorithm> algorithm = algorithmRepository.findById(algorithmId);
        
        if (user.isPresent() && algorithm.isPresent()) {
            UserSession session = new UserSession(user.get(), algorithm.get(), inputArray, arraySize);
            return userSessionRepository.save(session);
        }
        return null;
    }
    
    public UserSession completeSession(Long sessionId, Integer totalSteps, Integer timeTaken, Integer score, Boolean completed) {
        Optional<UserSession> sessionOpt = userSessionRepository.findById(sessionId);
        if (sessionOpt.isPresent()) {
            UserSession session = sessionOpt.get();
            session.setSessionEnd(LocalDateTime.now());
            session.setTotalSteps(totalSteps);
            session.setTimeTaken(timeTaken);
            session.setScore(score);
            session.setCompleted(completed);
            
            UserSession savedSession = userSessionRepository.save(session);
            
            // Update user progress
            updateUserProgress(session.getUser().getId(), session.getAlgorithm().getId(), timeTaken, score, completed);
            
            return savedSession;
        }
        return null;
    }
    
    private void updateUserProgress(Long userId, Long algorithmId, Integer timeTaken, Integer score, Boolean completed) {
        Optional<UserProgress> progressOpt = userProgressRepository.findByUserIdAndAlgorithmId(userId, algorithmId);
        UserProgress progress;
        
        if (progressOpt.isPresent()) {
            progress = progressOpt.get();
            progress.setAttempts(progress.getAttempts() + 1);
            if (completed) {
                progress.setCompletedAttempts(progress.getCompletedAttempts() + 1);
                if (progress.getBestTime() == null || timeTaken < progress.getBestTime()) {
                    progress.setBestTime(timeTaken);
                }
                if (score > progress.getBestScore()) {
                    progress.setBestScore(score);
                }
                
                // Update mastery level
                if (progress.getCompletedAttempts() >= 10 && progress.getBestScore() >= 95) {
                    progress.setMasteryLevel(UserProgress.MasteryLevel.EXPERT);
                } else if (progress.getCompletedAttempts() >= 5 && progress.getBestScore() >= 80) {
                    progress.setMasteryLevel(UserProgress.MasteryLevel.PROFICIENT);
                } else if (progress.getCompletedAttempts() >= 2) {
                    progress.setMasteryLevel(UserProgress.MasteryLevel.LEARNING);
                }
            }
        } else {
            Optional<User> user = userRepository.findById(userId);
            Optional<Algorithm> algorithm = algorithmRepository.findById(algorithmId);
            if (user.isPresent() && algorithm.isPresent()) {
                progress = new UserProgress(user.get(), algorithm.get());
                progress.setAttempts(1);
                if (completed) {
                    progress.setCompletedAttempts(1);
                    progress.setBestTime(timeTaken);
                    progress.setBestScore(score);
                    progress.setMasteryLevel(UserProgress.MasteryLevel.LEARNING);
                }
            } else {
                return;
            }
        }
        
        progress.setLastAttempt(LocalDateTime.now());
        userProgressRepository.save(progress);
    }
    
    public List<UserSession> getUserSessions(Long userId) {
        return userSessionRepository.findByUserId(userId);
    }
    
    public List<UserSession> getAlgorithmSessions(Long algorithmId) {
        return userSessionRepository.findByAlgorithmId(algorithmId);
    }
    
    public List<UserProgress> getUserProgress(Long userId) {
        return userProgressRepository.findByUserId(userId);
    }
    
    public Double getAverageTimeForAlgorithm(Long algorithmId) {
        return userSessionRepository.getAverageTimeForAlgorithm(algorithmId);
    }
}