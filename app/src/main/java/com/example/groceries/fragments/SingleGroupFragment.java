package com.example.groceries.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.example.groceries.adapter.GroceryItemAdapter;
import com.example.groceries.databinding.ActivityMainBinding;
import com.example.groceries.databinding.FragmentAllGroupBinding;
import com.example.groceries.databinding.FragmentSingleGroupBinding;
import com.example.groceries.helper.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SingleGroupFragment extends Fragment {
    private FragmentSingleGroupBinding b;
    private static final String ARG_GROUP_NAME = "group_name";
    private static final String ARG_GROUP_ID = "group_id";
    private String groupName;
    private String groupId;
    private GroceryItemAdapter adapter;
    private final List<GroceryItem> groceryList = new ArrayList<>();

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

        setupClickListeners();

        setupRecyclerView();

        getItemsFromFirebase();
    }

    private void setupClickListeners() {
        b.backArrow.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStackImmediate();
        });

        b.addItem.setOnClickListener(v -> {
            CategoryFragment categoryFragment = CategoryFragment.newInstance(groupId);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame, categoryFragment);
            transaction.addToBackStack("SingleFragment");
            transaction.commit();
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
                // Delete item on long click
                FirebaseHelper.removeGroupItem(groupId, item.getId(), (error, ref) -> {
                    if (error != null) {
                        // Handle error
                    }
                });
            }
        });

        b.groceryListView.setLayoutManager(new LinearLayoutManager(requireContext()));
        b.groceryListView.setAdapter(adapter);
//        // FOR GROCERY LIST VIEW
//        List<GroceryItem> groceryList = GroceryListManager.getInstance().getGroceryList();
//
//            ArrayAdapter<GroceryItem> gadapter = new ArrayAdapter<GroceryItem>(
//                    requireContext(),
//                    R.layout.grocery_list_item,
//                    R.id.itemName,
//                    groceryList
//            ) {
//                @Override
//                public View getView(int position, View convertView, ViewGroup parent) {
//                    View view = super.getView(position, convertView, parent);
//                    GroceryItem item = getItem(position);
//
//                    TextView name = view.findViewById(R.id.itemName);
//                    ImageView image = view.findViewById(R.id.itemImage);
//
//                    name.setText(item.getName());
//                    image.setImageResource(item.getImageRes());
//
//                    return view;
//                }
//            };
//
//            b.groceryListView.setAdapter(gadapter);
//        }
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

}