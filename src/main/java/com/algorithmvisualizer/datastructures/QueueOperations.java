package com.algorithmvisualizer.datastructures;

import java.util.*;

public class QueueOperations {
    
    public static List<DataStructureStep> enqueueElement(List<Integer> queue, int element) {
        List<DataStructureStep> steps = new ArrayList<>();
        
        // Step 1: Show initial state
        steps.add(new DataStructureStep(
            "enqueue",
            "Initial queue state",
            queue.toString(),
            "",
            1
        ));
        
        // Step 2: Add element to rear
        queue.add(element);
        steps.add(new DataStructureStep(
            "enqueue",
            "Enqueued " + element + " to rear of queue",
            queue.toString(),
            String.valueOf(queue.size() - 1),
            2
        ));
        
        return steps;
    }
    
    public static List<DataStructureStep> dequeueElement(List<Integer> queue) {
        List<DataStructureStep> steps = new ArrayList<>();
        
        if (queue.isEmpty()) {
            steps.add(new DataStructureStep(
                "dequeue",
                "Queue is empty - cannot dequeue",
                queue.toString(),
                "",
                1
            ));
            return steps;
        }
        
        // Step 1: Show initial state
        steps.add(new DataStructureStep(
            "dequeue",
            "Initial queue state",
            queue.toString(),
            "0",
            1
        ));
        
        // Step 2: Remove element from front
        int dequeuedElement = queue.remove(0);
        steps.add(new DataStructureStep(
            "dequeue",
            "Dequeued " + dequeuedElement + " from front of queue",
            queue.toString(),
            "",
            2
        ));
        
        return steps;
    }
    
    public static List<DataStructureStep> frontElement(List<Integer> queue) {
        List<DataStructureStep> steps = new ArrayList<>();
        
        if (queue.isEmpty()) {
            steps.add(new DataStructureStep(
                "front",
                "Queue is empty - no front element",
                queue.toString(),
                "",
                1
            ));
            return steps;
        }
        
        // Show front element
        steps.add(new DataStructureStep(
            "front",
            "Front element is: " + queue.get(0),
            queue.toString(),
            "0",
            1
        ));
        
        return steps;
    }
    
    public static List<DataStructureStep> rearElement(List<Integer> queue) {
        List<DataStructureStep> steps = new ArrayList<>();
        
        if (queue.isEmpty()) {
            steps.add(new DataStructureStep(
                "rear",
                "Queue is empty - no rear element",
                queue.toString(),
                "",
                1
            ));
            return steps;
        }
        
        // Show rear element
        steps.add(new DataStructureStep(
            "rear",
            "Rear element is: " + queue.get(queue.size() - 1),
            queue.toString(),
            String.valueOf(queue.size() - 1),
            1
        ));
        
        return steps;
    }
    
    public static List<DataStructureStep> searchElement(List<Integer> queue, int element) {
        List<DataStructureStep> steps = new ArrayList<>();
        
        // Step 1: Show initial state
        steps.add(new DataStructureStep(
            "search",
            "Searching for " + element + " in queue",
            queue.toString(),
            "",
            1
        ));
        
        // Step 2: Search through queue
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i) == element) {
                steps.add(new DataStructureStep(
                    "search",
                    "Found " + element + " at position " + i,
                    queue.toString(),
                    String.valueOf(i),
                    i + 2
                ));
                return steps;
            } else {
                steps.add(new DataStructureStep(
                    "search",
                    "Checking position " + i + ": " + queue.get(i),
                    queue.toString(),
                    String.valueOf(i),
                    i + 2
                ));
            }
        }
        
        // Element not found
        steps.add(new DataStructureStep(
            "search",
            "Element " + element + " not found in queue",
            queue.toString(),
            "",
            queue.size() + 2
        ));
        
        return steps;
    }
}