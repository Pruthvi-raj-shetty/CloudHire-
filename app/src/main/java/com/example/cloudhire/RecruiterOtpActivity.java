package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class RecruiterOtpActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnVerifyOtp;
    private TextInputEditText etOtp;
    private TextView txtResendOtp;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recruiter_otp);

        btnBack = findViewById(R.id.btnBack);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);
        etOtp = findViewById(R.id.etOtp);
        txtResendOtp = findViewById(R.id.txtResendOtp);

        email = getIntent().getStringExtra("email");

        btnBack.setOnClickListener(v -> finish());

        txtResendOtp.setOnClickListener(v -> {

            Toast.makeText(
                    this,
                    "OTP resent",
                    Toast.LENGTH_SHORT
            ).show();

        });

        btnVerifyOtp.setOnClickListener(v -> {

            String otp = etOtp.getText()
                    .toString()
                    .trim();

            if (otp.length() != 6) {

                etOtp.setError("Enter 6-digit OTP");

                return;
            }

            Intent intent = new Intent(
                    RecruiterOtpActivity.this,
                    RecruiterNewPasswordActivity.class
            );

            intent.putExtra("email", email);

            startActivity(intent);

            finish();
        });
    }
}