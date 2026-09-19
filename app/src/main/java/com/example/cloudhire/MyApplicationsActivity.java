package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudhire.api.ApiService;
import com.example.cloudhire.api.RetrofitClient;
import com.example.cloudhire.model.ApplicationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        ImageButton btnBack =
                findViewById(R.id.btnBack);

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
        // BACK
        // ==============================

        btnBack.setOnClickListener(v -> finish());


        // ==============================
        // PROFILE
        // ==============================

        LinearLayout navProfile =
                findViewById(R.id.navProfile);

        if (navProfile != null) {

            navProfile.setOnClickListener(v -> {

                Intent intent =
                        new Intent(
                                MyApplicationsActivity.this,
                                CandidateProfileActivity.class
                        );

                startActivity(intent);
            });
        }


        // ==============================
        // HOME
        // ==============================

        LinearLayout navHome =
                findViewById(R.id.navHome);

        if (navHome != null) {

            navHome.setOnClickListener(v -> {

                Intent intent =
                        new Intent(
                                MyApplicationsActivity.this,
                                CandidateDashboardActivity.class
                        );

                startActivity(intent);

                finish();
            });
        }


        // ==============================
        // APPLICATIONS
        // ==============================

        LinearLayout navApplications =
                findViewById(R.id.navApplications);

        if (navApplications != null) {

            navApplications.setOnClickListener(v -> {
                // Already here
            });
        }


        // ==============================
        // BROWSE JOBS
        // ==============================

        btnBrowseJobs.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MyApplicationsActivity.this,
                            CandidateDashboardActivity.class
                    );

            startActivity(intent);

            finish();
        });


        // ==============================
        // RETRY
        // ==============================

        btnRetry.setOnClickListener(v ->
                loadApplications()
        );


        // ==============================
        // LOAD
        // ==============================

        loadApplications();
    }


    // =====================================================
    // LOAD APPLICATIONS
    // =====================================================

    private void loadApplications() {

        showLoading();

        ApiService apiService =
                RetrofitClient.getApiService(this);


        apiService.getMyApplications()
                .enqueue(new Callback<List<ApplicationResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<ApplicationResponse>> call,
                            Response<List<ApplicationResponse>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            List<ApplicationResponse> applications =
                                    response.body();


                            if (applications.isEmpty()) {

                                showEmpty();

                            } else {

                                displayApplications(
                                        applications
                                );
                            }

                        } else {

                            showError();
                        }
                    }


                    @Override
                    public void onFailure(
                            Call<List<ApplicationResponse>> call,
                            Throwable t) {

                        showError();
                    }
                });
    }


    // =====================================================
    // DISPLAY APPLICATIONS
    // =====================================================

    private void displayApplications(
            List<ApplicationResponse> applications) {

        applicationsContainer.removeAllViews();

        loadingState.setVisibility(View.GONE);

        emptyState.setVisibility(View.GONE);

        errorState.setVisibility(View.GONE);

        applicationsContainer.setVisibility(View.VISIBLE);


        for (ApplicationResponse application : applications) {

            LinearLayout card =
                    new LinearLayout(this);

            card.setOrientation(
                    LinearLayout.VERTICAL
            );

            card.setPadding(
                    24,
                    20,
                    24,
                    20
            );


            // =========================
            // JOB TITLE
            // =========================

            TextView jobTitle =
                    new TextView(this);

            jobTitle.setText(
                    safeText(
                            application.getJobTitle(),
                            "Job"
                    )
            );

            jobTitle.setTextSize(18);

            jobTitle.setTextColor(
                    0xFF111827
            );

            jobTitle.setTypeface(
                    null,
                    android.graphics.Typeface.BOLD
            );


            // =========================
            // COMPANY
            // =========================

            TextView company =
                    new TextView(this);

            company.setText(
                    safeText(
                            application.getCompanyName(),
                            "Company"
                    )
            );

            company.setTextSize(15);

            company.setTextColor(
                    0xFF2563EB
            );


            // =========================
            // LOCATION
            // =========================

            TextView location =
                    new TextView(this);

            location.setText(
                    "📍 " +
                            safeText(
                                    application.getLocation(),
                                    "Location"
                            )
            );

            location.setTextSize(13);

            location.setTextColor(
                    0xFF64748B
            );


            // =========================
            // STATUS
            // =========================

            TextView status =
                    new TextView(this);

            status.setText(
                    "Status: " +
                            safeText(
                                    application.getStatus(),
                                    "APPLIED"
                            )
            );

            status.setTextSize(14);

            status.setTextColor(
                    0xFF334155
            );


            // =========================
            // APPLIED DATE
            // =========================

            TextView appliedAt =
                    new TextView(this);

            appliedAt.setText(
                    "Applied: " +
                            safeText(
                                    application.getAppliedAt(),
                                    "-"
                            )
            );

            appliedAt.setTextSize(12);

            appliedAt.setTextColor(
                    0xFF64748B
            );


            // =========================
            // OPEN DETAILS
            // =========================

            card.setClickable(true);

            card.setOnClickListener(v -> {

                Intent intent =
                        new Intent(
                                MyApplicationsActivity.this,
                                ApplicationDetailsActivity.class
                        );

                intent.putExtra(
                        "applicationId",
                        application.getId()
                );

                startActivity(intent);
            });


            // =========================
            // ADD VIEWS
            // =========================

            card.addView(jobTitle);

            card.addView(company);

            card.addView(location);

            card.addView(status);

            card.addView(appliedAt);


            // =========================
            // DIVIDER
            // =========================

            View divider =
                    new View(this);

            divider.setBackgroundColor(
                    0xFFE5E7EB
            );


            LinearLayout.LayoutParams dividerParams =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1
                    );

            dividerParams.setMargins(
                    0,
                    15,
                    0,
                    15
            );


            applicationsContainer.addView(card);

            applicationsContainer.addView(
                    divider,
                    dividerParams
            );
        }
    }


    // =====================================================
    // LOADING
    // =====================================================

    private void showLoading() {

        loadingState.setVisibility(View.VISIBLE);

        applicationsContainer.setVisibility(View.GONE);

        emptyState.setVisibility(View.GONE);

        errorState.setVisibility(View.GONE);
    }


    // =====================================================
    // EMPTY
    // =====================================================

    private void showEmpty() {

        loadingState.setVisibility(View.GONE);

        applicationsContainer.setVisibility(View.GONE);

        emptyState.setVisibility(View.VISIBLE);

        errorState.setVisibility(View.GONE);
    }


    // =====================================================
    // ERROR
    // =====================================================

    private void showError() {

        loadingState.setVisibility(View.GONE);

        applicationsContainer.setVisibility(View.GONE);

        emptyState.setVisibility(View.GONE);

        errorState.setVisibility(View.VISIBLE);
    }


    // =====================================================
    // SAFE TEXT
    // =====================================================

    private String safeText(
            String value,
            String fallback) {

        if (value == null ||
                value.trim().isEmpty()) {

            return fallback;
        }

        return value;
    }
}