package com.example.groceries.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceries.R;
import com.example.groceries.adapter.MyAdapter;
import com.example.groceries.fragments.ActivityFragment;
import com.example.groceries.fragments.HomeFragment;
import com.example.groceries.fragments.MessageFragment;
import com.example.groceries.fragments.ProfileFragment;
import com.example.groceries.helper.FirebaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment = new HomeFragment();
    private ActivityFragment activityFragment = new ActivityFragment();
    private MessageFragment messageFragment = new MessageFragment();
    private ProfileFragment profileFragment = new ProfileFragment();


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

        bottomNavigationView = findViewById(R.id.main_bottomNavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, homeFragment).commit();
                    return true;
                } else if (itemId == R.id.activities) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, activityFragment).commit();
                    return true;
                } else if (itemId == R.id.messages) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, messageFragment).commit();
                    return true;
                } else if (itemId == R.id.profile) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, profileFragment).commit();
                    return true;
                }
                return false;
            }
        });

    }



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