package com.example.groceries;

import java.util.HashMap;
import java.util.Map;

public class GroceryData {
    private static final Map<String, Map<String, Integer>> CATEGORIES_ITEMS = new HashMap<String, Map<String, Integer>>() {{
        put("Alcohol", new HashMap<String, Integer>(){{
            put("Beer", R.drawable.product_beer);
            put("Champagne", R.drawable.product_champagne);
            put("Wine", R.drawable.product_wine);
        }});
        put("Bakery", new HashMap<String, Integer>(){{
            put("Bread", R.drawable.product_bread);
            put("Muffin", R.drawable.product_muffin);
        }});
        put("Beauty", new HashMap<String, Integer>(){{
            put("Lip Stick", R.drawable.product_lipstick);
            put("Mascara", R.drawable.product_mascara);
        }});
        put("Beverages", new HashMap<String, Integer>(){{
            put("Cola", R.drawable.product_cola);
            put("Water", R.drawable.product_water);
        }});
        put("Candy", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
        }});
        put("Canned Goods", new HashMap<String, Integer>(){{
            put("Canned Corn", R.drawable.product_canned_corn);
        }});
        put("Chilled", new HashMap<String, Integer>(){{
            put("Salad", R.drawable.product_salad);
            put("Banana", R.drawable.product_banana);
        }});
        put("Condiments", new HashMap<String, Integer>(){{
            put("Ketchup", R.drawable.product_ketchup);
            put("Mayonnaise", R.drawable.product_mayo);
            put("Mustard", R.drawable.product_mustard);
        }});
        put("Dairy", new HashMap<String, Integer>(){{
            put("Milk", R.drawable.product_milk);
            put("Cheese", R.drawable.product_cheese);
            put("Butter", R.drawable.product_butter);
        }});
        put("Eggs", new HashMap<String, Integer>(){{
            put("Egg", R.drawable.product_egg);
        }});
        put("Fruits", new HashMap<String, Integer>(){{
            put("Apple", R.drawable.product_apple);
            put("Banana", R.drawable.product_banana);
            put("Orange", R.drawable.product_orange);
            put("Grape", R.drawable.product_grape);
        }});
        put("Grain", new HashMap<String, Integer>(){{
            put("Pasta", R.drawable.product_pasta);
            put("Rice", R.drawable.product_rice);
        }});
        put("Ice Cream", new HashMap<String, Integer>(){{
            put("Chocolate Ice Cream", R.drawable.product_chocolate_ice);
            put("Strawberry Ice Cream", R.drawable.product_strawberry_ice);
        }});
        put("Meat", new HashMap<String, Integer>(){{
            put("Chicken", R.drawable.product_chicken);
            put("Pork", R.drawable.product_pork);
            put("Beef", R.drawable.product_beef);
        }});
        put("Noodles", new HashMap<String, Integer>(){{
            put("Cup Noodle", R.drawable.product_cup_noods);
            put("Ramen", R.drawable.product_ramen);
        }});
        put("Personal Care", new HashMap<String, Integer>(){{
            put("Body Wash", R.drawable.product_body_wash);
            put("Shampoo", R.drawable.product_shampoo);
            put("Toothbrush", R.drawable.product_toothbrush);
        }});
        put("Seafood", new HashMap<String, Integer>(){{
            put("Crab", R.drawable.product_crab);
            put("Shrimp", R.drawable.product_shrimp);
        }});
        put("Snacks", new HashMap<String, Integer>(){{
            put("Popcorn", R.drawable.product_popcorn);
            put("Pretzel", R.drawable.product_pretzel);
        }});
        put("Vegetables", new HashMap<String, Integer>(){{
            put("Broccoli", R.drawable.product_broccoli);
            put("Carrot", R.drawable.product_carrot);
        }});





    }};

    public static String[] getCategoryNames() {
        return CATEGORIES_ITEMS.keySet().toArray(new String[0]);
    }

    public static Map<String, Integer> getItemsForCategory(String category) {
        return CATEGORIES_ITEMS.get(category);
    }
}
