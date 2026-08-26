package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MyApplicationsActivity extends AppCompatActivity {

    private LinearLayout applicationsContainer;
    private LinearLayout emptyState;
    private LinearLayout loadingState;
    private LinearLayout errorState;

    private Button btnBrowseJobs;
    private Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_applications);

        // ==============================
        // FIND VIEWS
        // ==============================

        ImageButton btnBack = findViewById(R.id.btnBack);

        applicationsContainer =
                findViewById(R.id.applicationsContainer);

        emptyState =
                findViewById(R.id.emptyState);

        loadingState =
                findViewById(R.id.loadingState);

        errorState =
                findViewById(R.id.errorState);

        btnBrowseJobs =
                findViewById(R.id.btnBrowseJobs);

        btnRetry =
                findViewById(R.id.btnRetry);


        // ==============================
        // BACK BUTTON
        // ==============================

        btnBack.setOnClickListener(v -> {
            finish();
        });


        // ==============================
        // PROFILE
        // ==============================

        LinearLayout navProfile =
                findViewById(R.id.navProfile);

        navProfile.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MyApplicationsActivity.this,
                    CandidateProfileActivity.class
            );

            startActivity(intent);
        });


        // ==============================
        // HOME
        // ==============================

        LinearLayout navHome =
                findViewById(R.id.navHome);

        navHome.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MyApplicationsActivity.this,
                    CandidateDashboardActivity.class
            );

            startActivity(intent);

            finish();
        });


        // ==============================
        // MY APPLICATIONS
        // ==============================

        LinearLayout navApplications =
                findViewById(R.id.navApplications);

        navApplications.setOnClickListener(v -> {
            // Already on My Applications
        });


        // ==============================
        // BROWSE JOBS
        // ==============================

        btnBrowseJobs.setOnClickListener(v -> {

            /*
             * Later connect this to your Search Jobs screen.
             *
             * Example:
             *
             * Intent intent = new Intent(
             *      MyApplicationsActivity.this,
             *      SearchJobsActivity.class
             * );
             *
             * startActivity(intent);
             */

        });


        // ==============================
        // RETRY
        // ==============================

        btnRetry.setOnClickListener(v -> {

            loadApplications();

        });


        // ==============================
        // LOAD APPLICATIONS
        // ==============================

        loadApplications();
    }


    // =====================================================
    // LOAD APPLICATIONS
    // =====================================================

    private void loadApplications() {

        showLoading();

        /*
         * BACKEND INTEGRATION WILL GO HERE.
         *
         * Expected application object:
         *
         * applicationId
         * jobId
         * jobTitle
         * companyName
         * location
         * employmentType
         * appliedAt
         * status
         * resume information
         *
         * Later connect this method to your Spring Boot API.
         *
         * Do NOT put recruiterId or userId into the UI.
         */

        /*
         * For now we don't create fake application data.
         *
         * When API integration is added:
         *
         * API SUCCESS:
         *      displayApplications(applications);
         *
         * API EMPTY:
         *      showEmpty();
         *
         * API ERROR:
         *      showError();
         */

        showEmpty();
    }


    // =====================================================
    // LOADING STATE
    // =====================================================

    private void showLoading() {

        loadingState.setVisibility(View.VISIBLE);

        applicationsContainer.setVisibility(View.GONE);

        emptyState.setVisibility(View.GONE);

        errorState.setVisibility(View.GONE);
    }


    // =====================================================
    // EMPTY STATE
    // =====================================================

    private void showEmpty() {

        loadingState.setVisibility(View.GONE);

        applicationsContainer.setVisibility(View.GONE);

        emptyState.setVisibility(View.VISIBLE);

        errorState.setVisibility(View.GONE);
    }


    // =====================================================
    // ERROR STATE
    // =====================================================

    private void showError() {

        loadingState.setVisibility(View.GONE);

        applicationsContainer.setVisibility(View.GONE);

        emptyState.setVisibility(View.GONE);

        errorState.setVisibility(View.VISIBLE);
    }
}