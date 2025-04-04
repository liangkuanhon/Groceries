package com.example.groceries.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groceries.R;
import com.example.groceries.activities.User;
import com.example.groceries.adapter.MemberAdapter;
import com.example.groceries.databinding.FragmentGroupSettingBinding;
import com.example.groceries.databinding.FragmentSingleGroupBinding;
import com.example.groceries.helper.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupSettingFragment extends Fragment {

    private FragmentGroupSettingBinding b;
    private static final String ARG_GROUP_NAME = "group_name";
    private static final String ARG_GROUP_ID = "group_id";
    private String groupName;
    private String groupId;
    private MemberAdapter adapter;
    private final List<User> members = new ArrayList<>();

    public static GroupSettingFragment newInstance(String groupId, String groupName) {
        GroupSettingFragment fragment = new GroupSettingFragment();
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
        b = FragmentGroupSettingBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.groupName.setText(groupName);

        setupRecyclerView();
        loadGroupMembers();

        setupClickListeners();

    }

    private void setupClickListeners() {
        b.backArrow.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStackImmediate();
        });

        b.addMember.setOnClickListener(v -> showAddMemberDialog());
    }

    private void setupRecyclerView(){
        adapter = new MemberAdapter(members, member -> {
            removeMemberFromGroup(member.getUid());
        });

        b.membersList.setLayoutManager(new LinearLayoutManager(requireContext()));
        b.membersList.setAdapter(adapter);
    }

    private void loadGroupMembers() {
        FirebaseHelper.getGroupMembers(groupId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                members.clear();
                for (DataSnapshot memberSnapshot : snapshot.getChildren()) {
                    String userId = memberSnapshot.getKey();
                    // Fetch user details
                    FirebaseHelper.getUserDetails(userId, new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            User user = userSnapshot.getValue(User.class);
                            if (user != null) {
                                user.setUid(userId);
                                members.add(user);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(requireContext(),
                                    "Failed to load user: " + error.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(),
                        "Failed to load members: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeMemberFromGroup(String userId) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Remove Member")
                .setMessage("Are you sure you want to remove this member?")
                .setPositiveButton("Remove", (dialog, which) -> {
                    // Remove from group's members list
                    FirebaseHelper.removeGroupMember(groupId, userId, (error, ref) -> {
                        if (error == null) {
                            // Remove group from user's group list
                            FirebaseHelper.removeGroupFromUser(userId, groupId, (userError, userRef) -> {
                                if (userError == null) {
                                    Toast.makeText(requireContext(),
                                            "Member removed", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(requireContext(),
                                            "Failed to update user: " + userError.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(requireContext(),
                                    "Failed to remove member: " + error.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showAddMemberDialog() {
        // Create dialog with search functionality
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Member by Username");

        // Set up the input
        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        input.setHint("Enter username");
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Add", (dialog, which) -> {
            String username = input.getText().toString().trim();
            if (!username.isEmpty()) {
                addUserToGroupByUsername(username);
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void addUserToGroupByUsername(String username) {

        // Check if username exists using FirebaseHelper
        FirebaseHelper.checkUsernameExists(username, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Username exists, get the first match
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String userId = userSnapshot.getKey();
                        addUserToGroup(userId);
                        return;
                    }
                } else {
                    Toast.makeText(requireContext(),
                            "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(),
                        "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addUserToGroup(String userId) {
        // Add user to group members using FirebaseHelper
        Map<String, Object> groupData = new HashMap<>();
        groupData.put("members/" + userId, true); // Add to members list

        FirebaseHelper.createGroup(groupId, groupData, (error, ref) -> {
            if (error == null) {
                // Also add group to user's group list
                FirebaseHelper.addGroupToUser(userId, groupId, (userError, userRef) -> {
                    if (userError == null) {
                        Toast.makeText(requireContext(),
                                "Member added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(),
                                "Failed to update user's groups: " + userError.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(requireContext(),
                        "Failed to add member: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}