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

    private TextView filterAll;
    private TextView filterOpen;
    private TextView filterClosed;

    private String selectedFilter = "ALL";

    private final List<Job> jobs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recruiter_my_jobs);

        initializeViews();
        setupFilters();

        loadMockJobs();

        // Handle filter from Intent
        String filter = getIntent().getStringExtra("filter");
        if (filter != null) {
            applyInitialFilter(filter);
        }

        displayJobs();

        setupClicks();

        updateNotificationBadge(3); // Mock notification count
    }

    private void applyInitialFilter(String filter) {
        selectedFilter = filter;
        TextView selectedView;

        if (filter.equals("OPEN")) {
            selectedView = filterOpen;
        } else if (filter.equals("CLOSED")) {
            selectedView = filterClosed;
        } else {
            selectedFilter = "ALL";
            selectedView = filterAll;
        }

        updateFilterUI(selectedView);
    }

    private void setupFilters() {
        filterAll.setOnClickListener(v -> selectFilter("ALL", filterAll));
        filterOpen.setOnClickListener(v -> selectFilter("OPEN", filterOpen));
        filterClosed.setOnClickListener(v -> selectFilter("CLOSED", filterClosed));
    }

    private void selectFilter(String filter, TextView view) {
        selectedFilter = filter;
        updateFilterUI(view);
        displayJobs();
    }

    private void updateFilterUI(TextView selected) {
        TextView[] filters = {filterAll, filterOpen, filterClosed};
        for (TextView f : filters) {
            f.setBackgroundResource(R.drawable.bg_filter_inactive);
            f.setTextColor(Color.parseColor("#6B7280"));
            f.setTypeface(null, Typeface.NORMAL);
        }
        selected.setBackgroundResource(R.drawable.bg_filter_active);
        selected.setTextColor(Color.WHITE);
        selected.setTypeface(null, Typeface.BOLD);
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

        filterAll = findViewById(R.id.filterJobsAll);
        filterOpen = findViewById(R.id.filterJobsOpen);
        filterClosed = findViewById(R.id.filterJobsClosed);
    }


    // =========================================================
    // MOCK DATA
    // =========================================================

    private void loadMockJobs() {

        jobs.clear();

        jobs.add(new Job(
                "JOB_001",
                "Java Developer",
                "NexTech Solutions",
                "Mangalore, Karnataka",
                "Full Time",
                "2 - 4 Years",
                "₹5 - ₹8 LPA",
                "12",
                "OPEN",
                "18 Aug 2026",
                "We are looking for a skilled Java Developer to join our development team. The candidate will be responsible for designing, developing, testing and maintaining high-quality Java applications. The role involves working with the development team to build scalable applications, develop REST APIs, work with databases and participate in the complete software development lifecycle.",
                "Java, Spring Boot, MySQL, REST API",
                "NexHire",
                ""
        ));

        jobs.add(new Job(
                "JOB_002",
                "Python Developer",
                "NexTech Solutions",
                "Mangalore, Karnataka",
                "Full Time",
                "1 - 3 Years",
                "₹4 - ₹7 LPA",
                "8 Applicants",
                "OPEN",
                "15 Aug 2026",
                "Python developer role description.",
                "Python, Django",
                "NexHire",
                ""
        ));

        jobs.add(new Job(
                "JOB_003",
                "Frontend Developer",
                "NexTech Solutions",
                "Bangalore, Karnataka",
                "Full Time",
                "2 - 5 Years",
                "₹6 - ₹10 LPA",
                "15 Applicants",
                "CLOSED",
                "10 Aug 2026",
                "Frontend developer role description.",
                "React, Vue",
                "External",
                "https://nextech.com/careers/frontend"
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

        int visibleCount = 0;

        for (Job job : jobs) {

            if (!selectedFilter.equals("ALL") && !job.status.equalsIgnoreCase(selectedFilter)) {
                continue;
            }

            createJobCard(job);
            visibleCount++;
        }

        txtJobCount.setText(visibleCount + " Jobs");

        if (visibleCount == 0) {
            jobsContainer.setVisibility(View.GONE);
            emptyState.setVisibility(View.VISIBLE);
        } else {
            jobsContainer.setVisibility(View.VISIBLE);
            emptyState.setVisibility(View.GONE);
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

            Intent intent =
                    new Intent(
                            RecruiterMyJobsActivity.this,
                            RecruiterJobDetailsActivity.class
                    );

            intent.putExtra(
                    "jobId",
                    job.jobId
            );

            intent.putExtra(
                    "title",
                    job.title
            );

            intent.putExtra(
                    "company",
                    job.company
            );

            intent.putExtra(
                    "location",
                    job.location
            );

            intent.putExtra(
                    "employmentType",
                    job.employmentType
            );

            intent.putExtra(
                    "experience",
                    job.experience
            );

            intent.putExtra(
                    "salary",
                    job.salary
            );

            intent.putExtra(
                    "applicants",
                    job.applicants
            );

            intent.putExtra(
                    "postedDate",
                    job.postedDate
            );

            intent.putExtra(
                    "description",
                    job.description
            );

            intent.putExtra(
                    "skills",
                    job.skills
            );

            intent.putExtra(
                    "status",
                    job.status
            );

            intent.putExtra(
                    "applicationMethod",
                    job.applicationMethod
            );

            intent.putExtra(
                    "applicationUrl",
                    job.applicationUrl
            );

            startActivity(intent);
        });


        // Edit

        edit.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            RecruiterMyJobsActivity.this,
                            RecruiterEditJobActivity.class
                    );

            intent.putExtra(
                    "jobId",
                    job.jobId
            );

            intent.putExtra(
                    "title",
                    job.title
            );

            intent.putExtra(
                    "company",
                    job.company
            );

            intent.putExtra(
                    "location",
                    job.location
            );

            intent.putExtra(
                    "employmentType",
                    job.employmentType
            );

            intent.putExtra(
                    "experience",
                    job.experience
            );

            intent.putExtra(
                    "salary",
                    job.salary
            );

            intent.putExtra(
                    "applicants",
                    job.applicants
            );

            intent.putExtra(
                    "postedDate",
                    job.postedDate
            );

            intent.putExtra(
                    "description",
                    job.description
            );

            intent.putExtra(
                    "skills",
                    job.skills
            );

            intent.putExtra(
                    "status",
                    job.status
            );

            intent.putExtra(
                    "applicationMethod",
                    job.applicationMethod
            );

            intent.putExtra(
                    "applicationUrl",
                    job.applicationUrl
            );

            startActivity(intent);
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


        // Back

        findViewById(R.id.btnJobsBack)
                .setOnClickListener(v -> finish());


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

        if (findViewById(R.id.navProfile) != null) {
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
        }
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

        String jobId;

        String title;
        String company;
        String location;
        String employmentType;
        String experience;
        String salary;
        String applicants;
        String status;
        String postedDate;

        String description;
        String skills;

        String applicationMethod;
        String applicationUrl;


        Job(
                String jobId,
                String title,
                String company,
                String location,
                String employmentType,
                String experience,
                String salary,
                String applicants,
                String status,
                String postedDate,
                String description,
                String skills,
                String applicationMethod,
                String applicationUrl
        ) {

            this.jobId =
                    jobId;

            this.title =
                    title;

            this.company =
                    company;

            this.location =
                    location;

            this.employmentType =
                    employmentType;

            this.experience =
                    experience;

            this.salary =
                    salary;

            this.applicants =
                    applicants;

            this.status =
                    status;

            this.postedDate =
                    postedDate;

            this.description =
                    description;

            this.skills =
                    skills;

            this.applicationMethod =
                    applicationMethod;

            this.applicationUrl =
                    applicationUrl;
        }
    }
}