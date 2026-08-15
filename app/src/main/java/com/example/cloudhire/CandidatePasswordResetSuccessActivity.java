package com.example.cloudhire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CandidatePasswordResetSuccessActivity
        extends AppCompatActivity {

    private Button btnBackToLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_candidate_password_reset_success
        );


        btnBackToLogin =
                findViewById(R.id.btnBackToLogin);


        btnBackToLogin.setOnClickListener(v -> {

            Intent intent = new Intent(
                    CandidatePasswordResetSuccessActivity.this,
                    CandidateLoginActivity.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
            );

            startActivity(intent);

            finish();

        });

    }
}