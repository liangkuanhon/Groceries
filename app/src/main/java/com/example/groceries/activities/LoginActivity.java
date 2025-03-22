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
import com.example.groceries.databinding.ActivityLoginBinding; // Import the generated binding class
import com.example.groceries.helper.FirebaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import android.content.SharedPreferences;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private ActivityLoginBinding binding; // Declare the binding variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize View Binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();

        // Use binding to access views
        binding.loginButton.setOnClickListener(view -> {
            if (ValidInput() && ValidPassword()) {
                String input = binding.loginInput.getText().toString().trim();
                String password = binding.loginPassword.getText().toString().trim();

                if (isValidEmail(input)) {
                    // If input is an email, log in directly
                    loginWithEmail(input, password);
                } else {
                    // If input is a username, look up the email in the database
                    loginWithUsername(input, password);
                }
            }
        });

        binding.signupButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    private boolean isValidEmail(String input) {
        return Patterns.EMAIL_ADDRESS.matcher(input).matches();
    }

    private Boolean ValidInput() {
        String input = binding.loginInput.getText().toString();
        if (input.isEmpty()) {
            binding.loginInput.setError("Input cannot be empty");
            binding.loginInput.requestFocus();
            return false;
        } else {
            binding.loginInput.setError(null);
            return true;
        }
    }

    private Boolean ValidPassword() {
        String password = binding.loginPassword.getText().toString();
        if (password.isEmpty()) {
            binding.loginPassword.setError("Password cannot be empty");
            binding.loginPassword.requestFocus();
            return false;
        } else {
            binding.loginPassword.setError(null);
            return true;
        }
    }

    private void loginWithEmail(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            // Save user email in SharedPreferences for auto-login
                            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email", email);
                            editor.apply();

                            // Navigate to MainActivity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        // Login failed
                        Toast.makeText(LoginActivity.this, "Authentication failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

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
                        binding.loginInput.setError("Username does not exist");
                        binding.loginInput.requestFocus();
                    }
                } else {
                    // If the snapshot does not exist at all
                    binding.loginInput.setError("Username does not exist");
                    binding.loginInput.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is already logged in
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // If the user is already logged in, skip LoginActivity and go to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Prevent going back to LoginActivity
        }
    }
}