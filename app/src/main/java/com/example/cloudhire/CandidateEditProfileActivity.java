package com.example.cloudhire;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

        btnBack.setOnClickListener(v -> finish());

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPhone.getError() != null) {
                    etPhone.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnSaveChanges.setOnClickListener(v -> {
            if (validateInputs()) {
                Toast.makeText(CandidateEditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private boolean validateInputs() {
        String phone = etPhone.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Phone number is required");
            etPhone.requestFocus();
            return false;
        }

        String cleanedPhone = phone.replaceAll("[^0-9]", "");

        if (cleanedPhone.length() != 10) {
            etPhone.setError("Phone number must be exactly 10 digits");
            etPhone.requestFocus();
            return false;
        }

        if (!cleanedPhone.matches("^[6-9]\\d{9}$")) {
            etPhone.setError("Please enter a valid 10-digit mobile number starting with 6-9");
            etPhone.requestFocus();
            return false;
        }

        return true;
    }
}
