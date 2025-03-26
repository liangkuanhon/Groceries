package com.example.groceries.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.groceries.GroceryItem;
import com.example.groceries.GroceryListManager;
import com.example.groceries.R;

import java.util.Map;

public class ItemAdapter extends ArrayAdapter<Map.Entry<String, Integer>> {

    public ItemAdapter(@NonNull Context context, Map<String, Integer> items) {
        super(context, 0, items.entrySet().toArray(new Map.Entry[0]));
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Map.Entry<String, Integer> item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_grid, parent, false);
        }

        ImageView itemImage = convertView.findViewById(R.id.itemImage);
        TextView itemName = convertView.findViewById(R.id.itemName);

        itemName.setText(item.getKey());
        itemImage.setImageResource(item.getValue());

        convertView.setOnClickListener(v -> {
            GroceryItem groceryItem = new GroceryItem(item.getKey(), item.getValue());
            GroceryListManager.getInstance().addItem(groceryItem);
            Toast.makeText(getContext(),
                    item.getKey() + " added to list",
                    Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
