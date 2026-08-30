package com.example.cloudhire;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RecruiterMyJobsActivity extends AppCompatActivity {

    private LinearLayout jobsContainer;
    private LinearLayout emptyState;
    private TextView txtJobCount;
    private TextView txtNotificationBadge;

    private final List<Job> jobs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recruiter_my_jobs);

        initializeViews();

        loadMockJobs();

        displayJobs();

        setupClicks();

        updateNotificationBadge(3); // Mock notification count
    }

    private void updateNotificationBadge(int count) {
        if (txtNotificationBadge == null) return;
        if (count > 0) {
            txtNotificationBadge.setVisibility(View.VISIBLE);
            txtNotificationBadge.setText(String.valueOf(count));
        } else {
            txtNotificationBadge.setVisibility(View.GONE);
        }
    }


    // =========================================================
    // INITIALIZE VIEWS
    // =========================================================

    private void initializeViews() {

        jobsContainer = findViewById(R.id.jobsContainer);

        emptyState = findViewById(R.id.emptyState);

        txtJobCount = findViewById(R.id.txtJobCount);

        txtNotificationBadge = findViewById(R.id.txtJobsNotificationBadge);
    }


    // =========================================================
    // MOCK DATA
    // =========================================================

    private void loadMockJobs() {

        jobs.clear();

        jobs.add(new Job(
                "Java Developer",
                "NexTech Solutions",
                "Mangalore, Karnataka",
                "Full Time",
                "2 - 4 Years",
                "₹5 - ₹8 LPA",
                "12 Applicants",
                "OPEN",
                "18 Aug 2026"
        ));

        jobs.add(new Job(
                "Python Developer",
                "NexTech Solutions",
                "Mangalore, Karnataka",
                "Full Time",
                "1 - 3 Years",
                "₹4 - ₹7 LPA",
                "8 Applicants",
                "OPEN",
                "15 Aug 2026"
        ));

        jobs.add(new Job(
                "Frontend Developer",
                "NexTech Solutions",
                "Bangalore, Karnataka",
                "Full Time",
                "2 - 5 Years",
                "₹6 - ₹10 LPA",
                "15 Applicants",
                "CLOSED",
                "10 Aug 2026"
        ));
    }


    // =========================================================
    // DISPLAY JOBS
    // =========================================================

    private void displayJobs() {

        jobsContainer.removeAllViews();

        if (jobs.isEmpty()) {

            jobsContainer.setVisibility(View.GONE);

            emptyState.setVisibility(View.VISIBLE);

            txtJobCount.setText("0 Jobs");

            return;
        }

        jobsContainer.setVisibility(View.VISIBLE);

        emptyState.setVisibility(View.GONE);

        txtJobCount.setText(jobs.size() + " Jobs");

        for (Job job : jobs) {

            createJobCard(job);
        }
    }


    // =========================================================
    // CREATE JOB CARD
    // =========================================================

    private void createJobCard(Job job) {

        LinearLayout card = new LinearLayout(this);

        card.setOrientation(LinearLayout.VERTICAL);

        card.setPadding(
                dp(16),
                dp(16),
                dp(16),
                dp(14)
        );

        card.setBackgroundResource(
                R.drawable.recruiter_dashboard_card
        );

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        cardParams.setMargins(
                0,
                0,
                0,
                dp(16)
        );

        card.setLayoutParams(cardParams);


        // =====================================================
        // TOP ROW
        // =====================================================

        LinearLayout topRow = new LinearLayout(this);

        topRow.setOrientation(LinearLayout.HORIZONTAL);

        topRow.setGravity(Gravity.CENTER_VERTICAL);


        // Briefcase icon

        TextView icon = new TextView(this);

        icon.setText("💼");

        icon.setTextSize(25);

        icon.setGravity(Gravity.CENTER);

        topRow.addView(
                icon,
                new LinearLayout.LayoutParams(
                        dp(42),
                        dp(42)
                )
        );


        // Title + company

        LinearLayout titleContainer =
                new LinearLayout(this);

        titleContainer.setOrientation(
                LinearLayout.VERTICAL
        );


        TextView title =
                createText(
                        job.title,
                        17
                );

        title.setTypeface(
                null,
                Typeface.BOLD
        );


        TextView company =
                createText(
                        job.company,
                        13
                );

        company.setTextColor(
                Color.parseColor("#6B7280")
        );

        company.setPadding(
                0,
                dp(3),
                0,
                0
        );


        titleContainer.addView(title);

        titleContainer.addView(company);


        LinearLayout.LayoutParams titleParams =
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                );

        titleParams.setMargins(
                dp(10),
                0,
                dp(8),
                0
        );

        topRow.addView(
                titleContainer,
                titleParams
        );


        // Status

        TextView status =
                createText(
                        job.status,
                        10
                );

        status.setGravity(Gravity.CENTER);

        status.setTypeface(
                null,
                Typeface.BOLD
        );

        status.setTextColor(
                getStatusTextColor(job.status)
        );

        status.setBackground(
                getStatusBackground(job.status)
        );


        topRow.addView(
                status,
                new LinearLayout.LayoutParams(
                        dp(70),
                        dp(32)
                )
        );


        card.addView(topRow);


        // =====================================================
        // JOB INFORMATION
        // =====================================================

        addInfo(
                card,
                "📍  " + job.location
        );

        addInfo(
                card,
                "💼  " + job.employmentType
        );

        addInfo(
                card,
                "Experience: " + job.experience
        );

        addInfo(
                card,
                "Salary: " + job.salary
        );


        // =====================================================
        // APPLICANTS + POSTED DATE
        // =====================================================

        LinearLayout detailsRow =
                new LinearLayout(this);

        detailsRow.setOrientation(
                LinearLayout.HORIZONTAL
        );

        detailsRow.setGravity(
                Gravity.CENTER_VERTICAL
        );

        detailsRow.setPadding(
                0,
                dp(10),
                0,
                0
        );


        TextView applicants =
                createText(
                        job.applicants,
                        12
                );

        applicants.setTextColor(
                Color.parseColor("#6B7280")
        );


        TextView posted =
                createText(
                        "Posted: " + job.postedDate,
                        12
                );

        posted.setTextColor(
                Color.parseColor("#6B7280")
        );


        detailsRow.addView(
                applicants,
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                )
        );

        detailsRow.addView(posted);

        card.addView(detailsRow);


        // =====================================================
        // DIVIDER
        // =====================================================

        View divider = new View(this);

        divider.setBackgroundColor(
                Color.parseColor("#E5E7EB")
        );

        LinearLayout.LayoutParams dividerParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(1)
                );

        dividerParams.setMargins(
                0,
                dp(14),
                0,
                dp(8)
        );

        card.addView(
                divider,
                dividerParams
        );


        // =====================================================
        // ACTION ROW
        // =====================================================

        LinearLayout actionRow =
                new LinearLayout(this);

        actionRow.setOrientation(
                LinearLayout.HORIZONTAL
        );

        actionRow.setGravity(
                Gravity.CENTER
        );


        TextView viewDetails =
                createAction("View Details");

        TextView edit =
                createAction("Edit");

        TextView close =
                createAction(
                        job.status.equals("OPEN")
                                ? "Close"
                                : "Delete"
                );


        // View Details

        viewDetails.setOnClickListener(v -> {

            Toast.makeText(
                    this,
                    "Opening " + job.title,
                    Toast.LENGTH_SHORT
            ).show();

        });


        // Edit

        edit.setOnClickListener(v -> {

            Toast.makeText(
                    this,
                    "Edit " + job.title,
                    Toast.LENGTH_SHORT
            ).show();

        });


        // Close / Delete

        close.setOnClickListener(v -> {

            if (job.status.equals("OPEN")) {

                job.status = "CLOSED";

                displayJobs();

                Toast.makeText(
                        this,
                        "Job closed",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                jobs.remove(job);

                displayJobs();

                Toast.makeText(
                        this,
                        "Job deleted",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });


        actionRow.addView(
                viewDetails,
                new LinearLayout.LayoutParams(
                        0,
                        dp(44),
                        1
                )
        );

        actionRow.addView(
                edit,
                new LinearLayout.LayoutParams(
                        0,
                        dp(44),
                        1
                )
        );

        actionRow.addView(
                close,
                new LinearLayout.LayoutParams(
                        0,
                        dp(44),
                        1
                )
        );


        card.addView(actionRow);


        // Clicking whole card

        card.setOnClickListener(v -> {

            Toast.makeText(
                    this,
                    "Job Details: " + job.title,
                    Toast.LENGTH_SHORT
            ).show();

        });


        jobsContainer.addView(card);
    }


    // =========================================================
    // ADD INFORMATION
    // =========================================================

    private void addInfo(
            LinearLayout parent,
            String text
    ) {

        TextView info =
                createText(
                        text,
                        13
                );

        info.setTextColor(
                Color.parseColor("#6B7280")
        );

        info.setPadding(
                0,
                dp(8),
                0,
                0
        );

        parent.addView(info);
    }


    // =========================================================
    // ACTION TEXT
    // =========================================================

    private TextView createAction(
            String text
    ) {

        TextView action =
                createText(
                        text,
                        13
                );

        action.setGravity(
                Gravity.CENTER
        );

        action.setTextColor(
                Color.parseColor("#2563EB")
        );

        action.setTypeface(
                null,
                Typeface.BOLD
        );

        action.setClickable(true);

        action.setFocusable(true);

        return action;
    }


    // =========================================================
    // CREATE TEXT
    // =========================================================

    private TextView createText(
            String text,
            float size
    ) {

        TextView textView =
                new TextView(this);

        textView.setText(text);

        textView.setTextSize(size);

        textView.setTextColor(
                Color.parseColor("#111827")
        );

        return textView;
    }


    // =========================================================
    // STATUS BACKGROUND
    // =========================================================

    private GradientDrawable getStatusBackground(
            String status
    ) {

        GradientDrawable drawable =
                new GradientDrawable();

        if (status.equals("OPEN")) {

            drawable.setColor(
                    Color.parseColor("#DCFCE7")
            );

        } else {

            drawable.setColor(
                    Color.parseColor("#FEE2E2")
            );
        }

        drawable.setCornerRadius(
                dp(20)
        );

        return drawable;
    }


    // =========================================================
    // STATUS TEXT
    // =========================================================

    private int getStatusTextColor(
            String status
    ) {

        if (status.equals("OPEN")) {

            return Color.parseColor("#15803D");

        } else {

            return Color.parseColor("#B91C1C");
        }
    }


    // =========================================================
    // CLICK EVENTS
    // =========================================================

    private void setupClicks() {

        // Post Job

        findViewById(R.id.btnPostJob)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            RecruiterMyJobsActivity.this,
                            RecruiterPostJobActivity.class
                    );

                    startActivity(intent);

                });


        // Empty state Post Job

        findViewById(R.id.btnEmptyPostJob)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            RecruiterMyJobsActivity.this,
                            RecruiterPostJobActivity.class
                    );

                    startActivity(intent);

                });


        // Notifications

        findViewById(R.id.btnJobsNotifications)
                .setOnClickListener(v -> {

                    Intent intent =
                            new Intent(
                                    RecruiterMyJobsActivity.this,
                                    RecruiterNotificationsActivity.class
                            );

                    startActivity(intent);

                });


        // Home

        findViewById(R.id.navHome)
                .setOnClickListener(v -> {

                    Intent intent =
                            new Intent(
                                    RecruiterMyJobsActivity.this,
                                    RecruiterDashboardActivity.class
                            );

                    startActivity(intent);

                    finish();

                });


        // Jobs

        findViewById(R.id.navJobs)
                .setOnClickListener(v -> {
                    // Already on My Jobs
                });


        // Applicants

        findViewById(R.id.navApplicants)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            RecruiterMyJobsActivity.this,
                            RecruiterApplicantsActivity.class
                    );

                    startActivity(intent);

                });


        // Profile

        findViewById(R.id.navProfile)
                .setOnClickListener(v -> {

                    Intent intent =
                            new Intent(
                                    RecruiterMyJobsActivity.this,
                                    RecruiterProfileActivity.class
                            );

                    startActivity(intent);

                    finish();

                });


        // Menu

        findViewById(R.id.btnJobsMenu)
                .setOnClickListener(v -> {

                    Toast.makeText(
                            this,
                            "Menu",
                            Toast.LENGTH_SHORT
                    ).show();

                });
    }


    // =========================================================
    // DP CONVERSION
    // =========================================================

    private int dp(int value) {

        return (int) (
                value *
                        getResources()
                                .getDisplayMetrics()
                                .density
                        + 0.5f
        );
    }


    // =========================================================
    // JOB MODEL
    // =========================================================

    private static class Job {

        String title;
        String company;
        String location;
        String employmentType;
        String experience;
        String salary;
        String applicants;
        String status;
        String postedDate;


        Job(
                String title,
                String company,
                String location,
                String employmentType,
                String experience,
                String salary,
                String applicants,
                String status,
                String postedDate
        ) {

            this.title = title;
            this.company = company;
            this.location = location;
            this.employmentType = employmentType;
            this.experience = experience;
            this.salary = salary;
            this.applicants = applicants;
            this.status = status;
            this.postedDate = postedDate;
        }
    }
}