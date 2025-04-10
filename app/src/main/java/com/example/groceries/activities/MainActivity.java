package com.example.groceries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groceries.R;
import com.example.groceries.databinding.ActivityMainBinding;
import com.example.groceries.fragments.ActivityFragment;
import com.example.groceries.fragments.AllGroupFragment;
import com.example.groceries.fragments.HomeFragment;
import com.example.groceries.fragments.ProfileFragment;
import com.example.groceries.fragments.SingleGroupFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private HomeFragment homeFragment = new HomeFragment();
    private ActivityFragment activityFragment = new ActivityFragment();
    private AllGroupFragment groupsFragment = new AllGroupFragment();
    private ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
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
                } else if (itemId == R.id.group) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, groupsFragment).commit();
                    return true;
                } else if (itemId == R.id.add) {
                    Intent intent = new Intent(MainActivity.this, CreateGroupActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.activities) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, activityFragment).commit();
                    return true;
                } else if (itemId == R.id.profile) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, profileFragment).commit();
                    return true;
                }
                return false;
            }
        });

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null && intent.hasExtra("openFragment")) {
            String fragmentToOpen = intent.getStringExtra("openFragment");

            if ("singleGroup".equals(fragmentToOpen)) {
                String groupId = intent.getStringExtra("groupId");
                String groupName = intent.getStringExtra("groupName");

                // Open SingleGroupFragment
                SingleGroupFragment fragment = SingleGroupFragment.newInstance(groupId, groupName);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }
}