package com.example.groceries.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groceries.databinding.ActivitySignupBinding; // Import the generated b class
import com.example.groceries.helper.FirebaseHelper;
import com.example.groceries.helper.UserHelper;
import com.example.groceries.helper.NavigationHelper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    // Decalre dependencies
    private FirebaseAuth auth;
    private ActivitySignupBinding b;
    private NavigationHelper navigationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialise();
        setupClickListeners();
    }

    // setup firebase, viewbinding, ui and initialise navigation helper
    private void initialise(){
        auth = FirebaseAuth.getInstance();
        b = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        navigationHelper = new NavigationHelper(this);
    }

    // assigning methods to handle button clicks
    private void setupClickListeners(){
        b.loginLink.setOnClickListener(view -> navigateToLogin());
        b.signupButton.setOnClickListener(view -> handleSignUp());
    }

    // convert the inputs to strings, if inputs are valid check if username exist otherwise create an account
    private void handleSignUp() {
        String email = b.signupEmail.getText().toString().trim();
        String username = b.signupUsername.getText().toString().trim();
        String password = b.signupPassword.getText().toString().trim();
        String reconfirmPassword = b.signupReconfirmPassword.getText().toString().trim();

        if (!validateInputs(email, username, password, reconfirmPassword)) {
            return;
        }
        checkUsernameAndRegister(username, email, password);
    }

    private boolean validateInputs(String email, String username, String password, String reconfirmPassword) {
        // check if email input is valid
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            b.signupEmail.setError("Please enter a valid email address");
            b.signupEmail.requestFocus();
            return false;
        }

        // Check for empty fields
        if (TextUtils.isEmpty(email)) {
            b.signupEmail.setError("Email is required");
            b.signupEmail.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(username)) {
            b.signupUsername.setError("Username is required");
            b.signupUsername.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            b.signupPassword.setError("Password is required");
            b.signupPassword.requestFocus();
            return false;
        }

        // Check password length
        if (password.length() < 6) {
            b.signupPassword.setError("Password must be at least 6 characters long");
            b.signupPassword.requestFocus();
            return false;
        }

        // Check if passwords match
        if (!password.equals(reconfirmPassword)) {
            b.signupReconfirmPassword.setError("Passwords do not match!");
            b.signupReconfirmPassword.requestFocus();
            return false;
        }
        return true;
    }

    // check if username exists in the database, register if it doesnt exist
    private void checkUsernameAndRegister(String username, String email, String password) {
        FirebaseHelper.checkUsernameExists(username, new ValueEventListener() { // listens for changes to data at a specific database location
            // triggers when the listener is attached, and whenever data at the watched location changes
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    b.signupUsername.setError("Username is already taken");
                    b.signupUsername.requestFocus();
                } else {
                    registerNewUser(email, password, username);
                }
            }

            // triggered during network failures, invalid queries etc
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showError("Failed to check username availability");
            }
        });
    }


    private void registerNewUser(String email, String password, String username) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            saveUserToFirebase(user.getUid(), email, username, password);
                        }
                    } else {
                        showError("Registration failed: " + task.getException().getMessage());
                    }
                });
    }

    private void saveUserToFirebase(String uid, String email, String username, String password) {
        UserHelper newUser = new UserHelper(email, username, password);
        FirebaseHelper.addUser(uid, newUser, (error, ref) -> {
            if (error == null) {
                showSuccess("User registered successfully!");
                navigateToNameActivity(uid);
            } else {
                showError("Failed to register user: " + error.getMessage());
            }
        });
    }

    private void showSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToNameActivity(String uid) {
        navigationHelper.navigateToActivityWithExtra(NameActivity.class, "UID", uid);
    }

    private void navigateToLogin() {
        navigationHelper.navigateToActivity(LoginActivity.class);
    }
}