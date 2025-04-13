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
import androidx.fragment.app.FragmentTransaction;

import com.example.groceries.GroceryData;
import com.example.groceries.R;
import com.example.groceries.adapter.ItemAdapter;
import com.example.groceries.databinding.ActivityItemsBinding;
import com.example.groceries.fragments.CategoryFragment;

import java.util.Map;

public class ItemsActivity extends AppCompatActivity {

    private ActivityItemsBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityItemsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        b.backArrow.setOnClickListener(v -> {
            finish();
        });

        // Get the selected category from intent
        String category = getIntent().getStringExtra("CATEGORY");
        String groupId = getIntent().getStringExtra("GROUP_ID");

        b.categoryTitle.setText(category);

        // Get items for this category
        Map<String, Integer> items = GroceryData.getItemsForCategory(category);

        // Set up the GridView

        ItemAdapter adapter = new ItemAdapter(this, items, groupId);
        b.itemsGridView.setAdapter(adapter);
    }
}