package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class RecruiterOtpActivity extends AppCompatActivity {

    // =====================================================
    // VIEWS
    // =====================================================

    private ImageButton btnBack;
    private Button btnVerifyOtp;

    private TextInputEditText etOtp;

    private TextView txtResendOtp;
    private TextView txtOtpTimer;


    // =====================================================
    // VARIABLES
    // =====================================================

    private String email;

    private CountDownTimer countDownTimer;

    private boolean otpExpired = false;


    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recruiter_otp);


        // =================================================
        // INITIALIZE VIEWS
        // =================================================

        btnBack = findViewById(R.id.btnBack);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);

        etOtp = findViewById(R.id.etOtp);

        txtResendOtp = findViewById(R.id.txtResendOtp);
        txtOtpTimer = findViewById(R.id.txtOtpTimer);


        // =================================================
        // GET EMAIL
        // =================================================

        email = getIntent().getStringExtra("email");


        // =================================================
        // START 30 SECOND TIMER
        // =================================================

        startOtpTimer();


        // =================================================
        // BACK BUTTON
        // =================================================

        btnBack.setOnClickListener(v -> {

            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            finish();
        });


        // =================================================
        // RESEND OTP
        // =================================================

        txtResendOtp.setOnClickListener(v -> {

            if (!otpExpired) {
                return;
            }

            // Clear old OTP
            etOtp.setText("");

            // Remove previous error
            etOtp.setError(null);

            // Start timer again
            startOtpTimer();

            Toast.makeText(
                    RecruiterOtpActivity.this,
                    "OTP resent successfully",
                    Toast.LENGTH_SHORT
            ).show();
        });


        // =================================================
        // VERIFY OTP
        // =================================================

        btnVerifyOtp.setOnClickListener(v -> verifyOtp());

    }


    // =====================================================
    // START OTP TIMER
    // =====================================================

    private void startOtpTimer() {

        // OTP is active
        otpExpired = false;


        // Disable resend
        txtResendOtp.setClickable(false);
        txtResendOtp.setFocusable(false);

        txtResendOtp.setText("Resend OTP");
        txtResendOtp.setTextColor(
                getColor(android.R.color.darker_gray)
        );


        // Start 30 second timer
        countDownTimer = new CountDownTimer(
                30000,
                1000
        ) {

            @Override
            public void onTick(long millisUntilFinished) {

                long seconds =
                        millisUntilFinished / 1000;

                txtOtpTimer.setText(
                        String.format(
                                "OTP expires in 00:%02d",
                                seconds
                        )
                );
            }


            @Override
            public void onFinish() {

                // OTP expired
                otpExpired = true;


                txtOtpTimer.setText(
                        "OTP has expired"
                );

                txtOtpTimer.setTextColor(
                        getColor(android.R.color.holo_red_dark)
                );


                // Enable resend
                txtResendOtp.setClickable(true);
                txtResendOtp.setFocusable(true);

                txtResendOtp.setText(
                        "Resend OTP"
                );

                txtResendOtp.setTextColor(
                        getColor(R.color.purple_500)
                );
            }

        }.start();
    }


    // =====================================================
    // VERIFY OTP
    // =====================================================

    private void verifyOtp() {

        String otp = etOtp.getText()
                .toString()
                .trim();


        // =================================================
        // CHECK EMPTY OTP
        // =================================================

        if (otp.isEmpty()) {

            etOtp.setError(
                    "Please enter OTP"
            );

            etOtp.requestFocus();

            return;
        }


        // =================================================
        // CHECK OTP LENGTH
        // =================================================

        if (otp.length() != 6) {

            etOtp.setError(
                    "Enter 6-digit OTP"
            );

            etOtp.requestFocus();

            return;
        }


        // =================================================
        // CHECK OTP EXPIRY
        // =================================================

        if (otpExpired) {

            etOtp.setError(
                    "OTP expired. Please resend OTP."
            );

            Toast.makeText(
                    RecruiterOtpActivity.this,
                    "OTP expired. Please resend OTP.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // =================================================
        // OTP VALID
        // =================================================

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }


        Toast.makeText(
                RecruiterOtpActivity.this,
                "OTP verified successfully",
                Toast.LENGTH_SHORT
        ).show();


        // =================================================
        // GO TO NEW PASSWORD
        // =================================================

        Intent intent = new Intent(
                RecruiterOtpActivity.this,
                RecruiterNewPasswordActivity.class
        );

        intent.putExtra(
                "email",
                email
        );

        startActivity(intent);

        finish();
    }


    // =====================================================
    // ACTIVITY DESTROY
    // =====================================================

    @Override
    protected void onDestroy() {

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        super.onDestroy();
    }
}