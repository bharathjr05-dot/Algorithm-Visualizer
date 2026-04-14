package com.algorithmvisualizer.graph;

import java.util.List;

public class GraphStep {
    private String operation;
    private String description;
    private int currentNode;
    private int targetNode;
    private List<Integer> visited;
    private List<Integer> queue;
    private String status;
    
    public GraphStep(String operation, String description, int currentNode, int targetNode, 
                    List<Integer> visited, List<Integer> queue, String status) {
        this.operation = operation;
        this.description = description;
        this.currentNode = currentNode;
        this.targetNode = targetNode;
        this.visited = visited;
        this.queue = queue;
        this.status = status;
    }
    
    // Getters and setters
    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public int getCurrentNode() { return currentNode; }
    public void setCurrentNode(int currentNode) { this.currentNode = currentNode; }
    
    public int getTargetNode() { return targetNode; }
    public void setTargetNode(int targetNode) { this.targetNode = targetNode; }
    
    public List<Integer> getVisited() { return visited; }
    public void setVisited(List<Integer> visited) { this.visited = visited; }
    
    public List<Integer> getQueue() { return queue; }
    public void setQueue(List<Integer> queue) { this.queue = queue; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}