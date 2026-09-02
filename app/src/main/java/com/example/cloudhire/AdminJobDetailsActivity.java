package com.example.cloudhire;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminJobDetailsActivity extends AppCompatActivity {

    private TextView txtStatus, txtTitle, txtCompany, txtLocation, txtType, txtExperience, txtSalary, txtDescription, txtAppMethod, txtAppUrl, txtPostedDate;
    private LinearLayout skillsContainer;
    private Button btnCloseJob, btnRemoveJob;

    private String jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_job_details);

        initializeViews();
        loadJobData();
        setupClicks();
    }

    private void initializeViews() {
        txtStatus = findViewById(R.id.txtStatus);
        txtTitle = findViewById(R.id.txtTitle);
        txtCompany = findViewById(R.id.txtCompany);
        txtLocation = findViewById(R.id.txtLocation);
        txtType = findViewById(R.id.txtType);
        txtExperience = findViewById(R.id.txtExperience);
        txtSalary = findViewById(R.id.txtSalary);
        txtDescription = findViewById(R.id.txtDescription);
        txtAppMethod = findViewById(R.id.txtAppMethod);
        txtAppUrl = findViewById(R.id.txtAppUrl);
        txtPostedDate = findViewById(R.id.txtPostedDate);
        skillsContainer = findViewById(R.id.skillsContainer);
        btnCloseJob = findViewById(R.id.btnCloseJob);
        btnRemoveJob = findViewById(R.id.btnRemoveJob);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void loadJobData() {
        Intent intent = getIntent();
        if (intent == null) return;

        jobId = intent.getStringExtra("jobId");
        txtTitle.setText(intent.getStringExtra("title"));
        txtCompany.setText(intent.getStringExtra("company"));
        txtLocation.setText("📍 " + intent.getStringExtra("location"));
        txtType.setText("💼 " + intent.getStringExtra("type"));
        txtExperience.setText("⏳ " + intent.getStringExtra("experience"));
        txtSalary.setText("💰 " + intent.getStringExtra("salary"));
        txtPostedDate.setText("Posted: " + intent.getStringExtra("postedDate"));
        
        String status = intent.getStringExtra("status");
        txtStatus.setText(status.toUpperCase());
        if (status.equalsIgnoreCase("Closed")) {
            txtStatus.setBackgroundResource(R.drawable.bg_filter_inactive);
            txtStatus.setTextColor(Color.parseColor("#64748B"));
            btnCloseJob.setText("Reopen Job");
            btnCloseJob.setTextColor(Color.parseColor("#10B981"));
            btnCloseJob.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0); // Reset if needed
        }

        String desc = intent.getStringExtra("description");
        txtDescription.setText(desc != null ? desc : "No description provided.");

        String skills = intent.getStringExtra("skills");
        if (skills != null && !skills.isEmpty()) {
            for (String skill : skills.split(",")) {
                addSkillChip(skill.trim());
            }
        }

        String method = intent.getStringExtra("appMethod");
        txtAppMethod.setText("Method: " + (method != null ? method : "NexHire Platform"));
        
        String url = intent.getStringExtra("appUrl");
        if (url != null && !url.isEmpty()) {
            txtAppUrl.setVisibility(View.VISIBLE);
            txtAppUrl.setText("URL: " + url);
        }
    }

    private void addSkillChip(String skill) {
        TextView chip = new TextView(this);
        chip.setText(skill);
        chip.setTextSize(12);
        chip.setPadding(dp(12), dp(6), dp(12), dp(6));
        chip.setTextColor(Color.parseColor("#475569"));
        
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setColor(Color.parseColor("#F1F5F9"));
        gd.setCornerRadius(dp(8));
        chip.setBackground(gd);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
        params.setMargins(0, 0, dp(8), 0);
        chip.setLayoutParams(params);
        
        skillsContainer.addView(chip);
    }

    private void setupClicks() {
        btnCloseJob.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("Update Status")
                .setMessage("Are you sure you want to change the status of this job?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Toast.makeText(this, "Job status updated", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
        });

        btnRemoveJob.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("Remove Job")
                .setMessage("This action is permanent. Do you want to remove this job listing?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    Toast.makeText(this, "Job removed from platform", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
        });
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
