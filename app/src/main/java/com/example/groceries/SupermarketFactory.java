package com.example.groceries;

public class SupermarketFactory {


    //the more supermarkets are added to app,the more switch statements must be made
    public static SupermarketGraph getSupermarketGraph(String supermarketName) {
        switch (supermarketName.toLowerCase()) {
            case "ntu c simei":
                return new NTUCSimeiGraph();
            default:
                throw new IllegalArgumentException("Unknown supermarket: " + supermarketName);
        }
    }
}
