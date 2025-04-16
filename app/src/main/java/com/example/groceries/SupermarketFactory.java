package com.example.groceries;


// import more supermarkets here as needed

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import com.example.groceries.supermarkets.*;

public class SupermarketFactory {
    private static final Map<String, Supplier<SupermarketGraph>> supermarketRegistry = new HashMap<>();

    static {
        // Manual registration of supermarket maps
        registerSupermarket("NTUC Simei", NTUCSimeiGraph::new);
        registerSupermarket("NTUC Expo", NTUCExpoGraph::new);
    }

    public static void registerSupermarket(String name, Supplier<SupermarketGraph> supplier) {
        supermarketRegistry.put(name.toLowerCase(), supplier);
    }

    public static SupermarketGraph getSupermarketGraph(String supermarketName) {
        Supplier<SupermarketGraph> supplier = supermarketRegistry.get(supermarketName.toLowerCase());
        if (supplier != null) return supplier.get();
        throw new IllegalArgumentException("Unknown supermarket: " + supermarketName);
    }

    public static ArrayList<String> getRegisteredSupermarkets() {
        return new ArrayList<>(supermarketRegistry.keySet());
    }
}
