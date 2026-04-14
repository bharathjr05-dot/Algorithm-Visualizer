package com.algorithmvisualizer.graph;

import java.util.*;

public class GraphAlgorithms {
    
    public static List<GraphStep> depthFirstSearch(Graph graph, int startNode) {
        List<GraphStep> steps = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        List<Integer> visitedList = new ArrayList<>();
        
        steps.add(new GraphStep("DFS", "Starting DFS from node " + startNode, 
                               startNode, -1, new ArrayList<>(), new ArrayList<>(), "start"));
        
        dfsHelper(graph, startNode, visited, visitedList, steps);
        
        steps.add(new GraphStep("DFS", "DFS completed. Traversal: " + visitedList, 
                               -1, -1, visitedList, new ArrayList<>(), "complete"));
        
        return steps;
    }
    
    private static void dfsHelper(Graph graph, int node, Set<Integer> visited, 
                                 List<Integer> visitedList, List<GraphStep> steps) {
        visited.add(node);
        visitedList.add(node);
        
        steps.add(new GraphStep("DFS", "Visiting node " + node, 
                               node, -1, new ArrayList<>(visitedList), new ArrayList<>(), "visiting"));
        
        for (Graph.Edge edge : graph.getNeighbors(node)) {
            if (!visited.contains(edge.to)) {
                steps.add(new GraphStep("DFS", "Exploring edge " + node + " -> " + edge.to, 
                                       node, edge.to, new ArrayList<>(visitedList), new ArrayList<>(), "exploring"));
                dfsHelper(graph, edge.to, visited, visitedList, steps);
            }
        }
    }
    
    public static List<GraphStep> breadthFirstSearch(Graph graph, int startNode) {
        List<GraphStep> steps = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> visitedList = new ArrayList<>();
        
        queue.offer(startNode);
        visited.add(startNode);
        
        steps.add(new GraphStep("BFS", "Starting BFS from node " + startNode, 
                               startNode, -1, new ArrayList<>(), new ArrayList<>(queue), "start"));
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            visitedList.add(current);
            
            steps.add(new GraphStep("BFS", "Processing node " + current, 
                                   current, -1, new ArrayList<>(visitedList), new ArrayList<>(queue), "processing"));
            
            for (Graph.Edge edge : graph.getNeighbors(current)) {
                if (!visited.contains(edge.to)) {
                    visited.add(edge.to);
                    queue.offer(edge.to);
                    
                    steps.add(new GraphStep("BFS", "Adding node " + edge.to + " to queue", 
                                           current, edge.to, new ArrayList<>(visitedList), new ArrayList<>(queue), "adding"));
                }
            }
        }
        
        steps.add(new GraphStep("BFS", "BFS completed. Traversal: " + visitedList, 
                               -1, -1, visitedList, new ArrayList<>(), "complete"));
        
        return steps;
    }
    
    public static List<GraphStep> dijkstraShortestPath(Graph graph, int startNode) {
        List<GraphStep> steps = new ArrayList<>();
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
        
        // Initialize distances
        for (int vertex : graph.getVertices()) {
            distances.put(vertex, vertex == startNode ? 0 : Integer.MAX_VALUE);
        }
        
        pq.offer(new Node(startNode, 0));
        
        steps.add(new GraphStep("Dijkstra", "Starting Dijkstra from node " + startNode, 
                               startNode, -1, new ArrayList<>(), new ArrayList<>(), "start"));
        
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            
            if (visited.contains(current.vertex)) continue;
            
            visited.add(current.vertex);
            
            steps.add(new GraphStep("Dijkstra", "Processing node " + current.vertex + 
                                   " (distance: " + current.distance + ")", 
                                   current.vertex, -1, new ArrayList<>(visited), new ArrayList<>(), "processing"));
            
            for (Graph.Edge edge : graph.getNeighbors(current.vertex)) {
                int newDistance = distances.get(current.vertex) + edge.weight;
                
                if (newDistance < distances.get(edge.to)) {
                    distances.put(edge.to, newDistance);
                    previous.put(edge.to, current.vertex);
                    pq.offer(new Node(edge.to, newDistance));
                    
                    steps.add(new GraphStep("Dijkstra", "Updated distance to node " + edge.to + 
                                           ": " + newDistance, 
                                           current.vertex, edge.to, new ArrayList<>(visited), new ArrayList<>(), "updating"));
                }
            }
        }
        
        steps.add(new GraphStep("Dijkstra", "Dijkstra completed. Shortest distances calculated", 
                               -1, -1, new ArrayList<>(visited), new ArrayList<>(), "complete"));
        
        return steps;
    }
    
    private static class Node {
        int vertex;
        int distance;
        
        Node(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
}