package com.example.groceries.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groceries.GroceryData;
import com.example.groceries.GroceryItem;
import com.example.groceries.GroceryListManager;
import com.example.groceries.R;
import com.example.groceries.activities.GroceryListActivity;
import com.example.groceries.activities.ItemsActivity;
import com.example.groceries.activities.LoginActivity;
import com.example.groceries.activities.SignupActivity;
import com.example.groceries.adapter.GroceryItemAdapter;
import com.example.groceries.databinding.ActivityMainBinding;
import com.example.groceries.databinding.FragmentAllGroupBinding;
import com.example.groceries.databinding.FragmentSingleGroupBinding;
import com.example.groceries.helper.FirebaseHelper;
import com.example.groceries.helper.NavigationHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SingleGroupFragment extends Fragment {
    private NavigationHelper navigationHelper;

    private FragmentSingleGroupBinding b;
    private static final String ARG_GROUP_NAME = "group_name";
    private static final String ARG_GROUP_ID = "group_id";
    private String groupName;
    private String groupId;
    private GroceryItemAdapter adapter;
    private final List<GroceryItem> groceryList = new ArrayList<>();


    // required default empty constructor
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

    // used for initialising non-UI components
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupId = getArguments().getString(ARG_GROUP_ID);
            groupName = getArguments().getString(ARG_GROUP_NAME);
        }
    }

    // used to inflate the fragment ui, must return the root
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = FragmentSingleGroupBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    // called immediately after onCreateView
    // initialise ui components here
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navigationHelper = new NavigationHelper(requireActivity(), R.id.main_frame);


        b.groupName.setText(groupName);

        setupClickListeners();

        setupRecyclerView();

        getItemsFromFirebase();
    }

    private void setupClickListeners() {
        b.backArrow.setOnClickListener(v -> {
            navigationHelper.navigateToFragment(new AllGroupFragment());
        });


        b.addItem.setOnClickListener(v -> {
            CategoryFragment categoryFragment = CategoryFragment.newInstance(groupId);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame, categoryFragment)
                    .addToBackStack("SingleGroupFragment")
                    .commit();
        });

        b.settings.setOnClickListener(v -> navigateToGroupSettings(groupId, groupName));

        b.remove.setOnClickListener(v -> removeAllItems());

        b.checkout.setOnClickListener(v -> {
            SupermarketListFragment supermarketFragment = SupermarketListFragment.newInstance(groupId, groupName);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame, supermarketFragment)
                    .addToBackStack("SingleFragment")
                    .commit();
        });
    }

    private void setupRecyclerView(){
        adapter = new GroceryItemAdapter(groceryList, new GroceryItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GroceryItem item) {
                // Toggle checked status
                FirebaseHelper.updateGroupItemStatus(groupId, item.getId(), !item.isChecked());
            }

            @Override
            public void onItemLongClick(GroceryItem item) {
                new AlertDialog.Builder(requireContext())
                    .setTitle("Delete Item")
                    .setMessage("Are you sure you want to delete " + item.getName() + "?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        FirebaseHelper.removeGroupItem(groupId, item.getId(), (error, ref) -> {
                            if (error != null) {
                                Toast.makeText(requireContext(), "Failed to delete item", Toast.LENGTH_SHORT).show();
                                Log.e("Firebase", "Error deleting item", error.toException());
                            } else {
                                Toast.makeText(requireContext(), item.getName() + " deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            }
        });

        b.groceryListView.setLayoutManager(new LinearLayoutManager(requireContext()));
        b.groceryListView.setAdapter(adapter);
    }

    private void getItemsFromFirebase(){
        FirebaseHelper.getGroupItems(groupId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groceryList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    GroceryItem item = itemSnapshot.getValue(GroceryItem.class);
                    if (item != null) {
                        item.setId(itemSnapshot.getKey());
                        groceryList.add(item);
                    }
                }
                // built in method to inform the recycler view to update when changes are made to the dataset
                adapter.notifyDataSetChanged();
                updateEmptyState();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

    }

    private void updateEmptyState() {
        if (groceryList.isEmpty()) {
            b.groceryListView.setVisibility(View.GONE);
            b.emptyView.setVisibility(View.VISIBLE);
        } else {
            b.groceryListView.setVisibility(View.VISIBLE);
            b.emptyView.setVisibility(View.GONE);
        }
    }

    private void removeAllItems(){
        new AlertDialog.Builder(requireContext())
            .setTitle("Remove All Items")
            .setMessage("Are you sure you want to remove all items? This action cannot be undone.")
            .setPositiveButton("Remove", (dialog, which) -> {
                // Show loading indicator
                ProgressDialog progressDialog = new ProgressDialog(requireContext());
                progressDialog.setMessage("Removing items...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                // Remove all items from Firebase
                FirebaseHelper.removeAllGroupItem(groupId, (error, ref) -> {
                    progressDialog.dismiss();

                    if (error != null) {
                        Toast.makeText(requireContext(),
                                "Failed to remove items: " + error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(),
                                "All items removed successfully",
                                Toast.LENGTH_SHORT).show();

                        adapter.notifyDataSetChanged();
                        updateEmptyState();
                    }
                });
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void navigateToGroupSettings(String groupId, String groupName) {
        GroupSettingFragment groupSettingFragment = GroupSettingFragment.newInstance(groupId, groupName);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame, groupSettingFragment)
                .addToBackStack("SingleFragment")
                .commit();
    }



}