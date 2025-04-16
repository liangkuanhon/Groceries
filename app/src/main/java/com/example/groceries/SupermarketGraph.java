package com.example.groceries;

import java.util.*;

public class SupermarketGraph implements Graph {
    private static Map<String, List<String>> adjacencyList;

    public SupermarketGraph() {
        this.adjacencyList = new HashMap<>();
    }

    @Override
    public void addEdge(String from, String to) {
        adjacencyList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        adjacencyList.computeIfAbsent(to, k -> new ArrayList<>()).add(from); // Bidirectional path
    }

    @Override
    public List<String> getNeighbors(String node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }

    // Add getGraph() method to expose the adjacency list
    public Map<String, List<String>> getGraph() {
        return adjacencyList;
    }

    //
    public boolean containsNode(String nodeName) {
        return adjacencyList.containsKey(nodeName);
    }


}
