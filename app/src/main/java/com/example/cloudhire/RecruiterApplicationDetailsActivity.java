package com.example.cloudhire;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecruiterApplicationDetailsActivity
        extends AppCompatActivity {

    private AutoCompleteTextView actApplicationStatus;

    private String applicationId;
    private String resumeUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_recruiter_application_details
        );

        initializeViews();
        loadApplicationFromIntent();
        setupStatusDropdown();
        setupClicks();
    }


    // =========================================================
    // INITIALIZE
    // =========================================================

    private void initializeViews() {

        actApplicationStatus =
                findViewById(
                        R.id.actApplicationStatus
                );
    }


    // =========================================================
    // LOAD DATA
    // =========================================================

    private void loadApplicationFromIntent() {

        Intent intent = getIntent();

        applicationId =
                intent.getStringExtra(
                        "applicationId"
                );

        resumeUrl =
                intent.getStringExtra(
                        "resumeUrl"
                );


        ((TextView) findViewById(
                R.id.txtCandidateName
        )).setText(
                valueOrEmpty(
                        intent.getStringExtra(
                                "candidateName"
                        )
                )
        );


        ((TextView) findViewById(
                R.id.txtProfessionalTitle
        )).setText(
                valueOrEmpty(
                        intent.getStringExtra(
                                "professionalTitle"
                        )
                )
        );


        ((TextView) findViewById(
                R.id.txtJobApplied
        )).setText(
                "Job: "
                        + valueOrEmpty(
                        intent.getStringExtra(
                                "jobTitle"
                        )
                )
        );


        ((TextView) findViewById(
                R.id.txtCompany
        )).setText(
                "Company: "
                        + valueOrEmpty(
                        intent.getStringExtra(
                                "companyName"
                        )
                )
        );


        ((TextView) findViewById(
                R.id.txtLocation
        )).setText(
                "Location: "
                        + valueOrEmpty(
                        intent.getStringExtra(
                                "location"
                        )
                )
        );


        ((TextView) findViewById(
                R.id.txtAppliedDate
        )).setText(
                "Applied: "
                        + valueOrEmpty(
                        intent.getStringExtra(
                                "appliedDate"
                        )
                )
        );


        ((TextView) findViewById(
                R.id.txtCandidateEmail
        )).setText(
                "Email: "
                        + valueOrEmpty(
                        intent.getStringExtra(
                                "email"
                        )
                )
        );


        ((TextView) findViewById(
                R.id.txtCandidatePhone
        )).setText(
                "Phone: "
                        + valueOrEmpty(
                        intent.getStringExtra(
                                "phone"
                        )
                )
        );


        String currentStatus =
                valueOrEmpty(
                        intent.getStringExtra(
                                "status"
                        )
                );

        actApplicationStatus.setText(
                currentStatus,
                false
        );
    }


    // =========================================================
    // STATUS DROPDOWN
    // =========================================================

    private void setupStatusDropdown() {

        String[] statuses = {
                "APPLIED",
                "SHORTLISTED",
                "INTERVIEW",
                "REJECTED",
                "HIRED"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        statuses
                );

        actApplicationStatus.setAdapter(
                adapter
        );

        actApplicationStatus.setOnClickListener(
                v ->
                        actApplicationStatus.showDropDown()
        );
    }


    // =========================================================
    // CLICKS
    // =========================================================

    private void setupClicks() {

        findViewById(
                R.id.btnApplicationBack
        ).setOnClickListener(v ->
                finish()
        );


        findViewById(
                R.id.btnViewResume
        ).setOnClickListener(v -> {

            if (resumeUrl == null ||
                    resumeUrl.trim().isEmpty()) {

                Toast.makeText(
                        this,
                        "Resume is not available",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            try {

                Intent browserIntent =
                        new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(resumeUrl)
                        );

                startActivity(
                        browserIntent
                );

            } catch (Exception e) {

                Toast.makeText(
                        this,
                        "Unable to open resume",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });


        findViewById(
                R.id.btnUpdateStatus
        ).setOnClickListener(v -> {

            String newStatus =
                    actApplicationStatus
                            .getText()
                            .toString()
                            .trim();

            if (newStatus.isEmpty()) {

                Toast.makeText(
                        this,
                        "Select a status",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            updateStatus(newStatus);
        });
    }


    // =========================================================
    // UPDATE STATUS
    // =========================================================

    private void updateStatus(
            String newStatus
    ) {

        /*
         * =====================================================
         * BACKEND CONNECTION POINT
         * =====================================================
         *
         * PUT:
         *
         * /api/applications/{applicationId}/status
         *
         * Authorization:
         * Bearer <JWT>
         *
         * Body:
         *
         * {
         *     "status": "SHORTLISTED"
         * }
         *
         * IMPORTANT:
         *
         * Do NOT send recruiterId from the Android UI.
         *
         * The backend should identify the logged-in recruiter
         * from the JWT and verify that this application belongs
         * to one of that recruiter's jobs.
         *
         * On successful API response:
         *
         * Toast...
         * finish()
         *
         * On failure:
         *
         * Show error and keep the screen open.
         *
         * =====================================================
         */


        /*
         * TEMPORARY FRONTEND BEHAVIOUR
         *
         * This keeps the UI functional before the backend is
         * connected.
         */

        Toast.makeText(
                this,
                "Status selected: " + newStatus,
                Toast.LENGTH_SHORT
        ).show();

        finish();
    }


    private String valueOrEmpty(
            String value
    ) {

        if (value == null) {
            return "";
        }

        return value;
    }
}