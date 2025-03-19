package com.example.groceries;

import java.util.HashSet;

public class Vertex {
    private String vertexName;
    private int distance;
    private String colour;  // Any of 3 values: white, grey, black
    private Vertex parent;

    private HashSet<Vertex> neighbours;  // Use HashSet for uniqueness and fast lookup

    // Constructor only initializes name, others are set to defaults
    public Vertex(String name){
        vertexName = name;
        distance = 0;
        colour = "white";
        parent = null;
        neighbours = new HashSet<>();  // HashSet to store unique neighbors
    }

    // Getters
    public String getVertexName(){
        return vertexName;
    }

    public int getDistance(){
        return distance;
    }

    public String getColour(){
        return colour;
    }

    public Vertex getParent(){
        return parent;
    }

    public HashSet<Vertex> getNeighbours(){
        return neighbours;
    }

    // Setters
    public void setDistance(int d){
        distance = d;
    }

    public void setColour(String c){
        colour = c;
    }

    public void setParent(Vertex p){
        parent = p;
    }

    // Add a neighbor to the vertex
    public void addNeighbour(Vertex n){
        neighbours.add(n);  // Ensures that the neighbor is unique
    }
}
