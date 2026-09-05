package com.example.cloudhire;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AdminApplicationsActivity extends AppCompatActivity {

    private EditText etAppSearch;
    private TextView filterAppAll, filterAppApplied, filterAppShortlisted, filterAppRejected;
    private LinearLayout appListContainer;

    private List<AdminApplication> allApps = new ArrayList<>();
    private String selectedFilter = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_applications);

        etAppSearch = findViewById(R.id.etAppSearch);
        filterAppAll = findViewById(R.id.filterAppAll);
        filterAppApplied = findViewById(R.id.filterAppApplied);
        filterAppShortlisted = findViewById(R.id.filterAppShortlisted);
        filterAppRejected = findViewById(R.id.filterAppRejected);
        appListContainer = findViewById(R.id.appListContainer);

        setupNavigation();
        setupFilters();
        loadMockApps();
        setupSearch();
        
        displayApps();
    }

    private void setupNavigation() {
        findViewById(R.id.navAdminHome).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminDashboardActivity.class));
        });

        findViewById(R.id.navAdminUsers).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminUsersActivity.class));
        });

        findViewById(R.id.navAdminJobs).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminJobsActivity.class));
        });

        findViewById(R.id.navAdminApps).setOnClickListener(v -> {
            // Already here
        });

        findViewById(R.id.btnAdminAppsBack).setOnClickListener(v -> finish());
    }

    private void setupFilters() {
        filterAppAll.setOnClickListener(v -> { selectedFilter = "ALL"; updateFilterUI(); displayApps(); });
        filterAppApplied.setOnClickListener(v -> { selectedFilter = "Applied"; updateFilterUI(); displayApps(); });
        filterAppShortlisted.setOnClickListener(v -> { selectedFilter = "Shortlisted"; updateFilterUI(); displayApps(); });
        filterAppRejected.setOnClickListener(v -> { selectedFilter = "Rejected"; updateFilterUI(); displayApps(); });
    }

    private void updateFilterUI() {
        TextView[] filters = {filterAppAll, filterAppApplied, filterAppShortlisted, filterAppRejected};
        String[] tags = {"ALL", "Applied", "Shortlisted", "Rejected"};
        
        for (int i = 0; i < filters.length; i++) {
            boolean isSelected = selectedFilter.equals(tags[i]);
            filters[i].setBackgroundResource(isSelected ? R.drawable.bg_filter_active : R.drawable.bg_filter_inactive);
            filters[i].setTextColor(isSelected ? Color.WHITE : getResources().getColor(R.color.dashboard_text_secondary));
        }
    }

    private void setupSearch() {
        etAppSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { displayApps(); }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void loadMockApps() {
        allApps.add(new AdminApplication("1", "JOB1", "Arjun Sharma", "arjun.s@email.com", "+91 9876543210", "Java Developer | 3 Years Exp", "Java, Spring Boot, SQL", "Senior Java Developer", "NexTech Solutions", "Bangalore", "Full Time", "Shortlisted", "30 Aug 2026", "30 Aug 2026"));
        allApps.add(new AdminApplication("2", "JOB2", "Priya Patel", "priya.p@email.com", "+91 8765432109", "UI/UX Designer | 2 Years Exp", "Figma, Adobe XD, Prototyping", "Product Designer", "Design Pros", "Remote", "Contract", "Applied", "28 Aug 2026", "29 Aug 2026"));
        allApps.add(new AdminApplication("3", "JOB1", "Rohan Gupta", "rohan.g@email.com", "+91 7654321098", "Full Stack Developer | 4 Years Exp", "React, Node.js, MongoDB", "Senior Java Developer", "NexTech Solutions", "Bangalore", "Full Time", "Rejected", "25 Aug 2026", "26 Aug 2026"));
    }

    private void displayApps() {
        appListContainer.removeAllViews();
        String query = etAppSearch.getText().toString().toLowerCase();

        for (AdminApplication app : allApps) {
            if (selectedFilter.equals("ALL") || app.getStatus().equalsIgnoreCase(selectedFilter)) {
                if (query.isEmpty() || app.getCandidateName().toLowerCase().contains(query) || app.getJobTitle().toLowerCase().contains(query)) {
                    addAppCard(app);
                }
            }
        }
    }

    private void addAppCard(AdminApplication app) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(16), dp(16), dp(16), dp(16));
        card.setBackgroundResource(R.drawable.recruiter_dashboard_card);
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, 0, 0, dp(16));
        card.setLayoutParams(params);

        TextView tvCandidate = new TextView(this);
        tvCandidate.setText(app.getCandidateName());
        tvCandidate.setTextSize(17);
        tvCandidate.setTypeface(null, android.graphics.Typeface.BOLD);
        tvCandidate.setTextColor(getResources().getColor(R.color.dashboard_text_primary));
        card.addView(tvCandidate);

        TextView tvJob = new TextView(this);
        tvJob.setText(app.getJobTitle() + " @ " + app.getCompanyName());
        tvJob.setTextSize(14);
        tvJob.setTextColor(getResources().getColor(R.color.dashboard_primary));
        tvJob.setPadding(0, dp(4), 0, 0);
        card.addView(tvJob);

        TextView tvDetails = new TextView(this);
        tvDetails.setText(app.getLocation() + " | " + app.getEmploymentType());
        tvDetails.setTextSize(12);
        tvDetails.setTextColor(getResources().getColor(R.color.dashboard_text_secondary));
        tvDetails.setPadding(0, dp(4), 0, 0);
        card.addView(tvDetails);

        LinearLayout statusRow = new LinearLayout(this);
        statusRow.setOrientation(LinearLayout.HORIZONTAL);
        statusRow.setGravity(android.view.Gravity.CENTER_VERTICAL);
        statusRow.setPadding(0, dp(12), 0, 0);

        TextView tvStatus = new TextView(this);
        tvStatus.setText(app.getStatus());
        tvStatus.setTextSize(11);
        tvStatus.setPadding(dp(12), dp(4), dp(12), dp(4));
        
        int statusColor = Color.parseColor("#2563EB");
        int statusBg = Color.parseColor("#EFF6FF");
        if (app.getStatus().equalsIgnoreCase("Shortlisted")) { statusColor = Color.parseColor("#15803D"); statusBg = Color.parseColor("#DCFCE7"); }
        if (app.getStatus().equalsIgnoreCase("Rejected")) { statusColor = Color.parseColor("#B91C1C"); statusBg = Color.parseColor("#FEE2E2"); }

        tvStatus.setTextColor(statusColor);
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setColor(statusBg);
        gd.setCornerRadius(dp(20));
        tvStatus.setBackground(gd);
        statusRow.addView(tvStatus);

        View spacer = new View(this);
        statusRow.addView(spacer, new LinearLayout.LayoutParams(0, 0, 1));

        TextView btnView = new TextView(this);
        btnView.setText("View Details");
        btnView.setTextSize(13);
        btnView.setTextColor(getResources().getColor(R.color.dashboard_primary));
        btnView.setTypeface(null, android.graphics.Typeface.BOLD);
        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminApplicationDetailsActivity.class);
            intent.putExtra("applicationId", app.getApplicationId());
            intent.putExtra("candidateName", app.getCandidateName());
            intent.putExtra("candidateEmail", app.getCandidateEmail());
            intent.putExtra("candidatePhone", app.getCandidatePhone());
            intent.putExtra("candidateTitle", app.getCandidateTitle());
            intent.putExtra("candidateSkills", app.getCandidateSkills());
            intent.putExtra("jobTitle", app.getJobTitle());
            intent.putExtra("companyName", app.getCompanyName());
            intent.putExtra("location", app.getLocation());
            intent.putExtra("employmentType", app.getEmploymentType());
            intent.putExtra("status", app.getStatus());
            intent.putExtra("appliedAt", app.getAppliedAt());
            startActivity(intent);
        });
        statusRow.addView(btnView);

        card.addView(statusRow);
        appListContainer.addView(card);
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
