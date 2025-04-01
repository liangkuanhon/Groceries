package com.example.groceries.fragments;

import static android.content.Context.MODE_PRIVATE;

import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.groceries.R;
import com.example.groceries.activities.CategoryActivity;
import com.example.groceries.activities.CreateGroupActivity;
import com.example.groceries.activities.GroceryListActivity;
import com.example.groceries.activities.GroupViewActivity;
import com.example.groceries.activities.MainActivity;
import com.example.groceries.databinding.FragmentProfileBinding; // Import the generated binding class
import com.example.groceries.activities.LoginActivity;
import com.example.groceries.helper.FirebaseHelper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding b; // Declare the binding variable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize View Binding
        b = FragmentProfileBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupClickListeners();
        fetchUserDetails();
    }

    private void setupClickListeners() {
        b.profileImage.setOnClickListener(v -> navigateToEditProfile());

        b.settings.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), GroupViewActivity.class);
            startActivity(intent);
        });

        b.language.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), CategoryActivity.class);
            startActivity(intent);
        });

        b.favourites.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), GroceryListActivity.class);
            startActivity(intent);
        });

        b.contact.setOnClickListener(v ->
                Toast.makeText(getContext(), "Contact Clicked!", Toast.LENGTH_SHORT).show()
        );

        b.web.setOnClickListener(v ->
                Toast.makeText(getContext(), "Web Clicked!", Toast.LENGTH_SHORT).show()
        );

        b.switchAccount.setOnClickListener(v ->
                Toast.makeText(getContext(), "Switch Account Clicked!", Toast.LENGTH_SHORT).show()
        );

        b.logout.setOnClickListener(v -> showLogoutDialog());
    }

    private void fetchUserDetails() {
        FirebaseHelper.fetchUserDetails(FirebaseHelper.getCurrentUserId(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    b.userName.setText(name);
                    b.userEmail.setText(email);
                } else {
                    b.userName.setText("Name does not exist");
                    b.userEmail.setText("Email does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load user data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToEditProfile() {
        EditProfileFragment editProfileFragment = new EditProfileFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, editProfileFragment);
        transaction.commit();
    }

    private void showLogoutDialog() {
        AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (d, which) -> logout())
                .setNegativeButton("Cancel", null)
                .setCancelable(false)
                .create();

        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        redirectToLoginScreen();
        clearUserData();
    }

    private void redirectToLoginScreen() {
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    private void clearUserData() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}