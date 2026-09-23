package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudhire.api.ApiService;
import com.example.cloudhire.api.RetrofitClient;
import com.example.cloudhire.model.ForgotPasswordRequest;
import com.example.cloudhire.model.VerifyOtpRequest;
import com.google.android.material.textfield.TextInputEditText;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidateOtpActivity
        extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnVerifyOtp;

    private TextInputEditText etOtp;

    private TextView txtResendOtp;
    private TextView txtOtpTimer;

    private String email;
    private String verifiedOtp;

    private CountDownTimer countDownTimer;

    private boolean otpExpired = false;

    private ApiService apiService;


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

        apiService =
                RetrofitClient.getApiService(this);


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

            resendOtp();
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


        // Backend OTP expires after 5 minutes
        countDownTimer = new CountDownTimer(
                5 * 60 * 1000L,
                1000
        ) {

            @Override
            public void onTick(
                    long millisUntilFinished
            ) {

                long totalSeconds =
                        millisUntilFinished / 1000;

                long minutes =
                        totalSeconds / 60;

                long seconds =
                        totalSeconds % 60;

                txtOtpTimer.setText(
                        String.format(
                                "OTP expires in %02d:%02d",
                                minutes,
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

            Toast.makeText(
                    CandidateOtpActivity.this,
                    "OTP expired. Please resend OTP.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        btnVerifyOtp.setEnabled(false);


        // ==============================
        // API REQUEST
        // ==============================

        VerifyOtpRequest request =
                new VerifyOtpRequest(
                        email,
                        otp
                );


        apiService.verifyOtp(request)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(
                            Call<ResponseBody> call,
                            Response<ResponseBody> response
                    ) {

                        btnVerifyOtp.setEnabled(true);


                        if (response.isSuccessful()) {

                            verifiedOtp = otp;


                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                            }


                            Toast.makeText(
                                    CandidateOtpActivity.this,
                                    "OTP verified successfully",
                                    Toast.LENGTH_SHORT
                            ).show();


                            Intent intent =
                                    new Intent(
                                            CandidateOtpActivity.this,
                                            CandidateNewPasswordActivity.class
                                    );

                            intent.putExtra(
                                    "email",
                                    email
                            );

                            // IMPORTANT:
                            // Pass verified OTP to reset screen
                            intent.putExtra(
                                    "otp",
                                    verifiedOtp
                            );

                            startActivity(intent);

                            finish();

                        } else {

                            String message =
                                    "Invalid OTP";

                            try {

                                if (response.errorBody() != null) {

                                    message =
                                            response.errorBody()
                                                    .string();
                                }

                            } catch (Exception ignored) {
                            }


                            Toast.makeText(
                                    CandidateOtpActivity.this,
                                    message,
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }


                    @Override
                    public void onFailure(
                            Call<ResponseBody> call,
                            Throwable t
                    ) {

                        btnVerifyOtp.setEnabled(true);

                        Toast.makeText(
                                CandidateOtpActivity.this,
                                "Unable to connect to server",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }


    // ==================================================
    // RESEND OTP
    // ==================================================

    private void resendOtp() {

        txtResendOtp.setClickable(false);


        ForgotPasswordRequest request =
                new ForgotPasswordRequest(email);


        apiService.forgotPassword(request)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(
                            Call<ResponseBody> call,
                            Response<ResponseBody> response
                    ) {

                        if (response.isSuccessful()) {

                            etOtp.setText("");
                            etOtp.setError(null);

                            startOtpTimer();

                            Toast.makeText(
                                    CandidateOtpActivity.this,
                                    "New OTP sent successfully",
                                    Toast.LENGTH_SHORT
                            ).show();

                        } else {

                            txtResendOtp.setClickable(true);

                            Toast.makeText(
                                    CandidateOtpActivity.this,
                                    "Could not resend OTP",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }


                    @Override
                    public void onFailure(
                            Call<ResponseBody> call,
                            Throwable t
                    ) {

                        txtResendOtp.setClickable(true);

                        Toast.makeText(
                                CandidateOtpActivity.this,
                                "Unable to connect to server",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }


    @Override
    protected void onDestroy() {

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        super.onDestroy();
    }
}