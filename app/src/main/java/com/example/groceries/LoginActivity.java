package com.example.groceries;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText login_email, login_password;
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

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
        signup_button = findViewById(R.id.signup_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ValidEmail() | !ValidPassword()){

                } else {
                    checkUser();
                }
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

    public Boolean ValidEmail(){
        String email = login_email.getText().toString();
        if (email.isEmpty()){
            login_email.setError("Email cannot be empty");
            return false;
        } else {
            login_email.setError(null);
            return true;
        }
    }

    public Boolean ValidPassword(){
        String password = login_password.getText().toString();
        if (password.isEmpty()){
            login_password.setError("Password cannot be empty");
            return false;
        } else {
            login_email.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userEmail = login_email.getText().toString().trim();
        String userPassword = login_password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("email").equalTo(userEmail);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){

                if (snapshot.exists()){
                    login_email.setError(null);
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String passwordfromDB = userSnapshot.child("password").getValue(String.class);

                        if (passwordfromDB != null && passwordfromDB.equals(userPassword)) {
                            login_email.setError(null);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            login_password.setError("Wrong Password");
                            login_password.requestFocus();
                        }
                    }
                } else {
                    login_email.setError("Account does not exist. Please create an account");
                    login_email.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}