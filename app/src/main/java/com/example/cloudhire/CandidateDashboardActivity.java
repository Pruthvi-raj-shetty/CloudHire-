package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class CandidateDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_candidate_dashboard);

        // My Profile
        LinearLayout navProfile = findViewById(R.id.navProfile);

        navProfile.setOnClickListener(v -> {
            Intent intent = new Intent(
                    CandidateDashboardActivity.this,
                    CandidateProfileActivity.class
            );

            startActivity(intent);
        });
    }
}