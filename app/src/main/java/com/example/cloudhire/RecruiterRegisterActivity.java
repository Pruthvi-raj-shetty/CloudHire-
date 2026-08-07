package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class RecruiterRegisterActivity extends AppCompatActivity {

    private TextInputEditText etRecruiterName;
    private TextInputEditText etCompanyName;
    private TextInputEditText etCompanyEmail;
    private TextInputEditText etPassword;
    private TextInputEditText etConfirmPassword;

    private Button btnRecruiterRegister;
    private TextView txtRecruiterLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recruiter_register);

        // Initialize fields
        etRecruiterName = findViewById(R.id.etRecruiterName);
        etCompanyName = findViewById(R.id.etCompanyName);
        etCompanyEmail = findViewById(R.id.etCompanyEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRecruiterRegister = findViewById(R.id.btnRecruiterRegister);
        txtRecruiterLogin = findViewById(R.id.txtRecruiterLogin);

        // Register button
        btnRecruiterRegister.setOnClickListener(v -> registerRecruiter());

        // Login
        txtRecruiterLogin.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RecruiterRegisterActivity.this,
                    LoginActivity.class
            );

            intent.putExtra("ROLE", "Recruiter");

            startActivity(intent);
            finish();
        });

        // Make sure keyboard opens when Recruiter Name is touched
        etRecruiterName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                v.postDelayed(() -> {
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(
                                    Context.INPUT_METHOD_SERVICE
                            );

                    if (imm != null) {
                        imm.showSoftInput(
                                v,
                                InputMethodManager.SHOW_IMPLICIT
                        );
                    }
                }, 150);
            }
        });
    }

    private void registerRecruiter() {

        String recruiterName =
                etRecruiterName.getText() != null
                        ? etRecruiterName.getText().toString().trim()
                        : "";

        String companyName =
                etCompanyName.getText() != null
                        ? etCompanyName.getText().toString().trim()
                        : "";

        String companyEmail =
                etCompanyEmail.getText() != null
                        ? etCompanyEmail.getText().toString().trim()
                        : "";

        String password =
                etPassword.getText() != null
                        ? etPassword.getText().toString()
                        : "";

        String confirmPassword =
                etConfirmPassword.getText() != null
                        ? etConfirmPassword.getText().toString()
                        : "";

        // Recruiter Name
        if (TextUtils.isEmpty(recruiterName)) {
            etRecruiterName.setError("Enter recruiter name");
            etRecruiterName.requestFocus();
            return;
        }

        // Company Name
        if (TextUtils.isEmpty(companyName)) {
            etCompanyName.setError("Enter company name");
            etCompanyName.requestFocus();
            return;
        }

        // Company Email
        if (TextUtils.isEmpty(companyEmail)) {
            etCompanyEmail.setError("Enter company email");
            etCompanyEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(companyEmail).matches()) {
            etCompanyEmail.setError("Enter a valid email");
            etCompanyEmail.requestFocus();
            return;
        }

        // Password
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Enter password");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return;
        }

        // Confirm Password
        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.setError("Confirm your password");
            etConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return;
        }

        // Registration successful
        Toast.makeText(
                RecruiterRegisterActivity.this,
                "Recruiter registration successful",
                Toast.LENGTH_SHORT
        ).show();

        // Go to Recruiter Login
        Intent intent = new Intent(
                RecruiterRegisterActivity.this,
                LoginActivity.class
        );

        intent.putExtra("ROLE", "Recruiter");

        startActivity(intent);
        finish();
    }
}