package com.example.groceries.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.groceries.R;
import com.example.groceries.activities.LoginActivity;
import com.example.groceries.adapter.GroupAdapter;
import com.example.groceries.databinding.FragmentAllGroupBinding;
import com.example.groceries.helper.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllGroupFragment extends Fragment {

    private FragmentAllGroupBinding b;
    private GroupAdapter adapter;
    private List<String> groupList;
    private Map<String, String> groupIdMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentAllGroupBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        groupList = new ArrayList<>();
        groupIdMap = new HashMap<>();
        adapter = new GroupAdapter(groupList);
        b.groupsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        b.groupsRecycler.setAdapter(adapter);

        adapter.setOnGroupClickListener(groupName -> {
            // Get the ID from our map
            String groupId = groupIdMap.get(groupName);
            if (groupId != null) {
                navigateToGroupDetailsFragment(groupId, groupName);
            }
        });

        fetchUserGroups();
    }

    private void fetchUserGroups() {
        String firebaseUID = FirebaseHelper.getCurrentUserId();

        if (firebaseUID == null) {
            Log.w("GroupViewFragment", "No user is logged in.");
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
            return;
        }

        DatabaseReference groupsRef = FirebaseDatabase.getInstance().getReference().child("groups");

        groupsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupList.clear();
                groupIdMap.clear();
                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                    if (groupSnapshot.child("members").hasChild(firebaseUID)) {
                        String groupId= groupSnapshot.getKey();
                        String groupName = groupSnapshot.child("groupName").getValue(String.class);
                        if (groupName != null) {
                            groupList.add(groupName);
                            groupIdMap.put(groupName, groupId);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("GroupViewFragment", "Failed to read groups", databaseError.toException());
            }
        });
    }

    private void navigateToGroupDetailsFragment(String groupId, String groupName) {
        SingleGroupFragment fragment = SingleGroupFragment.newInstance(groupId, groupName);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame, fragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null; // Clean up binding reference
    }
}