package com.example.groceries.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groceries.R;
import com.example.groceries.activities.GroupActivity;
import com.example.groceries.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private TextView settings, language, contact, web, switchAccount, logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Clickable Text
        settings = view.findViewById(R.id.settings);
        language = view.findViewById(R.id.language);
        contact = view.findViewById(R.id.contact);
        web = view.findViewById(R.id.web);
        switchAccount = view.findViewById(R.id.switchAccount);
        logout = view.findViewById(R.id.logout);

        // Get uid from intent
        String uid = requireActivity().getIntent().getStringExtra("UID");

        // Navigate to GroupActivity when "Your Groups" button is clicked
        settings.setOnClickListener(v ->
                Toast.makeText(getContext(), "TextView Clicked!", Toast.LENGTH_SHORT).show()
        );

        language.setOnClickListener(v ->
                Toast.makeText(getContext(), "TextView Clicked!", Toast.LENGTH_SHORT).show()
        );

        contact.setOnClickListener(v ->
                Toast.makeText(getContext(), "TextView Clicked!", Toast.LENGTH_SHORT).show()
        );

        web.setOnClickListener(v ->
                Toast.makeText(getContext(), "TextView Clicked!", Toast.LENGTH_SHORT).show()
        );

        switchAccount.setOnClickListener(v ->
                Toast.makeText(getContext(), "TextView Clicked!", Toast.LENGTH_SHORT).show()
        );

        // Setup logout button click listener
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    public void logout() {
        // Get an instance of FirebaseAuth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Sign out the current user
        firebaseAuth.signOut();

        // Optional: Perform post-logout actions
        redirectToLoginScreen();
        clearUserData();
    }

    private void redirectToLoginScreen() {
        // Redirect the user to the login screen
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
        startActivity(intent);
        requireActivity().finish(); // Close the current activity
    }

    private void clearUserData() {
        // Clear any user-related data from your app (e.g., SharedPreferences, cached data)
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}