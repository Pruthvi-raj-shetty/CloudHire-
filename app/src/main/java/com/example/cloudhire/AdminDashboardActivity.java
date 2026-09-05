package com.example.cloudhire;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    private LinearLayout recentActivityContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        recentActivityContainer = findViewById(R.id.recentActivityContainer);

        setupNavigation();
        loadMockActivity();
    }

    private void setupNavigation() {
        findViewById(R.id.navAdminHome).setOnClickListener(v -> {
            // Already on Dashboard
        });

        findViewById(R.id.navAdminUsers).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminUsersActivity.class));
        });

        findViewById(R.id.navAdminJobs).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminJobsActivity.class));
        });

        findViewById(R.id.navAdminApps).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminApplicationsActivity.class));
        });

        findViewById(R.id.btnAdminBack).setOnClickListener(v -> {
            // Logout and go to role selection
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadMockActivity() {
        recentActivityContainer.removeAllViews();
        
        addActivityItem("System", "John Doe registered as Candidate", "2m ago", "👤", "#3B82F6");
        addActivityItem("Hiring", "Global Tech posted 'Java Engineer'", "15m ago", "💼", "#F59E0B");
        addActivityItem("System", "NexTech Solutions verified", "1h ago", "🏢", "#10B981");
        addActivityItem("Apply", "Priya Patel applied for UX Designer", "3h ago", "📄", "#8B5CF6");
        addActivityItem("Security", "Admin password changed", "Yesterday", "🔒", "#EF4444");
    }

    private void addActivityItem(String type, String message, String time, String icon, String typeColor) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(dp(16), dp(16), dp(16), dp(16));
        row.setGravity(android.view.Gravity.CENTER_VERTICAL);

        // Icon Circle
        TextView tvIcon = new TextView(this);
        tvIcon.setText(icon);
        tvIcon.setTextSize(18);
        tvIcon.setGravity(android.view.Gravity.CENTER);
        tvIcon.setLayoutParams(new LinearLayout.LayoutParams(dp(44), dp(44)));
        
        android.graphics.drawable.GradientDrawable circle = new android.graphics.drawable.GradientDrawable();
        circle.setShape(android.graphics.drawable.GradientDrawable.OVAL);
        circle.setColor(Color.parseColor(typeColor + "15")); // 15 is hex for ~8% opacity
        tvIcon.setBackground(circle);
        row.addView(tvIcon);

        // Text Content
        LinearLayout textContent = new LinearLayout(this);
        textContent.setOrientation(LinearLayout.VERTICAL);
        textContent.setPadding(dp(16), 0, 0, 0);
        textContent.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1));

        TextView tvType = new TextView(this);
        tvType.setText(type.toUpperCase());
        tvType.setTextSize(10);
        tvType.setLetterSpacing(0.1f);
        tvType.setTextColor(Color.parseColor(typeColor));
        tvType.setTypeface(null, android.graphics.Typeface.BOLD);
        
        TextView tvMsg = new TextView(this);
        tvMsg.setText(message);
        tvMsg.setTextColor(Color.parseColor("#334155"));
        tvMsg.setTextSize(14);
        tvMsg.setPadding(0, dp(2), 0, 0);

        textContent.addView(tvType);
        textContent.addView(tvMsg);
        row.addView(textContent);

        // Time
        TextView tvTime = new TextView(this);
        tvTime.setText(time);
        tvTime.setTextColor(Color.parseColor("#94A3B8"));
        tvTime.setTextSize(11);
        row.addView(tvTime);

        recentActivityContainer.addView(row);

        // Add separator
        View divider = new View(this);
        divider.setLayoutParams(new LinearLayout.LayoutParams(-1, dp(1)));
        divider.setBackgroundColor(Color.parseColor("#F1F5F9"));
        recentActivityContainer.addView(divider);
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
