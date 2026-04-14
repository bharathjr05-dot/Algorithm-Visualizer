package com.algorithmvisualizer.controller;

import com.algorithmvisualizer.algorithm.SortingAlgorithms;
import com.algorithmvisualizer.algorithm.SearchingAlgorithms;
import com.algorithmvisualizer.model.*;
import com.algorithmvisualizer.service.*;
import com.algorithmvisualizer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/algorithms")
@CrossOrigin(origins = "*")
public class AlgorithmController {
    
    @Autowired
    private AlgorithmSessionService sessionService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AlgorithmRepository algorithmRepository;
    
    @PostMapping("/start-session")
    public Map<String, Object> startAlgorithmSession(@RequestBody Map<String, Object> request) {
        Long userId = Long.valueOf(request.get("userId").toString());
        String algorithmName = request.get("algorithmName").toString();
        String inputArray = request.get("inputArray").toString();
        Integer arraySize = Integer.valueOf(request.get("arraySize").toString());
        
        Algorithm algorithm = algorithmRepository.findByName(algorithmName);
        if (algorithm == null) {
            throw new IllegalArgumentException("Algorithm not found: " + algorithmName);
        }
        
        UserSession session = sessionService.startSession(userId, algorithm.getId(), inputArray, arraySize);
        
        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", session.getId());
        response.put("message", "Session started successfully");
        return response;
    }
    
    @PostMapping("/complete-session")
    public Map<String, Object> completeSession(@RequestBody Map<String, Object> request) {
        Long sessionId = Long.valueOf(request.get("sessionId").toString());
        Integer totalSteps = Integer.valueOf(request.get("totalSteps").toString());
        Integer timeTaken = Integer.valueOf(request.get("timeTaken").toString());
        Integer score = Integer.valueOf(request.get("score").toString());
        Boolean completed = Boolean.valueOf(request.get("completed").toString());
        
        UserSession session = sessionService.completeSession(sessionId, totalSteps, timeTaken, score, completed);
        
        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", session.getId());
        response.put("completed", session.getCompleted());
        response.put("score", session.getScore());
        response.put("message", "Session completed successfully");
        return response;
    }
    
    @PostMapping("/bubble-sort")
    public List<AlgorithmStep> bubbleSort(@RequestBody int[] array) {
        return SortingAlgorithms.bubbleSort(array);
    }
    
    @PostMapping("/selection-sort")
    public List<AlgorithmStep> selectionSort(@RequestBody int[] array) {
        return SortingAlgorithms.selectionSort(array);
    }
    
    @PostMapping("/insertion-sort")
    public List<AlgorithmStep> insertionSort(@RequestBody int[] array) {
        return SortingAlgorithms.insertionSort(array);
    }
    
    @PostMapping("/quick-sort")
    public List<AlgorithmStep> quickSort(@RequestBody int[] array) {
        return SortingAlgorithms.quickSort(array);
    }
    
    @PostMapping("/merge-sort")
    public List<AlgorithmStep> mergeSort(@RequestBody int[] array) {
        return SortingAlgorithms.mergeSort(array);
    }
    
    @PostMapping("/heap-sort")
    public List<AlgorithmStep> heapSort(@RequestBody int[] array) {
        return SortingAlgorithms.heapSort(array);
    }
    
    @GetMapping("/user/{userId}/progress")
    public List<UserProgress> getUserProgress(@PathVariable Long userId) {
        return sessionService.getUserProgress(userId);
    }
    
    @GetMapping("/user/{userId}/sessions")
    public List<UserSession> getUserSessions(@PathVariable Long userId) {
        return sessionService.getUserSessions(userId);
    }
    
    @GetMapping("/analytics/top-performers")
    public List<User> getTopPerformers(@RequestParam(defaultValue = "5") int minCompleted) {
        return userService.getTopPerformers(minCompleted);
    }
    
    @GetMapping("/analytics/algorithm-stats")
    public List<Object[]> getAlgorithmStats() {
        return algorithmRepository.getAlgorithmDifficultyAnalysis();
    }
    
    // Searching Algorithm Endpoints
    @PostMapping("/linear-search")
    public List<AlgorithmStep> linearSearch(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<Integer> arrayList = (List<Integer>) request.get("array");
        int[] array = arrayList.stream().mapToInt(Integer::intValue).toArray();
        int target = (Integer) request.get("target");
        return SearchingAlgorithms.linearSearch(array, target);
    }
    
    @PostMapping("/binary-search")
    public List<AlgorithmStep> binarySearch(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<Integer> arrayList = (List<Integer>) request.get("array");
        int[] array = arrayList.stream().mapToInt(Integer::intValue).toArray();
        int target = (Integer) request.get("target");
        return SearchingAlgorithms.binarySearch(array, target);
    }
    
    @PostMapping("/jump-search")
    public List<AlgorithmStep> jumpSearch(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<Integer> arrayList = (List<Integer>) request.get("array");
        int[] array = arrayList.stream().mapToInt(Integer::intValue).toArray();
        int target = (Integer) request.get("target");
        return SearchingAlgorithms.jumpSearch(array, target);
    }
    
    @PostMapping("/interpolation-search")
    public List<AlgorithmStep> interpolationSearch(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<Integer> arrayList = (List<Integer>) request.get("array");
        int[] array = arrayList.stream().mapToInt(Integer::intValue).toArray();
        int target = (Integer) request.get("target");
        return SearchingAlgorithms.interpolationSearch(array, target);
    }
    
    @GetMapping("/demo/{algorithm}")
    public List<AlgorithmStep> getDemoSteps(@PathVariable String algorithm) {
        int[] demoArray = {64, 34, 25, 12, 22, 11, 90};
        int[] sortedArray = {11, 12, 22, 25, 34, 64, 90};
        int target = 25;
        
        switch (algorithm.toLowerCase()) {
            case "bubble-sort":
                return SortingAlgorithms.bubbleSort(demoArray);
            case "selection-sort":
                return SortingAlgorithms.selectionSort(demoArray);
            case "insertion-sort":
                return SortingAlgorithms.insertionSort(demoArray);
            case "quick-sort":
                return SortingAlgorithms.quickSort(demoArray);
            case "merge-sort":
                return SortingAlgorithms.mergeSort(demoArray);
            case "linear-search":
                return SearchingAlgorithms.linearSearch(demoArray, target);
            case "binary-search":
                return SearchingAlgorithms.binarySearch(sortedArray, target);
            case "jump-search":
                return SearchingAlgorithms.jumpSearch(sortedArray, target);
            case "interpolation-search":
                return SearchingAlgorithms.interpolationSearch(sortedArray, target);
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }
    }
}