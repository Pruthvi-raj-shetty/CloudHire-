package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.textfield.TextInputEditText;import android.content.Intent;import androidx.activity.OnBackPressedCallback;

public class RegisterActivity extends AppCompatActivity {

    // -----------------------------------------
    // VARIABLES
    // -----------------------------------------

    private ImageButton btnBack;

    private TextInputEditText etFullName;
    private TextInputEditText etEmail;
    private TextInputEditText etPhone;
    private TextInputEditText etPassword;
    private TextInputEditText etConfirmPassword;

    private Button btnRegister;
    private TextView txtLogin;

    private NestedScrollView scrollView;


    // -----------------------------------------
    // ON CREATE
    // -----------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Keyboard handling
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        setContentView(R.layout.activity_register);


        // -----------------------------------------
        // INITIALIZE VIEWS
        // -----------------------------------------

        scrollView = findViewById(R.id.scrollView);

        btnBack = findViewById(R.id.btnBack);

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);


        // -----------------------------------------
        // BACK BUTTON
        // -----------------------------------------

        // =====================================================
// BACK BUTTON → MAIN ACTIVITY
// =====================================================

        // =====================================================
// BACK BUTTON → MAIN ACTIVITY
// =====================================================

        btnBack.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RegisterActivity.this,
                    MainActivity.class
            );

            startActivity(intent);
            finish();
        });
// =====================================================
// SYSTEM BACK BUTTON → MAIN ACTIVITY
// =====================================================

        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        Intent intent = new Intent(
                                RegisterActivity.this,
                                MainActivity.class
                        );

                        intent.setFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_SINGLE_TOP
                        );

                        startActivity(intent);
                        finish();
                    }
                }
        );
        // -----------------------------------------
        // KEYBOARD / SCROLL HANDLING
        // -----------------------------------------

        ViewCompat.setOnApplyWindowInsetsListener(
                scrollView,
                (v, insets) -> {

                    int imeHeight = insets.getInsets(
                            WindowInsetsCompat.Type.ime()
                    ).bottom;

                    v.setPadding(
                            v.getPaddingLeft(),
                            v.getPaddingTop(),
                            v.getPaddingRight(),
                            imeHeight + 120
                    );

                    return insets;
                }
        );


        // -----------------------------------------
        // FULL NAME
        // -----------------------------------------

        etFullName.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {

                scrollView.postDelayed(() ->
                        scrollView.smoothScrollTo(0, 0), 200);
            }
        });


        // -----------------------------------------
        // EMAIL
        // -----------------------------------------

        etEmail.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {

                scrollView.postDelayed(() ->
                        scrollView.smoothScrollTo(
                                0,
                                Math.max(0, v.getTop() - 80)
                        ), 200);
            }
        });


        // -----------------------------------------
        // PHONE
        // -----------------------------------------

        etPhone.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {

                scrollView.postDelayed(() ->
                        scrollView.smoothScrollTo(
                                0,
                                Math.max(0, v.getTop() - 80)
                        ), 200);
            }
        });


        // -----------------------------------------
        // PASSWORD
        // -----------------------------------------

        etPassword.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {

                scrollView.postDelayed(() ->
                        scrollView.smoothScrollTo(
                                0,
                                v.getBottom() + 600
                        ), 250);
            }
        });


        // -----------------------------------------
        // CONFIRM PASSWORD
        // -----------------------------------------

        etConfirmPassword.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {

                scrollView.postDelayed(() ->
                        scrollView.smoothScrollTo(
                                0,
                                v.getBottom() + 700
                        ), 250);
            }
        });


        // -----------------------------------------
        // REGISTER BUTTON
        // -----------------------------------------

        btnRegister.setOnClickListener(v ->
                validateData()
        );


        // -----------------------------------------
        // ALREADY HAVE ACCOUNT? LOGIN
        // -----------------------------------------

        txtLogin.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RegisterActivity.this,
                    CandidateLoginActivity.class
            );

            // Candidate role
            intent.putExtra("ROLE", "Candidate");

            startActivity(intent);
            finish();
        });
    }


    // =============================================
    // VALIDATE REGISTRATION
    // =============================================

    private void validateData() {

        // Clear old errors
        etFullName.setError(null);
        etEmail.setError(null);
        etPhone.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);


        // -----------------------------------------
        // GET VALUES
        // -----------------------------------------

        String name = getText(etFullName);
        String email = getText(etEmail);
        String phone = getText(etPhone);
        String password = getText(etPassword);
        String confirmPassword = getText(etConfirmPassword);


        // -----------------------------------------
        // FULL NAME
        // -----------------------------------------

        if (TextUtils.isEmpty(name)) {

            etFullName.setError(
                    "Enter your full name"
            );

            etFullName.requestFocus();
            return;
        }


        if (!name.matches(
                "^[A-Za-z ]{3,50}$"
        )) {

            etFullName.setError(
                    "Name should contain only letters (3-50 characters)"
            );

            etFullName.requestFocus();
            return;
        }


        // -----------------------------------------
        // EMAIL
        // -----------------------------------------

        if (TextUtils.isEmpty(email)) {

            etEmail.setError(
                    "Enter your email"
            );

            etEmail.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()) {

            etEmail.setError(
                    "Enter a valid email address"
            );

            etEmail.requestFocus();
            return;
        }


        // -----------------------------------------
        // PHONE
        // -----------------------------------------

        if (TextUtils.isEmpty(phone)) {

            etPhone.setError(
                    "Enter mobile number"
            );

            etPhone.requestFocus();
            return;
        }


        if (!phone.matches(
                "\\d{10}"
        )) {

            etPhone.setError(
                    "Mobile number must contain exactly 10 digits"
            );

            etPhone.requestFocus();
            return;
        }


        // -----------------------------------------
        // PASSWORD
        // -----------------------------------------

        if (TextUtils.isEmpty(password)) {

            etPassword.setError(
                    "Enter password"
            );

            etPassword.requestFocus();
            return;
        }


        if (!password.matches(
                "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$"
        )) {

            etPassword.setError(
                    "Password must contain at least 8 characters, " +
                            "one uppercase, one lowercase and one number"
            );

            etPassword.requestFocus();
            return;
        }


        // -----------------------------------------
        // CONFIRM PASSWORD
        // -----------------------------------------

        if (TextUtils.isEmpty(confirmPassword)) {

            etConfirmPassword.setError(
                    "Confirm your password"
            );

            etConfirmPassword.requestFocus();
            return;
        }


        if (!password.equals(confirmPassword)) {

            etConfirmPassword.setError(
                    "Passwords do not match"
            );

            etConfirmPassword.requestFocus();
            return;
        }


        // -----------------------------------------
        // SUCCESS
        // -----------------------------------------

        Toast.makeText(
                RegisterActivity.this,
                "Registration Successful",
                Toast.LENGTH_SHORT
        ).show();

        Intent intent = new Intent(
                RegisterActivity.this,
                CandidateLoginActivity.class
        );

        startActivity(intent);
        finish();
    }


    // =============================================
    // GET TEXT
    // =============================================

    private String getText(
            TextInputEditText editText
    ) {

        if (editText.getText() == null) {
            return "";
        }

        return editText.getText()
                .toString()
                .trim();
    }
}