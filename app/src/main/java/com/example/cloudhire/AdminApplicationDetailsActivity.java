package com.example.cloudhire;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminApplicationDetailsActivity extends AppCompatActivity {

    private TextView txtInitial, txtCandidateName, txtProfessionalTitle, txtStatusBadge;
    private TextView txtEmail, txtPhone, txtJobTitle, txtCompany, txtLocationType, txtDates;
    private Button btnViewResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_application_details);

        initializeViews();
        loadData();
    }

    private void initializeViews() {
        txtInitial = findViewById(R.id.txtInitial);
        txtCandidateName = findViewById(R.id.txtCandidateName);
        txtProfessionalTitle = findViewById(R.id.txtProfessionalTitle);
        txtStatusBadge = findViewById(R.id.txtStatusBadge);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        txtJobTitle = findViewById(R.id.txtJobTitle);
        txtCompany = findViewById(R.id.txtCompany);
        txtLocationType = findViewById(R.id.txtLocationType);
        txtDates = findViewById(R.id.txtDates);
        btnViewResume = findViewById(R.id.btnViewResume);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent == null) return;

        String name = intent.getStringExtra("candidateName");
        txtCandidateName.setText(name);
        if (name != null && !name.isEmpty()) {
            txtInitial.setText(name.substring(0, 1).toUpperCase());
        }

        txtProfessionalTitle.setText(intent.getStringExtra("candidateTitle"));
        txtEmail.setText("📧 " + intent.getStringExtra("candidateEmail"));
        txtPhone.setText("📞 " + intent.getStringExtra("candidatePhone"));
        
        txtJobTitle.setText("Job: " + intent.getStringExtra("jobTitle"));
        txtCompany.setText("Company: " + intent.getStringExtra("companyName"));
        txtLocationType.setText("Location: " + intent.getStringExtra("location") + " | " + intent.getStringExtra("employmentType"));
        txtDates.setText("Applied on: " + intent.getStringExtra("appliedAt"));

        String status = intent.getStringExtra("status");
        txtStatusBadge.setText(status != null ? status.toUpperCase() : "APPLIED");
        
        updateStatusBadge(status);

        btnViewResume.setOnClickListener(v -> {
            Toast.makeText(this, "Opening resume for " + name, Toast.LENGTH_SHORT).show();
        });
    }

    private void updateStatusBadge(String status) {
        if (status == null) return;
        
        int color = Color.parseColor("#3B82F6"); // Default Blue
        int bg = Color.parseColor("#EFF6FF");

        if (status.equalsIgnoreCase("Shortlisted")) {
            color = Color.parseColor("#15803D");
            bg = Color.parseColor("#DCFCE7");
        } else if (status.equalsIgnoreCase("Rejected")) {
            color = Color.parseColor("#B91C1C");
            bg = Color.parseColor("#FEE2E2");
        }

        txtStatusBadge.setTextColor(color);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(bg);
        gd.setCornerRadius(dp(20));
        txtStatusBadge.setBackground(gd);
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
