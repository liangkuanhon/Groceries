package com.example.groceries;

import java.util.HashMap;
import java.util.HashSet;

public class Graph {
    private HashMap<String, Vertex> allVertices;  // Use HashMap for fast lookup

    private Vertex source;

    // Constructor initializes the HashMap
    public Graph(){
        allVertices = new HashMap<>();
    }

    // Get vertex by name
    public Vertex getVertex(String s){
        return allVertices.get(s);  // HashMap provides O(1) lookup
    }

    // To populate the graph
    public void addVertex(Vertex v){
        allVertices.put(v.getVertexName(), v);  // Add to HashMap
    }

    // Add bidirectional edge between two vertices
    public void addBidirectionalEdge(Vertex start, Vertex end){
        try {
            if (start == null || end == null) {
                throw new NullPointerException("One or both vertices are null.");
            }

            if (!allVertices.containsKey(start.getVertexName()) || !allVertices.containsKey(end.getVertexName())) {
                throw new IllegalArgumentException("Both vertices must be added to the graph before creating edges.");
            }

            // Add edge between start and end (bidirectional)
            start.addNeighbour(end);
            end.addNeighbour(start);
        }
        catch(NullPointerException | IllegalArgumentException e){
            // Log the exception message for the developer, but don't expose to the user
            System.err.println("start or end vertices are either null or not within the graph");
        }
    }

    public boolean setSource(Vertex v){
        if(!allVertices.containsKey(v.getVertexName())){
            //cannot add
            return false;
        }
        else{
            //successfully added
            source = allVertices.get(v.getVertexName());
            return true;
        }
    }


    //leave empty for now idk
    public void performBFSSearch(){
        return;
    }
}
