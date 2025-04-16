package com.example.groceries.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceries.R;

import java.util.List;

public class SupermarketAdapter extends RecyclerView.Adapter<SupermarketAdapter.SupermarketViewHolder> {

    private List<String> supermarketNames;
    private OnSupermarketClickListener listener;
    // Interface for handling supermarket item clicks
    public interface OnSupermarketClickListener {
        void onSupermarketClick(String supermarketName);
    }

    public SupermarketAdapter(List<String> supermarketNames, OnSupermarketClickListener listener) {
        this.supermarketNames = supermarketNames;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SupermarketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supermarket_item, parent, false);
        return new SupermarketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SupermarketViewHolder holder, int position) {
        String name = supermarketNames.get(position);
        holder.supermarketName.setText(name); // Set the supermarket name
        holder.itemView.setOnClickListener(v -> listener.onSupermarketClick(name)); // Set up click listener for the item
    }


    @Override
    public int getItemCount() {
        return supermarketNames.size();
    }


    public static class SupermarketViewHolder extends RecyclerView.ViewHolder {
        TextView supermarketName;

        public SupermarketViewHolder(View itemView) {
            super(itemView);
            supermarketName = itemView.findViewById(R.id.supermarket_name);  // Find the TextView for supermarket name
        }
    }



}
