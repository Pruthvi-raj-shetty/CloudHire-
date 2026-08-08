package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView txtRegister;

    private String selectedRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // Get selected role
        selectedRole = getIntent().getStringExtra("ROLE");

        if (selectedRole == null) {
            selectedRole = "Candidate";
        }

        // Initialize views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);

        // =====================================================
        // LOGIN BUTTON
        // =====================================================

        btnLogin.setOnClickListener(v -> {

            Intent intent;

            if (selectedRole.equals("Recruiter")) {

                intent = new Intent(
                        LoginActivity.this,
                        RecruiterDashboardActivity.class
                );

            } else {

                intent = new Intent(
                        LoginActivity.this,
                        CandidateDashboardActivity.class
                );
            }

            startActivity(intent);
            finish();
        });

        // =====================================================
        // REGISTER
        // =====================================================

        txtRegister.setOnClickListener(v -> {

            Intent intent;

            if (selectedRole.equals("Recruiter")) {

                // Recruiter → Recruiter Registration
                intent = new Intent(
                        LoginActivity.this,
                        RecruiterRegisterActivity.class
                );

            } else {

                // Candidate → Candidate Registration
                intent = new Intent(
                        LoginActivity.this,
                        RegisterActivity.class
                );
            }

            intent.putExtra("ROLE", selectedRole);

            startActivity(intent);
        });
    }
}