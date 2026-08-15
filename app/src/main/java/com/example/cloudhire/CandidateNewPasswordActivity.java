package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class CandidateNewPasswordActivity
        extends AppCompatActivity {

    private ImageButton btnBack;

    private Button btnResetPassword;

    private TextInputEditText etNewPassword;
    private TextInputEditText etConfirmPassword;

    private String email;


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


        // ==============================
        // GET EMAIL
        // ==============================

        email = getIntent()
                .getStringExtra("email");


        // ==============================
        // BACK
        // ==============================

        btnBack.setOnClickListener(v -> {

            finish();

        });


        // ==============================
        // RESET PASSWORD
        // ==============================

        btnResetPassword.setOnClickListener(v -> {

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
            // MATCH
            // ==============================

            if (!password.equals(confirmPassword)) {

                etConfirmPassword.setError(
                        "Passwords do not match"
                );

                etConfirmPassword.requestFocus();

                return;
            }


            // ==============================
            // SUCCESS SCREEN
            // ==============================

            Intent intent = new Intent(
                    CandidateNewPasswordActivity.this,
                    CandidatePasswordResetSuccessActivity.class
            );

            intent.putExtra(
                    "email",
                    email
            );

            startActivity(intent);

            finish();

        });

    }
}