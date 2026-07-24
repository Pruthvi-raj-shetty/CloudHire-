package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout layoutAdmin, layoutRecruiter, layoutCandidate;
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView txtRegister;

    private String selectedRole = "Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        layoutAdmin = findViewById(R.id.layoutAdmin);
        layoutRecruiter = findViewById(R.id.layoutRecruiter);
        layoutCandidate = findViewById(R.id.layoutCandidate);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);

        layoutAdmin.setOnClickListener(v -> selectedRole = "Admin");

        layoutRecruiter.setOnClickListener(v -> selectedRole = "Recruiter");

        layoutCandidate.setOnClickListener(v -> selectedRole = "Candidate");

        btnLogin.setOnClickListener(v -> {

            Intent intent;

            switch (selectedRole) {

                case "Admin":
                    intent = new Intent(LoginActivity.this,
                            AdminDashboardActivity.class);
                    break;

                case "Recruiter":
                    intent = new Intent(LoginActivity.this,
                            RecruiterDashboardActivity.class);
                    break;

                default:
                    intent = new Intent(LoginActivity.this,
                            CandidateDashboardActivity.class);
                    break;
            }

            startActivity(intent);

        });

        txtRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this,
                        RegisterActivity.class)));

    }
}