package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecruiterProfileActivity extends AppCompatActivity {

    // =========================================================
    // HEADER
    // =========================================================

    private ImageButton btnMenu;

    // =========================================================
    // PROFILE ACTIONS
    // =========================================================

    private TextView btnEditProfile;
    private TextView btnChangePassword;
    private TextView btnNotificationsAccount;
    private TextView btnLogout;

    // =========================================================
    // BOTTOM NAVIGATION
    // =========================================================

    private TextView navHome;
    private TextView navJobs;
    private TextView navApplicants;
    private LinearLayout navProfile;


    // =========================================================
    // ON CREATE
    // =========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recruiter_profile);

        initializeViews();
        setupClicks();
    }


    // =========================================================
    // INITIALIZE VIEWS
    // =========================================================

    private void initializeViews() {

        // Header
        btnMenu = findViewById(R.id.btnProfileMenu);

        // Profile actions
        btnEditProfile = findViewById(R.id.btnEditProfile);

        btnChangePassword =
                findViewById(R.id.btnChangePassword);

        btnNotificationsAccount =
                findViewById(R.id.btnNotifications);

        btnLogout =
                findViewById(R.id.btnLogout);

        // Bottom navigation
        navHome =
                findViewById(R.id.navHome);

        navJobs =
                findViewById(R.id.navJobs);

        navApplicants =
                findViewById(R.id.navApplicants);

        navProfile =
                findViewById(R.id.navProfile);
    }


    // =========================================================
    // CLICK EVENTS
    // =========================================================

    private void setupClicks() {

        // -----------------------------------------------------
        // MENU
        // -----------------------------------------------------

        if (btnMenu != null) {

            btnMenu.setOnClickListener(v -> {

                Toast.makeText(
                        this,
                        "Menu",
                        Toast.LENGTH_SHORT
                ).show();

            });
        }


        // -----------------------------------------------------
        // EDIT PROFILE
        // -----------------------------------------------------

        if (btnEditProfile != null) {

            btnEditProfile.setOnClickListener(v -> {

                Intent intent = new Intent(
                        RecruiterProfileActivity.this,
                        RecruiterEditProfileActivity.class
                );

                startActivity(intent);

            });
        }


        // -----------------------------------------------------
        // CHANGE PASSWORD
        // -----------------------------------------------------

        if (btnChangePassword != null) {

            btnChangePassword.setOnClickListener(v -> {

                Intent intent = new Intent(
                        RecruiterProfileActivity.this,
                        RecruiterForgotPasswordActivity.class
                );

                startActivity(intent);

            });
        }


        // -----------------------------------------------------
        // ACCOUNT NOTIFICATIONS
        // -----------------------------------------------------

        if (btnNotificationsAccount != null) {

            btnNotificationsAccount.setOnClickListener(v -> {

                try {

                    Intent intent = new Intent(
                            RecruiterProfileActivity.this,
                            RecruiterNotificationsActivity.class
                    );

                    startActivity(intent);

                } catch (Exception e) {

                    Toast.makeText(
                            this,
                            "Notifications screen is not available yet",
                            Toast.LENGTH_SHORT
                    ).show();

                }

            });
        }


        // -----------------------------------------------------
        // LOGOUT
        // -----------------------------------------------------

        if (btnLogout != null) {

            btnLogout.setOnClickListener(v -> {

                Intent intent = new Intent(
                        RecruiterProfileActivity.this,
                        MainActivity.class
                );

                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                );

                startActivity(intent);

                finish();

            });
        }


        // =====================================================
        // BOTTOM NAVIGATION
        // =====================================================

        // -----------------------------------------------------
        // HOME
        // -----------------------------------------------------

        if (navHome != null) {

            navHome.setOnClickListener(v -> {

                Intent intent = new Intent(
                        RecruiterProfileActivity.this,
                        RecruiterDashboardActivity.class
                );

                startActivity(intent);

                finish();

            });
        }


        // -----------------------------------------------------
        // JOBS
        // -----------------------------------------------------

        if (navJobs != null) {

            navJobs.setOnClickListener(v -> {

                Toast.makeText(
                        this,
                        "Jobs",
                        Toast.LENGTH_SHORT
                ).show();

            });
        }


        // -----------------------------------------------------
        // APPLICANTS
        // -----------------------------------------------------

        if (navApplicants != null) {

            navApplicants.setOnClickListener(v -> {

                Toast.makeText(
                        this,
                        "Applicants",
                        Toast.LENGTH_SHORT
                ).show();

            });
        }


        // -----------------------------------------------------
        // PROFILE
        // -----------------------------------------------------

        if (navProfile != null) {

            navProfile.setOnClickListener(v -> {

                // Already on Profile

            });
        }
    }
}