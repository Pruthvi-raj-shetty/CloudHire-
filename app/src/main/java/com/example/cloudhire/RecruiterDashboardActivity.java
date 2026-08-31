package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RecruiterDashboardActivity extends AppCompatActivity {

    // =========================================================
    // VIEWS
    // =========================================================

    private TextView txtGreeting;
    private TextView txtRecruiterSubtitle;

    private TextView txtJobsCount;
    private TextView txtApplicantsCount;
    private TextView txtShortlistedCount;
    private TextView txtInterviewsCount;

    private TextView txtNotificationBadge;

    private LinearLayout activeJobsContainer;
    private LinearLayout applicantsContainer;
    private LinearLayout interviewsContainer;


    // =========================================================
    // DATA
    // =========================================================

    private RecruiterDashboardData dashboardData;


    // =========================================================
    // ON CREATE
    // =========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recruiter_dashboard);

        initializeViews();

        dashboardData = new RecruiterDashboardData();

        loadDashboardData();

        setupClicks();
    }


    // =========================================================
    // INITIALIZE VIEWS
    // =========================================================

    private void initializeViews() {

        txtGreeting = findViewById(R.id.txtGreeting);

        txtRecruiterSubtitle =
                findViewById(R.id.txtRecruiterSubtitle);

        txtJobsCount =
                findViewById(R.id.txtJobsCount);

        txtApplicantsCount =
                findViewById(R.id.txtApplicantsCount);

        txtShortlistedCount =
                findViewById(R.id.txtShortlistedCount);

        txtInterviewsCount =
                findViewById(R.id.txtInterviewsCount);

        // Make statistics cards clickable
        View applicantsCard = (View) findViewById(R.id.txtApplicantsCount).getParent();
        applicantsCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecruiterApplicantsActivity.class);
            intent.putExtra("filter", "ALL"); // Shows everyone from the total count card
            startActivity(intent);
        });

        View jobsCard = (View) findViewById(R.id.txtJobsCount).getParent();
        jobsCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecruiterMyJobsActivity.class);
            intent.putExtra("filter", "ALL"); // Shows all jobs from the total count card
            startActivity(intent);
        });

        View shortlistedCard = (View) findViewById(R.id.txtShortlistedCount).getParent();
        shortlistedCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecruiterApplicantsActivity.class);
            intent.putExtra("filter", "SHORTLISTED");
            startActivity(intent);
        });

        View interviewsCard = (View) findViewById(R.id.txtInterviewsCount).getParent();
        interviewsCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecruiterApplicantsActivity.class);
            intent.putExtra("filter", "INTERVIEW");
            startActivity(intent);
        });

        txtNotificationBadge =
                findViewById(R.id.txtNotificationBadge);

        activeJobsContainer =
                findViewById(R.id.activeJobsContainer);

        applicantsContainer =
                findViewById(R.id.applicantsContainer);

        interviewsContainer =
                findViewById(R.id.interviewsContainer);
    }


    // =========================================================
    // LOAD DASHBOARD
    // =========================================================

    private void loadDashboardData() {

        dashboardData.setRecruiterName("NexTech Recruiter");

        dashboardData.setJobsCount(12);
        dashboardData.setApplicantsCount(45);
        dashboardData.setShortlistedCount(8);
        dashboardData.setInterviewsCount(5);
        dashboardData.setUnreadNotifications(3);

        // Add Mock Applicants
        List<Applicant> mockApplicants = new ArrayList<>();
        mockApplicants.add(new Applicant("APP_001", "Arjun Sharma", "Java Developer", "3 Years", "Java, Spring", "Shortlisted", "A"));
        mockApplicants.add(new Applicant("APP_002", "Priya Patel", "UI/UX Designer", "2 Years", "Figma, Adobe", "Interview", "P"));
        mockApplicants.add(new Applicant("APP_003", "Rohan Gupta", "Full Stack", "4 Years", "React, Node", "Under Review", "R"));
        dashboardData.setApplicants(mockApplicants);

        // Add Mock Jobs
        List<Job> mockJobs = new ArrayList<>();
        mockJobs.add(new Job("JOB_001", "Senior Java Developer", "NexTech Solutions", "12 Applicants", "2 days ago", "Active", "Bangalore", "Full Time", "3 Years", "₹12 - ₹15 LPA"));
        mockJobs.add(new Job("JOB_002", "Product Designer", "NexTech Solutions", "8 Applicants", "5 days ago", "Active", "Mumbai", "Full Time", "2 Years", "₹8 - ₹10 LPA"));
        dashboardData.setJobs(mockJobs);

        updateDashboard();
    }


    // =========================================================
    // UPDATE UI
    // =========================================================

    private void updateDashboard() {

        String recruiterName =
                dashboardData.getRecruiterName();

        if (recruiterName == null ||
                recruiterName.trim().isEmpty()) {

            recruiterName = "Recruiter";
        }

        txtGreeting.setText(
                getGreeting() + ", " + recruiterName + " 👋"
        );

        txtRecruiterSubtitle.setText(
                "Manage your hiring efficiently"
        );


        // Statistics

        txtJobsCount.setText(
                String.valueOf(
                        dashboardData.getJobsCount()
                )
        );

        txtApplicantsCount.setText(
                String.valueOf(
                        dashboardData.getApplicantsCount()
                )
        );

        txtShortlistedCount.setText(
                String.valueOf(
                        dashboardData.getShortlistedCount()
                )
        );

        txtInterviewsCount.setText(
                String.valueOf(
                        dashboardData.getInterviewsCount()
                )
        );

        // Make Active Jobs cards clickable to My Jobs screen with "OPEN" filter
        txtJobsCount.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecruiterMyJobsActivity.class);
            intent.putExtra("filter", "OPEN");
            startActivity(intent);
        });

        ((View)txtJobsCount.getParent()).setOnClickListener(v -> {
            Intent intent = new Intent(this, RecruiterMyJobsActivity.class);
            intent.putExtra("filter", "OPEN");
            startActivity(intent);
        });


        // Notification badge

        int unread =
                dashboardData.getUnreadNotifications();

        if (unread > 0) {

            txtNotificationBadge.setVisibility(
                    View.VISIBLE
            );

            txtNotificationBadge.setText(
                    String.valueOf(unread)
            );

        } else {

            txtNotificationBadge.setVisibility(
                    View.GONE
            );
        }


        // Dynamic sections

        loadJobs(
                dashboardData.getJobs()
        );

        loadApplicants(
                dashboardData.getApplicants()
        );

        loadInterviews(
                dashboardData.getInterviews()
        );
    }


    // =========================================================
    // GREETING
    // =========================================================

    private String getGreeting() {

        int hour =
                java.util.Calendar
                        .getInstance()
                        .get(
                                java.util.Calendar.HOUR_OF_DAY
                        );

        if (hour >= 5 && hour < 12) {

            return "Good morning";

        } else if (hour >= 12 && hour < 17) {

            return "Good afternoon";

        } else if (hour >= 17 && hour < 21) {

            return "Good evening";

        } else {

            return "Good night";
        }
    }


    // =========================================================
    // JOBS
    // =========================================================

    private void loadJobs(List<Job> jobs) {

        activeJobsContainer.removeAllViews();

        if (jobs == null || jobs.isEmpty()) {

            TextView empty =
                    createText(
                            "No jobs available yet.",
                            14
                    );

            empty.setTextColor(
                    getColor(R.color.dashboard_text_secondary)
            );

            empty.setPadding(
                    dp(4),
                    dp(8),
                    dp(4),
                    dp(16)
            );

            activeJobsContainer.addView(empty);

            return;
        }


        for (Job job : jobs) {

            addJobCard(job);
        }
    }


    // =========================================================
    // JOB CARD
    // =========================================================

    private void addJobCard(Job job) {

        LinearLayout card =
                new LinearLayout(this);

        card.setOrientation(
                LinearLayout.VERTICAL
        );

        card.setPadding(
                dp(16),
                dp(16),
                dp(16),
                dp(16)
        );

        card.setBackgroundResource(
                R.drawable.recruiter_dashboard_card
        );


        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        cardParams.setMargins(
                0,
                0,
                0,
                dp(12)
        );

        card.setLayoutParams(cardParams);


        // -----------------------------------------------------
        // TOP ROW
        // -----------------------------------------------------

        LinearLayout topRow =
                new LinearLayout(this);

        topRow.setOrientation(
                LinearLayout.HORIZONTAL
        );

        topRow.setGravity(
                Gravity.CENTER_VERTICAL
        );


        TextView icon =
                createText("💼", 23);

        topRow.addView(
                icon,
                new LinearLayout.LayoutParams(
                        dp(44),
                        dp(44)
                )
        );


        LinearLayout titleBox =
                new LinearLayout(this);

        titleBox.setOrientation(
                LinearLayout.VERTICAL
        );

        TextView title =
                createText(
                        safe(job.getTitle()),
                        17
                );

        title.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );


        TextView company =
                createText(
                        safe(job.getCompany()),
                        13
                );

        company.setTextColor(
                getColor(
                        R.color.dashboard_text_secondary
                )
        );


        titleBox.addView(title);
        titleBox.addView(company);


        LinearLayout.LayoutParams titleParams =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        titleParams.setMargins(
                dp(10),
                0,
                dp(8),
                0
        );

        topRow.addView(
                titleBox,
                titleParams
        );


        // Three-dot menu

        TextView menu =
                createText("⋮", 27);

        menu.setGravity(
                Gravity.CENTER
        );

        menu.setContentDescription(
                "Job options"
        );

        menu.setOnClickListener(
                v -> showJobMenu(
                        menu,
                        job
                )
        );


        topRow.addView(
                menu,
                new LinearLayout.LayoutParams(
                        dp(40),
                        dp(44)
                )
        );


        card.addView(topRow);


        // -----------------------------------------------------
        // INFORMATION
        // -----------------------------------------------------

        LinearLayout infoRow =
                new LinearLayout(this);

        infoRow.setOrientation(
                LinearLayout.HORIZONTAL
        );

        infoRow.setGravity(
                Gravity.CENTER_VERTICAL
        );

        infoRow.setPadding(
                0,
                dp(14),
                0,
                0
        );


        TextView info =
                createText(
                        safe(
                                job.getApplicantsCountText()
                        )
                                + "   •   "
                                + safe(
                                job.getPostedText()
                        ),
                        12
                );

        info.setTextColor(
                getColor(
                        R.color.dashboard_text_secondary
                )
        );


        infoRow.addView(
                info,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );


        TextView status =
                createText(
                        safe(job.getStatus()),
                        10
                );

        status.setGravity(
                Gravity.CENTER
        );

        status.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );

        status.setPadding(
                dp(12),
                0,
                dp(12),
                0
        );

        status.setBackground(
                getStatusBackground(
                        job.getStatus()
                )
        );


        infoRow.addView(
                status,
                new LinearLayout.LayoutParams(
                        dp(82),
                        dp(32)
                )
        );


        card.addView(infoRow);


        // -----------------------------------------------------
        // VIEW DETAILS
        // -----------------------------------------------------

        TextView viewDetails =
                createText(
                        "View Details  →",
                        14
                );

        viewDetails.setTextColor(
                getColor(
                        R.color.dashboard_primary
                )
        );

        viewDetails.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );

        viewDetails.setPadding(
                0,
                dp(14),
                0,
                0
        );


        viewDetails.setOnClickListener(
                v -> {

                    Intent intent = new Intent(
                            this,
                            RecruiterJobDetailsActivity.class
                    );

                    intent.putExtra("jobId", job.getId());
                    intent.putExtra("title", job.getTitle());
                    intent.putExtra("company", job.getCompany());
                    intent.putExtra("location", job.getLocation());
                    intent.putExtra("employmentType", job.getType());
                    intent.putExtra("experience", job.getExperience());
                    intent.putExtra("salary", job.getSalary());
                    intent.putExtra("applicants", job.getApplicantsCountText());
                    intent.putExtra("postedDate", job.getPostedText());
                    intent.putExtra("status", job.getStatus());
                    // Dashboard job model might not have full description/skills, passing placeholder
                    intent.putExtra("description", "Job description for " + job.getTitle());
                    intent.putExtra("skills", "Required skills for this role");

                    startActivity(intent);
                }
        );


        card.addView(
                viewDetails
        );


        activeJobsContainer.addView(
                card
        );
    }


    // =========================================================
    // JOB MENU
    // =========================================================

    private void showJobMenu(
            View anchor,
            Job job
    ) {

        PopupMenu popup =
                new PopupMenu(
                        this,
                        anchor
                );

        popup.getMenu().add("Edit Job");

        popup.getMenu().add("View Job");

        popup.getMenu().add("View Applicants");

        popup.getMenu().add("Pause Hiring");

        popup.getMenu().add("Close Job");


        popup.setOnMenuItemClickListener(
                item -> {

                    String action =
                            item.getTitle().toString();


                    switch (action) {

                        case "Edit Job":

                            Toast.makeText(
                                    this,
                                    "Edit Job selected",
                                    Toast.LENGTH_SHORT
                            ).show();

                            break;


                        case "View Job":

                            Toast.makeText(
                                    this,
                                    "View Job selected",
                                    Toast.LENGTH_SHORT
                            ).show();

                            break;


                        case "View Applicants":

                            Toast.makeText(
                                    this,
                                    "View Applicants selected",
                                    Toast.LENGTH_SHORT
                            ).show();

                            break;


                        case "Pause Hiring":

                            job.setStatus(
                                    "Paused"
                            );

                            updateDashboard();

                            break;


                        case "Close Job":

                            job.setStatus(
                                    "Closed"
                            );

                            updateDashboard();

                            break;
                    }

                    return true;
                }
        );

        popup.show();
    }


    // =========================================================
    // APPLICANTS
    // =========================================================

    private void loadApplicants(
            List<Applicant> applicants
    ) {

        applicantsContainer.removeAllViews();

        if (applicants == null ||
                applicants.isEmpty()) {

            TextView empty =
                    createText(
                            "No recent applicants.",
                            14
                    );

            empty.setTextColor(
                    getColor(
                            R.color.dashboard_text_secondary
                    )
            );

            empty.setPadding(
                    dp(4),
                    dp(8),
                    dp(4),
                    dp(16)
            );

            applicantsContainer.addView(
                    empty
            );

            return;
        }


        for (Applicant applicant :
                applicants) {

            addApplicantCard(
                    applicant
            );
        }
    }


    // =========================================================
    // APPLICANT CARD
    // =========================================================

    private void addApplicantCard(
            Applicant applicant
    ) {

        LinearLayout card =
                new LinearLayout(this);

        card.setOrientation(
                LinearLayout.HORIZONTAL
        );

        card.setGravity(
                Gravity.CENTER_VERTICAL
        );

        card.setPadding(
                dp(14),
                dp(14),
                dp(14),
                dp(14)
        );

        card.setBackgroundResource(
                R.drawable.recruiter_dashboard_card
        );


        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        params.setMargins(
                0,
                0,
                0,
                dp(10)
        );

        card.setLayoutParams(params);


        // Avatar

        TextView avatar =
                createText(
                        safe(
                                applicant.getInitial()
                        ),
                        17
                );

        avatar.setTextColor(
                android.graphics.Color.WHITE
        );

        avatar.setGravity(
                Gravity.CENTER
        );

        avatar.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );

        avatar.setBackgroundResource(
                R.drawable.recruiter_dashboard_avatar
        );


        card.addView(
                avatar,
                new LinearLayout.LayoutParams(
                        dp(44),
                        dp(44)
                )
        );


        // Details

        LinearLayout details =
                new LinearLayout(this);

        details.setOrientation(
                LinearLayout.VERTICAL
        );

        details.setPadding(
                dp(12),
                0,
                dp(8),
                0
        );


        TextView name =
                createText(
                        safe(applicant.getName()),
                        15
                );

        name.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );


        TextView role =
                createText(
                        safe(applicant.getJobTitle()),
                        12
                );


        TextView experience =
                createText(
                        safe(applicant.getExperience())
                                + " • "
                                + safe(applicant.getSkill()),
                        11
                );

        experience.setTextColor(
                getColor(
                        R.color.dashboard_text_secondary
                )
        );


        details.addView(name);
        details.addView(role);
        details.addView(experience);


        card.addView(
                details,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );


        // Status

        TextView status =
                createText(
                        safe(applicant.getStatus()),
                        9
                );

        status.setGravity(
                Gravity.CENTER
        );

        status.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );

        status.setPadding(
                dp(8),
                0,
                dp(8),
                0
        );

        status.setBackground(
                getApplicantStatusBackground(
                        applicant.getStatus()
                )
        );


        card.addView(
                status,
                new LinearLayout.LayoutParams(
                        dp(96),
                        dp(32)
                )
        );

        // Link to RecruiterApplicationDetailsActivity
        card.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecruiterApplicationDetailsActivity.class);
            intent.putExtra("applicationId", applicant.getId());
            intent.putExtra("candidateName", applicant.getName());
            intent.putExtra("professionalTitle", applicant.getExperience() + " | " + applicant.getSkill());
            intent.putExtra("jobTitle", applicant.getJobTitle());
            intent.putExtra("status", applicant.getStatus());
            startActivity(intent);
        });


        applicantsContainer.addView(
                card
        );
    }


    // =========================================================
    // INTERVIEWS
    // =========================================================

    private void loadInterviews(
            List<Interview> interviews
    ) {

        interviewsContainer.removeAllViews();

        if (interviews == null ||
                interviews.isEmpty()) {

            TextView empty =
                    createText(
                            "No upcoming interviews.",
                            14
                    );

            empty.setTextColor(
                    getColor(
                            R.color.dashboard_text_secondary
                    )
            );

            empty.setPadding(
                    dp(4),
                    dp(8),
                    dp(4),
                    dp(16)
            );

            interviewsContainer.addView(
                    empty
            );

            return;
        }


        for (Interview interview :
                interviews) {

            addInterviewCard(
                    interview
            );
        }
    }


    // =========================================================
    // INTERVIEW CARD
    // =========================================================

    private void addInterviewCard(
            Interview interview
    ) {

        LinearLayout card =
                new LinearLayout(this);

        card.setOrientation(
                LinearLayout.HORIZONTAL
        );

        card.setGravity(
                Gravity.CENTER_VERTICAL
        );

        card.setPadding(
                dp(14),
                dp(14),
                dp(14),
                dp(14)
        );

        card.setBackgroundResource(
                R.drawable.recruiter_dashboard_card
        );


        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        params.setMargins(
                0,
                0,
                0,
                dp(10)
        );

        card.setLayoutParams(params);


        TextView icon =
                createText(
                        "📅",
                        22
                );

        icon.setGravity(
                Gravity.CENTER
        );


        card.addView(
                icon,
                new LinearLayout.LayoutParams(
                        dp(44),
                        dp(44)
                )
        );


        LinearLayout details =
                new LinearLayout(this);

        details.setOrientation(
                LinearLayout.VERTICAL
        );

        details.setPadding(
                dp(12),
                0,
                dp(8),
                0
        );


        TextView candidate =
                createText(
                        safe(
                                interview.getCandidateName()
                        ),
                        15
                );

        candidate.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );


        TextView job =
                createText(
                        safe(
                                interview.getJobTitle()
                        ),
                        12
                );


        TextView date =
                createText(
                        safe(
                                interview.getDate()
                        )
                                + " • "
                                + safe(
                                interview.getTime()
                        ),
                        11
                );

        date.setTextColor(
                getColor(
                        R.color.dashboard_text_secondary
                )
        );


        TextView type =
                createText(
                        safe(
                                interview.getInterviewType()
                        ),
                        11
                );

        type.setTextColor(
                getColor(
                        R.color.dashboard_primary
                )
        );


        details.addView(candidate);
        details.addView(job);
        details.addView(date);
        details.addView(type);


        card.addView(
                details,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );


        TextView scheduled =
                createText(
                        "SCHEDULED",
                        9
                );

        scheduled.setGravity(
                Gravity.CENTER
        );

        scheduled.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );

        scheduled.setTextColor(
                getColor(
                        R.color.dashboard_primary
                )
        );

        scheduled.setBackgroundResource(
                R.drawable.recruiter_dashboard_interview_badge
        );


        card.addView(
                scheduled,
                new LinearLayout.LayoutParams(
                        dp(88),
                        dp(32)
                )
        );

        // Link to Applicants screen with Interview filter
        card.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecruiterApplicantsActivity.class);
            intent.putExtra("filter", "INTERVIEW");
            startActivity(intent);
        });


        interviewsContainer.addView(
                card
        );
    }


    // =========================================================
    // CLICKS
    // =========================================================

    private void setupClicks() {

        // Post Job

        findViewById(R.id.btnPostJob)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            RecruiterDashboardActivity.this,
                            RecruiterPostJobActivity.class
                    );

                    startActivity(intent);
                });


        // Notifications

        findViewById(R.id.btnNotifications)
                .setOnClickListener(v -> {

                    startActivity(
                            new Intent(
                                    this,
                                    RecruiterNotificationsActivity.class
                            )
                    );
                });


        // Bottom navigation

        findViewById(R.id.navHome)
                .setOnClickListener(v -> {
                    // Already on Home
                });


        findViewById(R.id.navJobs)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            RecruiterDashboardActivity.this,
                            RecruiterMyJobsActivity.class
                    );

                    startActivity(intent);
                });
        findViewById(R.id.navApplicants)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            RecruiterDashboardActivity.this,
                            RecruiterApplicantsActivity.class
                    );

                    startActivity(intent);
                });


        findViewById(R.id.navProfile)
                .setOnClickListener(v -> {

                    startActivity(
                            new Intent(
                                    this,
                                    RecruiterProfileActivity.class
                            )
                    );
                });


        // Section buttons

        findViewById(R.id.btnViewAllJobs)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            RecruiterDashboardActivity.this,
                            RecruiterApplicantsActivity.class
                    );
                    intent.putExtra("filter", "ALL");
                    startActivity(intent);
                });

        findViewById(R.id.btnViewAllJobs2)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            RecruiterDashboardActivity.this,
                            RecruiterMyJobsActivity.class
                    );
                    intent.putExtra("filter", "OPEN");
                    startActivity(intent);
                });


        findViewById(R.id.btnViewAllApplicants)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            RecruiterDashboardActivity.this,
                            RecruiterApplicantsActivity.class
                    );
                    intent.putExtra("filter", "APPLIED"); // Specifically shows NEW applicants
                    startActivity(intent);
                });


        findViewById(R.id.btnViewAllInterviews)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            RecruiterDashboardActivity.this,
                            RecruiterApplicantsActivity.class
                    );
                    intent.putExtra("filter", "INTERVIEW");
                    startActivity(intent);
                });


        // Quick Actions

        findViewById(R.id.quickPostJob)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            RecruiterDashboardActivity.this,
                            RecruiterPostJobActivity.class
                    );

                    startActivity(intent);
                });

        findViewById(R.id.quickApplicants)
                .setOnClickListener(
                        v -> {
                            Intent intent = new Intent(
                                    RecruiterDashboardActivity.this,
                                    RecruiterApplicantsActivity.class
                            );
                            intent.putExtra("filter", "APPLIED"); // Quick link to NEW applicants
                            startActivity(intent);
                        }
                );


        findViewById(R.id.quickInterviews)
                .setOnClickListener(
                        v -> {
                            Intent intent = new Intent(
                                    RecruiterDashboardActivity.this,
                                    RecruiterApplicantsActivity.class
                            );
                            intent.putExtra("filter", "INTERVIEW");
                            startActivity(intent);
                        }
                );


        findViewById(R.id.quickShortlisted)
                .setOnClickListener(
                        v -> {
                            Intent intent = new Intent(
                                    RecruiterDashboardActivity.this,
                                    RecruiterApplicantsActivity.class
                            );
                            intent.putExtra("filter", "SHORTLISTED");
                            startActivity(intent);
                        }
                );
    }


    // =========================================================
    // JOB STATUS BACKGROUND
    // =========================================================

    private android.graphics.drawable.GradientDrawable
    getStatusBackground(String status) {

        String color;

        if ("Active".equalsIgnoreCase(status)) {

            color = "#DCFCE7";

        } else if ("Paused".equalsIgnoreCase(status)) {

            color = "#FEF3C7";

        } else if ("Closed".equalsIgnoreCase(status)) {

            color = "#FEE2E2";

        } else {

            color = "#E5E7EB";
        }


        android.graphics.drawable.GradientDrawable
                drawable =
                new android.graphics.drawable.GradientDrawable();

        drawable.setColor(
                android.graphics.Color.parseColor(
                        color
                )
        );

        drawable.setCornerRadius(
                dp(20)
        );

        return drawable;
    }


    // =========================================================
    // APPLICANT STATUS BACKGROUND
    // =========================================================

    private android.graphics.drawable.GradientDrawable
    getApplicantStatusBackground(
            String status
    ) {

        String color;

        if ("Shortlisted".equalsIgnoreCase(status) ||
                "Selected".equalsIgnoreCase(status)) {

            color = "#DCFCE7";

        } else if ("Under Review".equalsIgnoreCase(status)) {

            color = "#FEF3C7";

        } else if ("Rejected".equalsIgnoreCase(status)) {

            color = "#FEE2E2";

        } else if ("Interview".equalsIgnoreCase(status)) {

            color = "#DBEAFE";

        } else {

            color = "#F3F4F6";
        }


        android.graphics.drawable.GradientDrawable
                drawable =
                new android.graphics.drawable.GradientDrawable();

        drawable.setColor(
                android.graphics.Color.parseColor(
                        color
                )
        );

        drawable.setCornerRadius(
                dp(20)
        );

        return drawable;
    }


    // =========================================================
    // TEXTVIEW
    // =========================================================

    private TextView createText(
            String text,
            float size
    ) {

        TextView textView =
                new TextView(this);

        textView.setText(
                text
        );

        textView.setTextSize(
                size
        );

        textView.setTextColor(
                getColor(
                        R.color.dashboard_text_primary
                )
        );

        return textView;
    }


    // =========================================================
    // SAFE TEXT
    // =========================================================

    private String safe(String value) {

        if (value == null) {
            return "";
        }

        return value;
    }


    // =========================================================
    // DP
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
}