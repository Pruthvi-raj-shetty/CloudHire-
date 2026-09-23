package com.example.cloudhire;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ApplyJobActivity extends AppCompatActivity {

    private TextView txtTitle;
    private TextView txtCompany;
    private TextView txtLocation;
    private TextView txtEmployment;
    private TextView txtExperience;
    private TextView txtSkills;

    private Button btnSelectResume;
    private Button btnSubmitApplication;

    private String jobId;
    private String jobTitle;
    private String companyName;
    private String location;
    private String employmentType;
    private String experience;
    private String skills;

    private static final int PICK_RESUME = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_apply_job);

        txtTitle = findViewById(R.id.txtApplyTitle);
        txtCompany = findViewById(R.id.txtApplyCompany);
        txtLocation = findViewById(R.id.txtApplyLocation);
        txtEmployment = findViewById(R.id.txtApplyEmployment);
        txtExperience = findViewById(R.id.txtApplyExperience);
        txtSkills = findViewById(R.id.txtApplySkills);

        btnSelectResume = findViewById(R.id.btnSelectResume);
        btnSubmitApplication = findViewById(R.id.btnSubmitApplication);

        Intent intent = getIntent();

        jobId = intent.getStringExtra("jobId");
        jobTitle = intent.getStringExtra("jobTitle");
        companyName = intent.getStringExtra("companyName");
        location = intent.getStringExtra("location");
        employmentType = intent.getStringExtra("employmentType");
        experience = intent.getStringExtra("experience");
        skills = intent.getStringExtra("skills");

        txtTitle.setText(
                "Apply for " + safeText(jobTitle)
        );

        txtCompany.setText(
                "Company: " + safeText(companyName)
        );

        txtLocation.setText(
                "Location: " + safeText(location)
        );

        txtEmployment.setText(
                "Employment Type: " + safeText(employmentType)
        );

        txtExperience.setText(
                "Experience: " + safeText(experience)
        );

        txtSkills.setText(
                "Skills: " + safeText(skills)
        );

        btnSelectResume.setOnClickListener(v -> selectResume());

        btnSubmitApplication.setOnClickListener(v -> submitApplication());
    }

    private String safeText(String value) {

        if (value == null || value.trim().isEmpty()) {
            return "Not provided";
        }

        return value;
    }

    private void selectResume() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        intent.setType("application/pdf");

        intent.addCategory(Intent.CATEGORY_OPENABLE);

        startActivityForResult(intent, PICK_RESUME);
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data
    ) {
        super.onActivityResult(
                requestCode,
                resultCode,
                data
        );

        if (requestCode == PICK_RESUME
                && resultCode == RESULT_OK
                && data != null) {

            Uri resumeUri = data.getData();

            if (resumeUri != null) {

                btnSelectResume.setText(
                        "Resume Selected"
                );

                Toast.makeText(
                        this,
                        "Resume selected",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    private void submitApplication() {

        /*
         * FRONTEND ONLY
         *
         * Your friend will connect this section
         * to the Spring Boot API.
         *
         * The authenticated JWT should be added
         * by the API/network layer.
         */

        Toast.makeText(
                this,
                "Application submission API will be connected here.",
                Toast.LENGTH_LONG
        ).show();
    }
}