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

        // =========================
        // MY PROFILE
        // =========================
        LinearLayout navProfile = findViewById(R.id.navProfile);

        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                Intent intent = new Intent(
                        CandidateDashboardActivity.this,
                        CandidateProfileActivity.class
                );

                startActivity(intent);
            });
        }

        // =========================
        // MY APPLICATIONS
        // =========================
        LinearLayout navApplications = findViewById(R.id.navApplications);

        if (navApplications != null) {
            navApplications.setOnClickListener(v -> {
                Intent intent = new Intent(
                        CandidateDashboardActivity.this,
                        MyApplicationsActivity.class
                );

                startActivity(intent);
            });
        }

        // =========================
        // SAVED JOBS
        // =========================
        LinearLayout navSaved = findViewById(R.id.navSaved);

        if (navSaved != null) {
            navSaved.setOnClickListener(v -> {
                Intent intent = new Intent(
                        CandidateDashboardActivity.this,
                        SavedJobsActivity.class
                );

                startActivity(intent);
            });
        }

        // =========================
        // APPLY NOW BUTTONS
        // =========================
        findViewById(R.id.btnApplyGoogle).setOnClickListener(v -> {
            Intent intent = new Intent(this, ApplyJobActivity.class);
            intent.putExtra("job_title", "Software Engineer");
            intent.putExtra("company_name", "Google");
            intent.putExtra("location", "Bangalore, India");
            startActivity(intent);
        });

        findViewById(R.id.btnApplyMicrosoft).setOnClickListener(v -> {
            Intent intent = new Intent(this, ApplyJobActivity.class);
            intent.putExtra("job_title", "Backend Developer");
            intent.putExtra("company_name", "Microsoft");
            intent.putExtra("location", "Hyderabad, India");
            startActivity(intent);
        });

        findViewById(R.id.btnApplyAmazon).setOnClickListener(v -> {
            Intent intent = new Intent(this, ApplyJobActivity.class);
            intent.putExtra("job_title", "SDE - II");
            intent.putExtra("company_name", "Amazon");
            intent.putExtra("location", "Bangalore, India");
            startActivity(intent);
        });
    }
}