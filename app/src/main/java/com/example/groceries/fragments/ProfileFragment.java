package com.example.groceries.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.groceries.R;
import com.example.groceries.databinding.FragmentProfileBinding; // Import the generated binding class
import com.example.groceries.activities.LoginActivity;
import com.example.groceries.helper.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding; // Declare the binding variable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize View Binding
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.profileImage.setOnClickListener(v -> {
            navigateToEditProfile();
        });

        // Fetch user details
        FirebaseHelper.fetchUserDetails(FirebaseHelper.getCurrentUserId(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    binding.userName.setText(name);
                    binding.userEmail.setText(email);
                } else {
                    binding.userName.setText("Name does not exist");
                    binding.userEmail.setText("Email does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load user data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Set up click listeners
        binding.settings.setOnClickListener(v ->
                Toast.makeText(getContext(), "Settings Clicked!", Toast.LENGTH_SHORT).show()
        );

        binding.language.setOnClickListener(v ->
                Toast.makeText(getContext(), "Language Clicked!", Toast.LENGTH_SHORT).show()
        );

        binding.favourites.setOnClickListener(v ->
                Toast.makeText(getContext(), "Language Clicked!", Toast.LENGTH_SHORT).show()
        );

        binding.contact.setOnClickListener(v ->
                Toast.makeText(getContext(), "Contact Clicked!", Toast.LENGTH_SHORT).show()
        );

        binding.web.setOnClickListener(v ->
                Toast.makeText(getContext(), "Web Clicked!", Toast.LENGTH_SHORT).show()
        );

        binding.switchAccount.setOnClickListener(v ->
                Toast.makeText(getContext(), "Switch Account Clicked!", Toast.LENGTH_SHORT).show()
        );

        binding.logout.setOnClickListener(v -> logout());
    }

    private void navigateToEditProfile(){
        EditProfileFragment editProfileFragment = new EditProfileFragment();

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, editProfileFragment);
        transaction.commit();
    }

    public void logout() {
        // Sign out the current user
        FirebaseAuth.getInstance().signOut();

        // Redirect to the login screen
        redirectToLoginScreen();

        // Clear user data
        clearUserData();
    }

    private void redirectToLoginScreen() {
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
        startActivity(intent);
        requireActivity().finish(); // Close the current activity
    }

    private void clearUserData() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}