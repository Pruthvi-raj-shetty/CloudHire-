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

public class AdminUsersActivity extends AppCompatActivity {

    private EditText etUserSearch;
    private TextView tabCandidates, tabRecruiters;
    private LinearLayout userListContainer;

    private final List<AdminUser> allUsers = new ArrayList<>();
    private String selectedRole = "Candidate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        etUserSearch = findViewById(R.id.etUserSearch);
        tabCandidates = findViewById(R.id.tabCandidates);
        tabRecruiters = findViewById(R.id.tabRecruiters);
        userListContainer = findViewById(R.id.userListContainer);

        setupNavigation();
        setupTabs();
        loadMockUsers();
        setupSearch();
        
        displayUsers();
    }

    private void setupNavigation() {
        findViewById(R.id.navAdminHome).setOnClickListener(v -> startActivity(new Intent(this, AdminDashboardActivity.class)));

        findViewById(R.id.navAdminUsers).setOnClickListener(v -> {
            // Already here
        });

        findViewById(R.id.navAdminJobs).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminJobsActivity.class));
        });

        findViewById(R.id.navAdminApps).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminApplicationsActivity.class));
        });

        findViewById(R.id.btnAdminUsersBack).setOnClickListener(v -> finish());
    }

    private void setupTabs() {
        tabCandidates.setOnClickListener(v -> {
            selectedRole = "Candidate";
            updateTabUI();
            displayUsers();
        });

        tabRecruiters.setOnClickListener(v -> {
            selectedRole = "Recruiter";
            updateTabUI();
            displayUsers();
        });
    }

    private void updateTabUI() {
        if (selectedRole.equals("Candidate")) {
            tabCandidates.setBackgroundResource(R.drawable.bg_filter_active);
            tabCandidates.setTextColor(Color.WHITE);
            tabCandidates.setTypeface(null, android.graphics.Typeface.BOLD);

            tabRecruiters.setBackgroundResource(R.drawable.bg_filter_inactive);
            tabRecruiters.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.dashboard_text_secondary));
            tabRecruiters.setTypeface(null, android.graphics.Typeface.NORMAL);
        } else {
            tabRecruiters.setBackgroundResource(R.drawable.bg_filter_active);
            tabRecruiters.setTextColor(Color.WHITE);
            tabRecruiters.setTypeface(null, android.graphics.Typeface.BOLD);

            tabCandidates.setBackgroundResource(R.drawable.bg_filter_inactive);
            tabCandidates.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.dashboard_text_secondary));
            tabCandidates.setTypeface(null, android.graphics.Typeface.NORMAL);
        }
    }

    private void setupSearch() {
        etUserSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { displayUsers(); }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void loadMockUsers() {
        allUsers.add(new AdminUser("1", "Arjun Sharma", "arjun.s@email.com", "+91 9876543210", "Candidate", "Active"));
        allUsers.add(new AdminUser("2", "Priya Patel", "priya.p@email.com", "+91 8765432109", "Candidate", "Active"));
        allUsers.add(new AdminUser("3", "Rohan Gupta", "rohan.g@email.com", "+91 7654321098", "Candidate", "Inactive"));
        allUsers.add(new AdminUser("4", "NexTech Solutions", "hr@nextech.com", "+91 9988776655", "Recruiter", "Active"));
        allUsers.add(new AdminUser("5", "Design Pros", "hiring@designpros.io", "+91 8877665544", "Recruiter", "Active"));
    }

    private void displayUsers() {
        userListContainer.removeAllViews();
        String query = etUserSearch.getText().toString().toLowerCase();

        for (AdminUser user : allUsers) {
            if (user.getRole().equals(selectedRole)) {
                if (query.isEmpty() || user.getName().toLowerCase().contains(query) || user.getEmail().toLowerCase().contains(query)) {
                    addUserCard(user);
                }
            }
        }
    }

    private void addUserCard(AdminUser user) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(16), dp(16), dp(16), dp(16));
        card.setBackgroundResource(R.drawable.recruiter_dashboard_card);
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, 0, 0, dp(16));
        card.setLayoutParams(params);

        TextView tvName = new TextView(this);
        tvName.setText(user.getName());
        tvName.setTextSize(17);
        tvName.setTypeface(null, android.graphics.Typeface.BOLD);
        tvName.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.dashboard_text_primary));
        card.addView(tvName);

        TextView tvEmail = new TextView(this);
        tvEmail.setText(user.getEmail());
        tvEmail.setTextSize(14);
        tvEmail.setTextColor(getResources().getColor(R.color.dashboard_text_secondary));
        tvEmail.setPadding(0, dp(4), 0, 0);
        card.addView(tvEmail);

        LinearLayout statusRow = new LinearLayout(this);
        statusRow.setOrientation(LinearLayout.HORIZONTAL);
        statusRow.setGravity(android.view.Gravity.CENTER_VERTICAL);
        statusRow.setPadding(0, dp(12), 0, 0);

        TextView tvStatus = new TextView(this);
        tvStatus.setText(user.getStatus());
        tvStatus.setTextSize(11);
        tvStatus.setPadding(dp(12), dp(4), dp(12), dp(4));
        
        int statusColor = user.getStatus().equalsIgnoreCase("Active") ? Color.parseColor("#15803D") : Color.parseColor("#B91C1C");
        int statusBg = user.getStatus().equalsIgnoreCase("Active") ? Color.parseColor("#DCFCE7") : Color.parseColor("#FEE2E2");

        tvStatus.setTextColor(statusColor);
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setColor(statusBg);
        gd.setCornerRadius(dp(20));
        tvStatus.setBackground(gd);
        statusRow.addView(tvStatus);

        View spacer = new View(this);
        statusRow.addView(spacer, new LinearLayout.LayoutParams(0, 0, 1));

        TextView btnAction = new TextView(this);
        btnAction.setText(user.getStatus().equalsIgnoreCase("Active") ? "Deactivate" : "Activate");
        btnAction.setTextSize(13);
        btnAction.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.dashboard_primary));
        btnAction.setTypeface(null, android.graphics.Typeface.BOLD);
        btnAction.setOnClickListener(v -> {
            String newStatus = user.getStatus().equalsIgnoreCase("Active") ? "Inactive" : "Active";
            user.setStatus(newStatus);
            displayUsers();
            Toast.makeText(this, "User " + newStatus, Toast.LENGTH_SHORT).show();
        });
        statusRow.addView(btnAction);

        card.addView(statusRow);
        userListContainer.addView(card);
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
