package com.example.groceries;

import java.util.HashMap;
import java.util.Map;

public class GroceryData {
    private static final Map<String, Map<String, Integer>> CATEGORIES_ITEMS = new HashMap<String, Map<String, Integer>>() {{
        put("Alcohol", new HashMap<String, Integer>(){{
            put("Beer", R.drawable.product_beer);
            put("Champagne", R.drawable.product_champagne);
            put("Red Wine", R.drawable.product_red_wine);
            put("White Wine", R.drawable.product_white_wine);
            put("Whiskey", R.drawable.product_whiskey);
            put("Soju", R.drawable.product_soju);
            put("Vodka", R.drawable.product_vodka);
            put("Tequila", R.drawable.product_tequila);
            put("Brandy", R.drawable.product_brandy);
            put("Gin", R.drawable.product_gin);
            put("Rum", R.drawable.product_rum);
            put("Cider", R.drawable.product_cider);
            put("Absinthe", R.drawable.product_absinthe);
            put("Sake", R.drawable.product_sake);
            put("Baijiu", R.drawable.product_baijiu);
        }});
        put("Bakery", new HashMap<String, Integer>(){{
            put("Bread", R.drawable.product_bread);
            put("Muffin", R.drawable.product_muffin);
            put("Croissant", R.drawable.product_croissant);
            put("Baguette", R.drawable.product_baguette);
            put("Doughnut", R.drawable.product_doughnut);
            put("Bagel", R.drawable.product_bagel);
            put("Cake", R.drawable.product_cake);
            put("Pita", R.drawable.product_pita);
            put("Scone", R.drawable.product_scone);
            put("Cupcake", R.drawable.product_cupcake);
            put("Biscuit", R.drawable.product_biscuit);
            put("Tortilla", R.drawable.product_tortilla);
        }});
        put("Beauty", new HashMap<String, Integer>(){{
            put("Lip Stick", R.drawable.product_lipstick);
            put("Mascara", R.drawable.product_mascara);
            put("Foundation", R.drawable.product_foundation);
            put("Eyeliner", R.drawable.product_eyeliner);
            put("Concealer", R.drawable.product_concealer);
            put("Blush", R.drawable.product_blush);
            put("Highlighter", R.drawable.product_highlighter);
            put("Compact Powder", R.drawable.product_compact_powder);
            put("Makeup Remover", R.drawable.product_makeup_remover);
            put("Nail Polish", R.drawable.product_nail_polish);
            put("Face Cream", R.drawable.product_face_cream);
            put("Face Mask", R.drawable.product_face_mask);
            put("Toner", R.drawable.product_toner);
            put("Serum", R.drawable.product_serum);
            put("Lip Balm", R.drawable.product_lip_balm);
        }});
        put("Beverages", new HashMap<String, Integer>(){{
            put("Soft Drink", R.drawable.product_soft_drink);
            put("Water", R.drawable.product_water);
            put("Juice", R.drawable.product_juice);
            put("Iced Tea", R.drawable.product_iced_tea);
            put("Lemonade", R.drawable.product_lemonade);
            put("Energy Drink", R.drawable.product_energy_drink);
            put("Coconut Water", R.drawable.product_coconut_water);
            put("Coffee", R.drawable.product_coffee);
            put("Milkshake", R.drawable.product_milkshake);
            put("Smoothie", R.drawable.product_smoothie);
            put("Sparkling Water", R.drawable.product_sparkling_water);
            put("Sports Drink", R.drawable.product_sports_drink);
            put("Hot Chocolate", R.drawable.product_hot_chocolate);
            put("Herbal Tea", R.drawable.product_herbal_tea);
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
            put("Pear", R.drawable.product_pear);
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
