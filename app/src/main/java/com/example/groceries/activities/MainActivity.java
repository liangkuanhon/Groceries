package com.example.groceries.activities;

import com.example.groceries.BFSRouter;
import com.example.groceries.SupermarketFactory;
import com.example.groceries.SupermarketGraph;
import java.util.*;
import android.util.Log;

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
import com.example.groceries.fragments.AllGroupFragment;
import com.example.groceries.fragments.ProfileFragment;
import com.example.groceries.fragments.SingleGroupFragment;
import com.example.groceries.helper.NavigationHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    private AllGroupFragment groupsFragment = new AllGroupFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private NavigationHelper navigationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialise();
        handleIntent(getIntent());
        setupNavigation();
    }

    private void initialise(){
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, groupsFragment).commit();
        navigationHelper = new NavigationHelper(this);
    }

    private void setupNavigation(){
        // Bottom Navigation setup with 3 items
        BottomNavigationView bottomNavigationView = b.mainBottomNavigation;
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.group) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, groupsFragment).commit();
                return true;
            } else if (itemId == R.id.add) {
                Intent intent = new Intent(MainActivity.this, CreateGroupActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.profile) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, profileFragment).commit();
                return true;
            }
            return false;
        });
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

                navigationHelper.navigateToFragment(fragment);
            }
        }
    }
}