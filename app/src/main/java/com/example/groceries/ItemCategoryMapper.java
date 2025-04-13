package com.example.groceries;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemCategoryMapper {

    private static final Map<String, String> itemToCategoryMap = new HashMap<>();

    static {
        // Alcohol
        itemToCategoryMap.put("baiju", "Alcohol");
        itemToCategoryMap.put("beer", "Alcohol");
        itemToCategoryMap.put("brandy", "Alcohol");
        itemToCategoryMap.put("champagne", "Alcohol");
        itemToCategoryMap.put("cider", "Alcohol");
        itemToCategoryMap.put("gin", "Alcohol");
        itemToCategoryMap.put("red wine", "Alcohol");
        itemToCategoryMap.put("rum", "Alcohol");
        itemToCategoryMap.put("sake", "Alcohol");
        itemToCategoryMap.put("soju", "Alcohol");
        itemToCategoryMap.put("tequila", "Alcohol");
        itemToCategoryMap.put("vodka", "Alcohol");
        itemToCategoryMap.put("whisky", "Alcohol");
        itemToCategoryMap.put("white wine", "Alcohol");
        itemToCategoryMap.put("absinthe", "Alcohol");

        // Bakery
        itemToCategoryMap.put("bagel", "Bakery");
        itemToCategoryMap.put("baguette", "Bakery");
        itemToCategoryMap.put("biscuits", "Bakery");
        itemToCategoryMap.put("bread", "Bakery");
        itemToCategoryMap.put("cake", "Bakery");
        itemToCategoryMap.put("croissant", "Bakery");
        itemToCategoryMap.put("cupcake", "Bakery");
        itemToCategoryMap.put("doughnut", "Bakery");
        itemToCategoryMap.put("muffin", "Bakery");
        itemToCategoryMap.put("pita", "Bakery");
        itemToCategoryMap.put("scone", "Bakery");
        itemToCategoryMap.put("tortilla", "Bakery");

        // Beauty
        itemToCategoryMap.put("blush", "Beauty");
        itemToCategoryMap.put("compact powder", "Beauty");
        itemToCategoryMap.put("concealer", "Beauty");
        itemToCategoryMap.put("eyeliner", "Beauty");
        itemToCategoryMap.put("face cream", "Beauty");
        itemToCategoryMap.put("face mask", "Beauty");
        itemToCategoryMap.put("foundation", "Beauty");
        itemToCategoryMap.put("highlighter", "Beauty");
        itemToCategoryMap.put("lip balm", "Beauty");
        itemToCategoryMap.put("lip stick", "Beauty");
        itemToCategoryMap.put("makeup remover", "Beauty");
        itemToCategoryMap.put("makeup mascara", "Beauty");
        itemToCategoryMap.put("nail polish", "Beauty");

        // Grain
        itemToCategoryMap.put("barley", "Grain");

        Log.d("ItemCategoryMapper", "Mappings loaded: " + itemToCategoryMap);
    }

    public static String getCategoryForItem(String item) {
        return itemToCategoryMap.get(item.trim().toLowerCase());
    }

    public static void addMapping(String item, String category) {
        itemToCategoryMap.put(item.trim().toLowerCase(), category);
    }

    public static List<String> getItemsAtCategory(String category) {
        List<String> items = new ArrayList<>();
        for (Map.Entry<String, String> entry : itemToCategoryMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(category)) {
                items.add(entry.getKey());
            }
        }
        return items;
    }
}
