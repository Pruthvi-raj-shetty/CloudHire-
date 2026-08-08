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

import com.google.android.material.textfield.TextInputEditText;

public class RecruiterRegisterActivity extends AppCompatActivity {

    // -----------------------------------------
    // VARIABLES
    // -----------------------------------------

    private ImageButton btnBack;

    private TextInputEditText etRecruiterName;
    private TextInputEditText etCompanyName;
    private TextInputEditText etCompanyEmail;
    private TextInputEditText etPassword;
    private TextInputEditText etConfirmPassword;

    private Button btnRecruiterRegister;
    private TextView txtRecruiterLogin;

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

        setContentView(R.layout.activity_recruiter_register);


        // -----------------------------------------
        // INITIALIZE VIEWS
        // -----------------------------------------

        scrollView = findViewById(R.id.scrollView);

        btnBack = findViewById(R.id.btnBack);

        etRecruiterName = findViewById(R.id.etRecruiterName);
        etCompanyName = findViewById(R.id.etCompanyName);
        etCompanyEmail = findViewById(R.id.etCompanyEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRecruiterRegister = findViewById(R.id.btnRecruiterRegister);
        txtRecruiterLogin = findViewById(R.id.txtRecruiterLogin);


        // -----------------------------------------
        // BACK BUTTON
        // -----------------------------------------

        btnBack.setOnClickListener(v -> {
            finish();
        });


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
        // RECRUITER NAME
        // -----------------------------------------

        etRecruiterName.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {

                scrollView.postDelayed(() ->
                        scrollView.smoothScrollTo(0, 0), 200);
            }
        });


        // -----------------------------------------
        // COMPANY NAME
        // -----------------------------------------

        etCompanyName.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {

                scrollView.postDelayed(() ->
                        scrollView.smoothScrollTo(
                                0,
                                Math.max(0, v.getTop() - 80)
                        ), 200);
            }
        });


        // -----------------------------------------
        // COMPANY EMAIL
        // -----------------------------------------

        etCompanyEmail.setOnFocusChangeListener((v, hasFocus) -> {

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

        btnRecruiterRegister.setOnClickListener(v ->
                registerRecruiter()
        );


        // -----------------------------------------
        // LOGIN
        // -----------------------------------------

        txtRecruiterLogin.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RecruiterRegisterActivity.this,
                    LoginActivity.class
            );

            // Recruiter role
            intent.putExtra("ROLE", "Recruiter");

            startActivity(intent);
            finish();
        });
    }


    // =============================================
    // VALIDATE RECRUITER REGISTRATION
    // =============================================

    private void registerRecruiter() {

        // Clear old errors
        etRecruiterName.setError(null);
        etCompanyName.setError(null);
        etCompanyEmail.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);


        // -----------------------------------------
        // GET VALUES
        // -----------------------------------------

        String recruiterName = getText(etRecruiterName);
        String companyName = getText(etCompanyName);
        String companyEmail = getText(etCompanyEmail);
        String password = getText(etPassword);
        String confirmPassword = getText(etConfirmPassword);


        // -----------------------------------------
        // RECRUITER NAME
        // -----------------------------------------

        if (TextUtils.isEmpty(recruiterName)) {

            etRecruiterName.setError(
                    "Enter recruiter name"
            );

            etRecruiterName.requestFocus();
            return;
        }


        if (!recruiterName.matches(
                "^[A-Za-z ]{3,50}$"
        )) {

            etRecruiterName.setError(
                    "Name should contain only letters (3-50 characters)"
            );

            etRecruiterName.requestFocus();
            return;
        }


        // -----------------------------------------
        // COMPANY NAME
        // -----------------------------------------

        if (TextUtils.isEmpty(companyName)) {

            etCompanyName.setError(
                    "Enter company name"
            );

            etCompanyName.requestFocus();
            return;
        }


        if (!companyName.matches(
                "^[A-Za-z0-9 .,&'-]{2,100}$"
        )) {

            etCompanyName.setError(
                    "Enter a valid company name"
            );

            etCompanyName.requestFocus();
            return;
        }


        // -----------------------------------------
        // COMPANY EMAIL
        // -----------------------------------------

        if (TextUtils.isEmpty(companyEmail)) {

            etCompanyEmail.setError(
                    "Enter company email"
            );

            etCompanyEmail.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS
                .matcher(companyEmail)
                .matches()) {

            etCompanyEmail.setError(
                    "Enter a valid company email"
            );

            etCompanyEmail.requestFocus();
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
                RecruiterRegisterActivity.this,
                "Recruiter Registration Successful",
                Toast.LENGTH_SHORT
        ).show();


        // -----------------------------------------
        // GO TO LOGIN
        // -----------------------------------------

        Intent intent = new Intent(
                RecruiterRegisterActivity.this,
                LoginActivity.class
        );

        intent.putExtra(
                "ROLE",
                "Recruiter"
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