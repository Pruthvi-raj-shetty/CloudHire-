package com.example.cloudhire;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ApplicationDetailsActivity extends AppCompatActivity {

    private TextView txtJobTitle;
    private TextView txtCompanyName;
    private TextView txtLocation;
    private TextView txtEmploymentType;
    private TextView txtAppliedAt;
    private TextView txtStatus;
    private TextView txtResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_application_details);

        txtJobTitle = findViewById(R.id.txtDetailJobTitle);
        txtCompanyName = findViewById(R.id.txtDetailCompanyName);
        txtLocation = findViewById(R.id.txtDetailLocation);
        txtEmploymentType = findViewById(R.id.txtDetailEmploymentType);
        txtAppliedAt = findViewById(R.id.txtDetailAppliedAt);
        txtStatus = findViewById(R.id.txtDetailStatus);
        txtResume = findViewById(R.id.txtDetailResume);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        String jobTitle = getIntent().getStringExtra("jobTitle");
        String companyName = getIntent().getStringExtra("companyName");
        String location = getIntent().getStringExtra("location");
        String employmentType = getIntent().getStringExtra("employmentType");
        String appliedAt = getIntent().getStringExtra("appliedAt");
        String status = getIntent().getStringExtra("status");
        String resumeInfo = getIntent().getStringExtra("resumeInfo");

        txtJobTitle.setText(jobTitle != null ? jobTitle : "-");
        txtCompanyName.setText(companyName != null ? companyName : "-");
        txtLocation.setText(location != null ? location : "-");
        txtEmploymentType.setText(employmentType != null ? employmentType : "-");
        txtAppliedAt.setText(appliedAt != null ? appliedAt : "-");
        txtStatus.setText(status != null ? status : "-");
        txtResume.setText(resumeInfo != null ? resumeInfo : "Not available");
    }
}