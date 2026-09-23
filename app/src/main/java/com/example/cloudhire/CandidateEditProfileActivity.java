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

import com.example.cloudhire.api.ApiService;
import com.example.cloudhire.api.RetrofitClient;
import com.example.cloudhire.model.UpdateProfileRequest;
import com.example.cloudhire.model.UserProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidateEditProfileActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnSaveChanges;

    private EditText etFullName;
    private EditText etJobTitle;
    private EditText etPhone;
    private EditText etLocation;
    private EditText etAbout;

    private ApiService apiService;

    private String currentSkills = "";
    private String currentTotalExperience = "";
    private String currentProfileImageUrl = "";

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

        apiService = RetrofitClient.getApiService(this);

        btnBack.setOnClickListener(v -> finish());

        etPhone.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(
                    CharSequence s,
                    int start,
                    int count,
                    int after
            ) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s,
                    int start,
                    int before,
                    int count
            ) {
                if (etPhone.getError() != null) {
                    etPhone.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        loadProfile();

        btnSaveChanges.setOnClickListener(v -> {

            if (!validateInputs()) {
                return;
            }

            updateProfile();
        });
    }

    private void loadProfile() {

        btnSaveChanges.setEnabled(false);

        apiService.getProfile().enqueue(new Callback<UserProfileResponse>() {

            @Override
            public void onResponse(
                    Call<UserProfileResponse> call,
                    Response<UserProfileResponse> response
            ) {

                btnSaveChanges.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {

                    UserProfileResponse profile = response.body();

                    etFullName.setText(safe(profile.getName()));
                    etJobTitle.setText(safe(profile.getJobTitle()));
                    etPhone.setText(safe(profile.getPhone()));
                    etLocation.setText(safe(profile.getLocation()));
                    etAbout.setText(safe(profile.getAbout()));

                    currentSkills = safe(profile.getSkills());
                    currentTotalExperience = safe(profile.getTotalExperience());
                    currentProfileImageUrl = safe(profile.getProfileImageUrl());

                } else {

                    Toast.makeText(
                            CandidateEditProfileActivity.this,
                            "Could not load profile",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<UserProfileResponse> call,
                    Throwable t
            ) {

                btnSaveChanges.setEnabled(true);

                Toast.makeText(
                        CandidateEditProfileActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void updateProfile() {

        btnSaveChanges.setEnabled(false);

        String name = etFullName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String jobTitle = etJobTitle.getText().toString().trim();
        String about = etAbout.getText().toString().trim();

        String cleanedPhone = phone.replaceAll("[^0-9]", "");

        UpdateProfileRequest request = new UpdateProfileRequest(
                name,
                cleanedPhone,
                location,
                jobTitle,
                about,
                currentSkills,
                currentTotalExperience,
                currentProfileImageUrl
        );

        apiService.updateProfile(request).enqueue(
                new Callback<UserProfileResponse>() {

                    @Override
                    public void onResponse(
                            Call<UserProfileResponse> call,
                            Response<UserProfileResponse> response
                    ) {

                        btnSaveChanges.setEnabled(true);

                        if (response.isSuccessful() && response.body() != null) {

                            Toast.makeText(
                                    CandidateEditProfileActivity.this,
                                    "Profile updated successfully",
                                    Toast.LENGTH_SHORT
                            ).show();

                            finish();

                        } else {

                            Toast.makeText(
                                    CandidateEditProfileActivity.this,
                                    "Could not update profile",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<UserProfileResponse> call,
                            Throwable t
                    ) {

                        btnSaveChanges.setEnabled(true);

                        Toast.makeText(
                                CandidateEditProfileActivity.this,
                                "Network error: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
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

            etPhone.setError(
                    "Please enter a valid 10-digit mobile number starting with 6-9"
            );

            etPhone.requestFocus();

            return false;
        }

        return true;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}