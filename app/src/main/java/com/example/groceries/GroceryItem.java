package com.example.groceries;

public class GroceryItem {
    private String name;
    private int imageResId;

    public GroceryItem(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
}
