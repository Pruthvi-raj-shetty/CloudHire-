package com.example.cloudhire;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class RecruiterNotificationsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_notifications);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}
