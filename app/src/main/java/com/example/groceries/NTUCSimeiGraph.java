package com.example.groceries;

public class NTUCSimeiGraph extends SupermarketGraph {
    static{
        //load simei graph onto factory upon each runtime
        SupermarketFactory.registerSupermarket("ntuc simei", NTUCSimeiGraph::new);
    }
    public NTUCSimeiGraph() {
        super();
        createGraph();
    }


    private void createGraph() {
        addEdge("Entrance", "Fruits");
        addEdge("Fruits", "Vegetables");
        addEdge("Fruits", "Dairy");
        addEdge("Vegetables", "Meat");
        addEdge("Meat", "Frozen");
        addEdge("Dairy", "Bakery");
        addEdge("Frozen", "Checkout");
        addEdge("Bakery", "Checkout");
    }
}
