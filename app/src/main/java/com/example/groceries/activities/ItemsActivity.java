package com.example.groceries.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groceries.GroceryData;
import com.example.groceries.R;
import com.example.groceries.adapter.ItemAdapter;

import java.util.Map;

public class ItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_items);

        // Get the selected category from intent
        String category = getIntent().getStringExtra("CATEGORY");

        // Set the category title
        TextView categoryTitle = findViewById(R.id.categoryTitle);
        categoryTitle.setText(category);

        // Get items for this category
        Map<String, Integer> items = GroceryData.getItemsForCategory(category);

        // Set up the GridView
        GridView itemsGridView = findViewById(R.id.itemsGridView);

        ItemAdapter adapter = new ItemAdapter(this, items);
        itemsGridView.setAdapter(adapter);
    }
}