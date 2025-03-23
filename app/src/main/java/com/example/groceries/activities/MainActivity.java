package com.example.groceries.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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
import com.example.groceries.databinding.ActivityMainBinding;
import com.example.groceries.fragments.ActivityFragment;
import com.example.groceries.fragments.AddFragment;
import com.example.groceries.fragments.HomeFragment;
import com.example.groceries.fragments.MessageFragment;
import com.example.groceries.fragments.ProfileFragment;
import com.example.groceries.helper.FirebaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private HomeFragment homeFragment = new HomeFragment();
    private ActivityFragment activityFragment = new ActivityFragment();
    private AddFragment addFragment = new AddFragment();
    private MessageFragment messageFragment = new MessageFragment();
    private ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Use binding to reference views
        BottomNavigationView bottomNavigationView = binding.mainBottomNavigation;
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
                } else if (itemId == R.id.add) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, addFragment).commit();
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
}