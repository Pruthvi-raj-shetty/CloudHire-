package com.example.cloudhire;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudhire.api.ApiService;
import com.example.cloudhire.api.RetrofitClient;
import com.example.cloudhire.model.JobDashboardResponse;
import com.example.cloudhire.model.JobResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidateDashboardActivity extends AppCompatActivity {

    // =========================================================
    // JOB BUTTONS
    // =========================================================

    private Button btnApplyGoogle;
    private Button btnApplyMicrosoft;
    private Button btnApplyAmazon;

    // =========================================================
    // SEARCH
    // =========================================================

    private EditText etSearchJobs;

    // =========================================================
    // RECENTLY POSTED
    // =========================================================

    private TextView txtRecent1;
    private TextView txtRecent2;
    private TextView txtRecent3;
    private TextView txtRecent4;

    // =========================================================
    // COMPANIES
    // =========================================================

    private TextView txtCompany1;
    private TextView txtCompany2;
    private TextView txtCompany3;
    private TextView txtCompany4;
    private TextView txtCompany5;

    // =========================================================
    // TRENDING SKILLS
    // =========================================================

    private TextView txtSkill1;
    private TextView txtSkill2;
    private TextView txtSkill3;
    private TextView txtSkill4;
    private TextView txtSkill5;

    // =========================================================
    // API
    // =========================================================

    private ApiService apiService;

    // =========================================================
    // ACTIVITY
    // =========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_candidate_dashboard);

        // =====================================================
        // API
        // =====================================================

        apiService =
                RetrofitClient.getApiService(
                        CandidateDashboardActivity.this
                );

        // =====================================================
        // FIND APPLY BUTTONS
        // =====================================================

        btnApplyGoogle = findViewById(R.id.btnApplyGoogle);
        btnApplyMicrosoft = findViewById(R.id.btnApplyMicrosoft);
        btnApplyAmazon = findViewById(R.id.btnApplyAmazon);

        // =====================================================
        // SEARCH
        // =====================================================

        etSearchJobs = findViewById(R.id.etSearchJobs);

        setupSearch();

        // =====================================================
        // RECENTLY POSTED
        // =====================================================

        txtRecent1 = findViewById(R.id.txtRecent1);
        txtRecent2 = findViewById(R.id.txtRecent2);
        txtRecent3 = findViewById(R.id.txtRecent3);
        txtRecent4 = findViewById(R.id.txtRecent4);

        // =====================================================
        // COMPANIES
        // =====================================================

        txtCompany1 = findViewById(R.id.txtCompany1);
        txtCompany2 = findViewById(R.id.txtCompany2);
        txtCompany3 = findViewById(R.id.txtCompany3);
        txtCompany4 = findViewById(R.id.txtCompany4);
        txtCompany5 = findViewById(R.id.txtCompany5);

        // =====================================================
        // TRENDING SKILLS
        // =====================================================

        txtSkill1 = findViewById(R.id.txtSkill1);
        txtSkill2 = findViewById(R.id.txtSkill2);
        txtSkill3 = findViewById(R.id.txtSkill3);
        txtSkill4 = findViewById(R.id.txtSkill4);
        txtSkill5 = findViewById(R.id.txtSkill5);

        // =====================================================
        // NOTIFICATIONS
        // =====================================================

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

        // =====================================================
        // VIEW ALL
        // =====================================================

        TextView txtViewAll = findViewById(R.id.txtViewAll);

        if (txtViewAll != null) {
            txtViewAll.setOnClickListener(v -> {

                if (etSearchJobs != null) {
                    etSearchJobs.setText("");
                }

                loadJobs();
            });
        }

        // =====================================================
        // MY PROFILE
        // =====================================================

        LinearLayout navProfile =
                findViewById(R.id.navProfile);

        if (navProfile != null) {

            navProfile.setOnClickListener(v -> {

                Intent intent = new Intent(
                        CandidateDashboardActivity.this,
                        CandidateProfileActivity.class
                );

                startActivity(intent);
            });
        }

        // =====================================================
        // MY APPLICATIONS
        // =====================================================

        LinearLayout navApplications =
                findViewById(R.id.navApplications);

        if (navApplications != null) {

            navApplications.setOnClickListener(v -> {

                Intent intent = new Intent(
                        CandidateDashboardActivity.this,
                        MyApplicationsActivity.class
                );

                startActivity(intent);
            });
        }

        // =====================================================
        // SAVED JOBS
        // =====================================================

        LinearLayout navSaved =
                findViewById(R.id.navSaved);

        if (navSaved != null) {

            navSaved.setOnClickListener(v -> {

                Intent intent = new Intent(
                        CandidateDashboardActivity.this,
                        SavedJobsActivity.class
                );

                startActivity(intent);
            });
        }

        // =====================================================
        // LOAD DASHBOARD
        // =====================================================

        loadDashboard();

        // =====================================================
        // LOAD JOBS
        // =====================================================

        loadJobs();
    }

    // =========================================================
    // LOAD COMPLETE DASHBOARD DATA
    // =========================================================

    private void loadDashboard() {

        apiService.getJobDashboard().enqueue(
                new Callback<JobDashboardResponse>() {

                    @Override
                    public void onResponse(
                            Call<JobDashboardResponse> call,
                            Response<JobDashboardResponse> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            JobDashboardResponse dashboard =
                                    response.body();

                            // -------------------------------
                            // RECENT JOBS
                            // -------------------------------

                            displayRecentJobs(
                                    dashboard.getRecentJobs()
                            );

                            // -------------------------------
                            // COMPANIES
                            // -------------------------------

                            displayCompanies(
                                    dashboard.getCompanies()
                            );

                            // -------------------------------
                            // TRENDING SKILLS
                            // -------------------------------

                            displayTrendingSkills(
                                    dashboard.getTrendingSkills()
                            );

                        } else {

                            Toast.makeText(
                                    CandidateDashboardActivity.this,
                                    "Failed to load dashboard",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<JobDashboardResponse> call,
                            Throwable t) {

                        Toast.makeText(
                                CandidateDashboardActivity.this,
                                "Unable to load dashboard data",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }

    // =========================================================
    // DISPLAY RECENTLY POSTED JOBS
    // =========================================================

    private void displayRecentJobs(
            List<JobResponse> jobs
    ) {

        resetRecentJobs();

        if (jobs == null || jobs.isEmpty()) {
            return;
        }

        if (jobs.size() > 0) {
            txtRecent1.setText(
                    formatRecentJob(jobs.get(0))
            );
        }

        if (jobs.size() > 1) {
            txtRecent2.setText(
                    formatRecentJob(jobs.get(1))
            );
        }

        if (jobs.size() > 2) {
            txtRecent3.setText(
                    formatRecentJob(jobs.get(2))
            );
        }

        if (jobs.size() > 3) {
            txtRecent4.setText(
                    formatRecentJob(jobs.get(3))
            );
        }
    }

    // =========================================================
    // FORMAT RECENT JOB
    // =========================================================

    private String formatRecentJob(JobResponse job) {

        if (job == null) {
            return "";
        }

        String company =
                safeText(
                        job.getCompanyName(),
                        "Company"
                );

        String title =
                safeText(
                        job.getTitle(),
                        "Job"
                );

        return company
                + "\n\n"
                + title;
    }

    // =========================================================
    // RESET RECENT JOBS
    // =========================================================

    private void resetRecentJobs() {

        txtRecent1.setText("");
        txtRecent2.setText("");
        txtRecent3.setText("");
        txtRecent4.setText("");
    }

    // =========================================================
    // DISPLAY COMPANIES
    // =========================================================

    private void displayCompanies(
            List<String> companies
    ) {

        resetCompanies();

        if (companies == null || companies.isEmpty()) {
            return;
        }

        if (companies.size() > 0) {
            txtCompany1.setText(
                    companies.get(0) + "                         ›"
            );
        }

        if (companies.size() > 1) {
            txtCompany2.setText(
                    companies.get(1) + "                         ›"
            );
        }

        if (companies.size() > 2) {
            txtCompany3.setText(
                    companies.get(2) + "                         ›"
            );
        }

        if (companies.size() > 3) {
            txtCompany4.setText(
                    companies.get(3) + "                         ›"
            );
        }

        if (companies.size() > 4) {
            txtCompany5.setText(
                    companies.get(4) + "                         ›"
            );
        }
    }

    // =========================================================
    // RESET COMPANIES
    // =========================================================

    private void resetCompanies() {

        txtCompany1.setText("");
        txtCompany2.setText("");
        txtCompany3.setText("");
        txtCompany4.setText("");
        txtCompany5.setText("");
    }

    // =========================================================
    // DISPLAY TRENDING SKILLS
    // =========================================================

    private void displayTrendingSkills(
            List<String> skills
    ) {

        resetTrendingSkills();

        if (skills == null || skills.isEmpty()) {
            return;
        }

        if (skills.size() > 0) {
            txtSkill1.setText(
                    skills.get(0) + "                         ›"
            );
        }

        if (skills.size() > 1) {
            txtSkill2.setText(
                    skills.get(1) + "                         ›"
            );
        }

        if (skills.size() > 2) {
            txtSkill3.setText(
                    skills.get(2) + "                         ›"
            );
        }

        if (skills.size() > 3) {
            txtSkill4.setText(
                    skills.get(3) + "                         ›"
            );
        }

        if (skills.size() > 4) {
            txtSkill5.setText(
                    skills.get(4) + "                         ›"
            );
        }
    }

    // =========================================================
    // RESET TRENDING SKILLS
    // =========================================================

    private void resetTrendingSkills() {

        txtSkill1.setText("");
        txtSkill2.setText("");
        txtSkill3.setText("");
        txtSkill4.setText("");
        txtSkill5.setText("");
    }

    // =========================================================
    // SEARCH SETUP
    // =========================================================

    private void setupSearch() {

        if (etSearchJobs == null) {
            return;
        }

        // Search when keyboard search button is pressed
        etSearchJobs.setOnEditorActionListener(
                (v, actionId, event) -> {

                    if (actionId == EditorInfo.IME_ACTION_SEARCH
                            || actionId == EditorInfo.IME_ACTION_DONE) {

                        String keyword =
                                etSearchJobs.getText()
                                        .toString()
                                        .trim();

                        if (keyword.isEmpty()) {
                            loadJobs();
                        } else {
                            searchJobs(keyword);
                        }

                        InputMethodManager imm =
                                (InputMethodManager)
                                        getSystemService(
                                                Context.INPUT_METHOD_SERVICE
                                        );

                        if (imm != null) {
                            imm.hideSoftInputFromWindow(
                                    v.getWindowToken(),
                                    0
                            );
                        }

                        return true;
                    }

                    if (event != null
                            && event.getKeyCode()
                            == KeyEvent.KEYCODE_ENTER
                            && event.getAction()
                            == KeyEvent.ACTION_DOWN) {

                        String keyword =
                                etSearchJobs.getText()
                                        .toString()
                                        .trim();

                        if (keyword.isEmpty()) {
                            loadJobs();
                        } else {
                            searchJobs(keyword);
                        }

                        return true;
                    }

                    return false;
                }
        );

        // Search automatically when the user types
        etSearchJobs.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {

                        String keyword =
                                s.toString().trim();

                        if (keyword.isEmpty()) {

                            loadJobs();

                        } else if (keyword.length() >= 2) {

                            searchJobs(keyword);
                        }
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s) {
                    }
                }
        );
    }

    // =========================================================
    // SEARCH JOBS
    // =========================================================

    private void searchJobs(String keyword) {

        apiService.searchJobs(keyword).enqueue(
                new Callback<List<JobResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<JobResponse>> call,
                            Response<List<JobResponse>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            List<JobResponse> jobs =
                                    response.body();

                            displaySearchResults(jobs);

                        } else {

                            Toast.makeText(
                                    CandidateDashboardActivity.this,
                                    "Search failed",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<JobResponse>> call,
                            Throwable t) {

                        Toast.makeText(
                                CandidateDashboardActivity.this,
                                "Unable to search jobs",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }

    // =========================================================
    // DISPLAY SEARCH RESULTS
    // =========================================================

    private void displaySearchResults(
            List<JobResponse> jobs
    ) {

        if (jobs == null || jobs.isEmpty()) {

            Toast.makeText(
                    CandidateDashboardActivity.this,
                    "No matching jobs found",
                    Toast.LENGTH_SHORT
            ).show();

            hideJobCard(btnApplyGoogle);
            hideJobCard(btnApplyMicrosoft);
            hideJobCard(btnApplyAmazon);

            return;
        }

        displayJob(
                jobs.size() > 0
                        ? jobs.get(0)
                        : null,
                btnApplyGoogle
        );

        displayJob(
                jobs.size() > 1
                        ? jobs.get(1)
                        : null,
                btnApplyMicrosoft
        );

        displayJob(
                jobs.size() > 2
                        ? jobs.get(2)
                        : null,
                btnApplyAmazon
        );
    }

    // =========================================================
    // LOAD NORMAL OPEN JOBS
    // =========================================================

    private void loadJobs() {

        apiService.getOpenJobs().enqueue(
                new Callback<List<JobResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<JobResponse>> call,
                            Response<List<JobResponse>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            List<JobResponse> jobs =
                                    response.body();

                            if (jobs.isEmpty()) {

                                Toast.makeText(
                                        CandidateDashboardActivity.this,
                                        "No jobs available",
                                        Toast.LENGTH_SHORT
                                ).show();

                                hideJobCard(btnApplyGoogle);
                                hideJobCard(btnApplyMicrosoft);
                                hideJobCard(btnApplyAmazon);

                                return;
                            }

                            // ---------------------------------
                            // SHOW MAXIMUM 3 REAL JOBS
                            // ---------------------------------

                            displayJob(
                                    jobs.size() > 0
                                            ? jobs.get(0)
                                            : null,
                                    btnApplyGoogle
                            );

                            displayJob(
                                    jobs.size() > 1
                                            ? jobs.get(1)
                                            : null,
                                    btnApplyMicrosoft
                            );

                            displayJob(
                                    jobs.size() > 2
                                            ? jobs.get(2)
                                            : null,
                                    btnApplyAmazon
                            );

                        } else {

                            Toast.makeText(
                                    CandidateDashboardActivity.this,
                                    "Failed to load jobs",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<JobResponse>> call,
                            Throwable t) {

                        Toast.makeText(
                                CandidateDashboardActivity.this,
                                "Unable to connect to server",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }

    // =========================================================
    // DISPLAY ONE JOB
    // =========================================================

    private void displayJob(
            JobResponse job,
            Button applyButton
    ) {

        if (job == null) {
            hideJobCard(applyButton);
            return;
        }

        if (applyButton == null) {
            return;
        }

        // Get the job card containing the button
        View parentView =
                (View) applyButton.getParent();

        if (!(parentView instanceof LinearLayout)) {
            return;
        }

        LinearLayout jobCard =
                (LinearLayout) parentView;

        jobCard.setVisibility(View.VISIBLE);

        // =====================================================
        // JOB CARD STRUCTURE
        //
        // 0 = company logo
        // 1 = job information
        // 2 = apply button
        // =====================================================

        if (jobCard.getChildCount() < 3) {
            return;
        }

        // =====================================================
        // COMPANY LOGO
        // =====================================================

        View logoView =
                jobCard.getChildAt(0);

        if (logoView instanceof TextView) {

            TextView logo =
                    (TextView) logoView;

            String companyName =
                    job.getCompanyName();

            if (companyName != null
                    && !companyName.isEmpty()) {

                logo.setText(
                        companyName
                                .substring(0, 1)
                                .toUpperCase()
                );

            } else {

                logo.setText("J");
            }

            logo.setTextColor(
                    Color.rgb(37, 99, 235)
            );
        }

        // =====================================================
        // JOB INFORMATION
        // =====================================================

        View informationView =
                jobCard.getChildAt(1);

        if (informationView instanceof LinearLayout) {

            LinearLayout informationLayout =
                    (LinearLayout) informationView;

            if (informationLayout.getChildCount() >= 4) {

                // ---------------------------------------------
                // COMPANY
                // ---------------------------------------------

                View companyView =
                        informationLayout.getChildAt(0);

                if (companyView instanceof TextView) {

                    TextView companyText =
                            (TextView) companyView;

                    companyText.setText(
                            safeText(
                                    job.getCompanyName(),
                                    "Company"
                            )
                    );
                }

                // ---------------------------------------------
                // TITLE
                // ---------------------------------------------

                View titleView =
                        informationLayout.getChildAt(1);

                if (titleView instanceof TextView) {

                    TextView titleText =
                            (TextView) titleView;

                    titleText.setText(
                            safeText(
                                    job.getTitle(),
                                    "Job"
                            )
                    );
                }

                // ---------------------------------------------
                // LOCATION + EXPERIENCE
                // ---------------------------------------------

                View locationView =
                        informationLayout.getChildAt(2);

                if (locationView instanceof TextView) {

                    TextView locationText =
                            (TextView) locationView;

                    String location =
                            safeText(
                                    job.getLocation(),
                                    "Location"
                            );

                    String experience =
                            safeText(
                                    job.getExperienceRequired(),
                                    "Experience not specified"
                            );

                    locationText.setText(
                            "📍 "
                                    + location
                                    + "  •  "
                                    + experience
                    );
                }

                // ---------------------------------------------
                // SKILLS
                // ---------------------------------------------

                View skillsView =
                        informationLayout.getChildAt(3);

                if (skillsView instanceof TextView) {

                    TextView skillsText =
                            (TextView) skillsView;

                    String skills =
                            safeText(
                                    job.getSkills(),
                                    "Skills not specified"
                            );

                    skillsText.setText(skills);
                }
            }
        }

        // =====================================================
        // APPLY BUTTON
        // =====================================================

        applyButton.setVisibility(View.VISIBLE);

        applyButton.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            CandidateDashboardActivity.this,
                            ApplyJobActivity.class
                    );

            // -------------------------------------------------
            // SEND REAL BACKEND JOB DATA
            // -------------------------------------------------

            intent.putExtra(
                    "job_id",
                    job.getId()
            );

            intent.putExtra(
                    "job_title",
                    job.getTitle()
            );

            intent.putExtra(
                    "company_name",
                    job.getCompanyName()
            );

            intent.putExtra(
                    "location",
                    job.getLocation()
            );

            intent.putExtra(
                    "description",
                    job.getDescription()
            );

            intent.putExtra(
                    "employment_type",
                    job.getEmploymentType()
            );

            intent.putExtra(
                    "experience_required",
                    job.getExperienceRequired()
            );

            intent.putExtra(
                    "skills",
                    job.getSkills()
            );

            startActivity(intent);
        });
    }

    // =========================================================
    // HIDE UNUSED JOB CARD
    // =========================================================

    private void hideJobCard(
            Button applyButton
    ) {

        if (applyButton == null) {
            return;
        }

        View parentView =
                (View) applyButton.getParent();

        if (parentView != null) {

            parentView.setVisibility(
                    View.GONE
            );
        }
    }

    // =========================================================
    // SAFE TEXT
    // =========================================================

    private String safeText(
            String value,
            String fallback
    ) {

        if (value == null
                || value.trim().isEmpty()) {

            return fallback;
        }

        return value;
    }
}