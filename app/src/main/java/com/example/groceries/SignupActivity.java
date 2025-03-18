package com.example.groceries;

import android.os.Bundle;

import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    EditText signup_email, signup_username, signup_password, signup_reconfirm_password;
    TextView signup_title;
    MaterialButton signup_button;
    FirebaseDatabase database;
    DatabaseReference reference;


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

                // Check if password matches
                if (!password.equals(reconfirm_password)){
                    signup_reconfirm_password.setError("Passwords do not match!");
                    signup_reconfirm_password.requestFocus();
                    return;
                }

                // Check if username exist in database
                reference.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Username already exists
                            signup_username.setError("Username is already taken");
                            signup_username.requestFocus();
                        } else {
                            HelperClass helperClass = new HelperClass(email, username, password);
                            reference.child(username).setValue(helperClass);

                            Toast.makeText(SignupActivity.this, "Signed up successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
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