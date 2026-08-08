package com.example.cloudhire;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class CandidateLoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;

    private Button btnLogin;
    private TextView txtForgotPassword;
    private TextView txtRegister;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Keyboard behavior
        getWindow().setSoftInputMode(
                android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                        android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        setContentView(R.layout.activity_candidate_login);

        // =====================================================
        // INITIALIZE VIEWS
        // =====================================================

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtRegister = findViewById(R.id.txtRegister);
        btnBack = findViewById(R.id.btnBack);

        // =====================================================
        // BACK BUTTON
        // =====================================================

        btnBack.setOnClickListener(v -> {
            hideKeyboard();
            finish();
        });

        // =====================================================
        // LOGIN BUTTON
        // =====================================================

        btnLogin.setOnClickListener(v -> loginCandidate());

        // =====================================================
        // FORGOT PASSWORD
        // =====================================================

        txtForgotPassword.setOnClickListener(v -> {

            hideKeyboard();

            Toast.makeText(
                    CandidateLoginActivity.this,
                    "Forgot Password selected",
                    Toast.LENGTH_SHORT
            ).show();

            // Later:
            // startActivity(new Intent(
            //         CandidateLoginActivity.this,
            //         ForgotPasswordActivity.class
            // ));
        });

        // =====================================================
        // REGISTER
        // =====================================================

        txtRegister.setOnClickListener(v -> {

            hideKeyboard();

            Intent intent = new Intent(
                    CandidateLoginActivity.this,
                    RegisterActivity.class
            );

            // Tell RegisterActivity that this is Candidate
            intent.putExtra("ROLE", "Candidate");

            startActivity(intent);
        });
    }

    // =========================================================
    // CANDIDATE LOGIN
    // =========================================================

    private void loginCandidate() {

        // Clear previous errors
        etEmail.setError(null);
        etPassword.setError(null);

        // Get input
        String email = getText(etEmail);
        String password = getText(etPassword);

        // =====================================================
        // EMAIL VALIDATION
        // =====================================================

        if (TextUtils.isEmpty(email)) {

            etEmail.setError("Enter your email");
            etEmail.requestFocus();

            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            etEmail.setError("Enter a valid email address");
            etEmail.requestFocus();

            return;
        }

        // =====================================================
        // PASSWORD VALIDATION
        // =====================================================

        if (TextUtils.isEmpty(password)) {

            etPassword.setError("Enter your password");
            etPassword.requestFocus();

            return;
        }

        if (password.length() < 8) {

            etPassword.setError(
                    "Password must contain at least 8 characters"
            );

            etPassword.requestFocus();

            return;
        }

        // =====================================================
        // HIDE KEYBOARD
        // =====================================================

        hideKeyboard();

        // =====================================================
        // LOGIN SUCCESS
        // =====================================================

        Toast.makeText(
                CandidateLoginActivity.this,
                "Candidate Login Successful",
                Toast.LENGTH_SHORT
        ).show();

        // =====================================================
        // OPEN CANDIDATE DASHBOARD
        // =====================================================

        Intent intent = new Intent(
                CandidateLoginActivity.this,
                CandidateDashboardActivity.class
        );

        intent.putExtra("EMAIL", email);
        intent.putExtra("ROLE", "Candidate");

        startActivity(intent);

        finish();
    }

    // =========================================================
    // GET TEXT
    // =========================================================

    private String getText(TextInputEditText editText) {

        if (editText.getText() == null) {
            return "";
        }

        return editText.getText()
                .toString()
                .trim();
    }

    // =========================================================
    // HIDE KEYBOARD
    // =========================================================

    private void hideKeyboard() {

        View currentView = getCurrentFocus();

        if (currentView != null) {

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
}