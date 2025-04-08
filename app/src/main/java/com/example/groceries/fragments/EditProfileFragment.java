package com.example.groceries.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.groceries.R;
import com.example.groceries.databinding.FragmentEditProfileBinding;
import com.example.groceries.databinding.FragmentProfileBinding;
import com.example.groceries.helper.FirebaseHelper;
import com.example.groceries.helper.NavigationHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding b;
    private NavigationHelper navigationHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = FragmentEditProfileBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navigationHelper = new NavigationHelper(requireActivity(), R.id.main_frame);

        setupClickListeners();
        loadUserData();
    }

    private void loadUserData(){
        FirebaseHelper.fetchUserDetails(FirebaseHelper.getCurrentUserId(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String username = snapshot.child("username").getValue(String.class);
                    b.profileName.setText(name);
                    b.username.setText("@" + username);
                } else {
                    b.profileName.setText("Name does not exist");
                    b.username.setText("Username does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load user data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupClickListeners() {
        b.backButton.setOnClickListener(v -> navigateToProfile());
        b.saveProfile.setOnClickListener(v -> navigateToProfile()); // add safe logic too when free
    }

    private void navigateToProfile() {
        // Use NavigationHelper instead of direct FragmentTransaction
        navigationHelper.navigateToFragment(new ProfileFragment());
    }
}