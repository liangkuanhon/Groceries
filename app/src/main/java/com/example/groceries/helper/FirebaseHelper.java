package com.example.groceries.helper;

import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class FirebaseHelper {
    private static FirebaseDatabase database;
    private static DatabaseReference userReference;
    private static DatabaseReference groupsReference;
    private static DatabaseReference itemsReference;

    static {
        database = FirebaseDatabase.getInstance();
        userReference = database.getReference("users");
        groupsReference = database.getReference("groups");
    }

    // Get current user UID
    public static String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid(); // Return the UID of the logged-in user
        }
        return null; // No user is logged in
    }

    // returns the database reference for our group
    public static DatabaseReference getGroupsReference() {
        return groupsReference;
    }

    // Check if username exists
    public static void checkUsernameExists(String username, ValueEventListener listener) {
        userReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(listener);
    }

    // Check if email exists
    public static void checkEmailExists(String email, ValueEventListener listener) {
        userReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(listener);
    }

    // Check if name exists
    public static void checkNameExists(String name, ValueEventListener listener) {
        userReference.orderByChild("name").equalTo(name).addListenerForSingleValueEvent(listener);
    }

    // Add a new user to the database using UID as key
    public static void addUser(String uid, UserHelper user, DatabaseReference.CompletionListener listener) {
        userReference.child(uid).setValue(user, listener);
    }

    // Add user name to the database using UID as key
    public static void updateName(String uid, String name){
        userReference.child(uid).child("name").setValue(name);
    }

    // Fetch user details by UID
    public static void fetchUserDetails(String uid, ValueEventListener listener) {
        userReference.child(uid).addListenerForSingleValueEvent(listener);
    }

    // Group Management

    // Create a new group in database
    public static void createGroup(String groupName, String creatorId, int imageResId,
                                   DatabaseReference.CompletionListener listener) {
        String groupId = groupsReference.push().getKey();
        if (groupId == null) {
            listener.onComplete(DatabaseError.fromException(new Exception("Failed to generate group ID")), null);
            return;
        }

        // Build complete group data structure
        Map<String, Object> groupData = new HashMap<>();
        groupData.put("groupName", groupName);
        groupData.put("createdBy", creatorId);
        groupData.put("imageResId", imageResId);

        // Initialize members with creator
        Map<String, Object> members = new HashMap<>();
        members.put(creatorId, true);
        groupData.put("members", members);

        // Single database operation
        groupsReference.child(groupId).updateChildren(groupData, listener);
    }

    public static void deleteGroup(String groupId, DatabaseReference.CompletionListener listener) {
        groupsReference.child(groupId).removeValue(listener);
    }

    // Add group to user's list
    public static void addGroupToUser(String userId, String groupId, DatabaseReference.CompletionListener listener) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("groups/" + groupId, true);
        groupsReference.child(userId).updateChildren(userData, listener);
    }

    public static DatabaseReference groupItemsReference(String groupId) {
        return groupsReference.child(groupId).child("items");
    }

    public static void addItemToGroup (String groupId, String itemName, int imageRes, DatabaseReference.CompletionListener listener){
        String userId = getCurrentUserId();
        if (userId == null) {
            return;
        }

        DatabaseReference itemReference = groupItemsReference(groupId);

        itemReference.orderByChild("name").equalTo(itemName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("name", itemName);
                    item.put("imageRes", imageRes);
                    item.put("addedBy", userId);
                    item.put("checked", false);

                    itemReference.push().setValue(item, listener);
                } else {
                    listener.onComplete(DatabaseError.fromException(
                        new Exception("Item already in list")),
                        itemReference
                    );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onComplete(error, null);
            }
        });

    }

    public static void getGroupItems(String groupId, ValueEventListener listener) {
        groupItemsReference(groupId).addValueEventListener(listener);
    }

    public static void updateGroupItemStatus(String groupId, String itemId, boolean isChecked) {
        groupItemsReference(groupId).child(itemId).child("checked").setValue(isChecked);
    }

    public static void removeGroupItem(String groupId, String itemId, DatabaseReference.CompletionListener listener) {
        groupItemsReference(groupId).child(itemId).removeValue(listener);
    }

    public static void removeAllGroupItem(String groupId, DatabaseReference.CompletionListener listener){
        groupItemsReference(groupId).removeValue(listener);
    }

    public static void getGroupMembers(String groupId, ValueEventListener listener) {
        groupsReference.child(groupId).child("members").addListenerForSingleValueEvent(listener);
    }

    public static void getUserDetails(String userId, ValueEventListener listener) {
        userReference.child(userId).addListenerForSingleValueEvent(listener);
    }

    public static void addGroupMember(String groupId, String userId, DatabaseReference.CompletionListener listener) {
        groupsReference.child(groupId).child("members").child(userId).setValue(true, listener);
    }

    public static void removeGroupMember(String groupId, String userId, DatabaseReference.CompletionListener listener) {
        groupsReference.child(groupId).child("members").child(userId).removeValue(listener);
    }

    //to reset the shopping list
// Remove all group items from the Firebase database
    public static void removeAllGroupItems(String groupId, DatabaseReference.CompletionListener listener) {
        // Assuming your group items are stored under a "groupItems" node under each group
        DatabaseReference groupItemsRef = database.getReference("groups").child(groupId).child("items");
        groupItemsRef.removeValue(listener);  // This will remove all items in the group
    }





}
