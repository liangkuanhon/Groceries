package com.example.groceries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groceries.R;
import com.example.groceries.helper.FirebaseHelper;
import com.google.android.material.button.MaterialButton;

public class NameActivity extends AppCompatActivity {

    EditText name;
    TextView heading, subtitle;
    MaterialButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_name);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.name);
        heading = findViewById(R.id.heading);
        subtitle = findViewById(R.id.subtitle);
        nextButton = findViewById(R.id.next_button);

        // Get uid from intent
        String uid = getIntent().getStringExtra("UID");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String setname = name.getText().toString();

                // Check Empty Email
                if (TextUtils.isEmpty(setname)) {
                    name.setError("Name is required");
                    name.requestFocus();
                } else {
                    FirebaseHelper.updateName(uid, setname);
                    Intent intent = new Intent(NameActivity.this, MainActivity.class);
                    intent.putExtra("UID", uid);
                    startActivity(intent);
                }
            }
        });
    }
}