package com.example.cloudhire;

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

import java.util.Calendar;

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
    // FRONTEND SAMPLE DATA
    // =========================================================

    private final String recruiterName = "Pruthvi Raj";

    private final int jobsCount = 12;
    private final int applicantsCount = 48;
    private final int shortlistedCount = 8;
    private final int interviewsCount = 3;

    private final int unreadNotifications = 3;


    // =========================================================
    // ON CREATE
    // =========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recruiter_dashboard);

        initializeViews();

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


        // Statistics

        txtJobsCount =
                findViewById(R.id.txtJobsCount);

        txtApplicantsCount =
                findViewById(R.id.txtApplicantsCount);

        txtShortlistedCount =
                findViewById(R.id.txtShortlistedCount);

        txtInterviewsCount =
                findViewById(R.id.txtInterviewsCount);


        // Notification

        txtNotificationBadge =
                findViewById(R.id.txtNotificationBadge);


        // Containers

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

        setDynamicGreeting();

        txtRecruiterSubtitle.setText(
                "Manage your hiring efficiently"
        );


        // =====================================================
        // HIRING OVERVIEW
        // =====================================================

        txtJobsCount.setText(
                String.valueOf(jobsCount)
        );

        txtApplicantsCount.setText(
                String.valueOf(applicantsCount)
        );

        txtShortlistedCount.setText(
                String.valueOf(shortlistedCount)
        );

        txtInterviewsCount.setText(
                String.valueOf(interviewsCount)
        );


        // =====================================================
        // NOTIFICATION
        // =====================================================

        if (unreadNotifications > 0) {

            txtNotificationBadge.setVisibility(
                    View.VISIBLE
            );

            txtNotificationBadge.setText(
                    String.valueOf(unreadNotifications)
            );

        } else {

            txtNotificationBadge.setVisibility(
                    View.GONE
            );
        }


        // =====================================================
        // LOAD CARDS
        // =====================================================

        loadActiveJobs();

        loadRecentApplicants();

        loadUpcomingInterviews();
    }


    // =========================================================
    // DYNAMIC GREETING
    // =========================================================

    private void setDynamicGreeting() {

        int hour =
                Calendar.getInstance()
                        .get(Calendar.HOUR_OF_DAY);

        String greeting;

        if (hour >= 5 && hour < 12) {

            greeting =
                    "Good morning, "
                            + recruiterName
                            + " 👋";

        } else if (hour >= 12 && hour < 17) {

            greeting =
                    "Good afternoon, "
                            + recruiterName
                            + " 👋";

        } else if (hour >= 17 && hour < 21) {

            greeting =
                    "Good evening, "
                            + recruiterName
                            + " 👋";

        } else {

            greeting =
                    "Good night, "
                            + recruiterName
                            + " 👋";
        }

        txtGreeting.setText(greeting);
    }


    // =========================================================
    // ACTIVE JOBS
    // =========================================================

    private void loadActiveJobs() {

        activeJobsContainer.removeAllViews();

        addJobCard(
                "Android Developer",
                "NexTech Solutions",
                "24 Applicants",
                "Posted 3d ago",
                "ACTIVE"
        );

        addJobCard(
                "Java Backend Developer",
                "CloudNova Technologies",
                "16 Applicants",
                "Posted 5d ago",
                "ACTIVE"
        );

        addJobCard(
                "Software Engineer",
                "TechBridge Pvt. Ltd.",
                "8 Applicants",
                "Posted 7d ago",
                "ACTIVE"
        );
    }


    // =========================================================
    // JOB CARD
    // =========================================================

    private void addJobCard(
            String title,
            String company,
            String applicants,
            String posted,
            String status
    ) {

        LinearLayout card =
                new LinearLayout(this);

        card.setOrientation(
                LinearLayout.VERTICAL
        );

        card.setPadding(
                dp(16),
                dp(15),
                dp(16),
                dp(15)
        );

        card.setBackground(
                cardBackground(
                        "#FFFFFF",
                        "#E5E7EB",
                        16
                )
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
                dp(12)
        );

        card.setLayoutParams(cardParams);


        // =====================================================
        // TOP ROW
        // =====================================================

        LinearLayout topRow =
                new LinearLayout(this);

        topRow.setOrientation(
                LinearLayout.HORIZONTAL
        );

        topRow.setGravity(
                Gravity.CENTER_VERTICAL
        );


        TextView icon =
                textView(
                        "💼",
                        24,
                        "#111827"
                );

        topRow.addView(
                icon,
                new LinearLayout.LayoutParams(
                        dp(42),
                        dp(42)
                )
        );


        LinearLayout titleBox =
                new LinearLayout(this);

        titleBox.setOrientation(
                LinearLayout.VERTICAL
        );


        TextView titleView =
                textView(
                        title,
                        16,
                        "#111827"
                );

        titleView.setTypeface(
                null,
                Typeface.BOLD
        );


        TextView companyView =
                textView(
                        company,
                        13,
                        "#6B7280"
                );


        titleBox.addView(titleView);

        titleBox.addView(companyView);


        LinearLayout.LayoutParams titleParams =
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                );


        topRow.addView(
                titleBox,
                titleParams
        );


        TextView menu =
                textView(
                        "⋮",
                        24,
                        "#6B7280"
                );

        menu.setGravity(
                Gravity.CENTER
        );


        topRow.addView(
                menu,
                new LinearLayout.LayoutParams(
                        dp(32),
                        dp(40)
                )
        );


        card.addView(topRow);


        // =====================================================
        // INFORMATION ROW
        // =====================================================

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
                dp(12),
                0,
                0
        );


        TextView info =
                textView(
                        "👥 "
                                + applicants
                                + "   •   "
                                + posted,
                        12,
                        "#667085"
                );


        infoRow.addView(
                info,
                new LinearLayout.LayoutParams(
                        0,
                        dp(34),
                        1
                )
        );


        TextView badge =
                textView(
                        status,
                        10,
                        "#15803D"
                );

        badge.setGravity(
                Gravity.CENTER
        );

        badge.setTypeface(
                null,
                Typeface.BOLD
        );

        badge.setPadding(
                dp(10),
                0,
                dp(10),
                0
        );

        badge.setBackground(
                cardBackground(
                        "#DCFCE7",
                        "#BBF7D0",
                        20
                )
        );


        infoRow.addView(
                badge,
                new LinearLayout.LayoutParams(
                        dp(70),
                        dp(30)
                )
        );


        card.addView(infoRow);


        // =====================================================
        // VIEW APPLICANTS
        // =====================================================

        TextView viewApplicants =
                textView(
                        "View Applicants  →",
                        13,
                        "#2563EB"
                );

        viewApplicants.setTypeface(
                null,
                Typeface.BOLD
        );

        viewApplicants.setPadding(
                0,
                dp(10),
                0,
                0
        );


        card.addView(
                viewApplicants
        );


        View.OnClickListener listener =
                v -> Toast.makeText(
                        RecruiterDashboardActivity.this,
                        "Opening applicants for "
                                + title,
                        Toast.LENGTH_SHORT
                ).show();


        card.setOnClickListener(listener);

        viewApplicants.setOnClickListener(listener);


        activeJobsContainer.addView(card);
    }


    // =========================================================
    // RECENT APPLICANTS
    // =========================================================

    private void loadRecentApplicants() {

        applicantsContainer.removeAllViews();

        addApplicantCard(
                "Rahul Sharma",
                "Android Developer",
                "3 yrs • Java",
                "SHORTLISTED",
                "R"
        );

        addApplicantCard(
                "Priya Nair",
                "Backend Developer",
                "2 yrs • Spring Boot",
                "REVIEW",
                "P"
        );

        addApplicantCard(
                "Arjun Kumar",
                "Software Engineer",
                "4 yrs • AWS",
                "INTERVIEW",
                "A"
        );
    }


    // =========================================================
    // APPLICANT CARD
    // =========================================================

    private void addApplicantCard(
            String name,
            String role,
            String experience,
            String status,
            String initial
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
                dp(13),
                dp(12),
                dp(13)
        );

        card.setBackground(
                cardBackground(
                        "#FFFFFF",
                        "#E5E7EB",
                        16
                )
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
                dp(10)
        );

        card.setLayoutParams(cardParams);


        // =====================================================
        // AVATAR
        // =====================================================

        TextView avatar =
                textView(
                        initial,
                        17,
                        "#FFFFFF"
                );

        avatar.setGravity(
                Gravity.CENTER
        );

        avatar.setTypeface(
                null,
                Typeface.BOLD
        );

        avatar.setBackground(
                circleBackground("#2563EB")
        );


        card.addView(
                avatar,
                new LinearLayout.LayoutParams(
                        dp(44),
                        dp(44)
                )
        );


        // =====================================================
        // DETAILS
        // =====================================================

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


        TextView nameView =
                textView(
                        name,
                        15,
                        "#111827"
                );

        nameView.setTypeface(
                null,
                Typeface.BOLD
        );


        TextView roleView =
                textView(
                        role,
                        12,
                        "#374151"
                );


        TextView experienceView =
                textView(
                        experience,
                        11,
                        "#6B7280"
                );


        details.addView(nameView);

        details.addView(roleView);

        details.addView(experienceView);


        card.addView(
                details,
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                )
        );


        // =====================================================
        // STATUS
        // =====================================================

        TextView statusView =
                textView(
                        status,
                        9,
                        statusColor(status)
                );

        statusView.setGravity(
                Gravity.CENTER
        );

        statusView.setTypeface(
                null,
                Typeface.BOLD
        );

        statusView.setPadding(
                dp(8),
                0,
                dp(8),
                0
        );

        statusView.setBackground(
                cardBackground(
                        statusBackground(status),
                        statusBorder(status),
                        20
                )
        );


        card.addView(
                statusView,
                new LinearLayout.LayoutParams(
                        dp(90),
                        dp(30)
                )
        );


        card.setOnClickListener(
                v -> Toast.makeText(
                        RecruiterDashboardActivity.this,
                        "Opening "
                                + name
                                + "'s application",
                        Toast.LENGTH_SHORT
                ).show()
        );


        applicantsContainer.addView(card);
    }


    // =========================================================
    // UPCOMING INTERVIEWS
    // =========================================================

    private void loadUpcomingInterviews() {

        interviewsContainer.removeAllViews();

        addInterviewCard(
                "Rahul Sharma",
                "Android Developer",
                "16 Aug • 10:00 AM",
                "SCHEDULED"
        );

        addInterviewCard(
                "Priya Nair",
                "Backend Developer",
                "16 Aug • 11:30 AM",
                "SCHEDULED"
        );
    }


    // =========================================================
    // INTERVIEW CARD
    // =========================================================

    private void addInterviewCard(
            String candidate,
            String role,
            String dateTime,
            String status
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
                dp(13),
                dp(14),
                dp(13)
        );

        card.setBackground(
                cardBackground(
                        "#FFFFFF",
                        "#E5E7EB",
                        16
                )
        );


        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(
                0,
                0,
                0,
                dp(10)
        );

        card.setLayoutParams(params);


        TextView calendarIcon =
                textView(
                        "📅",
                        22,
                        "#2563EB"
                );

        calendarIcon.setGravity(
                Gravity.CENTER
        );


        card.addView(
                calendarIcon,
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


        TextView candidateView =
                textView(
                        candidate,
                        14,
                        "#111827"
                );

        candidateView.setTypeface(
                null,
                Typeface.BOLD
        );


        TextView roleView =
                textView(
                        role,
                        12,
                        "#374151"
                );


        TextView timeView =
                textView(
                        dateTime,
                        11,
                        "#6B7280"
                );


        details.addView(candidateView);

        details.addView(roleView);

        details.addView(timeView);


        card.addView(
                details,
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                )
        );


        TextView statusView =
                textView(
                        status,
                        9,
                        "#2563EB"
                );

        statusView.setGravity(
                Gravity.CENTER
        );

        statusView.setTypeface(
                null,
                Typeface.BOLD
        );

        statusView.setPadding(
                dp(8),
                0,
                dp(8),
                0
        );

        statusView.setBackground(
                cardBackground(
                        "#DBEAFE",
                        "#BFDBFE",
                        20
                )
        );


        card.addView(
                statusView,
                new LinearLayout.LayoutParams(
                        dp(88),
                        dp(30)
                )
        );


        interviewsContainer.addView(card);
    }


    // =========================================================
    // BUTTON CLICKS
    // =========================================================

    private void setupClicks() {

        findViewById(R.id.btnPostJob)
                .setOnClickListener(
                        v -> Toast.makeText(
                                this,
                                "Post New Job selected",
                                Toast.LENGTH_SHORT
                        ).show()
                );


        findViewById(R.id.btnViewAllJobs)
                .setOnClickListener(
                        v -> Toast.makeText(
                                this,
                                "Opening all jobs",
                                Toast.LENGTH_SHORT
                        ).show()
                );


        findViewById(R.id.btnViewAllApplicants)
                .setOnClickListener(
                        v -> Toast.makeText(
                                this,
                                "Opening all applicants",
                                Toast.LENGTH_SHORT
                        ).show()
                );


        findViewById(R.id.btnViewAllInterviews)
                .setOnClickListener(
                        v -> Toast.makeText(
                                this,
                                "Opening all interviews",
                                Toast.LENGTH_SHORT
                        ).show()
                );


        findViewById(R.id.btnNotifications)
                .setOnClickListener(
                        v -> Toast.makeText(
                                this,
                                "Notifications",
                                Toast.LENGTH_SHORT
                        ).show()
                );


        findViewById(R.id.btnMenu)
                .setOnClickListener(
                        v -> Toast.makeText(
                                this,
                                "Menu",
                                Toast.LENGTH_SHORT
                        ).show()
                );


        findViewById(R.id.navHome)
                .setOnClickListener(
                        v -> Toast.makeText(
                                this,
                                "Home",
                                Toast.LENGTH_SHORT
                        ).show()
                );


        findViewById(R.id.navJobs)
                .setOnClickListener(
                        v -> Toast.makeText(
                                this,
                                "Jobs",
                                Toast.LENGTH_SHORT
                        ).show()
                );


        findViewById(R.id.navApplicants)
                .setOnClickListener(
                        v -> Toast.makeText(
                                this,
                                "Applicants",
                                Toast.LENGTH_SHORT
                        ).show()
                );


        findViewById(R.id.navProfile)
                .setOnClickListener(
                        v -> Toast.makeText(
                                this,
                                "Profile",
                                Toast.LENGTH_SHORT
                        ).show()
                );
    }


    // =========================================================
    // CREATE TEXTVIEW
    // =========================================================

    private TextView textView(
            String text,
            float size,
            String color
    ) {

        TextView view =
                new TextView(this);

        view.setText(text);

        view.setTextSize(size);

        view.setTextColor(
                Color.parseColor(color)
        );

        return view;
    }


    // =========================================================
    // CARD BACKGROUND
    // =========================================================

    private GradientDrawable cardBackground(
            String fill,
            String stroke,
            int radiusDp
    ) {

        GradientDrawable drawable =
                new GradientDrawable();

        drawable.setColor(
                Color.parseColor(fill)
        );

        drawable.setCornerRadius(
                dp(radiusDp)
        );

        drawable.setStroke(
                dp(1),
                Color.parseColor(stroke)
        );

        return drawable;
    }


    // =========================================================
    // CIRCLE BACKGROUND
    // =========================================================

    private GradientDrawable circleBackground(
            String color
    ) {

        GradientDrawable drawable =
                new GradientDrawable();

        drawable.setShape(
                GradientDrawable.OVAL
        );

        drawable.setColor(
                Color.parseColor(color)
        );

        return drawable;
    }


    // =========================================================
    // STATUS COLOR
    // =========================================================

    private String statusColor(
            String status
    ) {

        if (status.equals("SHORTLISTED")) {

            return "#15803D";

        } else if (status.equals("REVIEW")) {

            return "#C2410C";

        } else {

            return "#2563EB";
        }
    }


    // =========================================================
    // STATUS BACKGROUND
    // =========================================================

    private String statusBackground(
            String status
    ) {

        if (status.equals("SHORTLISTED")) {

            return "#DCFCE7";

        } else if (status.equals("REVIEW")) {

            return "#FFEDD5";

        } else {

            return "#DBEAFE";
        }
    }


    // =========================================================
    // STATUS BORDER
    // =========================================================

    private String statusBorder(
            String status
    ) {

        if (status.equals("SHORTLISTED")) {

            return "#BBF7D0";

        } else if (status.equals("REVIEW")) {

            return "#FED7AA";

        } else {

            return "#BFDBFE";
        }
    }


    // =========================================================
    // DP CONVERSION
    // =========================================================

    private int dp(
            int value
    ) {

        return (int) (
                value *
                        getResources()
                                .getDisplayMetrics()
                                .density
                        + 0.5f
        );
    }
}