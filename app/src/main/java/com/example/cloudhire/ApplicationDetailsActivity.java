package com.example.cloudhire;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudhire.api.ApiService;
import com.example.cloudhire.api.RetrofitClient;
import com.example.cloudhire.model.ApplicationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        setContentView(
                R.layout.activity_application_details
        );


        // =========================
        // FIND VIEWS
        // =========================

        txtJobTitle =
                findViewById(
                        R.id.txtDetailJobTitle
                );

        txtCompanyName =
                findViewById(
                        R.id.txtDetailCompanyName
                );

        txtLocation =
                findViewById(
                        R.id.txtDetailLocation
                );

        txtEmploymentType =
                findViewById(
                        R.id.txtDetailEmploymentType
                );

        txtAppliedAt =
                findViewById(
                        R.id.txtDetailAppliedAt
                );

        txtStatus =
                findViewById(
                        R.id.txtDetailStatus
                );

        txtResume =
                findViewById(
                        R.id.txtDetailResume
                );


        // =========================
        // BACK
        // =========================

        findViewById(R.id.btnBack)
                .setOnClickListener(
                        v -> finish()
                );


        // =========================
        // APPLICATION ID
        // =========================

        long applicationId =
                getIntent().getLongExtra(
                        "applicationId",
                        -1
                );


        if (applicationId == -1) {

            Toast.makeText(
                    this,
                    "Invalid application",
                    Toast.LENGTH_LONG
            ).show();

            finish();

            return;
        }


        // =========================
        // LOAD DETAILS
        // =========================

        loadApplicationDetails(
                applicationId
        );
    }


    // =========================================================
    // LOAD APPLICATION DETAILS
    // =========================================================

    private void loadApplicationDetails(
            Long applicationId) {

        ApiService apiService =
                RetrofitClient.getApiService(this);


        apiService.getMyApplicationDetails(
                        applicationId
                )
                .enqueue(
                        new Callback<ApplicationResponse>() {

                            @Override
                            public void onResponse(
                                    Call<ApplicationResponse> call,
                                    Response<ApplicationResponse> response) {

                                if (response.isSuccessful()
                                        && response.body() != null) {

                                    displayApplication(
                                            response.body()
                                    );

                                } else {

                                    Toast.makeText(
                                            ApplicationDetailsActivity.this,
                                            "Unable to load application",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }


                            @Override
                            public void onFailure(
                                    Call<ApplicationResponse> call,
                                    Throwable t) {

                                Toast.makeText(
                                        ApplicationDetailsActivity.this,
                                        "Unable to connect to server",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );
    }


    // =========================================================
    // DISPLAY APPLICATION
    // =========================================================

    private void displayApplication(
            ApplicationResponse application) {

        txtJobTitle.setText(
                safeText(
                        application.getJobTitle(),
                        "-"
                )
        );

        txtCompanyName.setText(
                safeText(
                        application.getCompanyName(),
                        "-"
                )
        );

        txtLocation.setText(
                safeText(
                        application.getLocation(),
                        "-"
                )
        );

        txtEmploymentType.setText(
                safeText(
                        application.getEmploymentType(),
                        "-"
                )
        );

        txtAppliedAt.setText(
                safeText(
                        application.getAppliedAt(),
                        "-"
                )
        );

        txtStatus.setText(
                safeText(
                        application.getStatus(),
                        "-"
                )
        );

        txtResume.setText(
                "Resume submitted with application"
        );
    }


    // =========================================================
    // SAFE TEXT
    // =========================================================

    private String safeText(
            String value,
            String fallback) {

        if (value == null ||
                value.trim().isEmpty()) {

            return fallback;
        }

        return value;
    }
}