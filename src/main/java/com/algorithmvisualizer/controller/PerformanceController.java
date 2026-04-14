package com.algorithmvisualizer.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/performance")
@CrossOrigin(origins = "*")
public class PerformanceController {

    @PostMapping("/compare-sorting")
    public Map<String, Object> compareSortingAlgorithms(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<Integer> data = (List<Integer>) request.get("data");
        int[] array = data.stream().mapToInt(Integer::intValue).toArray();
        
        Map<String, Object> results = new HashMap<>();
        
        // Bubble Sort
        int[] bubbleArray = array.clone();
        long startTime = System.nanoTime();
        bubbleSort(bubbleArray);
        long bubbleTime = System.nanoTime() - startTime;
        
        // Selection Sort
        int[] selectionArray = array.clone();
        startTime = System.nanoTime();
        selectionSort(selectionArray);
        long selectionTime = System.nanoTime() - startTime;
        
        // Insertion Sort
        int[] insertionArray = array.clone();
        startTime = System.nanoTime();
        insertionSort(insertionArray);
        long insertionTime = System.nanoTime() - startTime;
        
        results.put("bubbleSort", bubbleTime / 1000000.0); // Convert to milliseconds
        results.put("selectionSort", selectionTime / 1000000.0);
        results.put("insertionSort", insertionTime / 1000000.0);
        results.put("arraySize", array.length);
        
        return results;
    }
    
    private void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    
    private void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }
    
    private void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }
}