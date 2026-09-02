package com.example.cloudhire;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;

public class CandidateLoginActivity extends AppCompatActivity {

    // =====================================================
    // VIEWS
    // =====================================================

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;

    private Button btnLogin;
    private TextView txtForgotPassword;
    private TextView txtRegister;
    private ImageButton btnBack;

    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Prevent keyboard from opening automatically
        // and allow screen to resize when keyboard appears.
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        setContentView(R.layout.activity_candidate_login);

        // =================================================
        // INITIALIZE VIEWS
        // =================================================

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);

        btnLogin = findViewById(R.id.btnLogin);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtRegister = findViewById(R.id.txtRegister);
        btnBack = findViewById(R.id.btnBack);

        // =================================================
        // BACK BUTTON
        // =================================================

        btnBack.setOnClickListener(v -> {
            hideKeyboard();

            Intent intent = new Intent(
                    CandidateLoginActivity.this,
                    MainActivity.class
            );

            intent.putExtra("ROLE", "Candidate");
            startActivity(intent);
        });

        // =====================================================
// SYSTEM / MOBILE BACK BUTTON → CANDIDATE REGISTER
// =====================================================

        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        hideKeyboard();

                        Intent intent = new Intent(
                                CandidateLoginActivity.this,
                                MainActivity.class
                        );

                        intent.putExtra("ROLE", "Candidate");

                        startActivity(intent);
                        finish();
                    }
                }
        );
        // =================================================
        // LOGIN BUTTON
        // =================================================

        btnLogin.setOnClickListener(v -> {
            loginCandidate();
        });

        // =================================================
        // FORGOT PASSWORD
        // =================================================

        // ==========================================
// FORGOT PASSWORD
// ==========================================

        txtForgotPassword.setOnClickListener(v -> {

            hideKeyboard();

            Intent intent = new Intent(
                    CandidateLoginActivity.this,
                    CandidateForgotPasswordActivity.class
            );

            startActivity(intent);

        });

        // =================================================
        // REGISTER
        // =================================================

        txtRegister.setOnClickListener(v -> {

            hideKeyboard();

            Intent intent = new Intent(
                    CandidateLoginActivity.this,
                    RegisterActivity.class
            );

            // Send candidate role to registration screen
            intent.putExtra("ROLE", "Candidate");

            startActivity(intent);
        });

        // =================================================
        // MODERN BACK HANDLING
        // =================================================


    }

    // =====================================================
    // LOGIN VALIDATION
    // =====================================================

    private void loginCandidate() {

        // Clear previous errors
        emailInputLayout.setError(null);
        passwordInputLayout.setError(null);

        String email = getEmailText();
        String password = getPasswordText();

        // =================================================
        // EMAIL VALIDATION
        // =================================================

        if (TextUtils.isEmpty(email)) {

            emailInputLayout.setError("Enter your email");
            etEmail.requestFocus();
            showKeyboard(etEmail);

            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            emailInputLayout.setError("Enter a valid email address");
            etEmail.requestFocus();
            showKeyboard(etEmail);

            return;
        }

        // =================================================
        // PASSWORD VALIDATION
        // =================================================

        if (TextUtils.isEmpty(password)) {

            passwordInputLayout.setError("Enter your password");
            etPassword.requestFocus();
            showKeyboard(etPassword);

            return;
        }

        // Minimum 8 characters
        if (password.length() < 8) {

            passwordInputLayout.setError(
                    "Password must contain at least 8 characters"
            );

            etPassword.requestFocus();
            showKeyboard(etPassword);

            return;
        }

        // =================================================
        // FRONTEND MOCK LOGIN
        // =================================================
        //
        // TEMPORARY ONLY.
        //
        // No Android database.
        // No real authentication.
        // No password verification.
        //
        // Later this section will call:
        //
        // Android App
        //      ↓
        // Spring Boot REST API
        //      ↓
        // PostgreSQL
        //
        // Backend will handle:
        // - Login
        // - Password verification
        // - User role
        // - JWT authentication
        //
        // =================================================

        hideKeyboard();

        // Admin Mock Login
        if (email.equalsIgnoreCase("admin@nexhire.com") && password.equals("Admin123")) {
            Toast.makeText(
                    CandidateLoginActivity.this,
                    "Admin Login successful",
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent = new Intent(
                    CandidateLoginActivity.this,
                    AdminDashboardActivity.class
            );
            startActivity(intent);
            finish();
            return;
        }

        Toast.makeText(
                CandidateLoginActivity.this,
                "Login successful",
                Toast.LENGTH_SHORT
        ).show();

        // =================================================
        // OPEN CANDIDATE DASHBOARD
        // =================================================

        Intent intent = new Intent(
                CandidateLoginActivity.this,
                CandidateDashboardActivity.class
        );

        // Temporary data for UI testing
        intent.putExtra("EMAIL", email);
        intent.putExtra("ROLE", "Candidate");

        startActivity(intent);

        finish();
    }

    // =====================================================
    // GET EMAIL
    // =====================================================

    private String getEmailText() {

        if (etEmail.getText() == null) {
            return "";
        }

        return etEmail.getText()
                .toString()
                .trim();
    }

    // =====================================================
    // GET PASSWORD
    // =====================================================

    private String getPasswordText() {

        if (etPassword.getText() == null) {
            return "";
        }

        // Do NOT trim password.
        // Spaces can technically be part of a password.
        return etPassword.getText()
                .toString();
    }

    // =====================================================
    // SHOW KEYBOARD
    // =====================================================

    private void showKeyboard(View view) {

        view.post(() -> {

            InputMethodManager imm =
                    (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE
                    );

            if (imm != null) {

                imm.showSoftInput(
                        view,
                        InputMethodManager.SHOW_IMPLICIT
                );
            }
        });
    }

    // =====================================================
    // HIDE KEYBOARD
    // =====================================================

    private void hideKeyboard() {

        View currentView = getCurrentFocus();

        if (currentView == null) {
            return;
        }

        InputMethodManager imm =
                (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE
                );

        if (imm != null) {

            imm.hideSoftInputFromWindow(
                    currentView.getWindowToken(),
                    0
            );
        }

        currentView.clearFocus();
    }
}