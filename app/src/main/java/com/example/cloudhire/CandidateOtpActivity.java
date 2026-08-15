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

public class CandidateOtpActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnVerifyOtp;

    private TextInputEditText etOtp;

    private TextView txtResendOtp;
    private TextView txtOtpTimer;

    private String email;

    private CountDownTimer countDownTimer;

    private boolean otpExpired = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_candidate_otp
        );


        // ==============================
        // INITIALIZE
        // ==============================

        btnBack = findViewById(R.id.btnBack);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);

        etOtp = findViewById(R.id.etOtp);

        txtResendOtp =
                findViewById(R.id.txtResendOtp);

        txtOtpTimer =
                findViewById(R.id.txtOtpTimer);


        // ==============================
        // GET EMAIL
        // ==============================

        email = getIntent()
                .getStringExtra("email");


        // ==============================
        // START TIMER
        // ==============================

        startOtpTimer();


        // ==============================
        // BACK
        // ==============================

        btnBack.setOnClickListener(v -> {

            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            finish();

        });


        // ==============================
        // RESEND OTP
        // ==============================

        txtResendOtp.setOnClickListener(v -> {

            if (!otpExpired) {
                return;
            }

            etOtp.setText("");

            etOtp.setError(null);

            startOtpTimer();

            Toast.makeText(
                    CandidateOtpActivity.this,
                    "OTP resent successfully",
                    Toast.LENGTH_SHORT
            ).show();

        });


        // ==============================
        // VERIFY
        // ==============================

        btnVerifyOtp.setOnClickListener(
                v -> verifyOtp()
        );

    }


    // ==================================================
    // OTP TIMER
    // ==================================================

    private void startOtpTimer() {

        otpExpired = false;

        txtResendOtp.setClickable(false);
        txtResendOtp.setFocusable(false);

        txtResendOtp.setText("Resend OTP");

        txtResendOtp.setTextColor(
                getColor(android.R.color.darker_gray)
        );


        if (countDownTimer != null) {
            countDownTimer.cancel();
        }


        countDownTimer = new CountDownTimer(
                30000,
                1000
        ) {

            @Override
            public void onTick(
                    long millisUntilFinished) {

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

                otpExpired = true;

                txtOtpTimer.setText(
                        "OTP has expired"
                );

                txtOtpTimer.setTextColor(
                        getColor(
                                android.R.color.holo_red_dark
                        )
                );


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


    // ==================================================
    // VERIFY OTP
    // ==================================================

    private void verifyOtp() {

        String otp = etOtp.getText()
                .toString()
                .trim();


        if (otp.isEmpty()) {

            etOtp.setError(
                    "Please enter OTP"
            );

            etOtp.requestFocus();

            return;
        }


        if (otp.length() != 6) {

            etOtp.setError(
                    "Enter 6-digit OTP"
            );

            etOtp.requestFocus();

            return;
        }


        if (otpExpired) {

            etOtp.setError(
                    "OTP expired. Please resend OTP."
            );

            Toast.makeText(
                    CandidateOtpActivity.this,
                    "OTP expired. Please resend OTP.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // ==============================
        // OTP VERIFIED
        // ==============================

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }


        Toast.makeText(
                CandidateOtpActivity.this,
                "OTP verified successfully",
                Toast.LENGTH_SHORT
        ).show();


        // ==============================
        // NEW PASSWORD
        // ==============================

        Intent intent = new Intent(
                CandidateOtpActivity.this,
                CandidateNewPasswordActivity.class
        );

        intent.putExtra(
                "email",
                email
        );

        startActivity(intent);

        finish();

    }


    @Override
    protected void onDestroy() {

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        super.onDestroy();

    }

}