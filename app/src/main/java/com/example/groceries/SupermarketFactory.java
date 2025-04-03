package com.example.groceries;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SupermarketFactory {
    private static final Map<String, Supplier<SupermarketGraph>> supermarketRegistry = new HashMap<>();


    // Method called within each class to register supermarket
    public static void registerSupermarket(String name, Supplier<SupermarketGraph> constructor) {
        supermarketRegistry.put(name.toLowerCase(), constructor);
    }

    //retrieve supermarket if the class is created here
    public static SupermarketGraph getSupermarketGraph(String supermarketName) {
        Supplier<SupermarketGraph> supplier = supermarketRegistry.get(supermarketName.toLowerCase());
        if (supplier != null) return supplier.get();
        throw new IllegalArgumentException("Unknown supermarket: " + supermarketName);
    }
}
