package com.example.cloudhire;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CandidateNotificationsActivity extends AppCompatActivity {

    private LinearLayout notificationsContainer;
    private LinearLayout emptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_notifications);

        TextView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        notificationsContainer = findViewById(R.id.notificationsContainer);
        emptyState = findViewById(R.id.emptyState);

        TextView btnClearAll = findViewById(R.id.btnClearAll);
        if (btnClearAll != null) {
            btnClearAll.setOnClickListener(v -> {
                if (notificationsContainer != null && emptyState != null) {
                    notificationsContainer.removeAllViews();
                    notificationsContainer.setVisibility(View.GONE);
                    emptyState.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Notifications cleared", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
