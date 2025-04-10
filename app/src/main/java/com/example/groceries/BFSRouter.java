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

        while (!shoppingList.isEmpty()) {
            Queue<String> queue = new ArrayDeque<>();
            Map<String, String> parent = new HashMap<>();
            Set<String> visited = new HashSet<>();
            String foundItem = null;

            queue.add(currentLocation);
            visited.add(currentLocation);

            while (!queue.isEmpty() && foundItem == null) {
                String current = queue.poll();
                List<String> currentNeighbours = graph.getNeighbors(current);

                for (String neighbour : currentNeighbours) {
                    if (!visited.contains(neighbour)) {
                        visited.add(neighbour);
                        queue.add(neighbour);
                        parent.put(neighbour, current);

                        if (shoppingList.contains(neighbour)) {
                            foundItem = neighbour;
                            break;
                        }
                    }
                }
            }

            if (foundItem != null) {
                List<String> path = reconstructPath(currentLocation, foundItem, parent);
                if (!route.isEmpty() && route.get(route.size() - 1).equals(path.get(0))) {
                    path.remove(0);
                }
                route.addAll(path);
                shoppingList.remove(foundItem);
                currentLocation = foundItem;
            } else {
                break; // No more reachable items, proceed to checkout
            }
        }

        //all items inside shopping list are added to the route. Append exit to route
        route.add("Checkout");
        return route;
    }

    private List<String> reconstructPath(String start, String end, Map<String, String> parent) {
        List<String> path = new ArrayList<>();
        for (String at = end; at != null /*while parent is not null, keep looping*/; at = parent.get(at))/*current = current.parent equivalent*/ {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}
