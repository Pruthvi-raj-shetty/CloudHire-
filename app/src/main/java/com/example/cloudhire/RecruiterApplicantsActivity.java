package com.example.cloudhire;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecruiterApplicantsActivity extends AppCompatActivity {

    private LinearLayout applicantsContainer;
    private LinearLayout emptyApplicants;
    private LinearLayout errorApplicants;

    private ProgressBar progressApplicants;

    private TextView txtApplicantCount;
    private TextView txtApplicantError;
    private TextView txtNotificationBadge;

    private EditText etApplicantSearch;

    private TextView filterAll;
    private TextView filterApplied;
    private TextView filterShortlisted;
    private TextView filterInterview;
    private TextView filterRejected;

    private final List<RecruiterApplicant> applicants =
            new ArrayList<>();

    private String selectedFilter = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_recruiter_applicants
        );

        initializeViews();
        setupFilters();
        setupSearch();
        setupNavigation();

        loadApplicants();
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
    // INITIALIZE
    // =========================================================

    private void initializeViews() {

        applicantsContainer =
                findViewById(R.id.applicantsContainer);

        emptyApplicants =
                findViewById(R.id.emptyApplicants);

        errorApplicants =
                findViewById(R.id.errorApplicants);

        progressApplicants =
                findViewById(R.id.progressApplicants);

        txtApplicantCount =
                findViewById(R.id.txtApplicantCount);

        txtApplicantError =
                findViewById(R.id.txtApplicantError);

        txtNotificationBadge =
                findViewById(R.id.txtApplicantsNotificationBadge);

        etApplicantSearch =
                findViewById(R.id.etApplicantSearch);

        filterAll =
                findViewById(R.id.filterAll);

        filterApplied =
                findViewById(R.id.filterApplied);

        filterShortlisted =
                findViewById(R.id.filterShortlisted);

        filterInterview =
                findViewById(R.id.filterInterview);

        filterRejected =
                findViewById(R.id.filterRejected);
    }


    // =========================================================
    // LOAD APPLICANTS
    // =========================================================

    private void loadApplicants() {

        showLoading();

        /*
         * =====================================================
         * BACKEND CONNECTION POINT
         * =====================================================
         *
         * Later connect:
         *
         * GET /api/applications/recruiter
         *
         * Authorization:
         * Bearer <JWT>
         *
         * The backend must return applications belonging
         * only to the currently authenticated recruiter.
         *
         * When the API returns data:
         *
         * applicants.clear();
         * applicants.addAll(apiApplicants);
         * showApplicants();
         *
         * If API fails:
         *
         * showError("Unable to load applicants");
         *
         * =====================================================
         */

        /*
         * MOCK DATA
         */

        applicants.clear();

        applicants.add(new RecruiterApplicant(
                "APP_001",
                "CAND_001",
                "Arjun Sharma",
                "Java Developer | 3 Years Exp",
                "Senior Java Developer",
                "NexTech Solutions",
                "Bangalore",
                "30 Aug 2026",
                "arjun.s@email.com",
                "+91 9876543210",
                "https://example.com/resume/arjun",
                "",
                "SHORTLISTED"
        ));

        applicants.add(new RecruiterApplicant(
                "APP_002",
                "CAND_002",
                "Priya Patel",
                "UI/UX Designer | 2 Years Exp",
                "Product Designer",
                "NexTech Solutions",
                "Mumbai",
                "28 Aug 2026",
                "priya.p@email.com",
                "+91 8765432109",
                "https://example.com/resume/priya",
                "",
                "INTERVIEW"
        ));

        applicants.add(new RecruiterApplicant(
                "APP_003",
                "CAND_003",
                "Rohan Gupta",
                "Full Stack Developer | 4 Years Exp",
                "Senior Java Developer",
                "NexTech Solutions",
                "Delhi",
                "25 Aug 2026",
                "rohan.g@email.com",
                "+91 7654321098",
                "",
                "",
                "APPLIED"
        ));

        showApplicants();
    }


    // =========================================================
    // SHOW LOADING
    // =========================================================

    private void showLoading() {

        progressApplicants.setVisibility(
                View.VISIBLE
        );

        applicantsContainer.setVisibility(
                View.GONE
        );

        emptyApplicants.setVisibility(
                View.GONE
        );

        errorApplicants.setVisibility(
                View.GONE
        );
    }


    // =========================================================
    // SHOW APPLICANTS
    // =========================================================

    private void showApplicants() {

        progressApplicants.setVisibility(
                View.GONE
        );

        errorApplicants.setVisibility(
                View.GONE
        );

        applicantsContainer.setVisibility(
                View.VISIBLE
        );

        displayApplicants();
    }


    // =========================================================
    // DISPLAY
    // =========================================================

    private void displayApplicants() {

        applicantsContainer.removeAllViews();

        String search =
                etApplicantSearch
                        .getText()
                        .toString()
                        .trim()
                        .toLowerCase(
                                Locale.getDefault()
                        );

        int visibleCount = 0;

        for (RecruiterApplicant applicant : applicants) {

            if (!matchesFilter(applicant)) {
                continue;
            }

            if (!matchesSearch(
                    applicant,
                    search
            )) {
                continue;
            }

            createApplicantCard(applicant);

            visibleCount++;
        }

        txtApplicantCount.setText(
                String.valueOf(visibleCount)
        );

        if (visibleCount == 0) {

            applicantsContainer.setVisibility(
                    View.GONE
            );

            emptyApplicants.setVisibility(
                    View.VISIBLE
            );

            if (!applicants.isEmpty()) {

                TextView message =
                        findViewById(
                                R.id.txtEmptyApplicants
                        );

                message.setText(
                        "No applicants match your search or filter."
                );
            }

        } else {

            applicantsContainer.setVisibility(
                    View.VISIBLE
            );

            emptyApplicants.setVisibility(
                    View.GONE
            );
        }
    }


    // =========================================================
    // SEARCH
    // =========================================================

    private boolean matchesSearch(
            RecruiterApplicant applicant,
            String search
    ) {

        if (search.isEmpty()) {
            return true;
        }

        return safe(
                applicant.getCandidateName()
        ).contains(search)

                || safe(
                applicant.getProfessionalTitle()
        ).contains(search)

                || safe(
                applicant.getJobTitle()
        ).contains(search)

                || safe(
                applicant.getCompanyName()
        ).contains(search);
    }


    // =========================================================
    // FILTER
    // =========================================================

    private boolean matchesFilter(
            RecruiterApplicant applicant
    ) {

        if (selectedFilter.equals("ALL")) {
            return true;
        }

        String status =
                safe(applicant.getStatus())
                        .toUpperCase(Locale.getDefault());

        if (selectedFilter.equals("APPLIED")) {

            return status.equals("APPLIED")
                    || status.equals("UNDER REVIEW");
        }

        return status.equals(
                selectedFilter
        );
    }


    // =========================================================
    // CREATE APPLICANT CARD
    // =========================================================

    private void createApplicantCard(
            RecruiterApplicant applicant
    ) {

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

        /*
         * Reuse the same card drawable
         * already used by My Jobs.
         */

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

        card.setLayoutParams(
                cardParams
        );


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


        // AVATAR

        ImageView avatar =
                new ImageView(this);

        LinearLayout.LayoutParams avatarParams =
                new LinearLayout.LayoutParams(
                        dp(52),
                        dp(52)
                );

        avatar.setLayoutParams(
                avatarParams
        );

        avatar.setImageResource(
                R.drawable.recruiter_dashboard_person
        );

        avatar.setPadding(
                dp(8),
                dp(8),
                dp(8),
                dp(8)
        );


        // CANDIDATE INFO

        LinearLayout info =
                new LinearLayout(this);

        info.setOrientation(
                LinearLayout.VERTICAL
        );

        LinearLayout.LayoutParams infoParams =
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                );

        infoParams.setMargins(
                dp(12),
                0,
                0,
                0
        );

        info.setLayoutParams(
                infoParams
        );


        TextView name =
                createTextView(
                        safe(applicant.getCandidateName()),
                        17,
                        "#111827",
                        true
                );

        TextView role =
                createTextView(
                        safe(
                                applicant.getProfessionalTitle()
                        ),
                        13,
                        "#6B7280",
                        false
                );

        role.setPadding(
                0,
                dp(3),
                0,
                0
        );

        info.addView(name);
        info.addView(role);

        topRow.addView(avatar);
        topRow.addView(info);

        card.addView(topRow);


        // =====================================================
        // APPLICATION INFORMATION
        // =====================================================

        TextView appliedFor =
                createTextView(
                        "Applied for: "
                                + safe(
                                applicant.getJobTitle()
                        ),
                        14,
                        "#374151",
                        false
                );

        appliedFor.setPadding(
                0,
                dp(14),
                0,
                0
        );

        card.addView(appliedFor);


        TextView company =
                createTextView(
                        safe(
                                applicant.getCompanyName()
                        ),
                        13,
                        "#6B7280",
                        false
                );

        company.setPadding(
                0,
                dp(5),
                0,
                0
        );

        card.addView(company);


        TextView date =
                createTextView(
                        "Applied: "
                                + safe(
                                applicant.getAppliedDate()
                        ),
                        13,
                        "#6B7280",
                        false
                );

        date.setPadding(
                0,
                dp(5),
                0,
                0
        );

        card.addView(date);


        // =====================================================
        // STATUS
        // =====================================================

        TextView status =
                createStatusBadge(
                        safe(applicant.getStatus())
                );

        LinearLayout.LayoutParams statusParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        dp(32)
                );

        statusParams.setMargins(
                0,
                dp(12),
                0,
                0
        );

        status.setLayoutParams(
                statusParams
        );

        card.addView(status);


        // =====================================================
        // VIEW APPLICATION
        // =====================================================

        TextView viewButton =
                new TextView(this);

        viewButton.setText(
                "View Application"
        );

        viewButton.setTextColor(
                getColor(R.color.dashboard_primary)
        );

        viewButton.setTextSize(13);

        viewButton.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        viewButton.setGravity(
                Gravity.CENTER
        );

        viewButton.setBackgroundResource(
                R.drawable.bg_quick_action
        );

        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(46)
                );

        buttonParams.setMargins(
                0,
                dp(14),
                0,
                0
        );

        viewButton.setLayoutParams(
                buttonParams
        );

        viewButton.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            RecruiterApplicantsActivity.this,
                            RecruiterApplicationDetailsActivity.class
                    );

            intent.putExtra(
                    "applicationId",
                    applicant.getApplicationId()
            );

            intent.putExtra(
                    "candidateId",
                    applicant.getCandidateId()
            );

            intent.putExtra(
                    "candidateName",
                    applicant.getCandidateName()
            );

            intent.putExtra(
                    "professionalTitle",
                    applicant.getProfessionalTitle()
            );

            intent.putExtra(
                    "jobTitle",
                    applicant.getJobTitle()
            );

            intent.putExtra(
                    "companyName",
                    applicant.getCompanyName()
            );

            intent.putExtra(
                    "location",
                    applicant.getLocation()
            );

            intent.putExtra(
                    "appliedDate",
                    applicant.getAppliedDate()
            );

            intent.putExtra(
                    "email",
                    applicant.getEmail()
            );

            intent.putExtra(
                    "phone",
                    applicant.getPhone()
            );

            intent.putExtra(
                    "resumeUrl",
                    applicant.getResumeUrl()
            );

            intent.putExtra(
                    "status",
                    applicant.getStatus()
            );

            startActivity(intent);
        });

        card.addView(viewButton);

        applicantsContainer.addView(card);
    }


    // =========================================================
    // STATUS BADGE
    // =========================================================

    private TextView createStatusBadge(
            String status
    ) {

        TextView badge =
                new TextView(this);

        String normalized =
                status.toUpperCase(
                        Locale.getDefault()
                );

        badge.setText(
                normalized.isEmpty()
                        ? "APPLIED"
                        : normalized
        );

        badge.setTextSize(11);

        badge.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        badge.setGravity(
                Gravity.CENTER
        );

        badge.setPadding(
                dp(14),
                0,
                dp(14),
                0
        );

        GradientDrawable drawable =
                new GradientDrawable();

        drawable.setCornerRadius(
                dp(18)
        );

        if (normalized.equals("SHORTLISTED")) {

            drawable.setColor(
                    Color.parseColor("#DCFCE7")
            );

            badge.setTextColor(
                    Color.parseColor("#15803D")
            );

        } else if (
                normalized.equals("INTERVIEW")
        ) {

            drawable.setColor(
                    Color.parseColor("#DBEAFE")
            );

            badge.setTextColor(
                    Color.parseColor("#1D4ED8")
            );

        } else if (
                normalized.equals("REJECTED")
        ) {

            drawable.setColor(
                    Color.parseColor("#FEE2E2")
            );

            badge.setTextColor(
                    Color.parseColor("#B91C1C")
            );

        } else if (
                normalized.equals("HIRED")
        ) {

            drawable.setColor(
                    Color.parseColor("#EDE9FE")
            );

            badge.setTextColor(
                    Color.parseColor("#6D28D9")
            );

        } else {

            drawable.setColor(
                    Color.parseColor("#EFF6FF")
            );

            badge.setTextColor(
                    Color.parseColor("#2563EB")
            );
        }

        badge.setBackground(
                drawable
        );

        return badge;
    }


    // =========================================================
    // FILTER SETUP
    // =========================================================

    private void setupFilters() {

        filterAll.setOnClickListener(
                v -> selectFilter(
                        "ALL",
                        filterAll
                )
        );

        filterApplied.setOnClickListener(
                v -> selectFilter(
                        "APPLIED",
                        filterApplied
                )
        );

        filterShortlisted.setOnClickListener(
                v -> selectFilter(
                        "SHORTLISTED",
                        filterShortlisted
                )
        );

        filterInterview.setOnClickListener(
                v -> selectFilter(
                        "INTERVIEW",
                        filterInterview
                )
        );

        filterRejected.setOnClickListener(
                v -> selectFilter(
                        "REJECTED",
                        filterRejected
                )
        );
    }


    private void selectFilter(
            String filter,
            TextView selected
    ) {

        selectedFilter = filter;

        TextView[] filters = {
                filterAll,
                filterApplied,
                filterShortlisted,
                filterInterview,
                filterRejected
        };

        for (TextView filterView : filters) {

            filterView.setBackgroundResource(
                    R.drawable.bg_filter_inactive
            );

            filterView.setTextColor(
                    Color.parseColor("#6B7280")
            );
        }

        selected.setBackgroundResource(
                R.drawable.bg_filter_active
        );

        selected.setTextColor(
                Color.WHITE
        );

        displayApplicants();
    }


    // =========================================================
    // SEARCH
    // =========================================================

    private void setupSearch() {

        etApplicantSearch.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after
                    ) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count
                    ) {

                        displayApplicants();
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s
                    ) {
                    }
                }
        );
    }


    // =========================================================
    // NAVIGATION
    // =========================================================

    private void setupNavigation() {

        findViewById(R.id.navHome)
                .setOnClickListener(v -> {

                    startActivity(
                            new Intent(
                                    this,
                                    RecruiterDashboardActivity.class
                            )
                    );

                    finish();
                });


        findViewById(R.id.navJobs)
                .setOnClickListener(v -> {

                    startActivity(
                            new Intent(
                                    this,
                                    RecruiterMyJobsActivity.class
                            )
                    );

                    finish();
                });


        findViewById(R.id.navApplicants)
                .setOnClickListener(v -> {
                    // Already on Applicants
                });


        findViewById(R.id.navProfile)
                .setOnClickListener(v -> {

                    startActivity(
                            new Intent(
                                    this,
                                    RecruiterProfileActivity.class
                            )
                    );

                    finish();
                });


        findViewById(R.id.btnApplicantsNotifications)
                .setOnClickListener(v -> {

                    Toast.makeText(
                            this,
                            "Notifications",
                            Toast.LENGTH_SHORT
                    ).show();
                });


        findViewById(R.id.btnApplicantsMenu)
                .setOnClickListener(v -> {

                    Toast.makeText(
                            this,
                            "Menu",
                            Toast.LENGTH_SHORT
                    ).show();
                });


        findViewById(R.id.btnRetryApplicants)
                .setOnClickListener(v ->
                        loadApplicants()
                );
    }


    // =========================================================
    // ERROR
    // =========================================================

    private void showError(
            String message
    ) {

        progressApplicants.setVisibility(
                View.GONE
        );

        applicantsContainer.setVisibility(
                View.GONE
        );

        emptyApplicants.setVisibility(
                View.GONE
        );

        errorApplicants.setVisibility(
                View.VISIBLE
        );

        txtApplicantError.setText(
                message
        );
    }


    // =========================================================
    // TEXT VIEW
    // =========================================================

    private TextView createTextView(
            String text,
            int size,
            String color,
            boolean bold
    ) {

        TextView view =
                new TextView(this);

        view.setText(text);

        view.setTextSize(size);

        view.setTextColor(
                Color.parseColor(color)
        );

        if (bold) {

            view.setTypeface(
                    Typeface.DEFAULT,
                    Typeface.BOLD
            );
        }

        return view;
    }


    // =========================================================
    // SAFE STRING
    // =========================================================

    private String safe(
            String value
    ) {

        if (value == null) {
            return "";
        }

        return value
                .trim()
                .toLowerCase(
                        Locale.getDefault()
                );
    }


    // =========================================================
    // DP
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