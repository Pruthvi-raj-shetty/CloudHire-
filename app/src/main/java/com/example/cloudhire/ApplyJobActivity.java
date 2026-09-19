package com.example.cloudhire;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudhire.api.ApiService;
import com.example.cloudhire.api.RetrofitClient;
import com.example.cloudhire.model.ApplicationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyJobActivity extends AppCompatActivity {

    private TextView txtJobTitle;
    private TextView txtCompanyName;
    private TextView txtLocation;

    private EditText etFullName;
    private EditText etEmail;
    private EditText etCoverLetter;

    private LinearLayout layoutUploadResume;

    private Button btnSubmitApplication;

    private ImageButton btnBack;

    private Long jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_apply_job);

        // =========================
        // INITIALIZE VIEWS
        // =========================

        txtJobTitle = findViewById(R.id.txtJobTitle);
        txtCompanyName = findViewById(R.id.txtCompanyName);
        txtLocation = findViewById(R.id.txtLocation);

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etCoverLetter = findViewById(R.id.etCoverLetter);

        layoutUploadResume = findViewById(R.id.layoutUploadResume);

        btnSubmitApplication =
                findViewById(R.id.btnSubmitApplication);

        btnBack = findViewById(R.id.btnBack);


        // =========================
        // GET REAL JOB DATA
        // =========================

        jobId = getIntent().getLongExtra("job_id", -1);

        String jobTitle =
                getIntent().getStringExtra("job_title");

        String companyName =
                getIntent().getStringExtra("company_name");

        String location =
                getIntent().getStringExtra("location");


        // =========================
        // VALIDATE JOB ID
        // =========================

        if (jobId == -1) {

            Toast.makeText(
                    this,
                    "Invalid job",
                    Toast.LENGTH_LONG
            ).show();

            finish();
            return;
        }


        // =========================
        // DISPLAY JOB
        // =========================

        if (jobTitle != null) {
            txtJobTitle.setText(jobTitle);
        }

        if (companyName != null) {
            txtCompanyName.setText(companyName);
        }

        if (location != null) {
            txtLocation.setText(location);
        }


        // =========================
        // LOAD LOGGED-IN USER
        // =========================

        SessionManager sessionManager =
                new SessionManager(this);

        String name =
                sessionManager.getName();

        String email =
                sessionManager.getEmail();


        if (name != null && !name.isEmpty()) {
            etFullName.setText(name);
        }

        if (email != null && !email.isEmpty()) {
            etEmail.setText(email);
        }


        // =========================
        // BACK
        // =========================

        btnBack.setOnClickListener(v -> finish());


        // =========================
        // RESUME
        // =========================

        layoutUploadResume.setOnClickListener(v -> {

            Toast.makeText(
                    this,
                    "Resume upload will be connected next",
                    Toast.LENGTH_SHORT
            ).show();

        });


        // =========================
        // SUBMIT APPLICATION
        // =========================

        btnSubmitApplication.setOnClickListener(v -> {

            String fullName =
                    etFullName.getText()
                            .toString()
                            .trim();

            String enteredemail =
                    etEmail.getText()
                            .toString()
                            .trim();

            if (fullName.isEmpty() || enteredemail.isEmpty()) {

                Toast.makeText(
                        this,
                        "Please fill in all required fields",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            submitApplication();
        });
    }


    // =========================================================
    // SUBMIT APPLICATION TO BACKEND
    // =========================================================

    private void submitApplication() {

        btnSubmitApplication.setEnabled(false);

        btnSubmitApplication.setText("Submitting...");


        ApiService apiService =
                RetrofitClient.getApiService(this);


        apiService.applyToJob(jobId)
                .enqueue(new Callback<ApplicationResponse>() {

                    @Override
                    public void onResponse(
                            Call<ApplicationResponse> call,
                            Response<ApplicationResponse> response) {

                        btnSubmitApplication.setEnabled(true);

                        btnSubmitApplication.setText(
                                "Submit Application"
                        );


                        if (response.isSuccessful()
                                && response.body() != null) {

                            ApplicationResponse application =
                                    response.body();


                            Toast.makeText(
                                    ApplyJobActivity.this,
                                    "Application submitted successfully",
                                    Toast.LENGTH_LONG
                            ).show();


                            finish();

                            return;
                        }


                        // =========================
                        // DUPLICATE APPLICATION
                        // =========================

                        if (response.code() == 400
                                || response.code() == 409) {

                            Toast.makeText(
                                    ApplyJobActivity.this,
                                    "You have already applied for this job",
                                    Toast.LENGTH_LONG
                            ).show();

                            return;
                        }


                        // =========================
                        // OTHER SERVER ERROR
                        // =========================

                        Toast.makeText(
                                ApplyJobActivity.this,
                                "Unable to submit application",
                                Toast.LENGTH_LONG
                        ).show();
                    }


                    @Override
                    public void onFailure(
                            Call<ApplicationResponse> call,
                            Throwable t) {

                        btnSubmitApplication.setEnabled(true);

                        btnSubmitApplication.setText(
                                "Submit Application"
                        );


                        Toast.makeText(
                                ApplyJobActivity.this,
                                "Unable to connect to server",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}