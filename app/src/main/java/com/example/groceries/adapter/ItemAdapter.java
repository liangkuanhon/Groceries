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
import com.example.groceries.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ItemAdapter extends ArrayAdapter<Map.Entry<String, Integer>> {

    private String groupId;

    public ItemAdapter(@NonNull Context context, Map<String, Integer> items, String groupId) {
        // Convert map entries to a list and sort them
        super(context, 0, sortEntries(new ArrayList<>(items.entrySet())));
        this.groupId = groupId;
    }

    // Helper method to sort entries alphabetically by key (case-insensitive)
    private static List<Map.Entry<String, Integer>> sortEntries(List<Map.Entry<String, Integer>> entries) {
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                return e1.getKey().compareToIgnoreCase(e2.getKey());
            }
        });
        return entries;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Map.Entry<String, Integer> item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.layout_item_grid, parent, false);
        }

        ImageView itemImage = convertView.findViewById(R.id.itemImage);
        TextView itemName = convertView.findViewById(R.id.itemName);

        itemName.setText(item.getKey());
        itemImage.setImageResource(item.getValue());

        convertView.setOnClickListener(v -> {
            // Use FirebaseHelper to add the item to the group in Firebase
            FirebaseHelper.addItemToGroup(groupId, item.getKey(), item.getValue(), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                        Toast.makeText(getContext(),
                                item.getKey() + " added to list",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(),
                                "Failed to add item: " + error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        return convertView;
    }
}