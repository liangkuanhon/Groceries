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

        RecyclerView recyclerView = findViewById(R.id.groupsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groupList = new ArrayList<>();
        adapter = new GroupAdapter(groupList);
        recyclerView.setAdapter(adapter);

        Button createGroupButton = findViewById(R.id.create_group_button);
        createGroupButton.setOnClickListener(view -> {
            Intent intent = new Intent(GroupActivity.this, CreateGroupActivity.class);
            startActivity(intent);
        });

        // Fetch groups for the current user using FirebaseHelper
        fetchUserGroups();
    }

    private void fetchUserGroups() {
        String firebaseUID = FirebaseHelper.getCurrentUserId(); // Get the Firebase UID using FirebaseHelper

        if (firebaseUID == null) {
            Log.w("GroupActivity", "No user is logged in.");
            return;
        }

        DatabaseReference groupsRef = FirebaseDatabase.getInstance().getReference().child("groups");

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
