package com.example.cloudhire;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class RecruiterNewPasswordActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnResetPassword;

    private TextInputEditText etNewPassword;
    private TextInputEditText etConfirmPassword;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recruiter_new_password);

        // Find views
        btnBack = findViewById(R.id.btnBack);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        // Get email from previous activity
        email = getIntent().getStringExtra("email");

        // Back button
        btnBack.setOnClickListener(v -> {
            hideKeyboard();
            finish();
        });

        // Reset Password button
        btnResetPassword.setOnClickListener(v -> {

            // Hide keyboard
            hideKeyboard();

            String password = etNewPassword.getText()
                    .toString()
                    .trim();

            String confirmPassword = etConfirmPassword.getText()
                    .toString()
                    .trim();

            // Check new password
            if (password.isEmpty()) {

                etNewPassword.setError("Enter new password");
                etNewPassword.requestFocus();

                return;
            }

            // Check password length
            if (password.length() < 8) {

                etNewPassword.setError(
                        "Password must contain at least 8 characters"
                );

                etNewPassword.requestFocus();

                return;
            }

            // Check confirm password
            if (confirmPassword.isEmpty()) {

                etConfirmPassword.setError(
                        "Confirm your password"
                );

                etConfirmPassword.requestFocus();

                return;
            }

            // Check passwords match
            if (!password.equals(confirmPassword)) {

                etConfirmPassword.setError(
                        "Passwords do not match"
                );

                etConfirmPassword.requestFocus();

                return;
            }

            // Open success screen
            Intent intent = new Intent(
                    RecruiterNewPasswordActivity.this,
                    PasswordResetSuccessActivity.class
            );

            intent.putExtra("email", email);

            startActivity(intent);

            finish();
        });
    }

    // Method to hide keyboard
    private void hideKeyboard() {

        View view = getCurrentFocus();

        if (view != null) {

            InputMethodManager imm =
                    (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE
                    );

            imm.hideSoftInputFromWindow(
                    view.getWindowToken(),
                    0
            );

            view.clearFocus();
        }
    }
}