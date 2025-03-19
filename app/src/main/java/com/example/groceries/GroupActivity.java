package com.example.groceries;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GroupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupview);

        Button createGroupButton = findViewById(R.id.create_group_button);
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupActivity.this, CreateGroupActivity.class);
                startActivity(intent);
            }
        });

        // Fetch groups for the current user
        fetchUserGroups();
    }

    private void fetchUserGroups() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String firebaseUID = auth.getCurrentUser().getUid(); // Firebase UID

        // Step 1: Retrieve the unique username from the "users" database
        DatabaseReference usersRef = database.child("users");
        usersRef.orderByChild("firebaseUID").equalTo(firebaseUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String username = userSnapshot.getKey(); // This is the unique username

                        // Step 2: Use this username to find the user's groups
                        fetchGroupsForUser(username);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("GroupActivity", "Failed to read user data", databaseError.toException());
            }
        });
    }

    // This function fetches the groups the user is part of
    private void fetchGroupsForUser(String username) {
        DatabaseReference groupsRef = FirebaseDatabase.getInstance().getReference().child("groups");

        groupsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                    if (groupSnapshot.child("members").hasChild(username)) {
                        String groupName = groupSnapshot.child("groupName").getValue(String.class);
                        Log.d("GroupActivity", "User is in group: " + groupName);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("GroupActivity", "Failed to read groups", databaseError.toException());
            }
        });
    }

}
