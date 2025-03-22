package com.example.groceries;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class CreateGroupActivity extends AppCompatActivity {
    private EditText groupNameInput;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set the content to creategroup xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);

        //get reference to the input text box
        groupNameInput = findViewById(R.id.group_name_input);
        Button createGroupButton = findViewById(R.id.create_group_confirm_button);

        //get database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        createGroupButton.setOnClickListener(v -> createGroup());
    }

    private void createGroup() {
        String groupName = groupNameInput.getText().toString().trim();
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        if (groupName.isEmpty()) {
            Toast.makeText(this, "Enter a group name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate unique group ID
        String groupId = databaseReference.child("groups").push().getKey();

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
        databaseReference.child("groups").child(groupId).updateChildren(groupData);
        databaseReference.child("users").child(userId).updateChildren(userData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(CreateGroupActivity.this, "Group created!", Toast.LENGTH_SHORT).show();
                        finish(); // Close activity

                        //reload groups
                        Intent intent = new Intent(CreateGroupActivity.this, GroupActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CreateGroupActivity.this, "Failed to create group", Toast.LENGTH_SHORT).show();
                        Log.e("CreateGroupActivity", "Error: " + task.getException());
                    }
                });
    }
}
