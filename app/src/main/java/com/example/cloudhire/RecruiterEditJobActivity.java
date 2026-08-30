package com.example.cloudhire;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecruiterEditJobActivity extends AppCompatActivity {

    private ImageButton btnBack;

    private EditText edtJobTitle;
    private EditText edtCompanyName;
    private EditText edtLocation;
    private EditText edtExperience;
    private EditText edtSalaryMin;
    private EditText edtSalaryMax;
    private EditText edtSkills;
    private EditText edtJobDescription;

    private AutoCompleteTextView actEmploymentType;

    private TextView btnSaveChanges;

    private ProgressBar progressSaveJob;


    // Selected job ID

    private String jobId;


    // Original job data

    private String originalStatus;
    private String originalApplicants;
    private String originalPostedDate;


    private boolean saving = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_recruiter_edit_job
        );

        initializeViews();

        setupEmploymentTypeSpinner();

        receiveJobData();

        setupClicks();
    }


    // =========================================================
    // INITIALIZE
    // =========================================================

    private void initializeViews() {

        btnBack =
                findViewById(
                        R.id.btnEditJobBack
                );

        edtJobTitle =
                findViewById(
                        R.id.edtJobTitle
                );

        edtCompanyName =
                findViewById(
                        R.id.edtCompanyName
                );

        edtLocation =
                findViewById(
                        R.id.edtLocation
                );

        actEmploymentType =
                findViewById(
                        R.id.actEmploymentTypeEdit
                );

        edtExperience =
                findViewById(
                        R.id.edtExperience
                );

        edtSalaryMin =
                findViewById(
                        R.id.edtSalaryMin
                );

        edtSalaryMax =
                findViewById(
                        R.id.edtSalaryMax
                );

        edtSkills =
                findViewById(
                        R.id.edtSkills
                );

        edtJobDescription =
                findViewById(
                        R.id.edtJobDescription
                );

        btnSaveChanges =
                findViewById(
                        R.id.btnSaveChanges
                );

        progressSaveJob =
                findViewById(
                        R.id.progressSaveJob
                );
    }


    // =========================================================
    // EMPLOYMENT TYPE
    // =========================================================

    private void setupEmploymentTypeSpinner() {

        String[] employmentTypes = {

                "Full Time",
                "Part Time",
                "Contract",
                "Internship",
                "Freelance",
                "Temporary"

        };


        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        employmentTypes
                );

        actEmploymentType.setAdapter(
                adapter
        );

        actEmploymentType.setOnClickListener(v -> actEmploymentType.showDropDown());
    }


    // =========================================================
    // RECEIVE DATA
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


        String title =
                intent.getStringExtra(
                        "title"
                );

        String company =
                intent.getStringExtra(
                        "company"
                );

        String location =
                intent.getStringExtra(
                        "location"
                );

        String employmentType =
                intent.getStringExtra(
                        "employmentType"
                );

        String experience =
                intent.getStringExtra(
                        "experience"
                );

        String salary =
                intent.getStringExtra(
                        "salary"
                );

        String skills =
                intent.getStringExtra(
                        "skills"
                );

        String description =
                intent.getStringExtra(
                        "description"
                );


        originalStatus =
                intent.getStringExtra(
                        "status"
                );

        originalApplicants =
                intent.getStringExtra(
                        "applicants"
                );

        originalPostedDate =
                intent.getStringExtra(
                        "postedDate"
                );


        // =====================================================
        // PREFILL
        // =====================================================

        edtJobTitle.setText(
                safe(title)
        );

        edtCompanyName.setText(
                safe(company)
        );

        edtLocation.setText(
                safe(location)
        );

        edtExperience.setText(
                safe(experience)
        );

        edtSkills.setText(
                safe(skills)
        );

        edtJobDescription.setText(
                safe(description)
        );


        // =====================================================
        // SALARY
        // =====================================================

        setSalaryFields(
                salary
        );


        // =====================================================
        // EMPLOYMENT TYPE
        // =====================================================

        selectEmploymentType(
                employmentType
        );
    }


    // =========================================================
    // SALARY
    // =========================================================

    private void setSalaryFields(
            String salary
    ) {

        if (salary == null) {
            return;
        }

        String value =
                salary
                        .replace(
                                "₹",
                                ""
                        )
                        .replace(
                                "LPA",
                                ""
                        )
                        .trim();


        String[] parts =
                value.split(
                        "-"
                );


        if (parts.length >= 2) {

            edtSalaryMin.setText(
                    parts[0].trim()
            );

            edtSalaryMax.setText(
                    parts[1].trim()
            );

        } else {

            edtSalaryMin.setText(
                    value
            );
        }
    }


    // =========================================================
    // SELECT EMPLOYMENT TYPE
    // =========================================================

    private void selectEmploymentType(
            String selected
    ) {

        if (selected == null) {
            return;
        }

        actEmploymentType.setText(selected, false);
    }


    // =========================================================
    // CLICKS
    // =========================================================

    private void setupClicks() {

        btnBack.setOnClickListener(
                v -> finish()
        );


        btnSaveChanges.setOnClickListener(
                v -> saveChanges()
        );
    }


    // =========================================================
    // VALIDATION
    // =========================================================

    private boolean validateForm() {

        if (edtJobTitle
                .getText()
                .toString()
                .trim()
                .isEmpty()) {

            edtJobTitle.setError(
                    "Job title is required"
            );

            edtJobTitle.requestFocus();

            return false;
        }


        if (edtCompanyName
                .getText()
                .toString()
                .trim()
                .isEmpty()) {

            edtCompanyName.setError(
                    "Company name is required"
            );

            edtCompanyName.requestFocus();

            return false;
        }


        if (edtLocation
                .getText()
                .toString()
                .trim()
                .isEmpty()) {

            edtLocation.setError(
                    "Location is required"
            );

            edtLocation.requestFocus();

            return false;
        }


        if (actEmploymentType
                .getText()
                .toString()
                .trim()
                .isEmpty()) {

            Toast.makeText(
                    this,
                    "Select employment type",
                    Toast.LENGTH_SHORT
            ).show();

            return false;
        }


        if (edtExperience
                .getText()
                .toString()
                .trim()
                .isEmpty()) {

            edtExperience.setError(
                    "Experience is required"
            );

            edtExperience.requestFocus();

            return false;
        }


        if (edtSalaryMin
                .getText()
                .toString()
                .trim()
                .isEmpty()) {

            edtSalaryMin.setError(
                    "Minimum salary is required"
            );

            edtSalaryMin.requestFocus();

            return false;
        }


        if (edtSalaryMax
                .getText()
                .toString()
                .trim()
                .isEmpty()) {

            edtSalaryMax.setError(
                    "Maximum salary is required"
            );

            edtSalaryMax.requestFocus();

            return false;
        }


        if (edtSkills
                .getText()
                .toString()
                .trim()
                .isEmpty()) {

            edtSkills.setError(
                    "Skills are required"
            );

            edtSkills.requestFocus();

            return false;
        }


        if (edtJobDescription
                .getText()
                .toString()
                .trim()
                .isEmpty()) {

            edtJobDescription.setError(
                    "Job description is required"
            );

            edtJobDescription.requestFocus();

            return false;
        }


        return true;
    }


    // =========================================================
    // SAVE
    // =========================================================

    private void saveChanges() {

        if (saving) {
            return;
        }

        if (!validateForm()) {
            return;
        }

        saving = true;

        btnSaveChanges.setEnabled(
                false
        );

        btnSaveChanges.setAlpha(
                0.6f
        );

        btnSaveChanges.setText(
                "Saving..."
        );

        progressSaveJob.setVisibility(
                View.VISIBLE
        );


        /*
         * =====================================================
         * FRONTEND ONLY
         * =====================================================
         *
         * DATABASE / BACKEND WILL BE CONNECTED LATER.
         *
         * At this stage we create the updated job data and
         * return it to My Jobs.
         *
         * Later this method can send:
         *
         * PUT /api/jobs/{id}
         *
         * JSON:
         *
         * title
         * description
         * companyName
         * location
         * employmentType
         * experienceRequired
         * salaryMin
         * salaryMax
         * skills
         *
         * Do NOT send:
         *
         * id
         * recruiterId
         * status
         * createdAt
         * updatedAt
         *
         * =====================================================
         */


        String title =
                edtJobTitle
                        .getText()
                        .toString()
                        .trim();

        String company =
                edtCompanyName
                        .getText()
                        .toString()
                        .trim();

        String location =
                edtLocation
                        .getText()
                        .toString()
                        .trim();

        String employmentType =
                actEmploymentType
                        .getText()
                        .toString()
                        .trim();

        String experience =
                edtExperience
                        .getText()
                        .toString()
                        .trim();

        String salaryMin =
                edtSalaryMin
                        .getText()
                        .toString()
                        .trim();

        String salaryMax =
                edtSalaryMax
                        .getText()
                        .toString()
                        .trim();

        String skills =
                edtSkills
                        .getText()
                        .toString()
                        .trim();

        String description =
                edtJobDescription
                        .getText()
                        .toString()
                        .trim();


        String salary =
                "₹" +
                        salaryMin +
                        " - ₹" +
                        salaryMax +
                        " LPA";


        Intent result =
                new Intent();

        result.putExtra(
                "jobId",
                jobId
        );

        result.putExtra(
                "title",
                title
        );

        result.putExtra(
                "company",
                company
        );

        result.putExtra(
                "location",
                location
        );

        result.putExtra(
                "employmentType",
                employmentType
        );

        result.putExtra(
                "experience",
                experience
        );

        result.putExtra(
                "salary",
                salary
        );

        result.putExtra(
                "salaryMin",
                salaryMin
        );

        result.putExtra(
                "salaryMax",
                salaryMax
        );

        result.putExtra(
                "skills",
                skills
        );

        result.putExtra(
                "description",
                description
        );

        // Keep backend-controlled fields unchanged

        result.putExtra(
                "status",
                originalStatus
        );

        result.putExtra(
                "applicants",
                originalApplicants
        );

        result.putExtra(
                "postedDate",
                originalPostedDate
        );


        setResult(
                RESULT_OK,
                result
        );


        Toast.makeText(
                this,
                "Job updated successfully",
                Toast.LENGTH_SHORT
        ).show();


        finish();
    }


    // =========================================================
    // SAFE STRING
    // =========================================================

    private String safe(
            String value
    ) {

        if (value == null) {
            return "";
        }

        return value;
    }
}