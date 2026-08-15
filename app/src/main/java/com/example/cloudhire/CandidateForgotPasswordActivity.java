package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class CandidateForgotPasswordActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnSendOtp;
    private TextView txtBackLogin;

    private TextInputEditText etEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_candidate_forgot_password
        );


        // ==============================
        // INITIALIZE VIEWS
        // ==============================

        btnBack = findViewById(R.id.btnBack);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        txtBackLogin = findViewById(R.id.txtBackLogin);

        etEmail = findViewById(R.id.etEmail);


        // ==============================
        // BACK BUTTON
        // ==============================

        btnBack.setOnClickListener(v -> {

            finish();

        });


        // ==============================
        // BACK TO LOGIN
        // ==============================

        txtBackLogin.setOnClickListener(v -> {

            Intent intent = new Intent(
                    CandidateForgotPasswordActivity.this,
                    CandidateLoginActivity.class
            );

            startActivity(intent);

            finish();

        });


        // ==============================
        // SEND OTP
        // ==============================

        btnSendOtp.setOnClickListener(v -> {

            String email = etEmail.getText()
                    .toString()
                    .trim();


            // Empty email

            if (email.isEmpty()) {

                etEmail.setError(
                        "Enter your email"
                );

                etEmail.requestFocus();

                return;
            }


            // Invalid email

            if (!Patterns.EMAIL_ADDRESS
                    .matcher(email)
                    .matches()) {

                etEmail.setError(
                        "Enter a valid email"
                );

                etEmail.requestFocus();

                return;
            }


            // ==============================
            // OPEN OTP SCREEN
            // ==============================

            Intent intent = new Intent(
                    CandidateForgotPasswordActivity.this,
                    CandidateOtpActivity.class
            );

            intent.putExtra(
                    "email",
                    email
            );

            startActivity(intent);

        });

    }
}