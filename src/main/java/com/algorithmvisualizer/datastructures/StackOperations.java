package com.algorithmvisualizer.datastructures;

import java.util.*;

public class StackOperations {
    
    public static List<DataStructureStep> pushElement(List<Integer> stack, int element) {
        List<DataStructureStep> steps = new ArrayList<>();
        List<Integer> resultStack = new ArrayList<>(stack);
        
        steps.add(new DataStructureStep("PUSH", 
            "Pushing " + element + " onto the stack",
            getStackString(resultStack), "", 0));
        
        steps.add(new DataStructureStep("PUSH", 
            "Adding " + element + " to the top of stack (LIFO - Last In First Out)",
            getStackString(resultStack), "top", 1));
        
        resultStack.add(element);
        
        steps.add(new DataStructureStep("PUSH", 
            "Successfully pushed " + element + ". Stack size: " + resultStack.size(),
            getStackString(resultStack), String.valueOf(resultStack.size() - 1), 2));
        
        return steps;
    }
    
    public static List<DataStructureStep> popElement(List<Integer> stack) {
        List<DataStructureStep> steps = new ArrayList<>();
        List<Integer> resultStack = new ArrayList<>(stack);
        
        steps.add(new DataStructureStep("POP", 
            "Popping element from the stack",
            getStackString(resultStack), "", 0));
        
        if (resultStack.isEmpty()) {
            steps.add(new DataStructureStep("POP", 
                "Stack is empty! Cannot pop from empty stack (Stack Underflow)",
                getStackString(resultStack), "", 1));
            return steps;
        }
        
        int poppedElement = resultStack.get(resultStack.size() - 1);
        
        steps.add(new DataStructureStep("POP", 
            "Removing top element " + poppedElement + " from stack",
            getStackString(resultStack), String.valueOf(resultStack.size() - 1), 1));
        
        resultStack.remove(resultStack.size() - 1);
        
        steps.add(new DataStructureStep("POP", 
            "Successfully popped " + poppedElement + ". Stack size: " + resultStack.size(),
            getStackString(resultStack), "", 2));
        
        return steps;
    }
    
    public static List<DataStructureStep> peekElement(List<Integer> stack) {
        List<DataStructureStep> steps = new ArrayList<>();
        
        steps.add(new DataStructureStep("PEEK", 
            "Peeking at the top element of stack",
            getStackString(stack), "", 0));
        
        if (stack.isEmpty()) {
            steps.add(new DataStructureStep("PEEK", 
                "Stack is empty! No element to peek",
                getStackString(stack), "", 1));
            return steps;
        }
        
        int topElement = stack.get(stack.size() - 1);
        
        steps.add(new DataStructureStep("PEEK", 
            "Top element is " + topElement + " (element not removed)",
            getStackString(stack), String.valueOf(stack.size() - 1), 1));
        
        return steps;
    }
    
    // Stack using Array implementation
    public static List<DataStructureStep> pushArray(int[] stackArray, int top, int element, int maxSize) {
        List<DataStructureStep> steps = new ArrayList<>();
        
        steps.add(new DataStructureStep("PUSH_ARRAY", 
            "Pushing " + element + " onto array-based stack. Current top: " + top,
            getArrayStackString(stackArray, top, maxSize), String.valueOf(top), 0));
        
        if (top >= maxSize - 1) {
            steps.add(new DataStructureStep("PUSH_ARRAY", 
                "Stack Overflow! Cannot push to full stack",
                getArrayStackString(stackArray, top, maxSize), "", 1));
            return steps;
        }
        
        steps.add(new DataStructureStep("PUSH_ARRAY", 
            "Incrementing top pointer from " + top + " to " + (top + 1),
            getArrayStackString(stackArray, top, maxSize), String.valueOf(top + 1), 1));
        
        stackArray[top + 1] = element;
        
        steps.add(new DataStructureStep("PUSH_ARRAY", 
            "Placed " + element + " at position " + (top + 1),
            getArrayStackString(stackArray, top + 1, maxSize), String.valueOf(top + 1), 2));
        
        return steps;
    }
    
    private static String getStackString(List<Integer> stack) {
        if (stack.isEmpty()) return "Stack: EMPTY";
        
        StringBuilder sb = new StringBuilder("Stack: ");
        for (int i = stack.size() - 1; i >= 0; i--) {
            sb.append("[").append(stack.get(i)).append("]");
            if (i == stack.size() - 1) sb.append(" <- TOP");
            if (i > 0) sb.append(" | ");
        }
        return sb.toString();
    }
    
    private static String getArrayStackString(int[] array, int top, int maxSize) {
        StringBuilder sb = new StringBuilder("Array Stack: ");
        for (int i = 0; i < maxSize; i++) {
            if (i <= top) {
                sb.append("[").append(array[i]).append("]");
            } else {
                sb.append("[ ]");
            }
            if (i == top) sb.append(" <- TOP");
            if (i < maxSize - 1) sb.append(" | ");
        }
        return sb.toString();
    }
}