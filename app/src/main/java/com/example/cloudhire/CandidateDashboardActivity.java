package com.example.cloudhire;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudhire.api.ApiService;
import com.example.cloudhire.api.RetrofitClient;
import com.example.cloudhire.model.JobResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidateDashboardActivity extends AppCompatActivity {

    private Button btnApplyGoogle;
    private Button btnApplyMicrosoft;
    private Button btnApplyAmazon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_candidate_dashboard);

        // =========================
        // FIND APPLY BUTTONS
        // =========================

        btnApplyGoogle = findViewById(R.id.btnApplyGoogle);
        btnApplyMicrosoft = findViewById(R.id.btnApplyMicrosoft);
        btnApplyAmazon = findViewById(R.id.btnApplyAmazon);

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
        // LOAD REAL JOBS
        // =========================

        loadJobs();
    }

    // =========================================================
    // LOAD JOBS FROM SPRING BOOT
    // =========================================================

    private void loadJobs() {

        ApiService apiService =
                RetrofitClient.getApiService(CandidateDashboardActivity.this);

        apiService.getOpenJobs().enqueue(new Callback<List<JobResponse>>() {

            @Override
            public void onResponse(
                    Call<List<JobResponse>> call,
                    Response<List<JobResponse>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<JobResponse> jobs = response.body();

                    if (jobs.isEmpty()) {

                        Toast.makeText(
                                CandidateDashboardActivity.this,
                                "No jobs available",
                                Toast.LENGTH_SHORT
                        ).show();

                        hideJobCard(btnApplyGoogle);
                        hideJobCard(btnApplyMicrosoft);
                        hideJobCard(btnApplyAmazon);

                        return;
                    }

                    // Show maximum 3 jobs in the existing dashboard cards
                    displayJob(
                            jobs.size() > 0 ? jobs.get(0) : null,
                            btnApplyGoogle
                    );

                    displayJob(
                            jobs.size() > 1 ? jobs.get(1) : null,
                            btnApplyMicrosoft
                    );

                    displayJob(
                            jobs.size() > 2 ? jobs.get(2) : null,
                            btnApplyAmazon
                    );

                } else {

                    Toast.makeText(
                            CandidateDashboardActivity.this,
                            "Failed to load jobs",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<List<JobResponse>> call,
                    Throwable t) {

                Toast.makeText(
                        CandidateDashboardActivity.this,
                        "Unable to connect to server",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    // =========================================================
    // DISPLAY ONE JOB
    // =========================================================

    private void displayJob(JobResponse job, Button applyButton) {

        if (job == null) {
            hideJobCard(applyButton);
            return;
        }

        // Get the job card containing the button
        View parentView = (View) applyButton.getParent();

        if (!(parentView instanceof LinearLayout)) {
            return;
        }

        LinearLayout jobCard = (LinearLayout) parentView;

        jobCard.setVisibility(View.VISIBLE);

        // -----------------------------------------------------
        // JOB CARD STRUCTURE
        //
        // 0 = company logo
        // 1 = job information layout
        // 2 = Apply button
        // -----------------------------------------------------

        if (jobCard.getChildCount() < 3) {
            return;
        }

        // =========================
        // COMPANY LOGO
        // =========================

        View logoView = jobCard.getChildAt(0);

        if (logoView instanceof TextView) {

            TextView logo = (TextView) logoView;

            String companyName = job.getCompanyName();

            if (companyName != null && !companyName.isEmpty()) {

                logo.setText(
                        companyName.substring(0, 1).toUpperCase()
                );
            } else {

                logo.setText("J");
            }

            logo.setTextColor(Color.rgb(37, 99, 235));
        }

        // =========================
        // JOB INFORMATION
        // =========================

        View informationView = jobCard.getChildAt(1);

        if (informationView instanceof LinearLayout) {

            LinearLayout informationLayout =
                    (LinearLayout) informationView;

            if (informationLayout.getChildCount() >= 4) {

                // Company name
                View companyView =
                        informationLayout.getChildAt(0);

                if (companyView instanceof TextView) {

                    TextView companyText =
                            (TextView) companyView;

                    companyText.setText(
                            safeText(job.getCompanyName(), "Company")
                    );
                }

                // Job title
                View titleView =
                        informationLayout.getChildAt(1);

                if (titleView instanceof TextView) {

                    TextView titleText =
                            (TextView) titleView;

                    titleText.setText(
                            safeText(job.getTitle(), "Job")
                    );
                }

                // Location + experience
                View locationView =
                        informationLayout.getChildAt(2);

                if (locationView instanceof TextView) {

                    TextView locationText =
                            (TextView) locationView;

                    String location =
                            safeText(job.getLocation(), "Location");

                    String experience =
                            safeText(
                                    job.getExperienceRequired(),
                                    "Experience not specified"
                            );

                    locationText.setText(
                            "📍 " + location + "  •  " + experience
                    );
                }

                // Skills
                View skillsView =
                        informationLayout.getChildAt(3);

                if (skillsView instanceof TextView) {

                    TextView skillsText =
                            (TextView) skillsView;

                    String skills =
                            safeText(
                                    job.getSkills(),
                                    "Skills not specified"
                            );

                    skillsText.setText(skills);
                }
            }
        }

        // =========================
        // APPLY BUTTON
        // =========================

        applyButton.setVisibility(View.VISIBLE);

        applyButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    CandidateDashboardActivity.this,
                    ApplyJobActivity.class
            );

            // Send REAL backend job information
            intent.putExtra(
                    "job_id",
                    job.getId()
            );

            intent.putExtra(
                    "job_title",
                    job.getTitle()
            );

            intent.putExtra(
                    "company_name",
                    job.getCompanyName()
            );

            intent.putExtra(
                    "location",
                    job.getLocation()
            );

            intent.putExtra(
                    "description",
                    job.getDescription()
            );

            intent.putExtra(
                    "employment_type",
                    job.getEmploymentType()
            );

            intent.putExtra(
                    "experience_required",
                    job.getExperienceRequired()
            );

            intent.putExtra(
                    "skills",
                    job.getSkills()
            );

            startActivity(intent);
        });
    }

    // =========================================================
    // HIDE UNUSED JOB CARD
    // =========================================================

    private void hideJobCard(Button applyButton) {

        if (applyButton == null) {
            return;
        }

        View parentView = (View) applyButton.getParent();

        if (parentView != null) {
            parentView.setVisibility(View.GONE);
        }
    }

    // =========================================================
    // SAFE TEXT
    // =========================================================

    private String safeText(String value, String fallback) {

        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }

        return value;
    }
}