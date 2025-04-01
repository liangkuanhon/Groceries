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

import java.util.Map;

public class ItemAdapter extends ArrayAdapter<Map.Entry<String, Integer>> {

    private String groupId;
    public ItemAdapter(@NonNull Context context, Map<String, Integer> items, String groupId) {
        super(context, 0, items.entrySet().toArray(new Map.Entry[0]));
        this.groupId = groupId;
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

//        convertView.setOnClickListener(v -> {
//            GroceryItem groceryItem = new GroceryItem(item.getKey(), item.getValue());
//            GroceryListManager.getInstance().addItem(groceryItem);
//            Toast.makeText(getContext(),
//                    item.getKey() + " added to list",
//                    Toast.LENGTH_SHORT).show();
//        });
//
//        return convertView;
}
