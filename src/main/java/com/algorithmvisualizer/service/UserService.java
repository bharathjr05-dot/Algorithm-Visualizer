package com.algorithmvisualizer.service;

import com.algorithmvisualizer.model.*;
import com.algorithmvisualizer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserProgressRepository userProgressRepository;
    
    @Autowired
    private UserSessionRepository userSessionRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User createUser(String username, String email, String password, String fullName) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, email, encodedPassword, fullName);
        return userRepository.save(user);
    }
    
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<User> getTopPerformers(int minCompleted) {
        return userRepository.findTopPerformers(minCompleted);
    }
    
    public List<User> getMostActiveUsers(int minTime) {
        return userRepository.findMostActiveUsers(minTime);
    }
    
    public void updateLastLogin(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setLastLogin(LocalDateTime.now());
            userRepository.save(user.get());
        }
    }
    
    public void updateUserStats(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            // Update total algorithms completed
            Long completedCount = userProgressRepository.countCompletedAlgorithms(userId);
            user.setTotalAlgorithmsCompleted(completedCount.intValue());
            
            // Update total time spent
            List<UserSession> sessions = userSessionRepository.findByUserId(userId);
            int totalTime = sessions.stream()
                .filter(s -> s.getTimeTaken() != null)
                .mapToInt(UserSession::getTimeTaken)
                .sum();
            user.setTotalTimeSpent(totalTime);
            
            userRepository.save(user);
        }
    }
    
    public List<Object[]> getUserPerformanceSummary(int minAlgorithms) {
        return userRepository.getUserPerformanceSummary(minAlgorithms);
    }
}