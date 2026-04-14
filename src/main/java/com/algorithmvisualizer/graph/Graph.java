package com.algorithmvisualizer.graph;

import java.util.*;

public class Graph {
    private Map<Integer, List<Edge>> adjacencyList;
    private boolean isDirected;
    
    public Graph(boolean isDirected) {
        this.adjacencyList = new HashMap<>();
        this.isDirected = isDirected;
    }
    
    public void addVertex(int vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }
    
    public void addEdge(int from, int to, int weight) {
        addVertex(from);
        addVertex(to);
        
        adjacencyList.get(from).add(new Edge(to, weight));
        if (!isDirected) {
            adjacencyList.get(to).add(new Edge(from, weight));
        }
    }
    
    public Set<Integer> getVertices() {
        return adjacencyList.keySet();
    }
    
    public List<Edge> getNeighbors(int vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }
    
    public static class Edge {
        public final int to;
        public final int weight;
        
        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
}