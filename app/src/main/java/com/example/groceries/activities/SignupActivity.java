package com.example.groceries.activities;

import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groceries.R;
import com.example.groceries.databinding.ActivitySignupBinding; // Import the generated binding class
import com.example.groceries.helper.FirebaseHelper;
import com.example.groceries.helper.HelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ActivitySignupBinding binding; // Declare the binding variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize View Binding
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();

        binding.loginLink.setOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Set up the signup button click listener
        binding.signupButton.setOnClickListener(view -> {
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("users");

            String email = binding.signupEmail.getText().toString();
            String username = binding.signupUsername.getText().toString();
            String password = binding.signupPassword.getText().toString();
            String reconfirmPassword = binding.signupReconfirmPassword.getText().toString();

            // Validate email
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.signupEmail.setError("Please enter a valid email address");
                binding.signupEmail.requestFocus();
                return;
            }

            // Check for empty fields
            if (TextUtils.isEmpty(email)) {
                binding.signupEmail.setError("Email is required");
                binding.signupEmail.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(username)) {
                binding.signupUsername.setError("Username is required");
                binding.signupUsername.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                binding.signupPassword.setError("Password is required");
                binding.signupPassword.requestFocus();
                return;
            }

            // Check password length
            if (password.length() < 6) {
                binding.signupPassword.setError("Password must be at least 6 characters long");
                binding.signupPassword.requestFocus();
                return;
            }

            // Check if passwords match
            if (!password.equals(reconfirmPassword)) {
                binding.signupReconfirmPassword.setError("Passwords do not match!");
                binding.signupReconfirmPassword.requestFocus();
                return;
            }

            // Check if username exists in the database
            FirebaseHelper.checkUsernameExists(username, new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Username already exists
                        binding.signupUsername.setError("Username is already taken");
                        binding.signupUsername.requestFocus();
                    } else {
                        // Create user with Firebase Authentication
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = auth.getCurrentUser();
                                    if (user != null) {
                                        String uid = user.getUid();

                                        // Create a user object
                                        HelperClass newUser = new HelperClass(email, username, password);

                                        // Add the user to the database
                                        FirebaseHelper.addUser(uid, newUser, (error, ref) -> {
                                            if (error == null) {
                                                Toast.makeText(SignupActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SignupActivity.this, NameActivity.class);
                                                intent.putExtra("UID", uid);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(SignupActivity.this, "Failed to register user: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(SignupActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(SignupActivity.this, "Failed to check username availability", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}