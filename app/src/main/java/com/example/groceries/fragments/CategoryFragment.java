package com.example.groceries.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.groceries.GroceryData;
import com.example.groceries.R;
import com.example.groceries.activities.ItemsActivity;
import com.example.groceries.databinding.FragmentCategoryBinding;
import com.example.groceries.databinding.FragmentSingleGroupBinding;

import java.util.Arrays;


public class CategoryFragment extends Fragment {

    public FragmentCategoryBinding b;
    private String groupId;

    public static CategoryFragment newInstance(String groupId) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString("GROUP_ID", groupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupId = getArguments().getString("GROUP_ID");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentCategoryBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] categories = GroceryData.getCategoryNames();
        Arrays.sort(categories, String.CASE_INSENSITIVE_ORDER);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                categories
        );

        b.categoryList.setAdapter(adapter);

        // Set click listener for each category
        b.categoryList.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedCategory = categories[position];

            // Start ItemsActivity and pass the selected category
            Intent intent = new Intent(requireActivity(), ItemsActivity.class);
            intent.putExtra("CATEGORY", selectedCategory);
            intent.putExtra("GROUP_ID", groupId);
            startActivity(intent);
        });

        b.backArrow.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStackImmediate();
        });
    }

}