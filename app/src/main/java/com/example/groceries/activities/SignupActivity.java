package com.example.groceries.activities;

import android.os.Bundle;

import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import com.example.groceries.R;
import com.example.groceries.helper.FirebaseHelper;
import com.example.groceries.helper.HelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    private EditText signup_email, signup_username, signup_password, signup_reconfirm_password;
    private TextView signup_title;
    private MaterialButton signup_button;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        signup_email = findViewById(R.id.signup_email);
        signup_password = findViewById(R.id.signup_password);
        signup_username = findViewById(R.id.signup_username);
        signup_reconfirm_password = findViewById(R.id.signup_reconfirm_password);
        signup_title = findViewById(R.id.signup_title);
        signup_button = findViewById(R.id.signup_button);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String email = signup_email.getText().toString();
                String username = signup_username.getText().toString();
                String password = signup_password.getText().toString();
                String reconfirm_password = signup_reconfirm_password.getText().toString();

                // Check if the email is valid
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    signup_email.setError("Please enter a valid email address");
                    signup_email.requestFocus();
                    return;
                }

                // Check Empty Email
                if (TextUtils.isEmpty(email)) {
                    signup_email.setError("Email is required");
                    signup_email.requestFocus();
                    return;
                }

                // Check Empty Username
                if (TextUtils.isEmpty(username)) {
                    signup_username.setError("Username is required");
                    signup_username.requestFocus();
                    return;
                }

                // Check Empty Password
                if (TextUtils.isEmpty(password)) {
                    signup_password.setError("Password is required");
                    signup_password.requestFocus();
                    return;
                }

                // Check Password Length >= 6
                if (password.length() < 6) {
                    signup_password.setError("Password must be at least 6 characters long");
                    signup_password.requestFocus();
                    return;
                }

                // Check if password matches
                if (!password.equals(reconfirm_password)){
                    signup_reconfirm_password.setError("Passwords do not match!");
                    signup_reconfirm_password.requestFocus();
                }

                //Check if username exist in database
                FirebaseHelper.checkUsernameExists(username, new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //String usernameFromDB = dataSnapshot.child("username").getValue(String.class);
                        if (dataSnapshot.exists()) {
                            // Username already exists
                            signup_username.setError("Username is already taken");
                            signup_username.requestFocus();
                        } else {

                            // Firebase Authentication
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
            }
        });
    }
}
