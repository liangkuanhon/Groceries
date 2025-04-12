package com.example.groceries.helper;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class ItemCategoryMapper {

    // Create a static Map to store item-to-category mapping
    private static final Map<String, String> itemToCategoryMap = new HashMap<>();

    // Static block to initialize the map
    static {
        // Item-to-category mapping

        //Alcohol
        itemToCategoryMap.put("Baiju", "Alcohol");
        itemToCategoryMap.put("Beer", "Alcohol");
        itemToCategoryMap.put("Brandy", "Alcohol");
        itemToCategoryMap.put("Champagne", "Alcohol");
        itemToCategoryMap.put("Cider", "Alcohol");
        itemToCategoryMap.put("Gin", "Alcohol");
        itemToCategoryMap.put("Red Wine", "Alcohol");
        itemToCategoryMap.put("Rum", "Alcohol");
        itemToCategoryMap.put("Sake", "Alcohol");
        itemToCategoryMap.put("Soju", "Alcohol");
        itemToCategoryMap.put("Tequila", "Alcohol");
        itemToCategoryMap.put("Vodka", "Alcohol");
        itemToCategoryMap.put("Whisky", "Alcohol");
        itemToCategoryMap.put("White Wine", "Alcohol");
        itemToCategoryMap.put("Absinthe", "Alcohol");


        //Bakery
        itemToCategoryMap.put("Bagel", "Bakery");
        itemToCategoryMap.put("Baguette", "Bakery");
        itemToCategoryMap.put("Biscuits", "Bakery");
        itemToCategoryMap.put("Bread", "Bakery");
        itemToCategoryMap.put("Cake", "Bakery");
        itemToCategoryMap.put("Croissant", "Bakery");
        itemToCategoryMap.put("Cupcake", "Bakery");
        itemToCategoryMap.put("Doughnut", "Bakery");
        itemToCategoryMap.put("Muffin", "Bakery");
        itemToCategoryMap.put("Pita", "Bakery");
        itemToCategoryMap.put("Scone", "Bakery");
        itemToCategoryMap.put("Tortilla", "Bakery");

        //Beauty
        itemToCategoryMap.put("Blush", "Beauty");
        itemToCategoryMap.put("Compact Powder", "Beauty");
        itemToCategoryMap.put("Concealer", "Beauty");
        itemToCategoryMap.put("Eyeliner", "Beauty");
        itemToCategoryMap.put("Face Cream", "Beauty");
        itemToCategoryMap.put("Face Mask", "Beauty");
        itemToCategoryMap.put("Foundation", "Beauty");
        itemToCategoryMap.put("Highlighter", "Beauty");
        itemToCategoryMap.put("Lip Balm", "Beauty");
        itemToCategoryMap.put("Lip Stick", "Beauty");
        itemToCategoryMap.put("Makeup Remover", "Beauty");
        itemToCategoryMap.put("Makeup Mascara", "Beauty");
        itemToCategoryMap.put("Nail Polish", "Beauty");
        // Add more items and categories here as needed

        Log.d("ItemCategoryMapper", "Mappings loaded: " + itemToCategoryMap);
    }

    // Method to get the category for an item
    public static String getCategoryForItem(String item) {
        return itemToCategoryMap.get(item.trim()); // Ensure case-insensitivity
    }

    // Optionally, you can have a method to add more mappings at runtime
    public static void addMapping(String item, String category) {
        itemToCategoryMap.put(item.toLowerCase(), category); // Add or update the mapping
    }
}
