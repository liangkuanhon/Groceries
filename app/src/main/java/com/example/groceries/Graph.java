package com.example.groceries;

import java.util.List;

public interface Graph {
    void addEdge(String from, String to);
    List<String> getNeighbors(String node);
}
