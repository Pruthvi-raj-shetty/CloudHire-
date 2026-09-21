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

import com.example.cloudhire.api.ApiService;
import com.example.cloudhire.api.RetrofitClient;
import com.example.cloudhire.model.LoginRequest;
import com.example.cloudhire.model.LoginResponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        // =================================================

        btnBack.setOnClickListener(v -> {

            hideKeyboard();

            Intent intent = new Intent(
                    RecruiterLoginActivity.this,
                    MainActivity.class
            );

            intent.putExtra("ROLE", "Recruiter");

            startActivity(intent);
            finish();
        });


        // =================================================
        // SYSTEM BACK BUTTON
        // =================================================

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
        // LOGIN
        // =================================================

        btnLogin.setOnClickListener(v -> loginRecruiter());


        // =================================================
        // FORGOT PASSWORD
        // =================================================

        txtForgotPassword.setOnClickListener(v -> {

            hideKeyboard();

            Intent intent = new Intent(
                    RecruiterLoginActivity.this,
                    RecruiterForgotPasswordActivity.class
            );

            startActivity(intent);
        });


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
    }


    // =====================================================
    // RECRUITER LOGIN
    // =====================================================

    private void loginRecruiter() {

        // Clear previous errors
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
        // REAL BACKEND LOGIN
        // =================================================

        hideKeyboard();

        btnLogin.setEnabled(false);

        LoginRequest request =
                new LoginRequest(
                        email,
                        password
                );


        // =================================================
        // RETROFIT API
        // =================================================

        ApiService apiService =
                RetrofitClient.getApiService(
                        RecruiterLoginActivity.this
                );


        apiService.login(request).enqueue(
                new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(
                            Call<LoginResponse> call,
                            Response<LoginResponse> response
                    ) {

                        btnLogin.setEnabled(true);


                        // =================================================
                        // SUCCESS
                        // =================================================

                        if (response.isSuccessful()
                                && response.body() != null) {

                            LoginResponse loginResponse =
                                    response.body();


                            // =================================================
                            // CHECK RECRUITER ROLE
                            // =================================================

                            if (!"RECRUITER".equalsIgnoreCase(
                                    loginResponse.getRole()
                            )) {

                                Toast.makeText(
                                        RecruiterLoginActivity.this,
                                        "This account is not a recruiter account",
                                        Toast.LENGTH_LONG
                                ).show();

                                return;
                            }


                            // =================================================
                            // SAVE JWT SESSION
                            // =================================================

                            SessionManager sessionManager =
                                    new SessionManager(
                                            RecruiterLoginActivity.this
                                    );


                            sessionManager.saveSession(
                                    loginResponse.getToken(),
                                    loginResponse.getId(),
                                    loginResponse.getName(),
                                    loginResponse.getEmail(),
                                    loginResponse.getRole()
                            );


                            // =================================================
                            // LOGIN SUCCESS
                            // =================================================

                            Toast.makeText(
                                    RecruiterLoginActivity.this,
                                    "Login successful",
                                    Toast.LENGTH_SHORT
                            ).show();


                            // =================================================
                            // OPEN RECRUITER DASHBOARD
                            // =================================================

                            Intent intent =
                                    new Intent(
                                            RecruiterLoginActivity.this,
                                            RecruiterDashboardActivity.class
                                    );


                            intent.putExtra(
                                    "EMAIL",
                                    loginResponse.getEmail()
                            );

                            intent.putExtra(
                                    "ROLE",
                                    loginResponse.getRole()
                            );

                            intent.putExtra(
                                    "NAME",
                                    loginResponse.getName()
                            );


                            startActivity(intent);

                            finish();

                        } else {

                            // =================================================
                            // LOGIN FAILED
                            // =================================================

                            Toast.makeText(
                                    RecruiterLoginActivity.this,
                                    "Invalid email or password",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }


                    // =====================================================
                    // NETWORK FAILURE
                    // =====================================================

                    @Override
                    public void onFailure(
                            Call<LoginResponse> call,
                            Throwable t
                    ) {

                        btnLogin.setEnabled(true);

                        Toast.makeText(
                                RecruiterLoginActivity.this,
                                "Unable to connect to server",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
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

        View currentView =
                getCurrentFocus();

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