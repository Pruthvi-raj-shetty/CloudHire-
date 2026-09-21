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

import com.example.cloudhire.api.ApiService;
import com.example.cloudhire.api.RetrofitClient;
import com.example.cloudhire.model.JobResponse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        setupClicks();

        loadDashboardData();
    }


    // =========================================================
    // ON RESUME
    // =========================================================

    @Override
    protected void onResume() {
        super.onResume();

        if (dashboardData != null) {
            loadDashboardData();
        }
    }


    // =========================================================
    // INITIALIZE VIEWS
    // =========================================================

    private void initializeViews() {

        txtGreeting =
                findViewById(R.id.txtGreeting);

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

        txtNotificationBadge =
                findViewById(R.id.txtNotificationBadge);

        activeJobsContainer =
                findViewById(R.id.activeJobsContainer);

        applicantsContainer =
                findViewById(R.id.applicantsContainer);

        interviewsContainer =
                findViewById(R.id.interviewsContainer);


        // =====================================================
        // STATISTICS CARD CLICKS
        // =====================================================

        View applicantsCard =
                (View) txtApplicantsCount.getParent();

        applicantsCard.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            this,
                            RecruiterApplicantsActivity.class
                    );

            intent.putExtra(
                    "filter",
                    "ALL"
            );

            startActivity(intent);
        });


        View jobsCard =
                (View) txtJobsCount.getParent();

        jobsCard.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            this,
                            RecruiterMyJobsActivity.class
                    );

            intent.putExtra(
                    "filter",
                    "ALL"
            );

            startActivity(intent);
        });


        View shortlistedCard =
                (View) txtShortlistedCount.getParent();

        shortlistedCard.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            this,
                            RecruiterApplicantsActivity.class
                    );

            intent.putExtra(
                    "filter",
                    "SHORTLISTED"
            );

            startActivity(intent);
        });


        View interviewsCard =
                (View) txtInterviewsCount.getParent();

        interviewsCard.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            this,
                            RecruiterApplicantsActivity.class
                    );

            intent.putExtra(
                    "filter",
                    "INTERVIEW"
            );

            startActivity(intent);
        });
    }


    // =========================================================
    // LOAD REAL DASHBOARD DATA
    // =========================================================

    private void loadDashboardData() {

        SessionManager sessionManager =
                new SessionManager(this);


        // =====================================================
        // RECRUITER NAME FROM LOGIN SESSION
        // =====================================================

        String recruiterName =
                sessionManager.getName();

        if (recruiterName == null ||
                recruiterName.trim().isEmpty()) {

            recruiterName = "Recruiter";
        }

        dashboardData.setRecruiterName(
                recruiterName
        );


        // =====================================================
        // API
        // =====================================================

        ApiService apiService =
                RetrofitClient.getApiService(this);


        // =====================================================
        // GET RECRUITER'S REAL JOBS
        // =====================================================

        apiService.getRecruiterJobs().enqueue(
                new Callback<List<JobResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<JobResponse>> call,
                            Response<List<JobResponse>> response
                    ) {

                        if (!response.isSuccessful() ||
                                response.body() == null) {

                            Toast.makeText(
                                    RecruiterDashboardActivity.this,
                                    "Could not load recruiter jobs",
                                    Toast.LENGTH_SHORT
                            ).show();

                            dashboardData.setJobs(
                                    new ArrayList<>()
                            );

                            dashboardData.setJobsCount(0);

                            updateDashboard();

                            return;
                        }


                        List<JobResponse> backendJobs =
                                response.body();


                        // Convert backend jobs to
                        // existing dashboard Job objects

                        List<Job> jobs =
                                convertJobs(
                                        backendJobs
                                );


                        dashboardData.setJobs(
                                jobs
                        );


                        dashboardData.setJobsCount(
                                backendJobs.size()
                        );


                        // Load real applications next

                        loadRecruiterApplicants(
                                apiService
                        );
                    }


                    @Override
                    public void onFailure(
                            Call<List<JobResponse>> call,
                            Throwable t
                    ) {

                        Toast.makeText(
                                RecruiterDashboardActivity.this,
                                "Unable to connect to server",
                                Toast.LENGTH_SHORT
                        ).show();


                        dashboardData.setJobs(
                                new ArrayList<>()
                        );

                        dashboardData.setJobsCount(0);

                        dashboardData.setApplicantsCount(0);

                        dashboardData.setShortlistedCount(0);

                        dashboardData.setInterviewsCount(0);

                        dashboardData.setApplicants(
                                new ArrayList<>()
                        );

                        dashboardData.setInterviews(
                                new ArrayList<>()
                        );


                        updateDashboard();
                    }
                }
        );
    }


    // =========================================================
    // LOAD REAL RECRUITER APPLICATIONS
    // =========================================================

    private void loadRecruiterApplicants(
            ApiService apiService
    ) {

        apiService.getRecruiterApplications().enqueue(
                new Callback<List<RecruiterApplicant>>() {

                    @Override
                    public void onResponse(
                            Call<List<RecruiterApplicant>> call,
                            Response<List<RecruiterApplicant>> response
                    ) {

                        if (!response.isSuccessful() ||
                                response.body() == null) {

                            dashboardData.setApplicants(
                                    new ArrayList<>()
                            );

                            dashboardData.setApplicantsCount(0);

                            dashboardData.setShortlistedCount(0);

                            dashboardData.setInterviewsCount(0);

                            dashboardData.setInterviews(
                                    new ArrayList<>()
                            );

                            updateDashboard();

                            return;
                        }


                        List<RecruiterApplicant>
                                backendApplicants =
                                response.body();


                        // Convert backend applicants
                        // to existing dashboard model

                        List<Applicant> applicants =
                                convertApplicants(
                                        backendApplicants
                                );


                        dashboardData.setApplicants(
                                applicants
                        );


                        // =================================================
                        // REAL COUNTS
                        // =================================================

                        int shortlisted = 0;

                        int interviews = 0;


                        for (
                                RecruiterApplicant applicant :
                                backendApplicants
                        ) {

                            String status =
                                    applicant.getStatus();


                            if (
                                    "SHORTLISTED"
                                            .equalsIgnoreCase(status)
                            ) {

                                shortlisted++;
                            }


                            if (
                                    "INTERVIEW"
                                            .equalsIgnoreCase(status)
                            ) {

                                interviews++;
                            }
                        }


                        dashboardData.setApplicantsCount(
                                backendApplicants.size()
                        );


                        dashboardData.setShortlistedCount(
                                shortlisted
                        );


                        dashboardData.setInterviewsCount(
                                interviews
                        );


                        // =================================================
                        // NO FAKE INTERVIEW DATA
                        // =================================================
                        //
                        // Current backend stores the application
                        // status but does not yet store:
                        //
                        // interview date
                        // interview time
                        // interview type
                        //
                        // Therefore we don't create fake interviews.

                        dashboardData.setInterviews(
                                new ArrayList<>()
                        );


                        // No notification backend endpoint yet.

                        dashboardData.setUnreadNotifications(
                                0
                        );


                        updateDashboard();
                    }


                    @Override
                    public void onFailure(
                            Call<List<RecruiterApplicant>> call,
                            Throwable t
                    ) {

                        dashboardData.setApplicants(
                                new ArrayList<>()
                        );

                        dashboardData.setApplicantsCount(0);

                        dashboardData.setShortlistedCount(0);

                        dashboardData.setInterviewsCount(0);

                        dashboardData.setInterviews(
                                new ArrayList<>()
                        );


                        updateDashboard();
                    }
                }
        );
    }


    // =========================================================
    // CONVERT BACKEND JOBS
    // =========================================================

    private List<Job> convertJobs(
            List<JobResponse> backendJobs
    ) {

        List<Job> jobs =
                new ArrayList<>();


        for (
                JobResponse backendJob :
                backendJobs
        ) {

            String status =
                    backendJob.getStatus();


            // Backend:
            // OPEN
            //
            // Android:
            // Active

            if (
                    "OPEN".equalsIgnoreCase(status)
            ) {

                status = "Active";
            }


            String salary =
                    formatSalary(
                            backendJob.getSalaryMin(),
                            backendJob.getSalaryMax()
                    );


            Job job =
                    new Job(

                            String.valueOf(
                                    backendJob.getId()
                            ),

                            safe(
                                    backendJob.getTitle()
                            ),

                            safe(
                                    backendJob.getCompanyName()
                            ),

                            "0 Applicants",

                            formatPostedDate(
                                    backendJob.getCreatedAt()
                            ),

                            status,

                            safe(
                                    backendJob.getLocation()
                            ),

                            formatEmploymentType(
                                    backendJob.getEmploymentType()
                            ),

                            safe(
                                    backendJob.getExperienceRequired()
                            ),

                            salary,

                            safe(
                                    backendJob.getDescription()
                            ),

                            safe(
                                    backendJob.getSkills()
                            ),

                            safe(
                                    backendJob.getApplicationMethod()
                            ),

                            safe(
                                    backendJob.getApplicationUrl()
                            )
                    );


            jobs.add(job);
        }


        return jobs;
    }


    // =========================================================
    // CONVERT BACKEND APPLICANTS
    // =========================================================

    private List<Applicant> convertApplicants(
            List<RecruiterApplicant> backendApplicants
    ) {

        List<Applicant> applicants =
                new ArrayList<>();


        for (
                RecruiterApplicant backendApplicant :
                backendApplicants
        ) {

            String name =
                    safe(
                            backendApplicant.getCandidateName()
                    );


            String initial =
                    "?";


            if (!name.isEmpty()) {

                initial =
                        name.substring(
                                0,
                                1
                        ).toUpperCase();
            }


            String professionalTitle =
                    safe(
                            backendApplicant
                                    .getProfessionalTitle()
                    );


            String experience =
                    professionalTitle;


            String skill =
                    "";


            // professionalTitle currently comes from
            // backend as:
            //
            // experience | skills

            if (
                    professionalTitle.contains("|")
            ) {

                String[] parts =
                        professionalTitle.split(
                                "\\|",
                                2
                        );


                experience =
                        parts[0].trim();


                if (parts.length > 1) {

                    skill =
                            parts[1].trim();
                }
            }


            applicants.add(
                    new Applicant(

                            safe(
                                    backendApplicant
                                            .getApplicationId()
                            ),

                            name,

                            safe(
                                    backendApplicant
                                            .getJobTitle()
                            ),

                            experience,

                            skill,

                            formatApplicationStatus(
                                    backendApplicant
                                            .getStatus()
                            ),

                            initial
                    )
            );
        }


        return applicants;
    }


    // =========================================================
    // UPDATE DASHBOARD UI
    // =========================================================

    private void updateDashboard() {

        // =====================================================
        // GREETING
        // =====================================================

        String recruiterName =
                dashboardData.getRecruiterName();


        if (
                recruiterName == null ||
                        recruiterName.trim().isEmpty()
        ) {

            recruiterName = "Recruiter";
        }


        txtGreeting.setText(
                getGreeting()
                        + ", "
                        + recruiterName
                        + " 👋"
        );


        txtRecruiterSubtitle.setText(
                "Manage your hiring efficiently"
        );


        // =====================================================
        // REAL COUNTS
        // =====================================================

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


        // =====================================================
        // NOTIFICATION BADGE
        // =====================================================

        int unread =
                dashboardData
                        .getUnreadNotifications();


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


        // =====================================================
        // JOBS
        // =====================================================

        loadJobs(
                dashboardData.getJobs()
        );


        // =====================================================
        // APPLICANTS
        // =====================================================

        loadApplicants(
                dashboardData.getApplicants()
        );


        // =====================================================
        // INTERVIEWS
        // =====================================================

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

    private void loadJobs(
            List<Job> jobs
    ) {

        activeJobsContainer.removeAllViews();


        if (
                jobs == null ||
                        jobs.isEmpty()
        ) {

            TextView empty =
                    createText(
                            "No jobs available yet.",
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


            activeJobsContainer.addView(
                    empty
            );


            return;
        }


        for (
                Job job :
                jobs
        ) {

            addJobCard(job);
        }
    }


    // =========================================================
    // JOB CARD
    // =========================================================

    private void addJobCard(
            Job job
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


        TextView icon =
                createText(
                        "💼",
                        23
                );


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
                        safe(
                                job.getTitle()
                        ),
                        17
                );


        title.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );


        TextView company =
                createText(
                        safe(
                                job.getCompany()
                        ),
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


        // =====================================================
        // THREE DOT MENU
        // =====================================================

        TextView menu =
                createText(
                        "⋮",
                        27
                );


        menu.setGravity(
                Gravity.CENTER
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


        // =====================================================
        // INFORMATION
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
                dp(14),
                0,
                0
        );


        TextView info =
                createText(
                        getApplicantsCountForJob(job)
                                + " Applicants"
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
                        safe(
                                job.getStatus()
                        ),
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


        // =====================================================
        // VIEW DETAILS
        // =====================================================

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

                    Intent intent =
                            new Intent(
                                    this,
                                    RecruiterJobDetailsActivity.class
                            );


                    intent.putExtra(
                            "jobId",
                            job.getId()
                    );


                    intent.putExtra(
                            "title",
                            job.getTitle()
                    );


                    intent.putExtra(
                            "company",
                            job.getCompany()
                    );


                    intent.putExtra(
                            "location",
                            job.getLocation()
                    );


                    intent.putExtra(
                            "employmentType",
                            job.getType()
                    );


                    intent.putExtra(
                            "experience",
                            job.getExperience()
                    );


                    intent.putExtra(
                            "salary",
                            job.getSalary()
                    );


                    intent.putExtra(
                            "applicants",
                            getApplicantsCountForJob(job)
                                    + " Applicants"
                    );


                    intent.putExtra(
                            "postedDate",
                            job.getPostedText()
                    );


                    intent.putExtra(
                            "status",
                            job.getStatus()
                    );


                    // REAL backend data
                    intent.putExtra(
                            "description",
                            job.getDescription()
                    );


                    intent.putExtra(
                            "skills",
                            job.getSkills()
                    );


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
    // JOB APPLICANT COUNT
    // =========================================================

    private String getApplicantsCountForJob(
            Job job
    ) {

        int count = 0;


        if (
                dashboardData.getApplicants() == null
        ) {

            return "0";
        }


        for (
                Applicant applicant :
                dashboardData.getApplicants()
        ) {

            if (
                    safe(
                            applicant.getJobTitle()
                    )
                            .equalsIgnoreCase(
                                    safe(
                                            job.getTitle()
                                    )
                            )
            ) {

                count++;
            }
        }


        return String.valueOf(count);
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


        popup.getMenu().add(
                "Edit Job"
        );


        popup.getMenu().add(
                "View Job"
        );


        popup.getMenu().add(
                "View Applicants"
        );


        popup.getMenu().add(
                "Pause Hiring"
        );


        popup.getMenu().add(
                "Close Job"
        );


        popup.setOnMenuItemClickListener(
                item -> {

                    String action =
                            item.getTitle()
                                    .toString();


                    switch (action) {

                        case "Edit Job":

                            Toast.makeText(
                                    this,
                                    "Edit Job selected",
                                    Toast.LENGTH_SHORT
                            ).show();

                            break;


                        case "View Job":

                            Intent viewIntent =
                                    new Intent(
                                            this,
                                            RecruiterJobDetailsActivity.class
                                    );

                            viewIntent.putExtra(
                                    "jobId",
                                    job.getId()
                            );

                            viewIntent.putExtra(
                                    "title",
                                    job.getTitle()
                            );

                            viewIntent.putExtra(
                                    "company",
                                    job.getCompany()
                            );

                            viewIntent.putExtra(
                                    "location",
                                    job.getLocation()
                            );

                            viewIntent.putExtra(
                                    "employmentType",
                                    job.getType()
                            );

                            viewIntent.putExtra(
                                    "experience",
                                    job.getExperience()
                            );

                            viewIntent.putExtra(
                                    "salary",
                                    job.getSalary()
                            );

                            viewIntent.putExtra(
                                    "description",
                                    job.getDescription()
                            );

                            viewIntent.putExtra(
                                    "skills",
                                    job.getSkills()
                            );

                            viewIntent.putExtra(
                                    "status",
                                    job.getStatus()
                            );

                            startActivity(
                                    viewIntent
                            );

                            break;


                        case "View Applicants":

                            Intent applicantsIntent =
                                    new Intent(
                                            this,
                                            RecruiterApplicantsActivity.class
                                    );

                            applicantsIntent.putExtra(
                                    "filter",
                                    "ALL"
                            );

                            startActivity(
                                    applicantsIntent
                            );

                            break;


                        case "Pause Hiring":

                            Toast.makeText(
                                    this,
                                    "Job status update will be connected next",
                                    Toast.LENGTH_SHORT
                            ).show();

                            break;


                        case "Close Job":

                            Toast.makeText(
                                    this,
                                    "Job status update will be connected next",
                                    Toast.LENGTH_SHORT
                            ).show();

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


        if (
                applicants == null ||
                        applicants.isEmpty()
        ) {

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


        // Show up to 3 recent applicants
        int limit =
                Math.min(
                        applicants.size(),
                        3
                );


        for (
                int i = 0;
                i < limit;
                i++
        ) {

            addApplicantCard(
                    applicants.get(i)
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


        // =====================================================
        // AVATAR
        // =====================================================

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


        TextView name =
                createText(
                        safe(
                                applicant.getName()
                        ),
                        15
                );


        name.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );


        TextView role =
                createText(
                        safe(
                                applicant.getJobTitle()
                        ),
                        12
                );


        TextView experience =
                createText(
                        safe(
                                applicant.getExperience()
                        )
                                + (
                                safe(
                                        applicant.getSkill()
                                ).isEmpty()
                                        ? ""
                                        : " • "
                        )
                                + safe(
                                applicant.getSkill()
                        ),
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


        // =====================================================
        // STATUS
        // =====================================================

        TextView status =
                createText(
                        safe(
                                applicant.getStatus()
                        ),
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


        // =====================================================
        // OPEN APPLICATION DETAILS
        // =====================================================

        card.setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    this,
                                    RecruiterApplicationDetailsActivity.class
                            );


                    intent.putExtra(
                            "applicationId",
                            applicant.getId()
                    );


                    intent.putExtra(
                            "candidateName",
                            applicant.getName()
                    );


                    intent.putExtra(
                            "professionalTitle",
                            applicant.getExperience()
                                    + " | "
                                    + applicant.getSkill()
                    );


                    intent.putExtra(
                            "jobTitle",
                            applicant.getJobTitle()
                    );


                    intent.putExtra(
                            "status",
                            applicant.getStatus()
                    );


                    startActivity(intent);
                }
        );


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


        if (
                interviews == null ||
                        interviews.isEmpty()
        ) {

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


        for (
                Interview interview :
                interviews
        ) {

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


        card.setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    this,
                                    RecruiterApplicantsActivity.class
                            );


                    intent.putExtra(
                            "filter",
                            "INTERVIEW"
                    );


                    startActivity(intent);
                }
        );


        interviewsContainer.addView(
                card
        );
    }


    // =========================================================
    // CLICKS
    // =========================================================

    private void setupClicks() {

        // =====================================================
        // POST JOB
        // =====================================================

        findViewById(
                R.id.btnPostJob
        ).setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    RecruiterDashboardActivity.this,
                                    RecruiterPostJobActivity.class
                            );

                    startActivity(intent);
                }
        );


        // =====================================================
        // NOTIFICATIONS
        // =====================================================

        findViewById(
                R.id.btnNotifications
        ).setOnClickListener(
                v -> {

                    startActivity(
                            new Intent(
                                    this,
                                    RecruiterNotificationsActivity.class
                            )
                    );
                }
        );


        // =====================================================
        // BOTTOM NAVIGATION
        // =====================================================

        findViewById(
                R.id.navHome
        ).setOnClickListener(
                v -> {
                    // Already on Home
                }
        );


        findViewById(
                R.id.navJobs
        ).setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    RecruiterDashboardActivity.this,
                                    RecruiterMyJobsActivity.class
                            );

                    startActivity(intent);
                }
        );


        findViewById(
                R.id.navApplicants
        ).setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    RecruiterDashboardActivity.this,
                                    RecruiterApplicantsActivity.class
                            );

                    startActivity(intent);
                }
        );


        findViewById(
                R.id.navProfile
        ).setOnClickListener(
                v -> {

                    startActivity(
                            new Intent(
                                    this,
                                    RecruiterProfileActivity.class
                            )
                    );
                }
        );


        // =====================================================
        // VIEW ALL JOBS
        // =====================================================

        findViewById(
                R.id.btnViewAllJobs
        ).setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    RecruiterDashboardActivity.this,
                                    RecruiterMyJobsActivity.class
                            );

                    intent.putExtra(
                            "filter",
                            "ALL"
                    );

                    startActivity(intent);
                }
        );


        findViewById(
                R.id.btnViewAllJobs2
        ).setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    RecruiterDashboardActivity.this,
                                    RecruiterMyJobsActivity.class
                            );

                    intent.putExtra(
                            "filter",
                            "OPEN"
                    );

                    startActivity(intent);
                }
        );


        // =====================================================
        // VIEW ALL APPLICANTS
        // =====================================================

        findViewById(
                R.id.btnViewAllApplicants
        ).setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    RecruiterDashboardActivity.this,
                                    RecruiterApplicantsActivity.class
                            );

                    intent.putExtra(
                            "filter",
                            "APPLIED"
                    );

                    startActivity(intent);
                }
        );


        // =====================================================
        // VIEW ALL INTERVIEWS
        // =====================================================

        findViewById(
                R.id.btnViewAllInterviews
        ).setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    RecruiterDashboardActivity.this,
                                    RecruiterApplicantsActivity.class
                            );

                    intent.putExtra(
                            "filter",
                            "INTERVIEW"
                    );

                    startActivity(intent);
                }
        );


        // =====================================================
        // QUICK POST JOB
        // =====================================================

        findViewById(
                R.id.quickPostJob
        ).setOnClickListener(
                v -> {

                    startActivity(
                            new Intent(
                                    RecruiterDashboardActivity.this,
                                    RecruiterPostJobActivity.class
                            )
                    );
                }
        );


        // =====================================================
        // QUICK APPLICANTS
        // =====================================================

        findViewById(
                R.id.quickApplicants
        ).setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    RecruiterDashboardActivity.this,
                                    RecruiterApplicantsActivity.class
                            );

                    intent.putExtra(
                            "filter",
                            "APPLIED"
                    );

                    startActivity(intent);
                }
        );


        // =====================================================
        // QUICK INTERVIEWS
        // =====================================================

        findViewById(
                R.id.quickInterviews
        ).setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    RecruiterDashboardActivity.this,
                                    RecruiterApplicantsActivity.class
                            );

                    intent.putExtra(
                            "filter",
                            "INTERVIEW"
                    );

                    startActivity(intent);
                }
        );


        // =====================================================
        // QUICK SHORTLISTED
        // =====================================================

        findViewById(
                R.id.quickShortlisted
        ).setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    RecruiterDashboardActivity.this,
                                    RecruiterApplicantsActivity.class
                            );

                    intent.putExtra(
                            "filter",
                            "SHORTLISTED"
                    );

                    startActivity(intent);
                }
        );
    }


    // =========================================================
    // JOB STATUS BACKGROUND
    // =========================================================

    private android.graphics.drawable.GradientDrawable
    getStatusBackground(
            String status
    ) {

        String color;


        if (
                "Active".equalsIgnoreCase(status)
        ) {

            color = "#DCFCE7";

        } else if (
                "Paused".equalsIgnoreCase(status)
        ) {

            color = "#FEF3C7";

        } else if (
                "Closed".equalsIgnoreCase(status)
        ) {

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


        if (
                "Shortlisted"
                        .equalsIgnoreCase(status)
                        ||
                        "Selected"
                                .equalsIgnoreCase(status)
        ) {

            color = "#DCFCE7";

        } else if (
                "Under Review"
                        .equalsIgnoreCase(status)
        ) {

            color = "#FEF3C7";

        } else if (
                "Rejected"
                        .equalsIgnoreCase(status)
        ) {

            color = "#FEE2E2";

        } else if (
                "Interview"
                        .equalsIgnoreCase(status)
        ) {

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
    // APPLICATION STATUS FORMAT
    // =========================================================

    private String formatApplicationStatus(
            String status
    ) {

        if (
                status == null ||
                        status.trim().isEmpty()
        ) {

            return "Under Review";
        }


        switch (
                status.toUpperCase(Locale.ROOT)
        ) {

            case "SHORTLISTED":

                return "Shortlisted";


            case "INTERVIEW":

                return "Interview";


            case "REJECTED":

                return "Rejected";


            case "HIRED":

                return "Selected";


            case "APPLIED":

            default:

                return "Under Review";
        }
    }


    // =========================================================
    // EMPLOYMENT TYPE FORMAT
    // =========================================================

    private String formatEmploymentType(
            String type
    ) {

        if (
                type == null ||
                        type.trim().isEmpty()
        ) {

            return "";
        }


        String value =
                type
                        .replace("_", " ")
                        .toLowerCase(
                                Locale.ROOT
                        );


        String[] words =
                value.split(" ");


        StringBuilder result =
                new StringBuilder();


        for (
                String word :
                words
        ) {

            if (word.isEmpty()) {
                continue;
            }


            result.append(
                    Character.toUpperCase(
                            word.charAt(0)
                    )
            );


            if (word.length() > 1) {

                result.append(
                        word.substring(1)
                );
            }


            result.append(" ");
        }


        return result.toString().trim();
    }


    // =========================================================
    // SALARY FORMAT
    // =========================================================

    private String formatSalary(
            Double min,
            Double max
    ) {

        if (
                min == null &&
                        max == null
        ) {

            return "Salary not specified";
        }


        if (
                min != null &&
                        max != null
        ) {

            return "₹"
                    + formatAmount(min)
                    + " - ₹"
                    + formatAmount(max);
        }


        if (min != null) {

            return "₹"
                    + formatAmount(min)
                    + "+";
        }


        return "Up to ₹"
                + formatAmount(max);
    }


    // =========================================================
    // AMOUNT FORMAT
    // =========================================================

    private String formatAmount(
            Double amount
    ) {

        if (amount == null) {

            return "0";
        }


        if (amount >= 10000000) {

            return String.format(
                    Locale.US,
                    "%.1f Cr",
                    amount / 10000000.0
            );
        }


        if (amount >= 100000) {

            return String.format(
                    Locale.US,
                    "%.1f L",
                    amount / 100000.0
            );
        }


        return String.format(
                Locale.US,
                "%.0f",
                amount
        );
    }


    // =========================================================
    // POSTED DATE FORMAT
    // =========================================================

    private String formatPostedDate(
            String createdAt
    ) {

        if (
                createdAt == null ||
                        createdAt.trim().isEmpty()
        ) {

            return "";
        }


        try {

            LocalDateTime created =
                    LocalDateTime.parse(
                            createdAt
                    );


            Duration duration =
                    Duration.between(
                            created,
                            LocalDateTime.now()
                    );


            long hours =
                    Math.max(
                            0,
                            duration.toHours()
                    );


            if (hours < 1) {

                return "Just now";
            }


            if (hours < 24) {

                return hours
                        + " hours ago";
            }


            long days =
                    hours / 24;


            if (days == 1) {

                return "1 day ago";
            }


            return days
                    + " days ago";

        } catch (Exception e) {

            return createdAt;
        }
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

    private String safe(
            String value
    ) {

        if (value == null) {

            return "";
        }


        return value;
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