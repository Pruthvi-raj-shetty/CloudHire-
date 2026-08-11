package com.example.cloudhire;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // =====================================================
    // VIEWS
    // =====================================================

    private LinearLayout layoutCandidate;
    private LinearLayout layoutRecruiter;


    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // =================================================
        // INITIALIZE VIEWS
        // =================================================

        layoutCandidate = findViewById(R.id.layoutCandidate);
        layoutRecruiter = findViewById(R.id.layoutRecruiter);


        // =================================================
        // CANDIDATE CARD CLICK
        // =================================================

        layoutCandidate.setOnClickListener(v -> {

            layoutCandidate.setBackgroundResource(
                    R.drawable.role_selected
            );

            layoutRecruiter.setBackgroundResource(
                    R.drawable.role_unselected
            );

            layoutCandidate.postDelayed(() -> {

                Intent intent = new Intent(
                        MainActivity.this,
                        CandidateLoginActivity.class
                );

                startActivity(intent);

            }, 200);
        });


        // =================================================
        // RECRUITER CARD CLICK
        // =================================================

        layoutRecruiter.setOnClickListener(v -> {

            layoutRecruiter.setBackgroundResource(
                    R.drawable.role_selected
            );

            layoutCandidate.setBackgroundResource(
                    R.drawable.role_unselected
            );

            layoutRecruiter.postDelayed(() -> {

                Intent intent = new Intent(
                        MainActivity.this,
                        RecruiterLoginActivity.class
                );

                startActivity(intent);

            }, 200);
        });


        // =================================================
        // MOBILE / SYSTEM BACK BUTTON
        // → SHOW EXIT CONFIRMATION
        // =================================================

        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Exit NexHire")
                                .setMessage("Do you want to exit the app?")
                                .setPositiveButton(
                                        "Yes",
                                        (dialog, which) -> finishAffinity()
                                )
                                .setNegativeButton(
                                        "No",
                                        null
                                )
                                .show();
                    }
                }
        );
    }
}