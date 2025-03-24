package com.example.groceries.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {
    private static FirebaseDatabase database;
    private static DatabaseReference reference;

    static {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
    }


//    public static void init() {
//        if (database == null) {
//            database = FirebaseDatabase.getInstance();
//        }
//        if (reference == null) {
//            reference = database.getReference("users");
//        }
//    }

    // Get user reference
//    public static DatabaseReference getUserReference() {
//        init();
//        return reference;
//    }

    // Get current user UID
    public static String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid(); // Return the UID of the logged-in user
        }
        return null; // No user is logged in
    }

    // Check if username exists
    public static void checkUsernameExists(String username, ValueEventListener listener) {
        reference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(listener);
    }

    // Check if email exists
    public static void checkEmailExists(String email, ValueEventListener listener) {
        reference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(listener);
    }

    // Check if name exists
    public static void checkNameExists(String name, ValueEventListener listener) {
        reference.orderByChild("name").equalTo(name).addListenerForSingleValueEvent(listener);
    }

    // Add a new user to the database using UID as key
    public static void addUser(String uid, HelperClass user, DatabaseReference.CompletionListener listener) {
        reference.child(uid).setValue(user, listener);
    }

    // Add user name to the database using UID as key
    public static void updateName(String uid, String name){
        reference.child(uid).child("name").setValue(name);
    }

    // Fetch user details by UID
    public static void fetchUserDetails(String uid, ValueEventListener listener) {
        reference.child(uid).addListenerForSingleValueEvent(listener);
    }

    // Create a new group in database
    public static void createGroup(String groupId, Map<String, Object> groupData, DatabaseReference.CompletionListener listener) {
        database.getReference("groups").child(groupId).updateChildren(groupData, listener);
    }

    // Add group to user's list
    public static void addGroupToUser(String userId, String groupId, DatabaseReference.CompletionListener listener) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("groups/" + groupId, true);
        database.getReference("users").child(userId).updateChildren(userData, listener);
    }


}
