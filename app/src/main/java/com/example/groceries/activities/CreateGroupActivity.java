package com.example.groceries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groceries.R;
import com.example.groceries.adapter.GroupImageAdapter;
import com.example.groceries.databinding.ActivityCreategroupBinding;
import com.example.groceries.helper.FirebaseHelper;
import com.example.groceries.helper.GroupImageHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateGroupActivity extends AppCompatActivity {
    private ActivityCreategroupBinding b;
    private FirebaseAuth auth;
    private GroupImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialise();
        setupImageGrid();
        setupClickListeners();
    }

    private void initialise(){
        b = ActivityCreategroupBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        auth = FirebaseAuth.getInstance();
    }

    private void setupImageGrid() {
        imageAdapter = new GroupImageAdapter(this, GroupImageHelper.getGroupImageEntries());
        b.imageGridView.setAdapter(imageAdapter);
        b.imageGridView.setOnItemClickListener((parent, view, position, id) -> {
            imageAdapter.setSelectedPosition(position);
        });

        // Select first image by default
        if (imageAdapter.getCount() > 0) {
            imageAdapter.setSelectedPosition(0);
        }
    }

    private void setupClickListeners() {
        b.createGroupButton.setOnClickListener(v -> createGroup());
        b.cancel.setOnClickListener(view -> finish());
    }

    // In CreateGroupActivity.java
    private void createGroup() {
        b.createGroupButton.setEnabled(false);

        String groupName = b.groupNameInput.getText().toString().trim();
        String currentUserId = FirebaseHelper.getCurrentUserId();
        int selectedImageResId = imageAdapter.getSelectedImageResId();

        if (!validateInput(groupName, currentUserId)) {
            b.createGroupButton.setEnabled(true);
            return;
        }

        // Single clean call to FirebaseHelper
        FirebaseHelper.createGroup(groupName, currentUserId, selectedImageResId,
                (error, ref) -> {
                    b.createGroupButton.setEnabled(true);

                    if (error != null) {
                        handleError("Failed to create group: " + error.getMessage());
                    } else {
                        String groupId = ref.getKey();
                        navigateToGroup(groupId, groupName);
                    }
                });
    }

    private boolean validateInput(String groupName, String currentUserId) {
        if (groupName.isEmpty()) {
            b.groupNameInput.setError("Group Name cannot be empty");
            b.groupNameInput.requestFocus();
            return false;
        }

        if (groupName.length() > 30) {
            b.groupNameInput.setError("Group name too long (max 30 chars)");
            b.groupNameInput.requestFocus();
            return false;
        }

        if (currentUserId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return false;
        }

        return true;
    }

    private void handleError(String error) {
        b.createGroupButton.setEnabled(true);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        Log.e("CreateGroupActivity", error);
    }

    private void navigateToGroup(String groupId, String groupName) {
        Toast.makeText(this, groupName + " created successfully!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("openFragment", "singleGroup");
        intent.putExtra("groupId", groupId);
        intent.putExtra("groupName", groupName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        finish();
    }
}