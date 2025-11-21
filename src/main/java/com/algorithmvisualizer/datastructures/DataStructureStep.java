package com.algorithmvisualizer.datastructures;

public class DataStructureStep {
    private String operation;
    private String description;
    private String currentState;
    private String highlightedElements;
    private int stepNumber;
    
    public DataStructureStep() {}
    
    public DataStructureStep(String operation, String description, String currentState, String highlightedElements, int stepNumber) {
        this.operation = operation;
        this.description = description;
        this.currentState = currentState;
        this.highlightedElements = highlightedElements;
        this.stepNumber = stepNumber;
    }
    
    // Getters and Setters
    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getCurrentState() { return currentState; }
    public void setCurrentState(String currentState) { this.currentState = currentState; }
    
    public String getHighlightedElements() { return highlightedElements; }
    public void setHighlightedElements(String highlightedElements) { this.highlightedElements = highlightedElements; }
    
    public int getStepNumber() { return stepNumber; }
    public void setStepNumber(int stepNumber) { this.stepNumber = stepNumber; }
}