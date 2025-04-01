package com.example.groceries.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceries.databinding.GroceryListItemBinding;
import com.example.groceries.GroceryItem;

import java.util.List;

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.GroceryItemViewHolder> {
    private final List<GroceryItem> groceryItems;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(GroceryItem item);
        void onItemLongClick(GroceryItem item);
    }

    public GroceryItemAdapter(List<GroceryItem> groceryItems, OnItemClickListener listener) {
        this.groceryItems = groceryItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GroceryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        GroceryListItemBinding binding = GroceryListItemBinding.inflate(inflater, parent, false);
        return new GroceryItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryItemViewHolder holder, int position) {
        holder.bind(groceryItems.get(position));
    }

    @Override
    public int getItemCount() {
        return groceryItems.size();
    }

    class GroceryItemViewHolder extends RecyclerView.ViewHolder {
        private final GroceryListItemBinding b;

        public GroceryItemViewHolder(GroceryListItemBinding b) {
            super(b.getRoot());
            this.b = b;
        }

        public void bind(GroceryItem item) {
            b.itemName.setText(item.getName());
            b.itemImage.setImageResource(item.getImageRes());

            b.getRoot().setOnClickListener(v -> listener.onItemClick(item));
            b.getRoot().setOnLongClickListener(v -> {
                listener.onItemLongClick(item);
                return true;
            });
        }
    }
}