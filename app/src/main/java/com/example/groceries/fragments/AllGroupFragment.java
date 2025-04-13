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
import com.example.groceries.helper.GroupImageHelper;
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
    private List<Map<String, Object>> groupDataList;
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
        groupDataList = new ArrayList<>();
        recommendationList = new ArrayList<>(DEFAULT_RECOMMENDATIONS);
        groupIdMap = new HashMap<>();
    }

    private void setupGroupsRecyclerView() {
        adapter = new GroupAdapter(groupDataList);
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
            createRecommendedGroup(position);
        });
    }

    private void createRecommendedGroup(int position) {
        String currentUserId = FirebaseHelper.getCurrentUserId();
        if (currentUserId == null) {
            Toast.makeText(requireContext(), "Please login first", Toast.LENGTH_SHORT).show();
            return;
        }

        String groupName = recommendationList.get(position);
        int imageResId = GroupImageHelper.getGroupImage(groupName);

        // using helper class to create a group
        FirebaseHelper.createGroup(groupName, currentUserId, imageResId, (error, ref) -> {
            if (error != null) {
                Toast.makeText(requireContext(),
                        "Failed to create group: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.e("CreateGroup", error.getMessage());
                return;
            }

            Toast.makeText(requireContext(),
                    groupName + " created successfully!",
                    Toast.LENGTH_SHORT).show();
            fetchUserGroups(); // Refresh the list
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
                groupDataList.clear();
                groupIdMap.clear();
                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                    if (groupSnapshot.child("members").hasChild(firebaseUID)) {
                        Map<String, Object> groupData = new HashMap<>();
                        String groupId = groupSnapshot.getKey();
                        String groupName = groupSnapshot.child("groupName").getValue(String.class);
                        Integer imageResId = groupSnapshot.child("imageResId").getValue(Integer.class);
                        if (groupName != null) {
                            groupData.put("groupName", groupName);
                            groupData.put("imageResId", imageResId);
                            groupDataList.add(groupData);
                            groupIdMap.put(groupName, groupId);
                        }
                    }
                }
                // update list whenever there is a change in the group list
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