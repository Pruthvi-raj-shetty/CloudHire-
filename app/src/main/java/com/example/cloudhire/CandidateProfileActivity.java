package com.example.cloudhire;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CandidateProfileActivity extends AppCompatActivity {

    private static final int PICK_RESUME_REQUEST = 1001;

    private ImageButton btnBack;

    private Button btnEditProfile;
    private Button btnAddExperience;
    private Button btnAddEducation;
    private Button btnAddResume;
    private Button btnReplaceResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_candidate_profile);

        // -----------------------------
        // Find Views
        // -----------------------------

        btnBack = findViewById(R.id.btnBack);

        btnEditProfile = findViewById(R.id.btnEditProfile);

        btnAddExperience = findViewById(R.id.btnAddExperience);

        btnAddEducation = findViewById(R.id.btnAddEducation);

        btnAddResume = findViewById(R.id.btnAddResume);

        btnReplaceResume = findViewById(R.id.btnReplaceResume);


        // -----------------------------
        // Back Button
        // -----------------------------

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // -----------------------------
        // Edit Profile
        // -----------------------------

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(
                        CandidateProfileActivity.this,
                        "Edit Profile",
                        Toast.LENGTH_SHORT
                ).show();

            }
        });


        // -----------------------------
        // Add Experience
        // -----------------------------

        btnAddExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(
                        CandidateProfileActivity.this,
                        "Add Experience",
                        Toast.LENGTH_SHORT
                ).show();

            }
        });


        // -----------------------------
        // Add Education
        // -----------------------------

        btnAddEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(
                        CandidateProfileActivity.this,
                        "Add Education",
                        Toast.LENGTH_SHORT
                ).show();

            }
        });


        // -----------------------------
        // ADD RESUME
        // -----------------------------

        btnAddResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openResumePicker();

            }
        });


        // -----------------------------
        // REPLACE RESUME
        // -----------------------------

        btnReplaceResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openResumePicker();

            }
        });
    }


    // =========================================================
    // Open Phone File Picker
    // =========================================================

    private void openResumePicker() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        intent.addCategory(Intent.CATEGORY_OPENABLE);

        intent.setType("application/pdf");

        startActivityForResult(intent, PICK_RESUME_REQUEST);
    }


    // =========================================================
    // Get Selected Resume
    // =========================================================

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {

        super.onActivityResult(
                requestCode,
                resultCode,
                data
        );

        if (requestCode == PICK_RESUME_REQUEST
                && resultCode == RESULT_OK
                && data != null) {

            Uri resumeUri = data.getData();

            if (resumeUri != null) {

                Toast.makeText(
                        CandidateProfileActivity.this,
                        "Resume added successfully",
                        Toast.LENGTH_SHORT
                ).show();

                // Keep permission to access the selected PDF
                try {

                    getContentResolver().takePersistableUriPermission(
                            resumeUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                    );

                } catch (Exception e) {

                    e.printStackTrace();

                }
            }
        }
    }
}