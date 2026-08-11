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


public class RegisterActivity extends AppCompatActivity {

    // =====================================================
    // VARIABLES
    // =====================================================

    private ImageButton btnBack;

    private TextInputEditText etFullName;
    private TextInputEditText etEmail;
    private TextInputEditText etPhone;
    private TextInputEditText etPassword;
    private TextInputEditText etConfirmPassword;

    private Button btnRegister;
    private TextView txtLogin;

    private NestedScrollView scrollView;


    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Keyboard handling
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        setContentView(R.layout.activity_register);


        // =====================================================
        // INITIALIZE VIEWS
        // =====================================================

        scrollView = findViewById(R.id.scrollView);

        btnBack = findViewById(R.id.btnBack);

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);


        // =====================================================
        // BACK BUTTON
        // =====================================================

        btnBack.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RegisterActivity.this,
                    CandidateLoginActivity.class
            );

            startActivity(intent);
            finish();
        });


        // =====================================================
        // SYSTEM BACK BUTTON
        // =====================================================

        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        Intent intent = new Intent(
                                RegisterActivity.this,
                                CandidateLoginActivity.class
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
        // KEYBOARD / SCROLL HANDLING
        // =====================================================

        ViewCompat.setOnApplyWindowInsetsListener(
                scrollView,
                (view, insets) -> {

                    int imeHeight = insets.getInsets(
                            WindowInsetsCompat.Type.ime()
                    ).bottom;

                    boolean keyboardVisible =
                            insets.isVisible(
                                    WindowInsetsCompat.Type.ime()
                            );

                    // Add space at bottom when keyboard is open
                    view.setPadding(
                            view.getPaddingLeft(),
                            view.getPaddingTop(),
                            view.getPaddingRight(),
                            keyboardVisible
                                    ? imeHeight + 120
                                    : 120
                    );

                    // When keyboard closes,
                    // return the form to the top
                    if (!keyboardVisible) {

                        scrollView.postDelayed(() -> {
                            scrollView.smoothScrollTo(0, 0);
                        }, 150);
                    }

                    return insets;
                }
        );


        // =====================================================
        // AUTO SCROLL FOR ALL INPUT FIELDS
        // =====================================================

        View.OnFocusChangeListener focusListener =
                (v, hasFocus) -> {

                    if (hasFocus) {

                        scrollView.postDelayed(() -> {

                            Rect rect = new Rect();

                            v.getDrawingRect(rect);

                            scrollView.offsetDescendantRectToMyCoords(
                                    v,
                                    rect
                            );

                            // Move field into visible area
                            scrollView.smoothScrollTo(
                                    0,
                                    Math.max(
                                            0,
                                            rect.top - 80
                                    )
                            );

                        }, 250);
                    }
                };


        // Apply same listener to every field

        etFullName.setOnFocusChangeListener(focusListener);

        etEmail.setOnFocusChangeListener(focusListener);

        etPhone.setOnFocusChangeListener(focusListener);

        etPassword.setOnFocusChangeListener(focusListener);

        etConfirmPassword.setOnFocusChangeListener(focusListener);


        // =====================================================
        // REGISTER BUTTON
        // =====================================================

        btnRegister.setOnClickListener(v ->
                validateData()
        );


        // =====================================================
        // ALREADY HAVE ACCOUNT? LOGIN
        // =====================================================

        txtLogin.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RegisterActivity.this,
                    CandidateLoginActivity.class
            );

            intent.putExtra(
                    "ROLE",
                    "Candidate"
            );

            startActivity(intent);
            finish();
        });
    }


    // =====================================================
    // VALIDATE REGISTRATION
    // =====================================================

    private void validateData() {

        // Clear old errors
        etFullName.setError(null);
        etEmail.setError(null);
        etPhone.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);


        // =====================================================
        // GET VALUES
        // =====================================================

        String name = getText(etFullName);
        String email = getText(etEmail);
        String phone = getText(etPhone);
        String password = getText(etPassword);
        String confirmPassword = getText(etConfirmPassword);


        // =====================================================
        // FULL NAME
        // =====================================================

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


        // =====================================================
        // EMAIL
        // =====================================================

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


        // =====================================================
        // PHONE
        // =====================================================

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


        // =====================================================
        // PASSWORD
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


    // =====================================================
    // GET TEXT
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