package com.example.groceries.supermarkets;

import com.example.groceries.SupermarketFactory;
import com.example.groceries.SupermarketGraph;

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
        addEdge("", "");
        addEdge("Entrance", "Dairy");
        addEdge("Entrance", "Grain");
        addEdge("Entrance", "Vegetables");


        addEdge("Dairy", "Meat");

        addEdge("Meat", "Vegetables");
        addEdge("Meat", "Seafood");

        addEdge("Vegetables", "Fruits");

        addEdge("Seafood", "Fruits");

        addEdge("Fruits", "Bakery");

        addEdge("Bakery", "Canned Goods");
        addEdge("Bakery", "Condiments");

        addEdge("Canned Goods", "Condiments");
        addEdge("Canned Goods", "Personal Care");

        addEdge("Personal Care", "Ice Cream");
        addEdge("Personal Care", "Beauty");

        addEdge("Ice Cream", "Beauty");
        addEdge("Ice Cream", "Alcohol");

        addEdge("Beauty", "Condiments");
        addEdge("Beauty", "Snacks");

        addEdge("Alcohol", "Snacks");
        addEdge("Alcohol", "Beverages");

        addEdge("Snacks", "Noodles");
        addEdge("Snacks", "Candy");

        addEdge("Noodles", "Condiments");
        addEdge("Noodles", "Vegetables");
        addEdge("Noodles", "Vegetables");
        addEdge("Noodles", "Grain");

        addEdge("Grain", "Candy");

        addEdge("Candy", "Beverages");

        addEdge("Beverages", "Checkout");
    }
}
