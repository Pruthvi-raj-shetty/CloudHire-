package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudhire.api.ApiService;
import com.example.cloudhire.api.RetrofitClient;
import com.example.cloudhire.model.ResetPasswordRequest;
import com.google.android.material.textfield.TextInputEditText;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidateNewPasswordActivity
        extends AppCompatActivity {

    private ImageButton btnBack;

    private Button btnResetPassword;

    private TextInputEditText etNewPassword;
    private TextInputEditText etConfirmPassword;

    private String email;
    private String otp;

    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_candidate_new_password
        );


        // ==============================
        // INITIALIZE
        // ==============================

        btnBack =
                findViewById(R.id.btnBack);

        btnResetPassword =
                findViewById(R.id.btnResetPassword);

        etNewPassword =
                findViewById(R.id.etNewPassword);

        etConfirmPassword =
                findViewById(R.id.etConfirmPassword);

        apiService =
                RetrofitClient.getApiService(this);


        // ==============================
        // GET EMAIL + OTP
        // ==============================

        email = getIntent()
                .getStringExtra("email");

        otp = getIntent()
                .getStringExtra("otp");


        // ==============================
        // BACK
        // ==============================

        btnBack.setOnClickListener(v -> finish());


        // ==============================
        // RESET PASSWORD
        // ==============================

        btnResetPassword.setOnClickListener(
                v -> resetPassword()
        );
    }


    private void resetPassword() {

        String password =
                etNewPassword.getText()
                        .toString();

        String confirmPassword =
                etConfirmPassword.getText()
                        .toString();


        // ==============================
        // EMPTY PASSWORD
        // ==============================

        if (password.isEmpty()) {

            etNewPassword.setError(
                    "Enter new password"
            );

            etNewPassword.requestFocus();

            return;
        }


        // ==============================
        // PASSWORD LENGTH
        // ==============================

        if (password.length() < 8) {

            etNewPassword.setError(
                    "Password must contain at least 8 characters"
            );

            etNewPassword.requestFocus();

            return;
        }


        // ==============================
        // CONFIRM PASSWORD
        // ==============================

        if (confirmPassword.isEmpty()) {

            etConfirmPassword.setError(
                    "Confirm your password"
            );

            etConfirmPassword.requestFocus();

            return;
        }


        // ==============================
        // PASSWORD MATCH
        // ==============================

        if (!password.equals(confirmPassword)) {

            etConfirmPassword.setError(
                    "Passwords do not match"
            );

            etConfirmPassword.requestFocus();

            return;
        }


        // ==============================
        // CHECK OTP DATA
        // ==============================

        if (email == null ||
                email.isEmpty() ||
                otp == null ||
                otp.isEmpty()) {

            Toast.makeText(
                    CandidateNewPasswordActivity.this,
                    "Password reset session expired. Please try again.",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }


        btnResetPassword.setEnabled(false);


        // ==============================
        // API REQUEST
        // ==============================

        ResetPasswordRequest request =
                new ResetPasswordRequest(
                        email,
                        otp,
                        password
                );


        apiService.resetPassword(request)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(
                            Call<ResponseBody> call,
                            Response<ResponseBody> response
                    ) {

                        btnResetPassword.setEnabled(true);


                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    CandidateNewPasswordActivity.this,
                                    "Password reset successfully",
                                    Toast.LENGTH_SHORT
                            ).show();


                            Intent intent =
                                    new Intent(
                                            CandidateNewPasswordActivity.this,
                                            CandidatePasswordResetSuccessActivity.class
                                    );

                            intent.putExtra(
                                    "email",
                                    email
                            );

                            startActivity(intent);

                            finish();

                        } else {

                            String message =
                                    "Could not reset password";

                            try {

                                if (response.errorBody() != null) {

                                    message =
                                            response.errorBody()
                                                    .string();
                                }

                            } catch (Exception ignored) {
                            }


                            Toast.makeText(
                                    CandidateNewPasswordActivity.this,
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

                        btnResetPassword.setEnabled(true);

                        Toast.makeText(
                                CandidateNewPasswordActivity.this,
                                "Unable to connect to server",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}