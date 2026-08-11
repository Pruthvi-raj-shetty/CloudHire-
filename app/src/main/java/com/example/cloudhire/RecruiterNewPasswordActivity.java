package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class RecruiterNewPasswordActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnResetPassword;

    private TextInputEditText etNewPassword;
    private TextInputEditText etConfirmPassword;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recruiter_new_password);

        btnBack = findViewById(R.id.btnBack);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        email = getIntent().getStringExtra("email");

        btnBack.setOnClickListener(v -> finish());

        btnResetPassword.setOnClickListener(v -> {

            String password = etNewPassword.getText()
                    .toString();

            String confirmPassword = etConfirmPassword.getText()
                    .toString();

            if (password.isEmpty()) {

                etNewPassword.setError(
                        "Enter new password"
                );

                return;
            }

            if (password.length() < 8) {

                etNewPassword.setError(
                        "Password must contain at least 8 characters"
                );

                return;
            }

            if (!password.equals(confirmPassword)) {

                etConfirmPassword.setError(
                        "Passwords do not match"
                );

                return;
            }

            Intent intent = new Intent(
                    RecruiterNewPasswordActivity.this,
                    PasswordResetSuccessActivity.class
            );

            intent.putExtra("email", email);

            startActivity(intent);

            finish();
        });
    }
}