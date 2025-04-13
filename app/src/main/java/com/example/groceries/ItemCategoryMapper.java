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

        // Beverages
        itemToCategoryMap.put("Soft Drink","Beverages");
        itemToCategoryMap.put("Water","Beverages");
        itemToCategoryMap.put("Juice","Beverages");
        itemToCategoryMap.put("Iced Tea","Beverages");
        itemToCategoryMap.put("Lemonade","Beverages");
        itemToCategoryMap.put("Energy Drink","Beverages");
        itemToCategoryMap.put("Coconut Water","Beverages");
        itemToCategoryMap.put("Coffee","Beverages");
        itemToCategoryMap.put("Milkshake","Beverages");
        itemToCategoryMap.put("Smoothie","Beverages");
        itemToCategoryMap.put("Sparkling Water","Beverages");
        itemToCategoryMap.put("Sports Drink","Beverages");
        itemToCategoryMap.put("Hot Chocolate","Beverages");
        itemToCategoryMap.put("Herbal Tea","Beverages");

        //Candy
        itemToCategoryMap.put("Lollipop","Candy");
        itemToCategoryMap.put("Gummy Bears","Candy");
        itemToCategoryMap.put("Chocolate Bar","Candy");
        itemToCategoryMap.put("Caramel","Candy");
        itemToCategoryMap.put("Taffy","Candy");
        itemToCategoryMap.put("Sour Candy","Candy");
        itemToCategoryMap.put("Candy Cane","Candy");
        itemToCategoryMap.put("Licorice","Candy");
        itemToCategoryMap.put("Marshmallow","Candy");
        itemToCategoryMap.put("Jawbreaker","Candy");
        itemToCategoryMap.put("Jelly Beans","Candy");
        itemToCategoryMap.put("Toffee","Candy");
        itemToCategoryMap.put("Fudge","Candy");

        //Canned Goods
        itemToCategoryMap.put("Canned Corn","Canned Goods");
        itemToCategoryMap.put("Canned Tuna","Canned Goods");
        itemToCategoryMap.put("Canned Beans","Canned Goods");
        itemToCategoryMap.put("Canned Tomatoes","Canned Goods");
        itemToCategoryMap.put("Canned Peas","Canned Goods");
        itemToCategoryMap.put("Canned Pineapple","Canned Goods");
        itemToCategoryMap.put("Canned Soup","Canned Goods");
        itemToCategoryMap.put("Canned Peaches","Canned Goods");
        itemToCategoryMap.put("Canned Sardines","Canned Goods");
        itemToCategoryMap.put("Canned Chicken","Canned Goods");
        itemToCategoryMap.put("Canned Mushrooms","Canned Goods");
        itemToCategoryMap.put("Canned Milk","Canned Goods");
        itemToCategoryMap.put("Canned Ham","Canned Goods");

        //Chilled
        itemToCategoryMap.put("Salad","Chilled");

        //Condiments
        itemToCategoryMap.put("Ketchup","Condiments");
        itemToCategoryMap.put("Mayonnaise","Condiments");
        itemToCategoryMap.put("Mustard","Condiments");
        itemToCategoryMap.put("Soy Sauce","Condiments");
        itemToCategoryMap.put("Hot Sauce","Condiments");
        itemToCategoryMap.put("BBQ Sauce","Condiments");
        itemToCategoryMap.put("Vinegar","Condiments");
        itemToCategoryMap.put("Tahini","Condiments");
        itemToCategoryMap.put("Garlic Paste","Condiments");

        //Dairy
        itemToCategoryMap.put("Milk","Dairy");
        itemToCategoryMap.put("Cheese","Dairy");
        itemToCategoryMap.put("Butter","Dairy");
        itemToCategoryMap.put("Cream Cheese","Dairy");
        itemToCategoryMap.put("Whipping Cream","Dairy");
        itemToCategoryMap.put("Greek Yogurt","Dairy");
        itemToCategoryMap.put("Sour Cream","Dairy");
        itemToCategoryMap.put("Ghee","Dairy");
        itemToCategoryMap.put("Flavored Milk","Dairy");

        //Eggs
        itemToCategoryMap.put("Egg","Eggs");

        //Fruits
        itemToCategoryMap.put("Apple","Fruits");
        itemToCategoryMap.put("Orange","Fruits");
        itemToCategoryMap.put("Banana","Fruits");
        itemToCategoryMap.put("Grape","Fruits");
        itemToCategoryMap.put("Pear","Fruits");
        itemToCategoryMap.put("Kiwi","Fruits");
        itemToCategoryMap.put("Mango","Fruits");
        itemToCategoryMap.put("Strawberry","Fruits");
        itemToCategoryMap.put("Blueberry","Fruits");
        itemToCategoryMap.put("Pineapple","Fruits");
        itemToCategoryMap.put("Watermelon","Fruits");
        itemToCategoryMap.put("Melon","Fruits");
        itemToCategoryMap.put("Papaya","Fruits");
        itemToCategoryMap.put("Dragon Fruit","Fruits");
        itemToCategoryMap.put("Avocado","Fruits");

        //Grain
        itemToCategoryMap.put("Pasta","Grains");
        itemToCategoryMap.put("Barley","Grains");
        itemToCategoryMap.put("Quinoa","Grains");
        itemToCategoryMap.put("Oats","Grains");
        itemToCategoryMap.put("Couscous","Grains");
        itemToCategoryMap.put("Granola","Grains");
        itemToCategoryMap.put("Crackers","Grains");
        itemToCategoryMap.put("Rice","Grains");

        //Ice Cream
        itemToCategoryMap.put("Chocolate Ice Cream","Ice Cream");
        itemToCategoryMap.put("Strawberry Ice Cream","Ice Cream");

        //Meat
        itemToCategoryMap.put("Chicken","Meat");
        itemToCategoryMap.put("Pork","Meat");
        itemToCategoryMap.put("Beef","Meat");
        itemToCategoryMap.put("Turkey","Meat");
        itemToCategoryMap.put("Lamb","Meat");
        itemToCategoryMap.put("Duck","Meat");
        itemToCategoryMap.put("Bacon","Meat");
        itemToCategoryMap.put("Sausage","Meat");
        itemToCategoryMap.put("Salami","Meat");
        itemToCategoryMap.put("Meatballs","Meat");

        //Noodles
        itemToCategoryMap.put("Cup Noodle","Noodles");
        itemToCategoryMap.put("Ramen","Noodles");
        itemToCategoryMap.put("Udon","Noodles");
        itemToCategoryMap.put("Lasagna Sheets","Noodles");
        itemToCategoryMap.put("Macaroni","Noodles");
        itemToCategoryMap.put("Spaghetti","Noodles");
        itemToCategoryMap.put("Instant Noodles","Noodles");

        //Personal Care
        itemToCategoryMap.put("Body Wash","Personal Care");
        itemToCategoryMap.put("Shampoo","Personal Care");
        itemToCategoryMap.put("Toothbrush","Personal Care");
        itemToCategoryMap.put("Conditioner","Personal Care");
        itemToCategoryMap.put("Deodorant","Personal Care");
        itemToCategoryMap.put("Toothpaste","Personal Care");
        itemToCategoryMap.put("Razor","Personal Care");
        itemToCategoryMap.put("Mouthwash","Personal Care");
        itemToCategoryMap.put("Wet Wipes","Personal Care");
        itemToCategoryMap.put("Lotion","Personal Care");
        itemToCategoryMap.put("Comb","Personal Care");
        itemToCategoryMap.put("Nail Clippers","Personal Care");
        itemToCategoryMap.put("Cotton Swabs","Personal Care");
        itemToCategoryMap.put("Sunscreen","Personal Care");

        //Seafood
        itemToCategoryMap.put("Crab","Seafood");
        itemToCategoryMap.put("Shrimp","Seafood");

        //Snacks
        itemToCategoryMap.put("Popcorn","Snacks");
        itemToCategoryMap.put("Pretzel","Snacks");

        //Vegetables
        itemToCategoryMap.put("Broccoli","Vegetables");
        itemToCategoryMap.put("Carrot","Vegetables");

        //END

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
