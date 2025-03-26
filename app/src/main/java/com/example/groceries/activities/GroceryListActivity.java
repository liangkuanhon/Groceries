package com.example.groceries.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groceries.GroceryItem;
import com.example.groceries.GroceryListManager;
import com.example.groceries.R;

import java.util.List;

public class GroceryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        ListView groceryListView = findViewById(R.id.groceryListView);
        TextView emptyView = findViewById(R.id.emptyView);

        List<GroceryItem> groceryList = GroceryListManager.getInstance().getGroceryList();

        if (groceryList.isEmpty()) {
            groceryListView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            groceryListView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

            ArrayAdapter<GroceryItem> adapter = new ArrayAdapter<GroceryItem>(
                    this,
                    R.layout.grocery_list_item,
                    R.id.itemName,
                    groceryList
            ) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    GroceryItem item = getItem(position);

                    TextView name = view.findViewById(R.id.itemName);
                    ImageView image = view.findViewById(R.id.itemImage);

                    name.setText(item.getName());
                    image.setImageResource(item.getImageResId());

                    return view;
                }
            };

            groceryListView.setAdapter(adapter);
        }
    }
}