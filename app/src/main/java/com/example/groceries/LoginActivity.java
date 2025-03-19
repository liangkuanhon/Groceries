package com.example.groceries;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.button.MaterialButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

import android.content.SharedPreferences; //so they don't need to login everytime

public class LoginActivity extends AppCompatActivity {

    EditText login_input, login_password;
    MaterialButton login_button, signup_button;
    FirebaseDatabase database;
    DatabaseReference reference;

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

        login_input = findViewById(R.id.login_input);
        login_password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
        signup_button = findViewById(R.id.signup_button);


      
        login_button.setOnClickListener(view -> {
            if (ValidInput() && ValidPassword()) {
                checkUser();

            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is already logged in
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("email", null);

        if (userEmail != null) {
            // If the user is already logged in, skip LoginActivity and go to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Prevent going back to LoginActivity
        }
    }


    public Boolean ValidInput(){
        String input = login_input.getText().toString();
        if (input.isEmpty()){
            login_input.setError("Input cannot be empty");
            login_input.requestFocus();
            return false;
        }else {
            login_input.setError(null);
            return true;
        }
    }

    public Boolean ValidPassword(){
        String password = login_password.getText().toString();
        if (password.isEmpty()){
            login_password.setError("Password cannot be empty");
            login_password.requestFocus();
            return false;
        } else {
            login_password.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userInput = login_input.getText().toString().trim();
        String userPassword = login_password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        if (isValidEmail(userInput)) {
            checkEmail(reference, userInput, userPassword);
        } else {
            checkUsername(reference, userInput, userPassword);
        }
    }

    private void checkEmail(DatabaseReference reference, String email, String password){
        Query emailQuery = reference.orderByChild("email").equalTo(email);
        emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    validateUser(snapshot, password);
                } else {
                    login_input.setError("Account does not exist. Please create an account");
                    login_input.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void checkUsername(DatabaseReference reference, String username, String password) {
        DatabaseReference usernameRef = reference.child(username);
        usernameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    validateUser(snapshot, password);
                } else {
                    login_input.setError("Account does not exist. Please create an account");
                    login_input.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database errors
                Toast.makeText(LoginActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("LoginActivity", "Database error: " + error.getMessage());
            }
        });
    }

    private boolean isValidEmail(String input) {
        return Patterns.EMAIL_ADDRESS.matcher(input).matches();
    }

    private void validateUser(DataSnapshot snapshot, String userPassword) {
        if (snapshot.hasChild("password")) {
            // (checkUsername case)
            String passwordFromDB = snapshot.child("password").getValue(String.class);
            if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                // Wrong password
                login_password.setError("Wrong Password");
                login_password.requestFocus();
            }
        } else {
            // (checkEmail case)
            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                String passwordFromDB = userSnapshot.child("password").getValue(String.class);
                if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            // If no matching password is found
            login_password.setError("Wrong Password");
            login_password.requestFocus();
        }
    }
}