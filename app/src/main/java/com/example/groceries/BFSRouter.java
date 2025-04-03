package com.example.groceries;

import java.util.*;

public class BFSRouter {
    private Graph graph;

    public BFSRouter(Graph graph) {
        this.graph = graph;
    }

    public List<String> findShortestPath(String start, String end) {
        Queue<String> queue = new LinkedList<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(end)) break; // Stop early if we reach the goal

            for (String neighbor : graph.getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        return reconstructPath(start, end, previous);
    }

    private List<String> reconstructPath(String start, String end, Map<String, String> previous) {
        List<String> path = new ArrayList<>();
        for (String at = end; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path.size() == 1 ? null : path;
    }
}
