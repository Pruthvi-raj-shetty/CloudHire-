package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SavedJobsActivity extends AppCompatActivity {

    private LinearLayout savedJobsContainer;
    private LinearLayout emptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_saved_jobs);

        savedJobsContainer = findViewById(R.id.savedJobsContainer);
        emptyState = findViewById(R.id.emptyState);

        // Back / menu
        TextView btnMenu = findViewById(R.id.btnMenu);

        btnMenu.setOnClickListener(v -> {
            finish();
        });

        // Browse Jobs
        Button btnBrowseJobs = findViewById(R.id.btnBrowseJobs);

        btnBrowseJobs.setOnClickListener(v -> {
            Toast.makeText(
                    SavedJobsActivity.this,
                    "Search Jobs screen will open here",
                    Toast.LENGTH_SHORT
            ).show();

            // Later connect this to your Search Jobs Activity.
            // Example:
            // Intent intent = new Intent(SavedJobsActivity.this, SearchJobsActivity.class);
            // startActivity(intent);
        });

        // Bottom navigation
        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navProfile = findViewById(R.id.navProfile);
        LinearLayout navApplications = findViewById(R.id.navApplications);
        LinearLayout navSaved = findViewById(R.id.navSaved);

        navHome.setOnClickListener(v -> {
            finish();
        });

        navProfile.setOnClickListener(v -> {
            Intent intent = new Intent(
                    SavedJobsActivity.this,
                    CandidateProfileActivity.class
            );
            startActivity(intent);
        });

        navApplications.setOnClickListener(v -> {
            Intent intent = new Intent(
                    SavedJobsActivity.this,
                    MyApplicationsActivity.class
            );
            startActivity(intent);
        });

        // Already on Saved Jobs
        navSaved.setOnClickListener(v -> {
            Toast.makeText(
                    SavedJobsActivity.this,
                    "You are already on Saved Jobs",
                    Toast.LENGTH_SHORT
            ).show();
        });

        /*
         * FRONTEND TEST
         *
         * Currently we show the empty state.
         *
         * Later your friend can connect the backend here and
         * populate savedJobsContainer with real saved jobs.
         */
        showEmptyState();
    }

    private void showEmptyState() {

        savedJobsContainer.removeAllViews();

        savedJobsContainer.setVisibility(View.GONE);
        emptyState.setVisibility(View.VISIBLE);
    }

    /*
     * This method is intentionally separated from the UI.
     *
     * Later backend data can be passed here.
     */
    private void showSavedJobs() {

        emptyState.setVisibility(View.GONE);
        savedJobsContainer.setVisibility(View.VISIBLE);

        /*
         * Do NOT hardcode final job data here.
         *
         * Your friend can later call this method after receiving
         * saved-job data from the Spring Boot API.
         */
    }
}