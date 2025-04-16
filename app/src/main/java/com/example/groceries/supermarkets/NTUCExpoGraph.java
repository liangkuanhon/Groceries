package com.example.groceries.supermarkets;

import com.example.groceries.SupermarketFactory;
import com.example.groceries.SupermarketGraph;

public class NTUCExpoGraph extends SupermarketGraph {
    static{
        //load simei graph onto factory upon each runtime
        SupermarketFactory.registerSupermarket("ntuc simei", NTUCSimeiGraph::new);
    }
    public NTUCExpoGraph() {
        super();
        createGraph();
    }


    private void createGraph() {
        addEdge("", "");
        addEdge("Entrance", "Fruits");
        addEdge("Entrance", "Dairy");
        addEdge("Entrance", "Alcohol");

        addEdge("Fruits", "Vegetables");
        addEdge("Fruits", "Alcohol");
        addEdge("Fruits", "Grains");
        addEdge("Fruits", "Canned Goods");

        addEdge("Vegetables", "Bakery");
        addEdge("Vegetables", "Canned Goods");
        addEdge("Vegetables", "Grains");
        addEdge("Vegetables", "Personal Care");

        addEdge("Bakery", "Personal Care");
        addEdge("Bakery", "Ice Cream");

        addEdge("Ice Cream", "Personal Care");
        addEdge("Ice Cream", "Beauty");
        addEdge("Ice Cream", "Chilled");

        addEdge("Chilled", "Eggs");
        addEdge("Chilled", "Ice Cream");
        addEdge("Chilled", "Beauty");
        addEdge("Chilled", "Checkout");

        addEdge("Eggs", "Checkout");
        addEdge("Eggs", "Candy");

        addEdge("Candy", "Checkout");
        addEdge("Candy", "Beauty");
        addEdge("Candy", "Condiments");
        addEdge("Candy", "Snacks");
        addEdge("Candy", "Noodles");

        addEdge("Snacks", "Checkout");
        addEdge("Snacks", "Noodles");
        addEdge("Snacks", "Beverages");
        addEdge("Snacks", "Seafood");
        addEdge("Snacks", "Meat");

        addEdge("Seafood", "Checkout");
        addEdge("Seafood", "Meat");

        addEdge("Meat", "Beverages");
        addEdge("Meat", "Dairy");
        addEdge("Meat", "Alcohol");

        addEdge("Dairy", "Alcohol");
        addEdge("Dairy", "Beverages");

        addEdge("Alcohol", "Beverages");
        addEdge("Alcohol", "Grain");
        addEdge("Alcohol", "Noodles");

        addEdge("Grain", "Beverages");
        addEdge("Grain", "Noodles");
        addEdge("Grain", "Canned Goods");
        addEdge("Grain", "Condiments");

        addEdge("Canned Goods", "Condiments");
        addEdge("Canned Goods", "Noodles");
        addEdge("Canned Goods", "Beauty");
        addEdge("Canned Goods", "Personal Care");

        addEdge("Personal Care", "Condiments");
        addEdge("Personal Care", "Beauty");

        addEdge("Beauty", "Condiments");

        addEdge("Condiments", "Noodles");

        addEdge("Noodles", "Beverages");
    }
}
