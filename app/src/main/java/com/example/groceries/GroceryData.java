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
            put("Lollipop", R.drawable.product_lollipop);
            put("Gummy Bears", R.drawable.product_gummy_bears);
            put("Chocolate Bar", R.drawable.product_chocolate_bar);
            put("Caramel", R.drawable.product_caramel);
            put("Taffy", R.drawable.product_taffy);
            put("Sour Candy", R.drawable.product_sour_candy);
            put("Candy Cane", R.drawable.product_candy_cane);
            put("Licorice", R.drawable.product_licorice);
            put("Marshmallow", R.drawable.product_marshmallow);
            put("Jawbreaker", R.drawable.product_jawbreaker);
            put("Jelly Beans", R.drawable.product_jelly_beans);
            put("Toffee", R.drawable.product_toffee);
            put("Fudge", R.drawable.product_fudge);
        }});
        put("Canned Goods", new HashMap<String, Integer>(){{
            put("Canned Corn", R.drawable.product_canned_corn);
            put("Canned Tuna", R.drawable.product_canned_tuna);
            put("Canned Beans", R.drawable.product_canned_beans);
            put("Canned Tomatoes", R.drawable.product_canned_tomatoes);
            put("Canned Peas", R.drawable.product_canned_peas);
            put("Canned Pineapple", R.drawable.product_canned_pineapple);
            put("Canned Soup", R.drawable.product_canned_soup);
            put("Canned Peaches", R.drawable.product_canned_peaches);
            put("Canned Sardines", R.drawable.product_canned_sardines);
            put("Canned Chicken", R.drawable.product_canned_chicken);
            put("Canned Mushrooms", R.drawable.product_canned_mushrooms);
            put("Canned Milk", R.drawable.product_canned_milk);
            put("Canned Ham", R.drawable.product_canned_ham);
        }});
        put("Chilled", new HashMap<String, Integer>(){{
            put("Salad", R.drawable.product_salad);
        }});
        put("Condiments", new HashMap<String, Integer>(){{
            put("Ketchup", R.drawable.product_ketchup);
            put("Mayonnaise", R.drawable.product_mayo);
            put("Mustard", R.drawable.product_mustard);
            put("Soy Sauce", R.drawable.product_soy_sauce);
            put("Hot Sauce", R.drawable.product_hot_sauce);
            put("BBQ Sauce", R.drawable.product_bbq_sauce);
            put("Vinegar", R.drawable.product_vinegar);
            put("Tahini", R.drawable.product_tahini);
            put("Garlic Paste", R.drawable.product_garlic_paste);
        }});
        put("Dairy", new HashMap<String, Integer>(){{
            put("Milk", R.drawable.product_milk);
            put("Cheese", R.drawable.product_cheese);
            put("Butter", R.drawable.product_butter);
            put("Cream Cheese", R.drawable.product_cream_cheese);
            put("Whipping Cream", R.drawable.product_whipping_cream);
            put("Greek Yogurt", R.drawable.product_greek_yogurt);
            put("Sour Cream", R.drawable.product_sour_cream);
            put("Ghee", R.drawable.product_ghee);
            put("Flavored Milk", R.drawable.product_flavored_milk);
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
            put("Kiwi", R.drawable.product_kiwi);
            put("Mango", R.drawable.product_mango);
            put("Strawberry", R.drawable.product_strawberry);
            put("Blueberry", R.drawable.product_blueberry);
            put("Pineapple", R.drawable.product_pineapple);
            put("Watermelon", R.drawable.product_watermelon);
            put("Melon", R.drawable.product_melon);
            put("Papaya", R.drawable.product_papaya);
            put("Dragon Fruit", R.drawable.product_dragon_fruit);
            put("Avocado", R.drawable.product_avocado);
        }});
        put("Grain", new HashMap<String, Integer>(){{
            put("Pasta", R.drawable.product_pasta);
            put("Barley", R.drawable.product_barley);
            put("Quinoa", R.drawable.product_quinoa);
            put("Oats", R.drawable.product_oats);
            put("Couscous", R.drawable.product_couscous);
            put("Granola", R.drawable.product_granola);
            put("Crackers", R.drawable.product_crackers);
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
            put("Turkey", R.drawable.product_turkey);
            put("Lamb", R.drawable.product_lamb);
            put("Duck", R.drawable.product_duck);
            put("Bacon", R.drawable.product_bacon);
            put("Sausage", R.drawable.product_sausage);
            put("Salami", R.drawable.product_salami);
            put("Meatballs", R.drawable.product_meatballs);
        }});
        put("Noodles", new HashMap<String, Integer>(){{
            put("Cup Noodle", R.drawable.product_cup_noods);
            put("Ramen", R.drawable.product_ramen);
            put("Udon", R.drawable.product_udon);
            put("Lasagna Sheets", R.drawable.product_lasagna_sheets);
            put("Macaroni", R.drawable.product_macaroni);
            put("Spaghetti", R.drawable.product_spaghetti);
            put("Instant Noodles", R.drawable.product_instant_noodles);
        }});
        put("Personal Care", new HashMap<String, Integer>(){{
            put("Body Wash", R.drawable.product_body_wash);
            put("Shampoo", R.drawable.product_shampoo);
            put("Toothbrush", R.drawable.product_toothbrush);
            put("Conditioner", R.drawable.product_conditioner);
            put("Deodorant", R.drawable.product_deodorant);
            put("Toothpaste", R.drawable.product_toothpaste);
            put("Razor", R.drawable.product_razor);
            put("Mouthwash", R.drawable.product_mouthwash);
            put("Wet Wipes", R.drawable.product_wet_wipes);
            put("Lotion", R.drawable.product_lotion);
            put("Comb", R.drawable.product_comb);
            put("Nail Clippers", R.drawable.product_nail_clippers);
            put("Cotton Swabs", R.drawable.product_cotton_swabs);
            put("Sunscreen", R.drawable.product_sunscreen);
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
