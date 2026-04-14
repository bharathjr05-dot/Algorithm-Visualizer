package com.algorithmvisualizer.controller;

import com.algorithmvisualizer.graph.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/graph")
@CrossOrigin(origins = "*")
public class GraphController {
    
    @PostMapping("/dfs")
    public List<GraphStep> depthFirstSearch(@RequestBody Map<String, Object> request) {
        Graph graph = buildGraphFromRequest(request);
        int startNode = (Integer) request.get("startNode");
        return GraphAlgorithms.depthFirstSearch(graph, startNode);
    }
    
    @PostMapping("/bfs")
    public List<GraphStep> breadthFirstSearch(@RequestBody Map<String, Object> request) {
        Graph graph = buildGraphFromRequest(request);
        int startNode = (Integer) request.get("startNode");
        return GraphAlgorithms.breadthFirstSearch(graph, startNode);
    }
    
    @PostMapping("/dijkstra")
    public List<GraphStep> dijkstraShortestPath(@RequestBody Map<String, Object> request) {
        Graph graph = buildGraphFromRequest(request);
        int startNode = (Integer) request.get("startNode");
        return GraphAlgorithms.dijkstraShortestPath(graph, startNode);
    }
    
    private Graph buildGraphFromRequest(Map<String, Object> request) {
        boolean isDirected = (Boolean) request.getOrDefault("isDirected", true);
        Graph graph = new Graph(isDirected);
        
        @SuppressWarnings("unchecked")
        List<Map<String, Integer>> edges = (List<Map<String, Integer>>) request.get("edges");
        
        for (Map<String, Integer> edge : edges) {
            int from = edge.get("from");
            int to = edge.get("to");
            int weight = edge.getOrDefault("weight", 1);
            graph.addEdge(from, to, weight);
        }
        
        return graph;
    }
}