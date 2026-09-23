package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudhire.api.ApiService;
import com.example.cloudhire.api.RetrofitClient;
import com.example.cloudhire.model.ForgotPasswordRequest;
import com.google.android.material.textfield.TextInputEditText;

import android.util.Patterns;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidateForgotPasswordActivity
        extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnSendOtp;
    private TextView txtBackLogin;

    private TextInputEditText etEmail;

    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_candidate_forgot_password
        );

        // ==============================
        // INITIALIZE
        // ==============================

        btnBack = findViewById(R.id.btnBack);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        txtBackLogin = findViewById(R.id.txtBackLogin);

        etEmail = findViewById(R.id.etEmail);

        apiService = RetrofitClient.getApiService(this);


        // ==============================
        // BACK
        // ==============================

        btnBack.setOnClickListener(v -> finish());


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

        btnSendOtp.setOnClickListener(v -> sendOtp());

    }


    private void sendOtp() {

        String email = etEmail.getText()
                .toString()
                .trim();


        // ==============================
        // VALIDATION
        // ==============================

        if (email.isEmpty()) {

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
                    "Enter a valid email"
            );

            etEmail.requestFocus();

            return;
        }


        // ==============================
        // DISABLE BUTTON
        // ==============================

        btnSendOtp.setEnabled(false);


        // ==============================
        // API CALL
        // ==============================

        ForgotPasswordRequest request =
                new ForgotPasswordRequest(email);

        apiService.forgotPassword(request)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(
                            Call<ResponseBody> call,
                            Response<ResponseBody> response
                    ) {

                        btnSendOtp.setEnabled(true);


                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    CandidateForgotPasswordActivity.this,
                                    "OTP sent to your email",
                                    Toast.LENGTH_SHORT
                            ).show();


                            Intent intent =
                                    new Intent(
                                            CandidateForgotPasswordActivity.this,
                                            CandidateOtpActivity.class
                                    );

                            intent.putExtra(
                                    "email",
                                    email
                            );

                            startActivity(intent);

                        } else {

                            String message =
                                    "Could not send OTP";

                            try {

                                if (response.errorBody() != null) {

                                    message =
                                            response.errorBody()
                                                    .string();
                                }

                            } catch (Exception ignored) {
                            }


                            Toast.makeText(
                                    CandidateForgotPasswordActivity.this,
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

                        btnSendOtp.setEnabled(true);

                        Toast.makeText(
                                CandidateForgotPasswordActivity.this,
                                "Unable to connect to server",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}