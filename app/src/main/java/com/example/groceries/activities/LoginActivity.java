package com.example.groceries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groceries.R;
import com.example.groceries.helper.FirebaseHelper;
import com.google.android.material.button.MaterialButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import android.widget.Toast;

import java.util.Objects;

import android.content.SharedPreferences; //so they don't need to login everytime

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText login_input, login_password;
    private MaterialButton login_button, signup_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        login_input = findViewById(R.id.login_input);
        login_password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
        signup_button = findViewById(R.id.signup_button);


      
        login_button.setOnClickListener(view -> {
            if (ValidInput() && ValidPassword()) {

                String input = login_input.getText().toString().trim();
                String password = login_password.getText().toString().trim();

                if (isValidEmail(input)) {
                    // If input is an email, log in directly
                    loginWithEmail(input, password);
                } else {
                    // If input is a username, look up the email in the database
                    loginWithUsername(input, password);
                }
            }
        });

        signup_button.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }


    private boolean isValidEmail(String input) {
        return Patterns.EMAIL_ADDRESS.matcher(input).matches();
    }

    private Boolean ValidInput() {
        String input = login_input.getText().toString();
        if (input.isEmpty()) {
            login_input.setError("Input cannot be empty");
            login_input.requestFocus();
            return false;
        } else {
            login_input.setError(null);
            return true;
        }
    }

    private Boolean ValidPassword() {
        String password = login_password.getText().toString();
        if (password.isEmpty()) {
            login_password.setError("Password cannot be empty");
            login_password.requestFocus();
            return false;
        } else {
            login_password.setError(null);
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
                        login_input.setError("Username does not exist");
                        login_input.requestFocus();
                    }
                } else {
                    // If the snapshot does not exist at all
                    login_input.setError("Username does not exist");
                    login_input.requestFocus();
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
