package com.example.groceries.fragments;

import android.graphics.Color;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groceries.R;
import com.example.groceries.User;
import com.example.groceries.adapter.MemberAdapter;
import com.example.groceries.databinding.FragmentGroupSettingBinding;
import com.example.groceries.helper.FirebaseHelper;
import com.example.groceries.helper.NavigationHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupSettingFragment extends Fragment {

    private NavigationHelper navigationHelper;
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

        initialise();
        setupRecyclerView();
        loadGroupMembers();
        setupClickListeners();
    }

    private void initialise(){
        navigationHelper = new NavigationHelper(requireActivity(), R.id.main_frame);
    }

    private void setupClickListeners() {
        b.backArrow.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStackImmediate();
        });

        b.addMember.setOnClickListener(v -> showAddMemberDialog());
    }

    private void setupRecyclerView(){
        String currentUserId = FirebaseHelper.getCurrentUserId();

        adapter = new MemberAdapter(members, member -> {
            showLeaveOrRemoveDialog(member);
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

    private void showLeaveOrRemoveDialog(User member) {
        boolean isCurrentUser = member.getUid().equals(FirebaseHelper.getCurrentUserId());

        AlertDialog alertDialog = new  AlertDialog.Builder(requireContext())
                .setTitle(isCurrentUser ? "Leave Group?" : "Remove Member")
                .setMessage(isCurrentUser
                        ? "Are you sure you want to leave this group?"
                        : "Are you sure you want to remove this member?")
                .setPositiveButton(isCurrentUser ? "Leave" : "Remove", (dialog, which) -> {
                    removeUserFromGroup(member.getUid(), isCurrentUser);
                })
                .setNegativeButton("Cancel", null)
                .create();

        alertDialog.setOnShowListener(d -> {
            Button positiveButton = ((AlertDialog) d).getButton(AlertDialog.BUTTON_POSITIVE);
            Button negativeButton = ((AlertDialog) d).getButton(AlertDialog.BUTTON_NEGATIVE);

            positiveButton.setTextColor(Color.RED);
            negativeButton.setTextColor(Color.BLACK);
        });
        alertDialog.show();
    }

    private void removeUserFromGroup(String userId, boolean isCurrentUser) {
        FirebaseHelper.removeGroupMember(groupId, userId, (error, ref) -> {
            if (error != null) {
                showError("Failed to " + (isCurrentUser ? "leave" : "remove") + ": " + error.getMessage());
                return;
            }
            // After successful removal, check if group is now empty
            FirebaseHelper.getGroupMembers(groupId, new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getChildrenCount() == 0) {
                        // Group is empty - delete it
                        FirebaseHelper.deleteGroup(groupId, (error, ref) -> {
                            if (error == null) {
                                showMessage(isCurrentUser, true);
                                navigationHelper.navigateToFragment(new AllGroupFragment());
                            } else {
                                showError("Failed to delete group: " + error.getMessage());
                            }
                        });
                    } else {
                        showMessage(isCurrentUser, false);
                        if (isCurrentUser) {
                            navigationHelper.navigateToFragment(new AllGroupFragment());
                        } else {
                            loadGroupMembers();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    showError("Failed to check members: " + error.getMessage());
                }
            });
        });
    }

    private void showMessage(boolean isCurrentUser, boolean groupDeleted) {
        String message = groupDeleted
                ? isCurrentUser ? "You left - group deleted" : "Member removed - group deleted"
                : isCurrentUser ? "You left the group" : "Member removed";
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // add user to group in the database
    private void addUserToGroup(String userId) {
        // Use the simpler addGroupMember method
        FirebaseHelper.addGroupMember(groupId, userId, (error, ref) -> {
            if (error == null) {
                Toast.makeText(requireContext(), "Member added successfully", Toast.LENGTH_SHORT).show();
                loadGroupMembers(); // Refresh the members list

            } else {
                Toast.makeText(requireContext(), "Failed to add member: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}