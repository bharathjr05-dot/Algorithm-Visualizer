package com.algorithmvisualizer.datastructures;

import java.util.*;

public class ArrayOperations {
    
    public static List<DataStructureStep> insertElement(int[] array, int element, int position) {
        List<DataStructureStep> steps = new ArrayList<>();
        List<Integer> resultArray = new ArrayList<>();
        
        // Convert array to list
        for (int value : array) {
            resultArray.add(value);
        }
        
        steps.add(new DataStructureStep("INSERT", 
            "Starting insertion of " + element + " at position " + position,
            resultArray.toString(), "", 0));
        
        if (position < 0 || position > resultArray.size()) {
            steps.add(new DataStructureStep("INSERT", 
                "Invalid position! Position must be between 0 and " + resultArray.size(),
                resultArray.toString(), "", 1));
            return steps;
        }
        
        steps.add(new DataStructureStep("INSERT", 
            "Shifting elements from position " + position + " to make space",
            resultArray.toString(), String.valueOf(position), 1));
        
        resultArray.add(position, element);
        
        steps.add(new DataStructureStep("INSERT", 
            "Successfully inserted " + element + " at position " + position,
            resultArray.toString(), String.valueOf(position), 2));
        
        return steps;
    }
    
    public static List<DataStructureStep> deleteElement(int[] array, int position) {
        List<DataStructureStep> steps = new ArrayList<>();
        List<Integer> resultArray = new ArrayList<>();
        
        for (int value : array) {
            resultArray.add(value);
        }
        
        steps.add(new DataStructureStep("DELETE", 
            "Starting deletion at position " + position,
            resultArray.toString(), String.valueOf(position), 0));
        
        if (position < 0 || position >= resultArray.size()) {
            steps.add(new DataStructureStep("DELETE", 
                "Invalid position! Position must be between 0 and " + (resultArray.size() - 1),
                resultArray.toString(), "", 1));
            return steps;
        }
        
        int deletedElement = resultArray.get(position);
        
        steps.add(new DataStructureStep("DELETE", 
            "Removing element " + deletedElement + " from position " + position,
            resultArray.toString(), String.valueOf(position), 1));
        
        resultArray.remove(position);
        
        steps.add(new DataStructureStep("DELETE", 
            "Successfully deleted " + deletedElement + ". Elements shifted left",
            resultArray.toString(), "", 2));
        
        return steps;
    }
    
    public static List<DataStructureStep> searchElement(int[] array, int element) {
        List<DataStructureStep> steps = new ArrayList<>();
        List<Integer> resultArray = new ArrayList<>();
        
        for (int value : array) {
            resultArray.add(value);
        }
        
        steps.add(new DataStructureStep("SEARCH", 
            "Starting search for element " + element,
            resultArray.toString(), "", 0));
        
        for (int i = 0; i < resultArray.size(); i++) {
            steps.add(new DataStructureStep("SEARCH", 
                "Checking position " + i + ": " + resultArray.get(i) + " == " + element + "?",
                resultArray.toString(), String.valueOf(i), i + 1));
            
            if (resultArray.get(i) == element) {
                steps.add(new DataStructureStep("SEARCH", 
                    "Found " + element + " at position " + i + "!",
                    resultArray.toString(), String.valueOf(i), i + 2));
                return steps;
            }
        }
        
        steps.add(new DataStructureStep("SEARCH", 
            "Element " + element + " not found in array",
            resultArray.toString(), "", resultArray.size() + 1));
        
        return steps;
    }
}