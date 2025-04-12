package com.example.groceries;

import android.util.Log;
import java.util.*;

public class BFSRouter {
    private Graph graph;

    public BFSRouter(Graph graph) {
        this.graph = graph;
    }

    public ArrayList<String> greedyBFSRouting(ArrayList<String> shoppingList) {
        ArrayList<String> route = new ArrayList<>();
        String currentLocation = "Entrance";

        // Use a set for faster lookup
        Set<String> targets = new HashSet<>(shoppingList);

        while (!targets.isEmpty()) {
            Map<String, String> parent = new HashMap<>();
            String found = bfsToClosestTarget(currentLocation, targets, parent);

            if (found == null) break;

            List<String> path = reconstructPath(currentLocation, found, parent);
            if (!route.isEmpty() && route.get(route.size() - 1).equals(path.get(0))) {
                path.remove(0);
            }

            route.addAll(path);
            targets.remove(found);
            currentLocation = found;
        }

        // Add checkout as final stop
        Map<String, String> parent = new HashMap<>();
        String checkout = bfsToClosestTarget(currentLocation, Set.of("Checkout"), parent);
        if (checkout != null) {
            List<String> path = reconstructPath(currentLocation, checkout, parent);
            if (!route.isEmpty() && route.get(route.size() - 1).equals(path.get(0))) {
                path.remove(0);
            }
            route.addAll(path);
        }

        return route;
    }

    // BFS to nearest target in the set, filling parent map as it goes
    private String bfsToClosestTarget(String start, Set<String> targets, Map<String, String> parent) {
        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String neighbor : graph.getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    parent.put(neighbor, current);
                    if (targets.contains(neighbor)) {
                        return neighbor;
                    }
                }
            }
        }

        return null; // No reachable target
    }

    //reconstructing path by backtracking from end to start
    private List<String> reconstructPath(String start, String end, Map<String, String> parent) {
        List<String> path = new ArrayList<>();
        for (String at = end; at != null; at = parent.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}
