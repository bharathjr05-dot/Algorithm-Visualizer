package com.algorithmvisualizer.algorithm;

import com.algorithmvisualizer.model.AlgorithmStep;
import java.util.ArrayList;
import java.util.List;

public class SearchingAlgorithms {
    
    public static List<AlgorithmStep> linearSearch(int[] array, int target) {
        List<AlgorithmStep> steps = new ArrayList<>();
        
        steps.add(new AlgorithmStep("Linear Search", 0, "Starting Linear Search for target: " + target, 
                                  arrayToString(array), ""));
        
        for (int i = 0; i < array.length; i++) {
            steps.add(new AlgorithmStep("Linear Search", i + 1, 
                                      "Checking element at index " + i + " (value: " + array[i] + ")", 
                                      arrayToString(array), String.valueOf(i)));
            
            if (array[i] == target) {
                steps.add(new AlgorithmStep("Linear Search", i + 2, 
                                          "Found target " + target + " at index " + i, 
                                          arrayToString(array), String.valueOf(i)));
                return steps;
            }
        }
        
        steps.add(new AlgorithmStep("Linear Search", array.length + 1, 
                                  "Target " + target + " not found in array", 
                                  arrayToString(array), ""));
        return steps;
    }
    
    public static List<AlgorithmStep> binarySearch(int[] array, int target) {
        List<AlgorithmStep> steps = new ArrayList<>();
        
        steps.add(new AlgorithmStep("Binary Search", 0, "Starting Binary Search for target: " + target + " (Array must be sorted)", 
                                  arrayToString(array), ""));
        
        int left = 0;
        int right = array.length - 1;
        int stepCount = 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            steps.add(new AlgorithmStep("Binary Search", stepCount++, 
                                      "Checking middle element at index " + mid + " (value: " + array[mid] + ")", 
                                      arrayToString(array), String.valueOf(mid)));
            
            if (array[mid] == target) {
                steps.add(new AlgorithmStep("Binary Search", stepCount++, 
                                          "Found target " + target + " at index " + mid, 
                                          arrayToString(array), String.valueOf(mid)));
                return steps;
            }
            
            if (array[mid] < target) {
                steps.add(new AlgorithmStep("Binary Search", stepCount++, 
                                          "Target is greater than " + array[mid] + ", searching right half", 
                                          arrayToString(array), left + "," + right));
                left = mid + 1;
            } else {
                steps.add(new AlgorithmStep("Binary Search", stepCount++, 
                                          "Target is less than " + array[mid] + ", searching left half", 
                                          arrayToString(array), left + "," + right));
                right = mid - 1;
            }
        }
        
        steps.add(new AlgorithmStep("Binary Search", stepCount++, 
                                  "Target " + target + " not found in array", 
                                  arrayToString(array), ""));
        return steps;
    }
    
    public static List<AlgorithmStep> jumpSearch(int[] array, int target) {
        List<AlgorithmStep> steps = new ArrayList<>();
        int n = array.length;
        int step = (int) Math.floor(Math.sqrt(n));
        int prev = 0;
        int stepCount = 1;
        
        steps.add(new AlgorithmStep("Jump Search", 0, "Starting Jump Search for target: " + target + " with step size: " + step, 
                                  arrayToString(array), ""));
        
        // Jump through blocks
        while (array[Math.min(step, n) - 1] < target) {
            steps.add(new AlgorithmStep("Jump Search", stepCount++, 
                                      "Checking block ending at index " + (Math.min(step, n) - 1) + 
                                      " (value: " + array[Math.min(step, n) - 1] + ")", 
                                      arrayToString(array), String.valueOf(Math.min(step, n) - 1)));
            
            prev = step;
            step += (int) Math.floor(Math.sqrt(n));
            
            if (prev >= n) {
                steps.add(new AlgorithmStep("Jump Search", stepCount++, 
                                          "Target " + target + " not found in array", 
                                          arrayToString(array), ""));
                return steps;
            }
        }
        
        // Linear search in the identified block
        steps.add(new AlgorithmStep("Jump Search", stepCount++, 
                                  "Found potential block, performing linear search from index " + prev, 
                                  arrayToString(array), String.valueOf(prev)));
        
        while (array[prev] < target) {
            steps.add(new AlgorithmStep("Jump Search", stepCount++, 
                                      "Checking element at index " + prev + " (value: " + array[prev] + ")", 
                                      arrayToString(array), String.valueOf(prev)));
            prev++;
            
            if (prev == Math.min(step, n)) {
                steps.add(new AlgorithmStep("Jump Search", stepCount++, 
                                          "Target " + target + " not found in array", 
                                          arrayToString(array), ""));
                return steps;
            }
        }
        
        if (array[prev] == target) {
            steps.add(new AlgorithmStep("Jump Search", stepCount++, 
                                      "Found target " + target + " at index " + prev, 
                                      arrayToString(array), String.valueOf(prev)));
        } else {
            steps.add(new AlgorithmStep("Jump Search", stepCount++, 
                                      "Target " + target + " not found in array", 
                                      arrayToString(array), ""));
        }
        
        return steps;
    }
    
    public static List<AlgorithmStep> interpolationSearch(int[] array, int target) {
        List<AlgorithmStep> steps = new ArrayList<>();
        int low = 0;
        int high = array.length - 1;
        int stepCount = 1;
        
        steps.add(new AlgorithmStep("Interpolation Search", 0, "Starting Interpolation Search for target: " + target, 
                                  arrayToString(array), ""));
        
        while (low <= high && target >= array[low] && target <= array[high]) {
            if (low == high) {
                if (array[low] == target) {
                    steps.add(new AlgorithmStep("Interpolation Search", stepCount++, 
                                              "Found target " + target + " at index " + low, 
                                              arrayToString(array), String.valueOf(low)));
                } else {
                    steps.add(new AlgorithmStep("Interpolation Search", stepCount++, 
                                              "Target " + target + " not found", 
                                              arrayToString(array), ""));
                }
                return steps;
            }
            
            // Calculate position using interpolation formula
            int pos = low + (((target - array[low]) * (high - low)) / (array[high] - array[low]));
            
            steps.add(new AlgorithmStep("Interpolation Search", stepCount++, 
                                      "Interpolated position: " + pos + " (value: " + array[pos] + ")", 
                                      arrayToString(array), String.valueOf(pos)));
            
            if (array[pos] == target) {
                steps.add(new AlgorithmStep("Interpolation Search", stepCount++, 
                                          "Found target " + target + " at index " + pos, 
                                          arrayToString(array), String.valueOf(pos)));
                return steps;
            }
            
            if (array[pos] < target) {
                steps.add(new AlgorithmStep("Interpolation Search", stepCount++, 
                                          "Target is greater, searching right half", 
                                          arrayToString(array), low + "," + high));
                low = pos + 1;
            } else {
                steps.add(new AlgorithmStep("Interpolation Search", stepCount++, 
                                          "Target is smaller, searching left half", 
                                          arrayToString(array), low + "," + high));
                high = pos - 1;
            }
        }
        
        steps.add(new AlgorithmStep("Interpolation Search", stepCount++, 
                                  "Target " + target + " not found in array", 
                                  arrayToString(array), ""));
        return steps;
    }
    
    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}