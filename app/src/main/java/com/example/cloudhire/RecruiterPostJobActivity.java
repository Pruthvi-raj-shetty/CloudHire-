package com.example.cloudhire;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RecruiterPostJobActivity extends AppCompatActivity {

    // =========================================================
    // VIEWS
    // =========================================================

    private View btnBack;

    private TextInputEditText etJobTitle;
    private TextInputEditText etJobDescription;
    private TextInputEditText etCompanyName;
    private TextInputEditText etLocation;

    private AutoCompleteTextView actEmploymentType;

    private TextInputEditText etExperience;
    private TextInputEditText etSalaryMin;
    private TextInputEditText etSalaryMax;
    private TextInputEditText etSkills;

    private RadioGroup rgApplicationMethod;
    private RadioButton rbNexHire;
    private RadioButton rbExternalLink;
    private TextInputLayout tilApplicationUrl;
    private TextInputEditText etApplicationUrl;

    private MaterialButton btnPostJobSubmit;

    private boolean isSubmitting = false;


    // =========================================================
    // ON CREATE
    // =========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recruiter_post_job);

        initializeViews();
        setupEmploymentType();
        setupClickListeners();
    }


    // =========================================================
    // INITIALIZE
    // =========================================================

    private void initializeViews() {

        btnBack = findViewById(R.id.btnPostJobBack);

        etJobTitle = findViewById(R.id.etJobTitle);

        etJobDescription =
                findViewById(R.id.etJobDescription);

        etCompanyName =
                findViewById(R.id.etCompanyName);

        etLocation =
                findViewById(R.id.etLocation);

        actEmploymentType =
                findViewById(R.id.actEmploymentType);

        etExperience =
                findViewById(R.id.etExperience);

        etSalaryMin =
                findViewById(R.id.etSalaryMin);

        etSalaryMax =
                findViewById(R.id.etSalaryMax);

        etSkills =
                findViewById(R.id.etSkills);

        rgApplicationMethod = findViewById(R.id.rgApplicationMethod);
        rbNexHire = findViewById(R.id.rbNexHire);
        rbExternalLink = findViewById(R.id.rbExternalLink);
        tilApplicationUrl = findViewById(R.id.tilApplicationUrl);
        etApplicationUrl = findViewById(R.id.etApplicationUrl);

        btnPostJobSubmit =
                findViewById(R.id.btnPostJobSubmit);
    }


    // =========================================================
    // EMPLOYMENT TYPE
    // =========================================================

    private void setupEmploymentType() {

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

        actEmploymentType.setAdapter(adapter);

        actEmploymentType.setOnClickListener(v ->
                actEmploymentType.showDropDown()
        );

        actEmploymentType.setOnFocusChangeListener(
                (v, hasFocus) -> {

                    if (hasFocus) {
                        actEmploymentType.showDropDown();
                    }
                }
        );
    }


    // =========================================================
    // CLICK LISTENERS
    // =========================================================

    private void setupClickListeners() {

        // BACK BUTTON

        btnBack.setOnClickListener(v -> {

            hideKeyboard();

            finish();
        });


        // APPLICATION METHOD RADIO GROUP

        rgApplicationMethod.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbExternalLink) {
                tilApplicationUrl.setVisibility(View.VISIBLE);
            } else {
                tilApplicationUrl.setVisibility(View.GONE);
                tilApplicationUrl.setError(null);
            }
        });


        // POST JOB BUTTON

        btnPostJobSubmit.setOnClickListener(v -> {

            if (!isSubmitting) {
                validateForm();
            }
        });
    }


    // =========================================================
    // VALIDATE FORM
    // =========================================================

    private void validateForm() {

        clearErrors();

        String title =
                getText(etJobTitle);

        String description =
                getText(etJobDescription);

        String companyName =
                getText(etCompanyName);

        String location =
                getText(etLocation);

        String employmentType =
                actEmploymentType
                        .getText()
                        .toString()
                        .trim();


        // JOB TITLE

        if (TextUtils.isEmpty(title)) {

            etJobTitle.setError(
                    "Job title is required"
            );

            etJobTitle.requestFocus();

            return;
        }


        // JOB DESCRIPTION

        if (TextUtils.isEmpty(description)) {

            etJobDescription.setError(
                    "Job description is required"
            );

            etJobDescription.requestFocus();

            return;
        }


        // COMPANY NAME

        if (TextUtils.isEmpty(companyName)) {

            etCompanyName.setError(
                    "Company name is required"
            );

            etCompanyName.requestFocus();

            return;
        }


        // LOCATION

        if (TextUtils.isEmpty(location)) {

            etLocation.setError(
                    "Location is required"
            );

            etLocation.requestFocus();

            return;
        }


        // EMPLOYMENT TYPE

        if (TextUtils.isEmpty(employmentType)) {

            actEmploymentType.setError(
                    "Employment type is required"
            );

            actEmploymentType.requestFocus();

            return;
        }


        // APPLICATION METHOD & URL

        String applicationMethod = rbNexHire.isChecked() ? "NexHire" : "External";
        String applicationUrl = getText(etApplicationUrl);

        if (rbExternalLink.isChecked()) {
            if (TextUtils.isEmpty(applicationUrl)) {
                tilApplicationUrl.setError("Application URL is required");
                etApplicationUrl.requestFocus();
                return;
            }
            if (!isValidUrl(applicationUrl)) {
                tilApplicationUrl.setError("Please enter a valid HTTP/HTTPS URL");
                etApplicationUrl.requestFocus();
                return;
            }
        }


        // =====================================================
        // OPTIONAL VALUES
        // =====================================================

        String experience =
                getText(etExperience);

        String salaryMin =
                getText(etSalaryMin);

        String salaryMax =
                getText(etSalaryMax);

        String skills =
                getText(etSkills);


        // =====================================================
        // JOB DATA
        // =====================================================

        JobData jobData =
                new JobData(
                        title,
                        description,
                        companyName,
                        location,
                        employmentType,
                        experience,
                        salaryMin,
                        salaryMax,
                        skills,
                        applicationMethod,
                        applicationUrl
                );


        // =====================================================
        // SEND JOB
        // =====================================================

        postJob(jobData);
    }

    private boolean isValidUrl(String url) {
        return !TextUtils.isEmpty(url) && (url.startsWith("http://") || url.startsWith("https://")) && Patterns.WEB_URL.matcher(url).matches();
    }


    // =========================================================
    // POST JOB
    // =========================================================

    private void postJob(JobData jobData) {

        isSubmitting = true;

        btnPostJobSubmit.setEnabled(false);

        btnPostJobSubmit.setText("Posting...");

        btnPostJobSubmit.setAlpha(0.7f);


        /*
         * =====================================================
         * BACKEND API
         * =====================================================
         *
         * Your backend endpoint is:
         *
         * POST /api/jobs
         *
         * The backend should receive:
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
         * Authentication:
         *
         * Authorization: Bearer JWT_TOKEN
         *
         * Do NOT send:
         *
         * recruiterId
         * userId
         * status
         * createdAt
         * updatedAt
         *
         * Those are handled by the backend.
         *
         * =====================================================
         */


        /*
         * TEMPORARY:
         *
         * This is only a UI test.
         *
         * Replace this block with your real Retrofit/Volley
         * API request when connecting to your Spring Boot backend.
         */


        new android.os.Handler(
                android.os.Looper.getMainLooper()
        ).postDelayed(() -> {

            showSuccess();

        }, 1000);
    }


    // =========================================================
    // SUCCESS
    // =========================================================

    private void showSuccess() {

        isSubmitting = false;

        Toast.makeText(
                this,
                "Job posted successfully",
                Toast.LENGTH_SHORT
        ).show();


        Intent intent =
                new Intent(
                        RecruiterPostJobActivity.this,
                        RecruiterMyJobsActivity.class
                );

        intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
        );

        startActivity(intent);

        finish();
    }


    // =========================================================
    // ERROR
    // =========================================================

    private void showPostError() {

        isSubmitting = false;

        btnPostJobSubmit.setEnabled(true);

        btnPostJobSubmit.setAlpha(1.0f);

        btnPostJobSubmit.setText("Post Job");

        Toast.makeText(
                this,
                "Unable to post job. Please try again.",
                Toast.LENGTH_LONG
        ).show();
    }


    // =========================================================
    // GET TEXT
    // =========================================================

    private String getText(
            TextInputEditText editText
    ) {

        if (editText == null ||
                editText.getText() == null) {

            return "";
        }

        return editText
                .getText()
                .toString()
                .trim();
    }


    // =========================================================
    // CLEAR ERRORS
    // =========================================================

    private void clearErrors() {

        etJobTitle.setError(null);

        etJobDescription.setError(null);

        etCompanyName.setError(null);

        etLocation.setError(null);

        actEmploymentType.setError(null);
    }


    // =========================================================
    // HIDE KEYBOARD
    // =========================================================

    private void hideKeyboard() {

        View view = getCurrentFocus();

        if (view != null) {

            InputMethodManager imm =
                    (InputMethodManager)
                            getSystemService(
                                    Context.INPUT_METHOD_SERVICE
                            );

            if (imm != null) {

                imm.hideSoftInputFromWindow(
                        view.getWindowToken(),
                        0
                );
            }
        }
    }


    // =========================================================
    // JOB DATA MODEL
    // =========================================================

    public static class JobData {

        public String title;

        public String description;

        public String companyName;

        public String location;

        public String employmentType;

        public String experienceRequired;

        public String salaryMin;

        public String salaryMax;

        public String skills;

        public String applicationMethod;

        public String applicationUrl;


        public JobData(
                String title,
                String description,
                String companyName,
                String location,
                String employmentType,
                String experienceRequired,
                String salaryMin,
                String salaryMax,
                String skills,
                String applicationMethod,
                String applicationUrl
        ) {

            this.title = title;

            this.description = description;

            this.companyName = companyName;

            this.location = location;

            this.employmentType = employmentType;

            this.experienceRequired =
                    experienceRequired;

            this.salaryMin = salaryMin;

            this.salaryMax = salaryMax;

            this.skills = skills;

            this.applicationMethod = applicationMethod;

            this.applicationUrl = applicationUrl;
        }
    }
}