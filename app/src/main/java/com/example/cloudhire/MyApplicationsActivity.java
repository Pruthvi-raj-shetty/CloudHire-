package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MyApplicationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_applications);

        // Back button
        ImageButton btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            finish();
        });


        // My Profile
        LinearLayout navProfile = findViewById(R.id.navProfile);

        navProfile.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MyApplicationsActivity.this,
                    CandidateProfileActivity.class
            );

            startActivity(intent);
        });
    }
}