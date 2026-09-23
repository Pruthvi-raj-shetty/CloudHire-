package com.example.cloudhire;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CandidateDashboardActivity extends AppCompatActivity {

    private EditText etSearchJobs;

    private LinearLayout layoutGoogleJob;
    private View dividerGoogle;

    private LinearLayout layoutMicrosoftJob;
    private View dividerMicrosoft;

    private LinearLayout layoutAmazonJob;
    private View dividerAmazon;

    private TextView txtNoJobsFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_candidate_dashboard);

        // =========================
        // NOTIFICATIONS BUTTON
        // =========================
        TextView btnNotification = findViewById(R.id.btnNotification);
        if (btnNotification != null) {
            btnNotification.setOnClickListener(v -> {
                Intent intent = new Intent(
                        CandidateDashboardActivity.this,
                        CandidateNotificationsActivity.class
                );
                startActivity(intent);
            });
        }

        // =========================
        // MY PROFILE
        // =========================
        LinearLayout navProfile = findViewById(R.id.navProfile);

        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                Intent intent = new Intent(
                        CandidateDashboardActivity.this,
                        CandidateProfileActivity.class
                );

                startActivity(intent);
            });
        }

        // =========================
        // MY APPLICATIONS
        // =========================
        LinearLayout navApplications = findViewById(R.id.navApplications);

        if (navApplications != null) {
            navApplications.setOnClickListener(v -> {
                Intent intent = new Intent(
                        CandidateDashboardActivity.this,
                        MyApplicationsActivity.class
                );

                startActivity(intent);
            });
        }

        // =========================
        // SAVED JOBS
        // =========================
        LinearLayout navSaved = findViewById(R.id.navSaved);

        if (navSaved != null) {
            navSaved.setOnClickListener(v -> {
                Intent intent = new Intent(
                        CandidateDashboardActivity.this,
                        SavedJobsActivity.class
                );

                startActivity(intent);
            });
        }

        // =========================
        // SEARCH JOBS
        // =========================
        etSearchJobs = findViewById(R.id.etSearchJobs);
        layoutGoogleJob = findViewById(R.id.layoutGoogleJob);
        dividerGoogle = findViewById(R.id.dividerGoogle);
        layoutMicrosoftJob = findViewById(R.id.layoutMicrosoftJob);
        dividerMicrosoft = findViewById(R.id.dividerMicrosoft);
        layoutAmazonJob = findViewById(R.id.layoutAmazonJob);
        dividerAmazon = findViewById(R.id.dividerAmazon);
        txtNoJobsFound = findViewById(R.id.txtNoJobsFound);

        if (etSearchJobs != null) {
            etSearchJobs.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterJobs(s.toString().trim());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

            etSearchJobs.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                    filterJobs(etSearchJobs.getText().toString().trim());
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            });
        }

        // =========================
        // VIEW ALL BUTTON
        // =========================
        TextView txtViewAll = findViewById(R.id.txtViewAll);
        if (txtViewAll != null) {
            txtViewAll.setOnClickListener(v -> {
                if (etSearchJobs != null) {
                    etSearchJobs.setText("");
                }
                filterJobs("");
            });
        }

        // =========================
        // APPLY NOW BUTTONS
        // =========================
        View btnApplyGoogle = findViewById(R.id.btnApplyGoogle);
        if (btnApplyGoogle != null) {
            btnApplyGoogle.setOnClickListener(v -> {
                Intent intent = new Intent(this, ApplyJobActivity.class);
                intent.putExtra("job_title", "Software Engineer");
                intent.putExtra("company_name", "Google");
                intent.putExtra("location", "Bangalore, India");
                startActivity(intent);
            });
        }

        View btnApplyMicrosoft = findViewById(R.id.btnApplyMicrosoft);
        if (btnApplyMicrosoft != null) {
            btnApplyMicrosoft.setOnClickListener(v -> {
                Intent intent = new Intent(this, ApplyJobActivity.class);
                intent.putExtra("job_title", "Backend Developer");
                intent.putExtra("company_name", "Microsoft");
                intent.putExtra("location", "Hyderabad, India");
                startActivity(intent);
            });
        }

        View btnApplyAmazon = findViewById(R.id.btnApplyAmazon);
        if (btnApplyAmazon != null) {
            btnApplyAmazon.setOnClickListener(v -> {
                Intent intent = new Intent(this, ApplyJobActivity.class);
                intent.putExtra("job_title", "SDE - II");
                intent.putExtra("company_name", "Amazon");
                intent.putExtra("location", "Bangalore, India");
                startActivity(intent);
            });
        }
    }

    private void filterJobs(String rawQuery) {
        String query = rawQuery == null ? "" : rawQuery.trim().toLowerCase();

        boolean matchGoogle = query.isEmpty()
                || "google".contains(query)
                || "software engineer".contains(query)
                || "bangalore, india".contains(query)
                || "java spring boot sql".contains(query);

        boolean matchMicrosoft = query.isEmpty()
                || "microsoft".contains(query)
                || "backend developer".contains(query)
                || "hyderabad, india".contains(query)
                || "java spring boot aws".contains(query);

        boolean matchAmazon = query.isEmpty()
                || "amazon".contains(query)
                || "sde - ii".contains(query)
                || "sde 2".contains(query)
                || "bangalore, india".contains(query)
                || "java data structures system design".contains(query);

        if (layoutGoogleJob != null) {
            layoutGoogleJob.setVisibility(matchGoogle ? View.VISIBLE : View.GONE);
        }
        if (dividerGoogle != null) {
            dividerGoogle.setVisibility(matchGoogle ? View.VISIBLE : View.GONE);
        }

        if (layoutMicrosoftJob != null) {
            layoutMicrosoftJob.setVisibility(matchMicrosoft ? View.VISIBLE : View.GONE);
        }
        if (dividerMicrosoft != null) {
            dividerMicrosoft.setVisibility(matchMicrosoft ? View.VISIBLE : View.GONE);
        }

        if (layoutAmazonJob != null) {
            layoutAmazonJob.setVisibility(matchAmazon ? View.VISIBLE : View.GONE);
        }
        if (dividerAmazon != null) {
            dividerAmazon.setVisibility(matchAmazon ? View.VISIBLE : View.GONE);
        }

        if (txtNoJobsFound != null) {
            txtNoJobsFound.setVisibility((!matchGoogle && !matchMicrosoft && !matchAmazon) ? View.VISIBLE : View.GONE);
        }
    }
}
