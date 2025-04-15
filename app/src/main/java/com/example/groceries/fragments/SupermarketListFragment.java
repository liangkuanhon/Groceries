package com.example.groceries.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groceries.R;
import com.example.groceries.adapter.SupermarketAdapter;
import com.example.groceries.SupermarketFactory;

import java.util.List;

public class SupermarketListFragment extends Fragment {

    private RecyclerView recyclerView;
    private SupermarketAdapter adapter;

    private static final String ARG_GROUP_ID = "group_id";
    private String groupId;

    public static SupermarketListFragment newInstance(String groupId) {
        SupermarketListFragment fragment = new SupermarketListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GROUP_ID, groupId);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupId = getArguments().getString(ARG_GROUP_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_supermarket_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize RecyclerView and Adapter
        recyclerView = view.findViewById(R.id.supermarket_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Get the list of supermarket names from SupermarketFactory
        List<String> supermarketNames = SupermarketFactory.getRegisteredSupermarkets();


        // pass the supermarket name to the next fragment or activity
        adapter = new SupermarketAdapter(supermarketNames, this::navigateToSupermarketMap);

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(adapter);
    }

    // Method to navigate to the supermarket map (replace this with your navigation code)
    private void navigateToSupermarketMap(String supermarketName) {
        SupermarketMapFragment fragment = SupermarketMapFragment.newInstance(groupId, supermarketName);


        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame, fragment) // make sure this ID is correct in your activity's layout!
                .addToBackStack(null)
                .commit();
    }

}
