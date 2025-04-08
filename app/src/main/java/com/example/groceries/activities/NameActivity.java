package com.example.groceries.activities;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groceries.R;
import com.example.groceries.databinding.ActivityNameBinding;
import com.example.groceries.helper.FirebaseHelper;
import com.example.groceries.helper.NavigationHelper;

public class NameActivity extends AppCompatActivity {

    private String uid;
    private ActivityNameBinding b;
    private NavigationHelper navigationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialise();
        setupEdgeToEdge();
        setupClickListeners();
    }

    private void initialise(){
        uid = getIntent().getStringExtra("UID");
        b = ActivityNameBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        navigationHelper = new NavigationHelper(this);
    }

    private void setupEdgeToEdge(){
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupClickListeners() {
        b.nextButton.setOnClickListener(view -> handleName());
    }

    private void handleName() {
        String name = b.name.getText().toString();

        // Check Empty Email
        if (TextUtils.isEmpty(name)) {
            b.name.setError("Name is required");
            b.name.requestFocus();
        } else {
            FirebaseHelper.updateName(uid, name);
            navigateToMainActivity();
        }
    }

    private void navigateToMainActivity(){
        navigationHelper.navigateToActivityClearStack(MainActivity.class);
    }

}