package com.example.groceries;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private TextView main_welcome;
    private Button yourgroups, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        main_welcome = findViewById(R.id.main_welcome);
        yourgroups = findViewById(R.id.yourgroups_button);
        logout = findViewById(R.id.logout_button);

        // Get uid from intent
        String uid = getIntent().getStringExtra("UID");

        // Get current user
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            fetchUserDetails(user.getUid());
//        } else {
//            ;
//        }

        // Navigate to GroupActivity when "Your Groups" button is clicked
        yourgroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Setup logout button click listener
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

//    private void fetchUserDetails(String userId) {
//        FirebaseHelper.checkNameExists()
//    }

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
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
        startActivity(intent);
        finish(); // Close the current activity
    }

    private void clearUserData() {
        // Clear any user-related data from your app (e.g., SharedPreferences, cached data)
        // Example:
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}