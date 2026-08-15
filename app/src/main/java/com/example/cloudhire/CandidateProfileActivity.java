package com.example.cloudhire;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class CandidateProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_candidate_profile);

        ImageButton btnBack = findViewById(R.id.btnBack);
        Button btnEditProfile = findViewById(R.id.btnEditProfile);
        Button btnAddExperience = findViewById(R.id.btnAddExperience);
        Button btnAddEducation = findViewById(R.id.btnAddEducation);
        Button btnViewResume = findViewById(R.id.btnViewResume);
        Button btnReplaceResume = findViewById(R.id.btnReplaceResume);

        // Back
        btnBack.setOnClickListener(v -> {
            finish();
        });

        // Edit Profile
        btnEditProfile.setOnClickListener(v -> {
            // We will connect this later
        });

        // Add Experience
        btnAddExperience.setOnClickListener(v -> {
            // We will connect this later
        });

        // Add Education
        btnAddEducation.setOnClickListener(v -> {
            // We will connect this later
        });

        // View Resume
        btnViewResume.setOnClickListener(v -> {
            // We will connect this later
        });

        // Replace Resume
        btnReplaceResume.setOnClickListener(v -> {
            // We will connect this later
        });
    }
}