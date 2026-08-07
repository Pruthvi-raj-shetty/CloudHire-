package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layoutCandidate;
    private LinearLayout layoutRecruiter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        layoutCandidate = findViewById(R.id.layoutCandidate);
        layoutRecruiter = findViewById(R.id.layoutRecruiter);

        // Candidate Card Click
        layoutCandidate.setOnClickListener(v -> {

            // Make Candidate card blue
            layoutCandidate.setBackgroundResource(R.drawable.role_selected);

            // Keep Recruiter unselected
            layoutRecruiter.setBackgroundResource(R.drawable.role_unselected);

            // Open Candidate Registration after short delay
            layoutCandidate.postDelayed(() -> {

                Intent intent = new Intent(
                        MainActivity.this,
                        RegisterActivity.class
                );

                startActivity(intent);

            }, 200);
        });

        // Recruiter Card Click
        layoutRecruiter.setOnClickListener(v -> {

            // Make Recruiter card blue
            layoutRecruiter.setBackgroundResource(R.drawable.role_selected);

            // Keep Candidate unselected
            layoutCandidate.setBackgroundResource(R.drawable.role_unselected);

            // Open Recruiter Registration after short delay
            layoutRecruiter.postDelayed(() -> {

                Intent intent = new Intent(
                        MainActivity.this,
                        RecruiterRegisterActivity.class
                );

                startActivity(intent);

            }, 200);
        });
    }
}