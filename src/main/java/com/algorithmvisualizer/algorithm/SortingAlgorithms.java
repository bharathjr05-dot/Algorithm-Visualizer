package com.algorithmvisualizer.algorithm;

import com.algorithmvisualizer.model.AlgorithmStep;
import java.util.*;

public class SortingAlgorithms {
    
    public static List<AlgorithmStep> bubbleSort(int[] arr) {
        List<AlgorithmStep> steps = new ArrayList<>();
        int[] workingArray = arr.clone();
        int n = workingArray.length;
        
        steps.add(new AlgorithmStep("Bubble Sort", 0, "Starting Bubble Sort with array", 
                Arrays.toString(workingArray), ""));
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                steps.add(new AlgorithmStep("Bubble Sort", steps.size(), 
                        "Comparing positions " + j + " and " + (j + 1) + ": " + workingArray[j] + " vs " + workingArray[j + 1] + ". Checking if " + workingArray[j] + " is greater than " + workingArray[j + 1] + ".",
                        Arrays.toString(workingArray), j + "," + (j + 1)));
                
                if (workingArray[j] > workingArray[j + 1]) {
                    int temp = workingArray[j];
                    workingArray[j] = workingArray[j + 1];
                    workingArray[j + 1] = temp;
                    
                    steps.add(new AlgorithmStep("Bubble Sort", steps.size(),
                            "" + temp + " is greater than " + workingArray[j] + ", so swapped. " + temp + " is moved to position " + (j + 1) + ".",
                            Arrays.toString(workingArray), j + "," + (j + 1)));
                } else {
                    steps.add(new AlgorithmStep("Bubble Sort", steps.size(),
                            "" + workingArray[j] + " is not greater than " + workingArray[j + 1] + ", so no swap needed. Numbers are in correct order.",
                            Arrays.toString(workingArray), j + "," + (j + 1)));
                }
            }
        }
        
        steps.add(new AlgorithmStep("Bubble Sort", steps.size(), "Array is now sorted!",
                Arrays.toString(workingArray), ""));
        
        return steps;
    }
    
    public static List<AlgorithmStep> selectionSort(int[] arr) {
        List<AlgorithmStep> steps = new ArrayList<>();
        int[] workingArray = arr.clone();
        int n = workingArray.length;
        
        steps.add(new AlgorithmStep("Selection Sort", 0, "Starting Selection Sort",
                Arrays.toString(workingArray), ""));
        
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            steps.add(new AlgorithmStep("Selection Sort", steps.size(),
                    "Finding minimum from position " + i + " onwards. Starting search in unsorted portion. Current value is " + workingArray[i] + ".",
                    Arrays.toString(workingArray), String.valueOf(i)));
            
            for (int j = i + 1; j < n; j++) {
                if (workingArray[j] < workingArray[minIdx]) {
                    minIdx = j;
                    steps.add(new AlgorithmStep("Selection Sort", steps.size(),
                            "Found smaller value " + workingArray[j] + " at position " + j + ". New minimum is " + workingArray[minIdx] + " at position " + minIdx + ".",
                            Arrays.toString(workingArray), j + "," + minIdx));
                } else {
                    steps.add(new AlgorithmStep("Selection Sort", steps.size(),
                            "" + workingArray[j] + " at position " + j + " is not smaller than current min " + workingArray[minIdx] + " at position " + minIdx + ".",
                            Arrays.toString(workingArray), j + "," + minIdx));
                }
            }
            
            if (minIdx != i) {
                int temp = workingArray[minIdx];
                workingArray[minIdx] = workingArray[i];
                workingArray[i] = temp;
                
                steps.add(new AlgorithmStep("Selection Sort", steps.size(),
                        "Swapped " + workingArray[i] + " to position " + i + " with " + workingArray[minIdx] + " from position " + minIdx + ". " + workingArray[i] + " is now in sorted position " + i + ".",
                        Arrays.toString(workingArray), i + "," + minIdx));
            } else {
                steps.add(new AlgorithmStep("Selection Sort", steps.size(),
                        "" + workingArray[i] + " at position " + i + " is already the minimum. No swap needed.",
                        Arrays.toString(workingArray), String.valueOf(i)));
            }
        }
        
        steps.add(new AlgorithmStep("Selection Sort", steps.size(), "Array is now sorted!",
                Arrays.toString(workingArray), ""));
        
        return steps;
    }
    
    public static List<AlgorithmStep> insertionSort(int[] arr) {
        List<AlgorithmStep> steps = new ArrayList<>();
        int[] workingArray = arr.clone();
        int n = workingArray.length;
        
        steps.add(new AlgorithmStep("Insertion Sort", 0, "Starting Insertion Sort",
                Arrays.toString(workingArray), ""));
        
        for (int i = 1; i < n; i++) {
            int key = workingArray[i];
            int j = i - 1;
            
            steps.add(new AlgorithmStep("Insertion Sort", steps.size(),
                    "Taking " + key + " from position " + i + " to insert into sorted portion.",
                    Arrays.toString(workingArray), String.valueOf(i)));
            
            while (j >= 0 && workingArray[j] > key) {
                steps.add(new AlgorithmStep("Insertion Sort", steps.size(),
                        "" + workingArray[j] + " at position " + j + " is greater than " + key + ", so shifting " + workingArray[j] + " right.",
                        Arrays.toString(workingArray), j + "," + (j + 1)));
                
                workingArray[j + 1] = workingArray[j];
                
                steps.add(new AlgorithmStep("Insertion Sort", steps.size(),
                        "Shifted " + workingArray[j + 1] + " from position " + j + " to position " + (j + 1) + ".",
                        Arrays.toString(workingArray), String.valueOf(j + 1)));
                
                j--;
            }
            
            workingArray[j + 1] = key;
            steps.add(new AlgorithmStep("Insertion Sort", steps.size(),
                    "Inserted " + key + " at position " + (j + 1) + ". Sorted portion extended.",
                    Arrays.toString(workingArray), String.valueOf(j + 1)));
        }
        
        steps.add(new AlgorithmStep("Insertion Sort", steps.size(), "Array is now sorted!",
                Arrays.toString(workingArray), ""));
        
        return steps;
    }
    
    public static List<AlgorithmStep> quickSort(int[] arr) {
        List<AlgorithmStep> steps = new ArrayList<>();
        int[] workingArray = arr.clone();
        
        steps.add(new AlgorithmStep("Quick Sort", 0, "Starting Quick Sort",
                Arrays.toString(workingArray), ""));
        
        quickSortHelper(workingArray, 0, workingArray.length - 1, steps);
        
        steps.add(new AlgorithmStep("Quick Sort", steps.size(), "Array is now sorted!",
                Arrays.toString(workingArray), ""));
        
        return steps;
    }
    
    private static void quickSortHelper(int[] arr, int low, int high, List<AlgorithmStep> steps) {
        if (low < high) {
            steps.add(new AlgorithmStep("Quick Sort", steps.size(),
                    "Sorting subarray from position " + low + " to " + high + ".",
                    Arrays.toString(arr), low + "," + high));
            
            int pi = partition(arr, low, high, steps);
            
            steps.add(new AlgorithmStep("Quick Sort", steps.size(),
                    "Pivot " + arr[pi] + " is now at correct position " + pi + ". Dividing array.",
                    Arrays.toString(arr), String.valueOf(pi)));
            
            quickSortHelper(arr, low, pi - 1, steps);
            quickSortHelper(arr, pi + 1, high, steps);
        }
    }
    
    private static int partition(int[] arr, int low, int high, List<AlgorithmStep> steps) {
        int pivot = arr[high];
        steps.add(new AlgorithmStep("Quick Sort", steps.size(),
                "Choosing " + pivot + " at position " + high + " as pivot.",
                Arrays.toString(arr), String.valueOf(high)));
        
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            steps.add(new AlgorithmStep("Quick Sort", steps.size(),
                    "Comparing " + arr[j] + " at position " + j + " with pivot " + pivot + ".",
                    Arrays.toString(arr), j + "," + high));
            
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                
                steps.add(new AlgorithmStep("Quick Sort", steps.size(),
                        "" + arr[i] + " is smaller than pivot, swapped to position " + i + ".",
                        Arrays.toString(arr), i + "," + j));
            }
        }
        
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        steps.add(new AlgorithmStep("Quick Sort", steps.size(),
                "Placed pivot " + arr[i + 1] + " at final position " + (i + 1) + ".",
                Arrays.toString(arr), (i + 1) + "," + high));
        
        return i + 1;
    }
    
    public static List<AlgorithmStep> mergeSort(int[] arr) {
        List<AlgorithmStep> steps = new ArrayList<>();
        int[] workingArray = arr.clone();
        
        steps.add(new AlgorithmStep("Merge Sort", 0, "Starting Merge Sort - We will divide the array into smaller parts",
                Arrays.toString(workingArray), ""));
        
        mergeSortHelper(workingArray, 0, workingArray.length - 1, steps);
        
        steps.add(new AlgorithmStep("Merge Sort", steps.size(), "Array is now completely sorted!",
                Arrays.toString(workingArray), ""));
        
        return steps;
    }
    
    private static void mergeSortHelper(int[] arr, int left, int right, List<AlgorithmStep> steps) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            // Show the division step
            String leftPart = Arrays.toString(Arrays.copyOfRange(arr, left, mid + 1));
            String rightPart = Arrays.toString(Arrays.copyOfRange(arr, mid + 1, right + 1));
            
            steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                    "Dividing array into two parts: LEFT " + leftPart + " and RIGHT " + rightPart,
                    Arrays.toString(arr), left + "," + mid + "," + right));
            
            // Recursively sort left half
            steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                    "Now sorting LEFT part " + leftPart + " (positions " + left + " to " + mid + ")",
                    Arrays.toString(arr), left + "," + mid));
            mergeSortHelper(arr, left, mid, steps);
            
            // Recursively sort right half
            steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                    "Now sorting RIGHT part " + rightPart + " (positions " + (mid + 1) + " to " + right + ")",
                    Arrays.toString(arr), (mid + 1) + "," + right));
            mergeSortHelper(arr, mid + 1, right, steps);
            
            // Merge the sorted halves
            merge(arr, left, mid, right, steps);
        } else {
            // Base case: single element
            steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                    "Single element [" + arr[left] + "] at position " + left + " - already sorted",
                    Arrays.toString(arr), String.valueOf(left)));
        }
    }
    
    private static void merge(int[] arr, int left, int mid, int right, List<AlgorithmStep> steps) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];
        
        System.arraycopy(arr, left, leftArr, 0, n1);
        System.arraycopy(arr, mid + 1, rightArr, 0, n2);
        
        steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                "Ready to merge: LEFT " + Arrays.toString(leftArr) + " with RIGHT " + Arrays.toString(rightArr),
                Arrays.toString(arr), left + "," + mid + "," + right));
        
        int i = 0, j = 0, k = left;
        
        while (i < n1 && j < n2) {
            steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                    "Comparing: " + leftArr[i] + " (from left) vs " + rightArr[j] + " (from right)",
                    Arrays.toString(arr), left + "," + right));
            
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                        "" + leftArr[i] + " is smaller or equal, placed at position " + k,
                        Arrays.toString(arr), String.valueOf(k)));
                i++;
            } else {
                arr[k] = rightArr[j];
                steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                        "" + rightArr[j] + " is smaller, placed at position " + k,
                        Arrays.toString(arr), String.valueOf(k)));
                j++;
            }
            k++;
        }
        
        // Copy remaining elements from left array
        while (i < n1) {
            arr[k] = leftArr[i];
            steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                    "No more elements in right array. Copying remaining " + leftArr[i] + " to position " + k,
                    Arrays.toString(arr), String.valueOf(k)));
            i++;
            k++;
        }
        
        // Copy remaining elements from right array
        while (j < n2) {
            arr[k] = rightArr[j];
            steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                    "No more elements in left array. Copying remaining " + rightArr[j] + " to position " + k,
                    Arrays.toString(arr), String.valueOf(k)));
            j++;
            k++;
        }
        
        String mergedPart = Arrays.toString(Arrays.copyOfRange(arr, left, right + 1));
        steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                "✅ Successfully merged! Result: " + mergedPart + " (positions " + left + " to " + right + ")",
                Arrays.toString(arr), left + "," + right));
    }
}