package com.example.groceries;

import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class SupermarketFactory {
    private static final Map<String, Supplier<SupermarketGraph>> supermarketRegistry = new HashMap<>();

    static {
        // Use Reflections to scan for all classes that extend SupermarketGraph in the supermarkets package
        registerSupermarkets();
    }

    private static void registerSupermarkets() {
        // Use Reflections to find all subclasses of SupermarketGraph within the supermarkets package
        Reflections reflections = new Reflections("com.example.groceries.supermarkets");
        Set<Class<? extends SupermarketGraph>> supermarketClasses = reflections.getSubTypesOf(SupermarketGraph.class);

        // For each supermarket class, register it in the factory
        for (Class<? extends SupermarketGraph> supermarketClass : supermarketClasses) {
            try {
                // Automatically register the supermarket class based on its simple name
                String supermarketName = supermarketClass.getSimpleName().toLowerCase();
                supermarketRegistry.put(supermarketName, () -> {
                    try {
                        return supermarketClass.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static SupermarketGraph getSupermarketGraph(String supermarketName) {
        Supplier<SupermarketGraph> supplier = supermarketRegistry.get(supermarketName.toLowerCase());
        if (supplier != null) return supplier.get();
        throw new IllegalArgumentException("Unknown supermarket: " + supermarketName);
    }

    // Returns all registered supermarket names
    public static ArrayList<String> getRegisteredSupermarkets() {
        ArrayList<String> supermarkets = new ArrayList<>();
        for (String className : supermarketRegistry.keySet()) {
            // Convert class name to a human-readable name
            supermarkets.add(className.replaceAll("Graph$", "").replaceAll("([a-z])([A-Z])", "$1 $2"));
        }
        return supermarkets;
    }

}
