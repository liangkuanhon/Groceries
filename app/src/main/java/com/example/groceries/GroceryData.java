package com.example.groceries;

import java.util.HashMap;
import java.util.Map;

public class GroceryData {
    private static final Map<String, Map<String, Integer>> CATEGORIES_ITEMS = new HashMap<String, Map<String, Integer>>() {{
        put("Eggs", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Dairy", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Meat", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Bakery", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Chilled", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Ice Cream", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Seafood", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Fruits", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Vegetables", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Alcohol", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Beverages", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Grain", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Snacks", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Candy", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Noodles", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Condiments", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Personal Care", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Beauty", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Canned Goods", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});

    }};

    public static String[] getCategoryNames() {
        return CATEGORIES_ITEMS.keySet().toArray(new String[0]);
    }

    public static Map<String, Integer> getItemsForCategory(String category) {
        return CATEGORIES_ITEMS.get(category);
    }
}
