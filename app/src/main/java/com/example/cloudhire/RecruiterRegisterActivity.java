package com.example.cloudhire;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.textfield.TextInputEditText;

public class RecruiterRegisterActivity extends AppCompatActivity {

    // =====================================================
    // VARIABLES
    // =====================================================

    private ImageButton btnBack;

    private TextInputEditText etRecruiterName;
    private TextInputEditText etCompanyName;
    private TextInputEditText etCompanyEmail;
    private TextInputEditText etPassword;
    private TextInputEditText etConfirmPassword;

    private Button btnRecruiterRegister;
    private TextView txtRecruiterLogin;

    private NestedScrollView scrollView;


    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // =====================================================
        // KEYBOARD HANDLING
        // =====================================================

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        setContentView(R.layout.activity_recruiter_register);


        // =====================================================
        // INITIALIZE VIEWS
        // =====================================================

        scrollView = findViewById(R.id.scrollView);

        btnBack = findViewById(R.id.btnBack);

        etRecruiterName = findViewById(R.id.etRecruiterName);
        etCompanyName = findViewById(R.id.etCompanyName);
        etCompanyEmail = findViewById(R.id.etCompanyEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRecruiterRegister = findViewById(R.id.btnRecruiterRegister);

        txtRecruiterLogin = findViewById(R.id.txtRecruiterLogin);


        // =====================================================
        // TOP BACK BUTTON
        // =====================================================

        btnBack.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RecruiterRegisterActivity.this,
                    MainActivity.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
            );

            startActivity(intent);
            finish();
        });


        // =====================================================
        // ALREADY HAVE AN ACCOUNT? LOGIN
        // =====================================================

        txtRecruiterLogin.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RecruiterRegisterActivity.this,
                    RecruiterLoginActivity.class
            );

            intent.putExtra("ROLE", "Recruiter");

            startActivity(intent);
            finish();
        });


        // =====================================================
        // MOBILE / SYSTEM BACK BUTTON
        // =====================================================

        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        Intent intent = new Intent(
                                RecruiterRegisterActivity.this,
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


        // =====================================================
        // KEYBOARD / WINDOW INSETS
        // =====================================================

        ViewCompat.setOnApplyWindowInsetsListener(
                scrollView,
                (view, insets) -> {

                    boolean keyboardVisible =
                            insets.isVisible(WindowInsetsCompat.Type.ime());

                    int imeHeight = insets.getInsets(
                            WindowInsetsCompat.Type.ime()
                    ).bottom;


                    if (keyboardVisible) {

                        // Keep enough space when keyboard is open
                        scrollView.setPadding(
                                scrollView.getPaddingLeft(),
                                scrollView.getPaddingTop(),
                                scrollView.getPaddingRight(),
                                imeHeight + 180
                        );

                    } else {

                        // Keyboard closed
                        scrollView.setPadding(
                                scrollView.getPaddingLeft(),
                                scrollView.getPaddingTop(),
                                scrollView.getPaddingRight(),
                                180
                        );

                        // Return to top when keyboard closes
                        scrollView.postDelayed(() -> {
                            scrollView.smoothScrollTo(0, 0);
                        }, 150);
                    }

                    return insets;
                }
        );


        // =====================================================
        // COMMON FOCUS SCROLL LISTENER
        // =====================================================

        View.OnFocusChangeListener focusListener =
                (v, hasFocus) -> {

                    if (hasFocus) {

                        scrollView.postDelayed(() -> {

                            Rect rect = new Rect();

                            // Get actual position of focused field
                            v.getDrawingRect(rect);

                            // Convert to NestedScrollView coordinates
                            scrollView.offsetDescendantRectToMyCoords(
                                    v,
                                    rect
                            );

                            // Scroll field into visible area
                            int targetY = Math.max(
                                    0,
                                    rect.top - 60
                            );

                            scrollView.smoothScrollTo(
                                    0,
                                    targetY
                            );

                        }, 250);
                    }
                };


        // =====================================================
        // APPLY FOCUS LISTENER TO ALL INPUT FIELDS
        // =====================================================

        etRecruiterName.setOnFocusChangeListener(focusListener);

        etCompanyName.setOnFocusChangeListener(focusListener);

        etCompanyEmail.setOnFocusChangeListener(focusListener);

        etPassword.setOnFocusChangeListener(focusListener);

        etConfirmPassword.setOnFocusChangeListener(focusListener);


        // =====================================================
        // REGISTER BUTTON
        // =====================================================

        btnRecruiterRegister.setOnClickListener(v ->
                registerRecruiter()
        );
    }


    // =====================================================
    // REGISTER RECRUITER
    // =====================================================

    private void registerRecruiter() {

        // Clear previous errors
        etRecruiterName.setError(null);
        etCompanyName.setError(null);
        etCompanyEmail.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);


        // =====================================================
        // GET VALUES
        // =====================================================

        String recruiterName = getText(etRecruiterName);
        String companyName = getText(etCompanyName);
        String companyEmail = getText(etCompanyEmail);
        String password = getText(etPassword);
        String confirmPassword = getText(etConfirmPassword);


        // =====================================================
        // RECRUITER NAME VALIDATION
        // =====================================================

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


        // =====================================================
        // COMPANY NAME VALIDATION
        // =====================================================

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


        // =====================================================
        // COMPANY EMAIL VALIDATION
        // =====================================================

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


        // =====================================================
        // PASSWORD VALIDATION
        // =====================================================

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


        // =====================================================
        // CONFIRM PASSWORD
        // =====================================================

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


        // =====================================================
        // SUCCESS
        // =====================================================

        Toast.makeText(
                RecruiterRegisterActivity.this,
                "Recruiter Registration Successful",
                Toast.LENGTH_SHORT
        ).show();


        // =====================================================
        // GO TO RECRUITER LOGIN
        // =====================================================

        Intent intent = new Intent(
                RecruiterRegisterActivity.this,
                RecruiterLoginActivity.class
        );

        intent.putExtra(
                "ROLE",
                "Recruiter"
        );

        startActivity(intent);
        finish();
    }


    // =====================================================
    // GET TEXT FROM EDIT TEXT
    // =====================================================

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