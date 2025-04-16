package com.example.groceries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groceries.GroceryData;
import com.example.groceries.R;
import com.example.groceries.databinding.ActivityCategoryBinding;
import com.example.groceries.helper.NavigationHelper;

public class CategoryActivity extends AppCompatActivity {

    private ActivityCategoryBinding b;
    private NavigationHelper navigationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialise();
        setupListView();
    }

    private void initialise() {
        b = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        navigationHelper = new NavigationHelper(this);
    }

    private void setupListView(){
        // Get category names from our data structure
        String[] categories = GroceryData.getCategoryNames();

        // Create adapter for the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                categories
        );

        b.categoryList.setAdapter(adapter);

        // Set click listener for each category
        b.categoryList.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = categories[position];
            navigateToItemsActivity(selectedCategory);
        });
    }

    private void navigateToItemsActivity(String category){
        navigationHelper.navigateToActivityWithExtra(ItemsActivity.class, "CATEGORY", category);
    }
}