package com.example.groceries.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.groceries.GroceryData;
import com.example.groceries.GroceryItem;
import com.example.groceries.GroceryListManager;
import com.example.groceries.R;
import com.example.groceries.activities.GroceryListActivity;
import com.example.groceries.activities.ItemsActivity;
import com.example.groceries.activities.LoginActivity;
import com.example.groceries.activities.SignupActivity;
import com.example.groceries.databinding.ActivityMainBinding;
import com.example.groceries.databinding.FragmentAllGroupBinding;
import com.example.groceries.databinding.FragmentSingleGroupBinding;

import java.util.List;

public class SingleGroupFragment extends Fragment {
    private FragmentSingleGroupBinding b;
    private static final String ARG_GROUP_NAME = "group_name";
    private static final String ARG_GROUP_ID = "group_id";

    private String groupName;
    private String groupId;

    public SingleGroupFragment(){
    }

    public static SingleGroupFragment newInstance(String groupId, String groupName) {
        SingleGroupFragment fragment = new SingleGroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GROUP_ID, groupId);
        args.putString(ARG_GROUP_NAME, groupName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupId = getArguments().getString(ARG_GROUP_ID);
            groupName = getArguments().getString(ARG_GROUP_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = FragmentSingleGroupBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.groupName.setText(groupName);

        // Here you can fetch additional group details using groupId
        // and populate the fragment with that data

        // FOR CATEGORY LIST VIEW
        String[] categories = GroceryData.getCategoryNames();

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
            startActivity(intent);
        });

        b.backArrow.setOnClickListener(v -> {
            navigateToAllGroup();
        });

        // FOR GROCERY LIST VIEW
        List<GroceryItem> groceryList = GroceryListManager.getInstance().getGroceryList();

        if (groceryList.isEmpty()) {
            b.groceryListView.setVisibility(View.GONE);
            b.emptyView.setVisibility(View.VISIBLE);
        } else {
            b.groceryListView.setVisibility(View.VISIBLE);
            b.emptyView.setVisibility(View.GONE);

            ArrayAdapter<GroceryItem> gadapter = new ArrayAdapter<GroceryItem>(
                    requireContext(),
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

            b.groceryListView.setAdapter(gadapter);
        }
    }

    private void navigateToAllGroup(){
        AllGroupFragment AllGroupFragment = new AllGroupFragment();

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, AllGroupFragment);
        transaction.commit();
    }
}