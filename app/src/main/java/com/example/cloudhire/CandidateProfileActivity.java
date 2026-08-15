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

        btnBack.setOnClickListener(v -> finish());

        btnEditProfile.setOnClickListener(v -> {
        });

        btnAddExperience.setOnClickListener(v -> {
        });

        btnAddEducation.setOnClickListener(v -> {
        });

        btnViewResume.setOnClickListener(v -> {
        });

        btnReplaceResume.setOnClickListener(v -> {
        });
    }
}