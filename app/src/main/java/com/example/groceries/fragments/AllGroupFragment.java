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
import com.example.groceries.adapter.RecommendationAdapter;
import com.example.groceries.databinding.FragmentAllGroupBinding;
import com.example.groceries.helper.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllGroupFragment extends Fragment {

    private FragmentAllGroupBinding b;
    private GroupAdapter adapter;
    private RecommendationAdapter recommendationAdapter;
    private List<String> groupList;
    private List<String> recommendationList;
    private Map<String, String> groupIdMap;

    // Predefined recommended groups
    private final List<String> DEFAULT_RECOMMENDATIONS = Arrays.asList(
            "Roomie Group",
            "School Group",
            "Family Group",
            "Couple Group",
            "Project Group",
            "Friend Group",
            "Work Group"
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentAllGroupBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialise();
        setupGroupsRecyclerView();
        setupRecommendationsRecyclerView();
        fetchUserGroups();
    }

    private void initialise() {
        groupList = new ArrayList<>();
        recommendationList = new ArrayList<>(DEFAULT_RECOMMENDATIONS);
        groupIdMap = new HashMap<>();
    }

    private void setupGroupsRecyclerView() {
        adapter = new GroupAdapter(groupList);
        b.groupsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        b.groupsRecycler.setAdapter(adapter);

        adapter.setOnGroupClickListener(groupName -> {
            String groupId = groupIdMap.get(groupName);
            if (groupId != null) {
                navigateToGroupDetailsFragment(groupId, groupName);
            }
        });
    }

    private void setupRecommendationsRecyclerView() {
        recommendationAdapter = new RecommendationAdapter(recommendationList);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        b.recommendationRecycler.setLayoutManager(horizontalLayoutManager);
        b.recommendationRecycler.setAdapter(recommendationAdapter);

        recommendationAdapter.setOnItemClickListener(position -> {
            String recommendedGroup = recommendationList.get(position);
            createRecommendedGroup(recommendedGroup);
        });
    }

    private void createRecommendedGroup(String groupName) {
        String currentUserId = FirebaseHelper.getCurrentUserId();
        if (currentUserId == null) {
            Toast.makeText(requireContext(), "Please login first", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create group data
        Map<String, Object> groupData = new HashMap<>();
        groupData.put("groupName", groupName);
        groupData.put("createdBy", currentUserId);

        // Add current user as member
        Map<String, Object> members = new HashMap<>();
        members.put(currentUserId, true);
        groupData.put("members", members);

        // Generate new group ID
        String groupId = FirebaseHelper.getGroupsReference().push().getKey();
        if (groupId == null) {
            Toast.makeText(requireContext(), "Failed to create group", Toast.LENGTH_SHORT).show();
            return;
        }

        // Use FirebaseHelper to create the group
        FirebaseHelper.createGroup(groupId, groupData, (error, ref) -> {
            if (error == null) {
                Toast.makeText(requireContext(), groupName + " created successfully!", Toast.LENGTH_SHORT).show();
                fetchUserGroups(); // Refresh the groups list
            } else {
                Toast.makeText(requireContext(), "Failed to create group: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserGroups() {
        String firebaseUID = FirebaseHelper.getCurrentUserId();

        if (firebaseUID == null) {
            handleUserNotLoggedIn();
            return;
        }

        FirebaseHelper.getGroupsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupList.clear();
                groupIdMap.clear();
                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                    if (groupSnapshot.child("members").hasChild(firebaseUID)) {
                        String groupId = groupSnapshot.getKey();
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

    private void handleUserNotLoggedIn() {
        Log.w("GroupViewFragment", "No user is logged in.");
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
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
        b = null;
    }
}