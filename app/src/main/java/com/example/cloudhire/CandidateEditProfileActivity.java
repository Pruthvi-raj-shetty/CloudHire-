package com.example.cloudhire;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CandidateEditProfileActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnSaveChanges;
    private EditText etFullName, etJobTitle, etPhone, etLocation, etAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_edit_profile);

        btnBack = findViewById(R.id.btnBack);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        etFullName = findViewById(R.id.etFullName);
        etJobTitle = findViewById(R.id.etJobTitle);
        etPhone = findViewById(R.id.etPhone);
        etLocation = findViewById(R.id.etLocation);
        etAbout = findViewById(R.id.etAbout);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here we would typically save the data to a database
                Toast.makeText(CandidateEditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
