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

        layoutCandidate = findViewById(R.id.layoutCandidate);
        layoutRecruiter = findViewById(R.id.layoutRecruiter);

        layoutCandidate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("ROLE", "Candidate");
            startActivity(intent);
        });

        layoutRecruiter.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("ROLE", "Recruiter");
            startActivity(intent);
        });
    }
}