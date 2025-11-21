package com.algorithmvisualizer.controller;

import com.algorithmvisualizer.model.User;
import com.algorithmvisualizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String email = request.get("email");
        String password = request.get("password");
        String fullName = request.get("fullName");
        
        User user = userService.createUser(username, email, password, fullName);
        
        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getId());
        response.put("username", user.getUsername());
        response.put("message", "User registered successfully");
        return response;
    }
    
    @PostMapping("/login")
    public Map<String, Object> loginUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        Optional<User> user = userService.findByUsername(username);
        
        Map<String, Object> response = new HashMap<>();
        if (user.isPresent()) {
            userService.updateLastLogin(user.get().getId());
            response.put("userId", user.get().getId());
            response.put("username", user.get().getUsername());
            response.put("skillLevel", user.get().getSkillLevel());
            response.put("success", true);
        } else {
            response.put("success", false);
            response.put("message", "User not found");
        }
        return response;
    }
    
    @GetMapping("/{userId}/profile")
    public Map<String, Object> getUserProfile(@PathVariable Long userId) {
        userService.updateUserStats(userId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("userStats", "Profile updated");
        return response;
    }
    
    @GetMapping("/leaderboard")
    public List<User> getLeaderboard(@RequestParam(defaultValue = "3") int minCompleted) {
        return userService.getTopPerformers(minCompleted);
    }
    
    @GetMapping("/analytics/performance")
    public List<Object[]> getPerformanceAnalytics(@RequestParam(defaultValue = "1") int minAlgorithms) {
        return userService.getUserPerformanceSummary(minAlgorithms);
    }
}