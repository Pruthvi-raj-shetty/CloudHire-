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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AdminJobsActivity extends AppCompatActivity {

    private EditText etJobSearch;
    private TextView filterJobAll, filterJobOpen, filterJobClosed;
    private LinearLayout jobListContainer;

    private List<Job> allJobs = new ArrayList<>();
    private String selectedFilter = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_jobs);

        etJobSearch = findViewById(R.id.etJobSearch);
        filterJobAll = findViewById(R.id.filterJobAll);
        filterJobOpen = findViewById(R.id.filterJobOpen);
        filterJobClosed = findViewById(R.id.filterJobClosed);
        jobListContainer = findViewById(R.id.jobListContainer);

        setupNavigation();
        setupFilters();
        loadMockJobs();
        setupSearch();
        
        displayJobs();
    }

    private void setupNavigation() {
        findViewById(R.id.navAdminHome).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminDashboardActivity.class));
        });

        findViewById(R.id.navAdminUsers).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminUsersActivity.class));
        });

        findViewById(R.id.navAdminJobs).setOnClickListener(v -> {
            // Already here
        });

        findViewById(R.id.navAdminApps).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminApplicationsActivity.class));
        });

        findViewById(R.id.btnAdminJobsBack).setOnClickListener(v -> finish());
    }

    private void setupFilters() {
        filterJobAll.setOnClickListener(v -> { selectedFilter = "ALL"; updateFilterUI(); displayJobs(); });
        filterJobOpen.setOnClickListener(v -> { selectedFilter = "Active"; updateFilterUI(); displayJobs(); });
        filterJobClosed.setOnClickListener(v -> { selectedFilter = "Closed"; updateFilterUI(); displayJobs(); });
    }

    private void updateFilterUI() {
        filterJobAll.setBackgroundResource(selectedFilter.equals("ALL") ? R.drawable.bg_filter_active : R.drawable.bg_filter_inactive);
        filterJobAll.setTextColor(selectedFilter.equals("ALL") ? Color.WHITE : getResources().getColor(R.color.dashboard_text_secondary));
        
        filterJobOpen.setBackgroundResource(selectedFilter.equals("Active") ? R.drawable.bg_filter_active : R.drawable.bg_filter_inactive);
        filterJobOpen.setTextColor(selectedFilter.equals("Active") ? Color.WHITE : getResources().getColor(R.color.dashboard_text_secondary));
        
        filterJobClosed.setBackgroundResource(selectedFilter.equals("Closed") ? R.drawable.bg_filter_active : R.drawable.bg_filter_inactive);
        filterJobClosed.setTextColor(selectedFilter.equals("Closed") ? Color.WHITE : getResources().getColor(R.color.dashboard_text_secondary));
    }

    private void setupSearch() {
        etJobSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { displayJobs(); }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void loadMockJobs() {
        allJobs.add(new Job("1", "Senior Java Developer", "NexTech Solutions", "12 Applicants", "30 Aug 2026", "Active", "Bangalore", "Full Time", "3-5 Years", "₹12-18 LPA", 
            "We are looking for a Senior Java Developer to lead our backend team. You will be responsible for designing and implementing high-performance services.", 
            "Java, Spring Boot, Microservices, PostgreSQL", "NexHire Platform", ""));
            
        allJobs.add(new Job("2", "UI/UX Designer", "Design Pros", "8 Applicants", "28 Aug 2026", "Active", "Remote", "Contract", "2+ Years", "₹8-12 LPA", 
            "Design Pros is seeking a creative UI/UX Designer to craft beautiful user experiences for our global clients.", 
            "Figma, Adobe XD, User Research, Prototyping", "External Link", "https://designpros.com/careers/apply"));
            
        allJobs.add(new Job("3", "Python Engineer", "Data Analytics", "15 Applicants", "25 Aug 2026", "Closed", "Mumbai", "Full Time", "4-6 Years", "₹15-22 LPA", 
            "Join our data team to build scalable data pipelines and analytics engines using Python and Big Data technologies.", 
            "Python, Django, AWS, SQL, Spark", "NexHire Platform", ""));
    }

    private void displayJobs() {
        jobListContainer.removeAllViews();
        String query = etJobSearch.getText().toString().toLowerCase();

        for (Job job : allJobs) {
            if (selectedFilter.equals("ALL") || job.getStatus().equalsIgnoreCase(selectedFilter)) {
                if (query.isEmpty() || job.getTitle().toLowerCase().contains(query) || job.getCompany().toLowerCase().contains(query)) {
                    addJobCard(job);
                }
            }
        }
    }

    private void addJobCard(Job job) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(16), dp(16), dp(16), dp(16));
        card.setBackgroundResource(R.drawable.recruiter_dashboard_card);
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, 0, 0, dp(16));
        card.setLayoutParams(params);

        TextView tvTitle = new TextView(this);
        tvTitle.setText(job.getTitle());
        tvTitle.setTextSize(17);
        tvTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        tvTitle.setTextColor(getResources().getColor(R.color.dashboard_text_primary));
        card.addView(tvTitle);

        TextView tvCompany = new TextView(this);
        tvCompany.setText(job.getCompany() + " | " + job.getLocation());
        tvCompany.setTextSize(13);
        tvCompany.setTextColor(getResources().getColor(R.color.dashboard_text_secondary));
        tvCompany.setPadding(0, dp(4), 0, 0);
        card.addView(tvCompany);

        TextView tvDetails = new TextView(this);
        tvDetails.setText(job.getType() + " • " + job.getExperience() + " • " + job.getSalary());
        tvDetails.setTextSize(12);
        tvDetails.setTextColor(getResources().getColor(R.color.dashboard_text_secondary));
        tvDetails.setPadding(0, dp(4), 0, 0);
        card.addView(tvDetails);

        LinearLayout actionRow = new LinearLayout(this);
        actionRow.setOrientation(LinearLayout.HORIZONTAL);
        actionRow.setGravity(android.view.Gravity.CENTER_VERTICAL);
        actionRow.setPadding(0, dp(12), 0, 0);

        TextView tvStatus = new TextView(this);
        tvStatus.setText(job.getStatus());
        tvStatus.setTextSize(11);
        tvStatus.setPadding(dp(12), dp(4), dp(12), dp(4));
        tvStatus.setTextColor(job.getStatus().equalsIgnoreCase("Active") ? Color.parseColor("#15803D") : Color.parseColor("#B91C1C"));
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setColor(job.getStatus().equalsIgnoreCase("Active") ? Color.parseColor("#DCFCE7") : Color.parseColor("#FEE2E2"));
        gd.setCornerRadius(dp(20));
        tvStatus.setBackground(gd);
        actionRow.addView(tvStatus);

        View spacer = new View(this);
        actionRow.addView(spacer, new LinearLayout.LayoutParams(0, 0, 1));

        TextView btnView = new TextView(this);
        btnView.setText("View Details");
        btnView.setTextSize(13);
        btnView.setTextColor(getResources().getColor(R.color.dashboard_primary));
        btnView.setTypeface(null, android.graphics.Typeface.BOLD);
        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminJobDetailsActivity.class);
            intent.putExtra("jobId", job.getId());
            intent.putExtra("title", job.getTitle());
            intent.putExtra("company", job.getCompany());
            intent.putExtra("location", job.getLocation());
            intent.putExtra("type", job.getType());
            intent.putExtra("experience", job.getExperience());
            intent.putExtra("salary", job.getSalary());
            intent.putExtra("status", job.getStatus());
            intent.putExtra("postedDate", job.getPostedText());
            intent.putExtra("description", job.getDescription());
            intent.putExtra("skills", job.getSkills());
            intent.putExtra("appMethod", job.getApplicationMethod());
            intent.putExtra("appUrl", job.getApplicationUrl());
            startActivity(intent);
        });
        actionRow.addView(btnView);

        TextView btnAction = new TextView(this);
        btnAction.setText(job.getStatus().equalsIgnoreCase("Active") ? "Close" : "Remove");
        btnAction.setTextSize(13);
        btnAction.setPadding(dp(12), 0, 0, 0);
        btnAction.setTextColor(Color.RED);
        btnAction.setTypeface(null, android.graphics.Typeface.BOLD);
        btnAction.setOnClickListener(v -> {
            if (job.getStatus().equalsIgnoreCase("Active")) job.setStatus("Closed");
            else allJobs.remove(job);
            displayJobs();
            Toast.makeText(this, "Action completed", Toast.LENGTH_SHORT).show();
        });
        actionRow.addView(btnAction);

        card.addView(actionRow);
        jobListContainer.addView(card);
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
