package com.example.groceries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groceries.R;
import com.example.groceries.databinding.ActivityCreategroupBinding;
import com.example.groceries.databinding.ActivityLoginBinding;
import com.example.groceries.helper.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class CreateGroupActivity extends AppCompatActivity {
    private ActivityCreategroupBinding b;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set the content to creategroup xml
        super.onCreate(savedInstanceState);
        b = ActivityCreategroupBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(b.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();

        b.createGroupButton.setOnClickListener(v -> createGroup());

        b.cancel.setOnClickListener(view -> {
            Intent intent = new Intent(CreateGroupActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void createGroup() {
        String groupName = b.groupNameInput.getText().toString().trim();
        String userId = FirebaseHelper.getCurrentUserId();

        if (groupName.isEmpty()) {
            b.groupNameInput.setError("Group Name cannot be empty");
            b.groupNameInput.requestFocus();
            return;
        }
        if (userId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate unique group ID
        String groupId = FirebaseDatabase.getInstance().getReference("groups").push().getKey();

        if (groupId == null) {
            Toast.makeText(this, "Error creating group", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare group data
        Map<String, Object> groupData = new HashMap<>();
        groupData.put("groupName", groupName);
        groupData.put("members/" + userId, true); // Add user as a member

        // Prepare user data
        Map<String, Object> userData = new HashMap<>();
        userData.put("groups/" + groupId, true); // Add group to user's list

        // Update Firebase
        FirebaseHelper.createGroup(groupId, groupData, (groupError, groupRef) -> {
            if (groupError == null) {
                // Group created successfully, now add group to user's list
                FirebaseHelper.addGroupToUser(userId, groupId, (userError, userRef) -> {
                    if (userError == null) {
                        // Group added to user's list successfully
                        Toast.makeText(CreateGroupActivity.this, "Group created!", Toast.LENGTH_SHORT).show();
                        finish(); // Close activity

                        Intent intent = new Intent(CreateGroupActivity.this, GroupViewActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CreateGroupActivity.this, "Failed to add group to user", Toast.LENGTH_SHORT).show();
                        Log.e("CreateGroupActivity", "Error: " + userError.getMessage());
                    }
                });
            } else {
                Toast.makeText(CreateGroupActivity.this, "Failed to create group", Toast.LENGTH_SHORT).show();
                Log.e("CreateGroupActivity", "Error: " + groupError.getMessage());
            }
        });
    }
}
