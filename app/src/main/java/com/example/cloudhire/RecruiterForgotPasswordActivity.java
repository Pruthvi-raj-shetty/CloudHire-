package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class RecruiterForgotPasswordActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnSendOtp;
    private TextView txtBackLogin;
    private TextInputEditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recruiter_forgot_password);

        btnBack = findViewById(R.id.btnBack);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        txtBackLogin = findViewById(R.id.txtBackLogin);
        etEmail = findViewById(R.id.etEmail);

        // BACK BUTTON

        btnBack.setOnClickListener(v -> {
            finish();
        });

        // BACK TO LOGIN

        txtBackLogin.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RecruiterForgotPasswordActivity.this,
                    RecruiterLoginActivity.class
            );

            startActivity(intent);
            finish();
        });

        // SEND OTP

        btnSendOtp.setOnClickListener(v -> {

            String email = etEmail.getText()
                    .toString()
                    .trim();

            if (email.isEmpty()) {

                etEmail.setError("Enter your email");

                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS
                    .matcher(email)
                    .matches()) {

                etEmail.setError("Enter a valid email");

                return;
            }

            Intent intent = new Intent(
                    RecruiterForgotPasswordActivity.this,
                    RecruiterOtpActivity.class
            );

            intent.putExtra("email", email);

            startActivity(intent);
        });
    }
}