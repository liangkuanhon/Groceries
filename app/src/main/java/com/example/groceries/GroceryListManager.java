package com.example.groceries;

import java.util.ArrayList;
import java.util.List;

public class GroceryListManager {
    private static GroceryListManager instance;
    private List<GroceryItem> groceryList;

    private GroceryListManager() {
        groceryList = new ArrayList<>();
    }

    public static synchronized GroceryListManager getInstance() {
        if (instance == null) {
            instance = new GroceryListManager();
        }
        return instance;
    }

    public void addItem(GroceryItem item) {
        groceryList.add(item);
    }

    public List<GroceryItem> getGroceryList() {
        return groceryList;
    }

    public void clearList() {
        groceryList.clear();
    }
}
