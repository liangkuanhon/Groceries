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
        addEdge("Dairy", "Vegetables");

        addEdge("Vegetables", "Fruits");
        addEdge("Vegetables", "Grain");
        addEdge("Vegetables", "Meat");
        addEdge("Vegetables", "Noodles");

        addEdge("Meat", "Fruits");
        addEdge("Meat", "Seafood");

        addEdge("Fruits", "Condiments");
        addEdge("Fruits", "Bakery");
        addEdge("Fruits", "Seafood");
        addEdge("Fruits", "Noodles");

        addEdge("Seafood", "Bakery");

        addEdge("Bakery", "Condiments");
        addEdge("Bakery", "Canned Goods");

        addEdge("Canned Goods", "Condiments");
        addEdge("Canned Goods", "Personal Care");
        addEdge("Canned Goods", "Beauty");

        addEdge("Condiments", "Personal Care");
        addEdge("Condiments", "Beauty");
        addEdge("Condiments", "Noodles");

        addEdge("Personal Care", "Ice Cream");
        addEdge("Personal Care", "Beauty");

        addEdge("Beauty", "Ice Cream");
        addEdge("Beauty", "Noodles");
        addEdge("Beauty", "Snacks");
        addEdge("Beauty", "Alcohol");

        addEdge("Ice Cream", "Alcohol");
        addEdge("Ice Cream", "Snacks");

        addEdge("Grain", "Noodles");
        addEdge("Grain", "Candy");
        addEdge("Grain", "Snacks");

        addEdge("Noodles", "Snacks");
        addEdge("Noodles", "Candy");

        addEdge("Snacks", "Candy");
        addEdge("Snacks", "Alcohol");
        addEdge("Snacks", "Beverages");

        addEdge("Candy", "Beverages");
        addEdge("Candy", "Checkout");

        addEdge("Alcohol", "Beverages");
        addEdge("Alcohol", "Chilled");

        addEdge("Beverages", "Checkout");
        addEdge("Beverages", "Chilled");
        addEdge("Beverages", "Eggs");

        addEdge("Eggs", "Checkout");
    }
}
