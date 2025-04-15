package com.example.groceries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groceries.R;
import com.example.groceries.databinding.ActivityLoginBinding;
import com.example.groceries.helper.FirebaseHelper;
import com.example.groceries.helper.NavigationHelper;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import android.content.SharedPreferences;

public class LoginActivity extends AppCompatActivity {

    private NavigationHelper navigationHelper;
    private FirebaseAuth auth;
    private ActivityLoginBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialise();
        setupClickListeners();

    }

    // setup firebase, viewbinding, ui and initialise navigation helper
    private void initialise(){
        auth = FirebaseAuth.getInstance();
        b = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        navigationHelper = new NavigationHelper(this);
    }

    // assign methods to handle click logic
    private void setupClickListeners(){
        b.loginButton.setOnClickListener(view -> handleLogin());
        b.signupButton.setOnClickListener(view -> navigateToSignup());
    }

    private void handleLogin(){
        if (ValidInput() && ValidPassword()) {
            String input = b.loginInput.getText().toString().trim();
            String password = b.loginPassword.getText().toString().trim();

            // check which method to login with, using email or username
            if (isValidEmail(input)) {
                loginWithEmail(input, password);
            } else {
                loginWithUsername(input, password);
            }
        }
    }

    // use Patterns.EMAIL_ADDRESS to check if input is a valid email format
    private boolean isValidEmail(String input) {
        return Patterns.EMAIL_ADDRESS.matcher(input).matches();
    }

    // check whether username or email is entered
    private Boolean ValidInput() {
        String input = b.loginInput.getText().toString();
        if (input.isEmpty()) {
            b.loginInput.setError("Input cannot be empty");
            b.loginInput.requestFocus();
            return false;
        } else {
            b.loginInput.setError(null);
            return true;
        }
    }

    // check whether password is entered
    private Boolean ValidPassword() {
        String password = b.loginPassword.getText().toString();
        if (password.isEmpty()) {
            b.loginPassword.setError("Password cannot be empty");
            b.loginPassword.requestFocus();
            return false;
        } else {
            b.loginPassword.setError(null);
            return true;
        }
    }

    // use firebase authenticator to login
    private void loginWithEmail(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            // Save user email in SharedPreferences for auto-login
                            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email", email);
                            editor.apply();

                            // Navigate to MainActivity
                            navigationHelper.navigateToActivityClearStack(MainActivity.class);
                        }
                    } else {
                        // Login failed
                        Toast.makeText(LoginActivity.this, "Authentication failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // check if username exists in the database, then fetch the associated email and log in with it
    private void loginWithUsername(String username, String password) {
        FirebaseHelper.checkUsernameExists(username, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    boolean userFound = false;
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String usernameFromDB = userSnapshot.child("username").getValue(String.class);
                        if (username.equals(usernameFromDB)) {
                            // Retrieve the email associated with this username
                            String emailFromDB = userSnapshot.child("email").getValue(String.class);
                            if (emailFromDB != null) {
                                // Use the email to log in
                                loginWithEmail(emailFromDB, password);
                                userFound = true;
                                break; // Exit the loop once the user is found
                            }
                        }
                    }
                    if (!userFound) {
                        b.loginInput.setError("Username does not exist");
                        b.loginInput.requestFocus();
                    }
                } else {
                    // If the snapshot does not exist at all
                    b.loginInput.setError("Username does not exist");
                    b.loginInput.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToSignup(){
        navigationHelper.navigateToActivityClearStack(SignupActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is already logged in
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // If the user is already logged in, skip LoginActivity and go to MainActivity
            navigationHelper.navigateToActivityClearStack(MainActivity.class);
        }
    }
}