package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layoutCandidate;
    private LinearLayout layoutRecruiter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        layoutCandidate = findViewById(R.id.layoutCandidate);
        layoutRecruiter = findViewById(R.id.layoutRecruiter);

        // Candidate Card Click
        layoutCandidate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CandidateDashboardActivity.class);
            startActivity(intent);
        });

        // Recruiter Card Click
        layoutRecruiter.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecruiterDashboardActivity.class);
            startActivity(intent);
        });
    }
}