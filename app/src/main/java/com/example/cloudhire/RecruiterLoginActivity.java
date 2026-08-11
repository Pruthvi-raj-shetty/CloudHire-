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

import com.google.android.material.textfield.TextInputEditText;import android.content.Intent;

public class RecruiterLoginActivity extends AppCompatActivity {

    // =====================================================
    // VIEWS
    // =====================================================

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;

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

        // Keyboard handling
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        setContentView(R.layout.activity_recruiter_login);

        // =================================================
        // INITIALIZE VIEWS
        // =================================================

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtRegister = findViewById(R.id.txtRegister);
        btnBack = findViewById(R.id.btnBack);

        // =================================================
        // BACK BUTTON
        // Goes to Recruiter Registration
        // =================================================

        btnBack.setOnClickListener(v -> {

            hideKeyboard();

            Intent intent = new Intent(
                    RecruiterLoginActivity.this,
                    MainActivity.class
            );

            intent.putExtra("ROLE", "Recruiter");

            startActivity(intent);
        });
        // =====================================================
// SYSTEM / MOBILE BACK BUTTON → RECRUITER REGISTER
// =====================================================

        // =====================================================
// SYSTEM / MOBILE BACK BUTTON → RECRUITER REGISTER
// =====================================================

        // =====================================================
// SYSTEM BACK BUTTON → RECRUITER REGISTER
// =====================================================

        // =====================================================
// MOBILE BACK BUTTON → RECRUITER REGISTER
// =====================================================

        // =====================================================
// SYSTEM BACK BUTTON → RECRUITER REGISTER
// =====================================================

        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        hideKeyboard();

                        Intent intent = new Intent(
                                RecruiterLoginActivity.this,
                                MainActivity.class
                        );

                        intent.putExtra("ROLE", "Recruiter");

                        startActivity(intent);
                        finish();
                    }
                }
        );
        // =================================================
        // LOGIN BUTTON
        // =================================================

        btnLogin.setOnClickListener(v -> loginRecruiter());

        // =================================================
        // FORGOT PASSWORD
        // =================================================

        // =====================================================
// FORGOT PASSWORD
// =====================================================

        txtForgotPassword.setOnClickListener(v -> {

            hideKeyboard();

            Intent intent = new Intent(
                    RecruiterLoginActivity.this,
                    RecruiterForgotPasswordActivity.class
            );

            startActivity(intent);

        });
            // Later:
            // startActivity(new Intent(
            //     RecruiterLoginActivity.this,
            //     ForgotPasswordActivity.class
            // ));

        // =================================================
        // REGISTER
        // =================================================

        txtRegister.setOnClickListener(v -> {

            hideKeyboard();

            Intent intent = new Intent(
                    RecruiterLoginActivity.this,
                    RecruiterRegisterActivity.class
            );

            intent.putExtra("ROLE", "Recruiter");

            startActivity(intent);
        });

        // =================================================
        // SYSTEM BACK BUTTON
        // =================================================

        // =====================================================
// SYSTEM BACK BUTTON → RECRUITER REGISTER
// =====================================================

        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        hideKeyboard();

                        Intent intent = new Intent(
                                RecruiterLoginActivity.this,
                              MainActivity.class
                        );

                        intent.putExtra("ROLE", "Recruiter");

                        startActivity(intent);
                        finish();
                    }
                }
        );
    }

    // =====================================================
    // LOGIN VALIDATION
    // =====================================================

    private void loginRecruiter() {

        // Clear old errors
        etEmail.setError(null);
        etPassword.setError(null);

        String email = getEmailText();
        String password = getPasswordText();

        // =================================================
        // EMAIL VALIDATION
        // =================================================

        if (TextUtils.isEmpty(email)) {

            etEmail.setError("Enter your email");
            etEmail.requestFocus();
            showKeyboard(etEmail);

            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            etEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            showKeyboard(etEmail);

            return;
        }

        // =================================================
        // PASSWORD VALIDATION
        // =================================================

        if (TextUtils.isEmpty(password)) {

            etPassword.setError("Enter your password");
            etPassword.requestFocus();
            showKeyboard(etPassword);

            return;
        }

        if (password.length() < 8) {

            etPassword.setError(
                    "Password must contain at least 8 characters"
            );

            etPassword.requestFocus();
            showKeyboard(etPassword);

            return;
        }

        // =================================================
        // TEMPORARY MOCK LOGIN
        // =================================================
        //
        // This is only for frontend UI testing.
        //
        // No Android database.
        // No real authentication.
        //
        // Later:
        //
        // Android
        //    ↓
        // Spring Boot REST API
        //    ↓
        // PostgreSQL
        //
        // Backend will handle:
        // - Login
        // - Password verification
        // - Recruiter role
        // - JWT
        //
        // =================================================

        hideKeyboard();

        Toast.makeText(
                RecruiterLoginActivity.this,
                "Login successful",
                Toast.LENGTH_SHORT
        ).show();

        // =================================================
        // OPEN RECRUITER DASHBOARD
        // =================================================

        Intent intent = new Intent(
                RecruiterLoginActivity.this,
                RecruiterDashboardActivity.class
        );

        // Temporary UI testing data
        intent.putExtra("EMAIL", email);
        intent.putExtra("ROLE", "Recruiter");

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

        // Do not trim password
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