package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etFullName, etEmail, etPhone, etPassword, etConfirmPassword;
    private Button btnRegister;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Views
        scrollView = findViewById(R.id.scrollView);

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Handle keyboard insets
        // Handle keyboard
        ViewCompat.setOnApplyWindowInsetsListener(scrollView, (v, insets) -> {
            int imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom;

            v.setPadding(
                    v.getPaddingLeft(),
                    v.getPaddingTop(),
                    v.getPaddingRight(),
                    imeHeight + 120
            );

            return insets;
        });

// Full Name -> Top
        etFullName.setOnClickListener(v ->
                scrollView.postDelayed(() ->
                        scrollView.smoothScrollTo(0, 0), 200));

// Email
        etEmail.setOnClickListener(v ->
                scrollView.postDelayed(() ->
                        scrollView.smoothScrollTo(0, etEmail.getTop() - 80), 200));

// Phone
        etPhone.setOnClickListener(v ->
                scrollView.postDelayed(() ->
                        scrollView.smoothScrollTo(0, etPhone.getTop() - 80), 200));

// Password (keep your old value)
        etPassword.setOnClickListener(v ->
                scrollView.postDelayed(() ->
                        scrollView.smoothScrollTo(0, v.getBottom() + 600), 250));

// Confirm Password (keep your old value)
        etConfirmPassword.setOnClickListener(v ->
                scrollView.postDelayed(() ->
                        scrollView.smoothScrollTo(0, v.getBottom() + 700), 250));

        btnRegister.setOnClickListener(v -> validateData());

        // Scroll to Password
        etPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                scrollView.postDelayed(() ->
                        scrollView.smoothScrollTo(0, v.getBottom() + 600), 250);
            }
        });

        etPassword.setOnClickListener(v ->
                scrollView.postDelayed(() ->
                        scrollView.smoothScrollTo(0, v.getBottom() + 600), 250));

        // Scroll to Confirm Password
        etConfirmPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                scrollView.postDelayed(() ->
                        scrollView.smoothScrollTo(0, v.getBottom() + 700), 250);
            }
        });

        etConfirmPassword.setOnClickListener(v ->
                scrollView.postDelayed(() ->
                        scrollView.smoothScrollTo(0, v.getBottom() + 700), 250));

        btnRegister.setOnClickListener(v -> validateData());
    }

    private void validateData() {

        etFullName.setError(null);
        etEmail.setError(null);
        etPhone.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);

        String name = etFullName.getText() != null ? etFullName.getText().toString().trim() : "";
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String phone = etPhone.getText() != null ? etPhone.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";
        String confirmPassword = etConfirmPassword.getText() != null ? etConfirmPassword.getText().toString().trim() : "";

        if (name.isEmpty()) {
            etFullName.setError("Enter your full name");
            etFullName.requestFocus();
            return;
        }

        if (!name.matches("^[A-Za-z ]{3,50}$")) {
            etFullName.setError("Name should contain only letters (3-50 characters)");
            etFullName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("Enter your email");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            etPhone.setError("Enter mobile number");
            etPhone.requestFocus();
            return;
        }

        if (!phone.matches("\\d{10}")) {
            etPhone.setError("Mobile number must contain exactly 10 digits");
            etPhone.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Enter password");
            etPassword.requestFocus();
            return;
        }

        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$")) {
            etPassword.setError("Password must contain at least 8 characters, one uppercase, one lowercase and one number");
            etPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            etConfirmPassword.setError("Confirm your password");
            etConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return;
        }

        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}