package com.example.cloudhire;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class PostJobActivity extends AppCompatActivity {

    private ImageButton btnBack;

    private TextInputEditText etJobTitle;
    private TextInputEditText etJobDescription;
    private TextInputEditText etCompanyName;
    private TextInputEditText etLocation;
    private TextInputEditText etExperience;
    private TextInputEditText etMinSalary;
    private TextInputEditText etMaxSalary;
    private TextInputEditText etSkills;

    private AutoCompleteTextView autoEmploymentType;

    private TextInputLayout jobTitleLayout;
    private TextInputLayout jobDescriptionLayout;
    private TextInputLayout companyNameLayout;
    private TextInputLayout locationLayout;
    private TextInputLayout employmentTypeLayout;

    private Button btnPostJob;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post_job);

        initializeViews();
        setupEmploymentTypeDropdown();
        setupListeners();
    }


    private void initializeViews() {

        btnBack = findViewById(R.id.btnBack);

        etJobTitle = findViewById(R.id.etJobTitle);
        etJobDescription = findViewById(R.id.etJobDescription);
        etCompanyName = findViewById(R.id.etCompanyName);
        etLocation = findViewById(R.id.etLocation);
        etExperience = findViewById(R.id.etExperience);
        etMinSalary = findViewById(R.id.etMinSalary);
        etMaxSalary = findViewById(R.id.etMaxSalary);
        etSkills = findViewById(R.id.etSkills);

        autoEmploymentType = findViewById(R.id.autoEmploymentType);

        jobTitleLayout = findViewById(R.id.jobTitleLayout);
        jobDescriptionLayout = findViewById(R.id.jobDescriptionLayout);
        companyNameLayout = findViewById(R.id.companyNameLayout);
        locationLayout = findViewById(R.id.locationLayout);
        employmentTypeLayout = findViewById(R.id.employmentTypeLayout);

        btnPostJob = findViewById(R.id.btnPostJob);

        progressBar = findViewById(R.id.progressBar);
    }


    private void setupEmploymentTypeDropdown() {

        String[] employmentTypes = {
                "Full Time",
                "Part Time",
                "Internship",
                "Contract"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        employmentTypes
                );

        autoEmploymentType.setAdapter(adapter);

        autoEmploymentType.setOnClickListener(v ->
                autoEmploymentType.showDropDown()
        );
    }


    private void setupListeners() {

        btnBack.setOnClickListener(v -> finish());

        btnPostJob.setOnClickListener(v -> {

            if (validateForm()) {

                postJob();

            }

        });
    }


    private boolean validateForm() {

        clearErrors();

        boolean isValid = true;


        // Job Title

        String title = getText(etJobTitle);

        if (title.isEmpty()) {

            jobTitleLayout.setError(
                    getString(R.string.job_title_required)
            );

            isValid = false;
        }


        // Description

        String description = getText(etJobDescription);

        if (description.isEmpty()) {

            jobDescriptionLayout.setError(
                    getString(R.string.job_description_required)
            );

            isValid = false;
        }


        // Company

        String company = getText(etCompanyName);

        if (company.isEmpty()) {

            companyNameLayout.setError(
                    getString(R.string.company_name_required)
            );

            isValid = false;
        }


        // Location

        String location = getText(etLocation);

        if (location.isEmpty()) {

            locationLayout.setError(
                    getString(R.string.location_required)
            );

            isValid = false;
        }


        // Employment Type

        String employmentType =
                autoEmploymentType.getText().toString().trim();

        if (employmentType.isEmpty()) {

            employmentTypeLayout.setError(
                    getString(R.string.employment_type_required)
            );

            isValid = false;
        }


        return isValid;
    }


    private void clearErrors() {

        jobTitleLayout.setError(null);
        jobDescriptionLayout.setError(null);
        companyNameLayout.setError(null);
        locationLayout.setError(null);
        employmentTypeLayout.setError(null);
    }


    private String getText(TextInputEditText editText) {

        if (editText.getText() == null) {
            return "";
        }

        return editText.getText()
                .toString()
                .trim();
    }


    private void postJob() {

        setLoading(true);

        /*
         * =====================================================
         * BACKEND CONNECTION
         * =====================================================
         *
         * The values below are exactly what will be sent to:
         *
         * POST /api/jobs
         *
         * Authentication:
         * Bearer JWT
         *
         * Do NOT send:
         * recruiterId
         * userId
         * status
         * createdAt
         * updatedAt
         */

        String title = getText(etJobTitle);

        String description = getText(etJobDescription);

        String companyName = getText(etCompanyName);

        String location = getText(etLocation);

        String employmentType =
                autoEmploymentType.getText()
                        .toString()
                        .trim();

        String experienceRequired =
                getText(etExperience);

        String salaryMin =
                getText(etMinSalary);

        String salaryMax =
                getText(etMaxSalary);

        String skills =
                getText(etSkills);


        /*
         * TODO:
         *
         * Connect these values to your existing Retrofit/API layer.
         *
         * JobRequest request = new JobRequest(
         *     title,
         *     description,
         *     companyName,
         *     location,
         *     employmentType,
         *     experienceRequired,
         *     salaryMin,
         *     salaryMax,
         *     skills
         * );
         *
         * api.createJob(request)
         *
         * On success:
         *     setLoading(false);
         *     Toast.makeText(...,
         *         "Job posted successfully",
         *         Toast.LENGTH_SHORT).show();
         *     finish();
         *
         * On failure:
         *     setLoading(false);
         *     Toast.makeText(...,
         *         "Unable to post job. Please try again.",
         *         Toast.LENGTH_SHORT).show();
         */


        /*
         * TEMPORARY:
         *
         * This is only here so the screen can be tested before
         * connecting your actual Spring Boot API.
         *
         * Remove this block once Retrofit/API code is connected.
         */

        new android.os.Handler().postDelayed(() -> {

            setLoading(false);

            Toast.makeText(
                    PostJobActivity.this,
                    getString(R.string.job_posted_successfully),
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        }, 1000);
    }


    private void setLoading(boolean loading) {

        if (loading) {

            btnPostJob.setEnabled(false);

            btnPostJob.setText(
                    getString(R.string.posting_job)
            );

            progressBar.setVisibility(View.VISIBLE);

        } else {

            btnPostJob.setEnabled(true);

            btnPostJob.setText(
                    getString(R.string.post_job_button)
            );

            progressBar.setVisibility(View.GONE);
        }
    }
}