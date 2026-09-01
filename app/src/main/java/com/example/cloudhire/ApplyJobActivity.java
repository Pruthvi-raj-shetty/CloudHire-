package com.example.cloudhire;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ApplyJobActivity extends AppCompatActivity {

    private TextView txtJobTitle, txtCompanyName, txtLocation;
    private EditText etFullName, etEmail, etCoverLetter;
    private LinearLayout layoutUploadResume;
    private Button btnSubmitApplication;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);

        // Initialize Views
        txtJobTitle = findViewById(R.id.txtJobTitle);
        txtCompanyName = findViewById(R.id.txtCompanyName);
        txtLocation = findViewById(R.id.txtLocation);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etCoverLetter = findViewById(R.id.etCoverLetter);
        layoutUploadResume = findViewById(R.id.layoutUploadResume);
        btnSubmitApplication = findViewById(R.id.btnSubmitApplication);
        btnBack = findViewById(R.id.btnBack);

        // Get Data from Intent
        String jobTitle = getIntent().getStringExtra("job_title");
        String companyName = getIntent().getStringExtra("company_name");
        String location = getIntent().getStringExtra("location");

        if (jobTitle != null) txtJobTitle.setText(jobTitle);
        if (companyName != null) txtCompanyName.setText(companyName);
        if (location != null) txtLocation.setText(location);

        // Pre-fill user data (In a real app, this would come from shared preferences or a database)
        etFullName.setText("Adithya Kumar");
        etEmail.setText("adithya@example.com");

        btnBack.setOnClickListener(v -> finish());

        layoutUploadResume.setOnClickListener(v -> {
            Toast.makeText(this, "Opening file picker...", Toast.LENGTH_SHORT).show();
            // Logic for file picker would go here
        });

        btnSubmitApplication.setOnClickListener(v -> {
            String fullName = etFullName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (fullName.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Logic to submit application would go here
            Toast.makeText(this, "Application submitted successfully to " + companyName, Toast.LENGTH_LONG).show();
            finish();
        });
    }
}
