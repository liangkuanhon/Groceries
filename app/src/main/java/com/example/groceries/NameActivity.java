package com.example.groceries;

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

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NameActivity extends AppCompatActivity {

    EditText name;
    TextView heading, subtitle;
    MaterialButton nextButton;
    FirebaseDatabase database;
    DatabaseReference reference;

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

        String username = getIntent().getStringExtra("USERNAME");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String setname = name.getText().toString();

                // Check Empty Email
                if (TextUtils.isEmpty(setname)) {
                    name.setError("Name is required");
                    name.requestFocus();
                } else {
                    reference.child(username).child("name").setValue(setname);
                    Intent intent = new Intent(NameActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}