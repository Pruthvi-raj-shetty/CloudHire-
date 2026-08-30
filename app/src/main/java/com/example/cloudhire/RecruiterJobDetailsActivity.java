package com.example.cloudhire;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;

public class RecruiterJobDetailsActivity extends AppCompatActivity {

    private ImageButton btnBack;

    private TextView txtJobTitle;
    private TextView txtCompanyName;

    private TextView txtJobLocation;
    private TextView txtEmploymentType;
    private TextView txtExperience;
    private TextView txtSalary;
    private TextView txtApplicants;
    private TextView txtPostedDate;
    private TextView txtJobStatus;

    private TextView txtJobDescription;
    private TextView txtSkills;

    private MaterialButton btnEditJob;
    private TextView btnCloseReopenJob;

    // Selected job data
    private String jobId;
    private String title;
    private String company;
    private String location;
    private String employmentType;
    private String experience;
    private String salary;
    private String applicants;
    private String postedDate;
    private String description;
    private String skills;
    private String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_recruiter_job_details
        );

        initializeViews();

        receiveJobData();

        displayJob();

        setupClicks();
    }


    // =========================================================
    // INITIALIZE
    // =========================================================

    private void initializeViews() {

        btnBack =
                findViewById(
                        R.id.btnJobDetailsBack
                );

        txtJobTitle =
                findViewById(
                        R.id.txtJobTitle
                );

        txtCompanyName =
                findViewById(
                        R.id.txtCompanyName
                );

        txtJobLocation =
                findViewById(
                        R.id.txtJobLocation
                );

        txtEmploymentType =
                findViewById(
                        R.id.txtEmploymentType
                );

        txtExperience =
                findViewById(
                        R.id.txtExperience
                );

        txtSalary =
                findViewById(
                        R.id.txtSalary
                );

        txtApplicants =
                findViewById(
                        R.id.txtApplicants
                );

        txtPostedDate =
                findViewById(
                        R.id.txtPostedDate
                );

        txtJobStatus =
                findViewById(
                        R.id.txtJobStatus
                );

        txtJobDescription =
                findViewById(
                        R.id.txtJobDescription
                );

        txtSkills =
                findViewById(
                        R.id.txtSkills
                );

        btnEditJob =
                findViewById(
                        R.id.btnEditJob
                );

        btnCloseReopenJob =
                findViewById(
                        R.id.btnCloseReopenJob
                );
    }


    // =========================================================
    // RECEIVE SELECTED JOB
    // =========================================================

    private void receiveJobData() {

        Intent intent =
                getIntent();

        if (intent == null) {
            return;
        }

        jobId =
                intent.getStringExtra(
                        "jobId"
                );

        title =
                intent.getStringExtra(
                        "title"
                );

        company =
                intent.getStringExtra(
                        "company"
                );

        location =
                intent.getStringExtra(
                        "location"
                );

        employmentType =
                intent.getStringExtra(
                        "employmentType"
                );

        experience =
                intent.getStringExtra(
                        "experience"
                );

        salary =
                intent.getStringExtra(
                        "salary"
                );

        applicants =
                intent.getStringExtra(
                        "applicants"
                );

        postedDate =
                intent.getStringExtra(
                        "postedDate"
                );

        description =
                intent.getStringExtra(
                        "description"
                );

        skills =
                intent.getStringExtra(
                        "skills"
                );

        status =
                intent.getStringExtra(
                        "status"
                );


        // Safe frontend defaults.
        // These are used only if the existing My Jobs card
        // has not yet been updated to pass the new fields.

        if (jobId == null) {
            jobId = "";
        }

        if (title == null) {
            title = "";
        }

        if (company == null) {
            company = "";
        }

        if (location == null) {
            location = "";
        }

        if (employmentType == null) {
            employmentType = "";
        }

        if (experience == null) {
            experience = "";
        }

        if (salary == null) {
            salary = "";
        }

        if (applicants == null) {
            applicants = "";
        }

        if (postedDate == null) {
            postedDate = "";
        }

        if (description == null) {
            description = "";
        }

        if (skills == null) {
            skills = "";
        }

        if (status == null ||
                status.trim().isEmpty()) {

            status = "OPEN";
        }
    }


    // =========================================================
    // DISPLAY
    // =========================================================

    private void displayJob() {

        txtJobTitle.setText(
                title
        );

        txtCompanyName.setText(
                company
        );

        txtJobLocation.setText(
                "📍  Location: " +
                        location
        );

        txtEmploymentType.setText(
                "💼  Employment Type: " +
                        employmentType
        );

        txtExperience.setText(
                "Experience: " +
                        experience
        );

        txtSalary.setText(
                "Salary: " +
                        salary
        );

        txtApplicants.setText(
                "Applicants: " +
                        applicants
        );

        txtPostedDate.setText(
                "Posted: " +
                        postedDate
        );

        txtJobDescription.setText(
                description
        );

        txtSkills.setText(
                formatSkills(skills)
        );

        status =
                status.toUpperCase();

        updateStatusUI();
    }


    // =========================================================
    // SKILLS
    // =========================================================

    private String formatSkills(
            String value
    ) {

        if (value == null ||
                value.trim().isEmpty()) {

            return "";
        }

        String[] items =
                value.split(",");

        StringBuilder result =
                new StringBuilder();

        for (String item : items) {

            String skill =
                    item.trim();

            if (skill.isEmpty()) {
                continue;
            }

            if (result.length() > 0) {

                result.append(
                        " • "
                );
            }

            result.append(
                    skill
            );
        }

        return result.toString();
    }


    // =========================================================
    // STATUS
    // =========================================================

    private void updateStatusUI() {

        txtJobStatus.setText(
                status
        );

        GradientDrawable background =
                new GradientDrawable();

        background.setCornerRadius(
                dp(20)
        );


        if ("CLOSED".equals(status)) {

            background.setColor(
                    Color.parseColor(
                            "#FEE2E2"
                    )
            );

            txtJobStatus.setTextColor(
                    Color.parseColor(
                            "#B91C1C"
                    )
            );

            btnCloseReopenJob.setText(
                    "Reopen Job"
            );

        } else {

            background.setColor(
                    Color.parseColor(
                            "#DCFCE7"
                    )
            );

            txtJobStatus.setTextColor(
                    Color.parseColor(
                            "#15803D"
                    )
            );

            btnCloseReopenJob.setText(
                    "Close Job"
            );
        }

        txtJobStatus.setBackground(
                background
        );
    }


    // =========================================================
    // CLICKS
    // =========================================================

    private void setupClicks() {

        // Back

        btnBack.setOnClickListener(
                v -> finish()
        );


        // Edit Job

        btnEditJob.setOnClickListener(
                v -> openEditJob()
        );


        // Close/Reopen

        btnCloseReopenJob.setOnClickListener(
                v -> showStatusConfirmation()
        );


        // Notification

        findViewById(
                R.id.btnJobDetailsNotifications
        ).setOnClickListener(
                v -> Toast.makeText(
                        this,
                        "Notifications",
                        Toast.LENGTH_SHORT
                ).show()
        );


        // Home

        findViewById(
                R.id.navHome
        ).setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    this,
                                    RecruiterDashboardActivity.class
                            );

                    startActivity(
                            intent
                    );

                    finish();
                }
        );


        // My Jobs

        findViewById(
                R.id.navJobs
        ).setOnClickListener(
                v -> finish()
        );


        // Applicants

        findViewById(
                R.id.navApplicants
        ).setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    this,
                                    RecruiterApplicantsActivity.class
                            );

                    startActivity(
                            intent
                    );

                    finish();
                }
        );


        // Profile

        findViewById(
                R.id.navProfile
        ).setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    this,
                                    RecruiterProfileActivity.class
                            );

                    startActivity(
                            intent
                    );

                    finish();
                }
        );
    }


    // =========================================================
    // OPEN EDIT JOB
    // =========================================================

    private void openEditJob() {

        Intent intent =
                new Intent(
                        this,
                        RecruiterEditJobActivity.class
                );

        intent.putExtra(
                "jobId",
                jobId
        );

        intent.putExtra(
                "title",
                title
        );

        intent.putExtra(
                "company",
                company
        );

        intent.putExtra(
                "location",
                location
        );

        intent.putExtra(
                "employmentType",
                employmentType
        );

        intent.putExtra(
                "experience",
                experience
        );

        intent.putExtra(
                "salary",
                salary
        );

        intent.putExtra(
                "applicants",
                applicants
        );

        intent.putExtra(
                "postedDate",
                postedDate
        );

        intent.putExtra(
                "description",
                description
        );

        intent.putExtra(
                "skills",
                skills
        );

        intent.putExtra(
                "status",
                status
        );

        startActivity(
                intent
        );
    }


    // =========================================================
    // CONFIRM CLOSE / REOPEN
    // =========================================================

    private void showStatusConfirmation() {

        boolean isOpen =
                "OPEN".equals(status);

        String title =
                isOpen
                        ? "Close Job"
                        : "Reopen Job";

        String message =
                isOpen
                        ? "Are you sure you want to close this job?"
                        : "Are you sure you want to reopen this job?";


        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(
                        "Cancel",
                        null
                )
                .setPositiveButton(
                        "Confirm",
                        (dialog, which) -> {

                            if (isOpen) {

                                status =
                                        "CLOSED";

                                Toast.makeText(
                                        this,
                                        "Job closed successfully",
                                        Toast.LENGTH_SHORT
                                ).show();

                            } else {

                                status =
                                        "OPEN";

                                Toast.makeText(
                                        this,
                                        "Job reopened successfully",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                            updateStatusUI();
                        }
                )
                .show();
    }


    // =========================================================
    // DP
    // =========================================================

    private int dp(
            int value
    ) {

        return (int) (
                value *
                        getResources()
                                .getDisplayMetrics()
                                .density
                        + 0.5f
        );
    }
}