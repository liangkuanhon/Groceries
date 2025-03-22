package com.example.groceries;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {
    private GroupAdapter adapter;
    private List<String> groupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupview);

        //find recycler view in the xml
        RecyclerView recyclerView = findViewById(R.id.groupsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //instantiate temp variables
        groupList = new ArrayList<>();
        adapter = new GroupAdapter(groupList);
        recyclerView.setAdapter(adapter);

        //create group button will take screen to creategroupactivity.java
        //it will go to the respective xml inside creategroupactivity.java
        Button createGroupButton = findViewById(R.id.create_group_button);
        createGroupButton.setOnClickListener(view -> {
            Intent intent = new Intent(GroupActivity.this, CreateGroupActivity.class);
            startActivity(intent);
        });

        // Fetch groups for the current user using FirebaseHelper
        fetchUserGroups();
    }

    private void fetchUserGroups() {
        // Get the Firebase UID using FirebaseHelper
        String firebaseUID = FirebaseHelper.getCurrentUserId();

        //if somehow the user is NOT logged in and in this view, log report
        //go back to login screen
        if (firebaseUID == null) {
            Log.w("GroupActivity", "No user is logged in.");
            Intent intent = new Intent(GroupActivity.this, LoginActivity.class);

            //go back to login
            startActivity(intent);
            return;
        }

        //create a reference to the CURRENT user's groups
        DatabaseReference groupsRef = FirebaseDatabase.getInstance().getReference().child("groups");

        //now event listener will update everytime a user is added to a group
        groupsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupList.clear();
                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                    if (groupSnapshot.child("members").hasChild(firebaseUID)) {
                        String groupName = groupSnapshot.child("groupName").getValue(String.class);
                        groupList.add(groupName);
                    }
                }
                adapter.notifyDataSetChanged(); // Update RecyclerView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("GroupActivity", "Failed to read groups", databaseError.toException());
            }
        });
    }
}
