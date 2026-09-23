package com.example.cloudhire;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudhire.api.ApiService;
import com.example.cloudhire.api.RetrofitClient;
import com.example.cloudhire.model.ApplicationResponse;
import com.example.cloudhire.model.ResumeResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyJobActivity extends AppCompatActivity {

    private static final long MAX_FILE_SIZE =
            5L * 1024 * 1024; // 5 MB

    // =========================================================
    // VIEWS
    // =========================================================

    private TextView txtJobTitle;
    private TextView txtCompanyName;
    private TextView txtLocation;
    private TextView txtResumeName;

    private EditText etFullName;
    private EditText etEmail;
    private EditText etCoverLetter;

    private LinearLayout layoutUploadResume;

    private Button btnSubmitApplication;
    private Button btnBack;

    // =========================================================
    // JOB DATA
    // =========================================================

    private Long jobId;

    // =========================================================
    // SELECTED RESUME
    // =========================================================

    private Uri selectedResumeUri;
    private String selectedResumeFileName;

    // =========================================================
    // RESUME PICKER
    // =========================================================

    private final ActivityResultLauncher<Intent> resumePicker =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                        if (result.getResultCode() != RESULT_OK
                                || result.getData() == null) {
                            return;
                        }

                        Uri uri = result.getData().getData();

                        if (uri == null) {
                            return;
                        }

                        handleSelectedResume(uri);
                    }
            );

    // =========================================================
    // ON CREATE
    // =========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_apply_job);

        // =====================================================
        // INITIALIZE VIEWS
        // =====================================================

        txtJobTitle = findViewById(R.id.txtJobTitle);

        txtCompanyName = findViewById(R.id.txtCompanyName);

        txtLocation = findViewById(R.id.txtLocation);

        txtResumeName = findViewById(R.id.txtResumeName);

        etFullName = findViewById(R.id.etFullName);

        etEmail = findViewById(R.id.etEmail);

        etCoverLetter = findViewById(R.id.etCoverLetter);

        layoutUploadResume = findViewById(R.id.layoutUploadResume);

        btnSubmitApplication = findViewById(R.id.btnSubmitApplication);

        btnBack = findViewById(R.id.btnBack);

        // =====================================================
        // GET JOB DATA
        // =====================================================

        jobId = getIntent().getLongExtra(
                "job_id",
                -1
        );

        String jobTitle = getIntent().getStringExtra(
                "job_title"
        );

        String companyName = getIntent().getStringExtra(
                "company_name"
        );

        String location = getIntent().getStringExtra(
                "location"
        );

        // =====================================================
        // VALIDATE JOB
        // =====================================================

        if (jobId == null || jobId == -1) {

            Toast.makeText(
                    this,
                    "Invalid job",
                    Toast.LENGTH_LONG
            ).show();

            finish();
            return;
        }

        // =====================================================
        // DISPLAY JOB
        // =====================================================

        if (jobTitle != null && !jobTitle.isEmpty()) {
            txtJobTitle.setText(jobTitle);
        }

        if (companyName != null && !companyName.isEmpty()) {
            txtCompanyName.setText(companyName);
        }

        if (location != null && !location.isEmpty()) {
            txtLocation.setText(location);
        }

        // =====================================================
        // LOAD LOGGED-IN USER
        // =====================================================

        SessionManager sessionManager =
                new SessionManager(this);

        String name = sessionManager.getName();

        String email = sessionManager.getEmail();

        if (name != null && !name.isEmpty()) {
            etFullName.setText(name);
        }

        if (email != null && !email.isEmpty()) {
            etEmail.setText(email);
        }

        // =====================================================
        // BACK
        // =====================================================

        btnBack.setOnClickListener(
                v -> finish()
        );

        // =====================================================
        // SELECT RESUME
        // =====================================================

        layoutUploadResume.setOnClickListener(
                v -> openResumePicker()
        );

        // =====================================================
        // SUBMIT APPLICATION
        // =====================================================

        btnSubmitApplication.setOnClickListener(
                v -> validateAndSubmit()
        );
    }

    // =========================================================
    // OPEN FILE PICKER
    // =========================================================

    private void openResumePicker() {

        Intent intent =
                new Intent(Intent.ACTION_OPEN_DOCUMENT);

        intent.addCategory(
                Intent.CATEGORY_OPENABLE
        );

        intent.setType(
                "application/pdf"
        );

        intent.putExtra(
                Intent.EXTRA_MIME_TYPES,
                new String[]{
                        "application/pdf"
                }
        );

        resumePicker.launch(intent);
    }

    // =========================================================
    // HANDLE SELECTED RESUME
    // =========================================================

    private void handleSelectedResume(Uri uri) {

        String fileName = getFileName(uri);

        if (fileName == null || fileName.isEmpty()) {

            Toast.makeText(
                    this,
                    "Unable to read selected file",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // =====================================================
        // CHECK PDF EXTENSION
        // =====================================================

        if (!fileName
                .toLowerCase(Locale.ROOT)
                .endsWith(".pdf")) {

            Toast.makeText(
                    this,
                    "Only PDF resumes are allowed",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        // =====================================================
        // CHECK MIME TYPE
        // =====================================================

        String mimeType =
                getContentResolver()
                        .getType(uri);

        if (mimeType != null
                && !mimeType.equalsIgnoreCase(
                "application/pdf"
        )) {

            Toast.makeText(
                    this,
                    "Only PDF resumes are allowed",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        // =====================================================
        // CHECK FILE SIZE
        // =====================================================

        long fileSize = getFileSize(uri);

        if (fileSize > MAX_FILE_SIZE) {

            Toast.makeText(
                    this,
                    "Resume must be smaller than 5 MB",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        // =====================================================
        // SAVE SELECTED FILE
        // =====================================================

        selectedResumeUri = uri;

        selectedResumeFileName = fileName;

        // =====================================================
        // UPDATE UI
        // =====================================================

        txtResumeName.setText(
                "📄 " + fileName
        );

        Toast.makeText(
                this,
                "Resume selected",
                Toast.LENGTH_SHORT
        ).show();
    }

    // =========================================================
    // VALIDATE FORM
    // =========================================================

    private void validateAndSubmit() {

        String fullName =
                etFullName.getText()
                        .toString()
                        .trim();

        String email =
                etEmail.getText()
                        .toString()
                        .trim();

        if (fullName.isEmpty()
                || email.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please fill in all required fields",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // Resume is required
        if (selectedResumeUri == null) {

            Toast.makeText(
                    this,
                    "Please select your resume",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        // Start submission
        uploadResumeThenApply();
    }

    // =========================================================
    // UPLOAD RESUME THEN APPLY
    // =========================================================

    private void uploadResumeThenApply() {

        btnSubmitApplication.setEnabled(false);

        btnSubmitApplication.setText(
                "Uploading Resume..."
        );

        try {

            InputStream inputStream =
                    getContentResolver()
                            .openInputStream(
                                    selectedResumeUri
                            );

            if (inputStream == null) {

                resetSubmitButton();

                Toast.makeText(
                        this,
                        "Unable to read selected resume",
                        Toast.LENGTH_LONG
                ).show();

                return;
            }

            byte[] fileBytes =
                    readInputStream(inputStream);

            inputStream.close();

            // =================================================
            // FINAL SIZE CHECK
            // =================================================

            if (fileBytes.length > MAX_FILE_SIZE) {

                resetSubmitButton();

                Toast.makeText(
                        this,
                        "Resume must be smaller than 5 MB",
                        Toast.LENGTH_LONG
                ).show();

                return;
            }

            // =================================================
            // CREATE REQUEST BODY
            // =================================================

            RequestBody requestBody =
                    RequestBody.create(
                            MediaType.parse(
                                    "application/pdf"
                            ),
                            fileBytes
                    );

            MultipartBody.Part filePart =
                    MultipartBody.Part.createFormData(
                            "file",
                            selectedResumeFileName,
                            requestBody
                    );

            // =================================================
            // UPLOAD TO BACKEND
            // =================================================

            ApiService apiService =
                    RetrofitClient.getApiService(
                            this
                    );

            apiService.uploadResume(
                    filePart
            ).enqueue(
                    new Callback<ResumeResponse>() {

                        @Override
                        public void onResponse(
                                Call<ResumeResponse> call,
                                Response<ResumeResponse> response
                        ) {

                            if (response.isSuccessful()
                                    && response.body() != null) {

                                // Resume has reached
                                // Spring Boot and MinIO.

                                submitApplication();

                            } else {

                                resetSubmitButton();

                                String message =
                                        "Resume upload failed";

                                if (response.errorBody()
                                        != null) {

                                    try {

                                        String error =
                                                response.errorBody()
                                                        .string();

                                        if (error != null
                                                && !error.isEmpty()) {

                                            message = error;
                                        }

                                    } catch (Exception ignored) {
                                    }
                                }

                                Toast.makeText(
                                        ApplyJobActivity.this,
                                        message,
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }

                        @Override
                        public void onFailure(
                                Call<ResumeResponse> call,
                                Throwable t
                        ) {

                            resetSubmitButton();

                            Toast.makeText(
                                    ApplyJobActivity.this,
                                    "Unable to upload resume",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            );

        } catch (Exception e) {

            resetSubmitButton();

            Toast.makeText(
                    this,
                    "Unable to read resume",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    // =========================================================
    // SUBMIT APPLICATION
    // =========================================================

    private void submitApplication() {

        btnSubmitApplication.setText(
                "Submitting Application..."
        );

        ApiService apiService =
                RetrofitClient.getApiService(
                        this
                );

        apiService.applyToJob(jobId)
                .enqueue(
                        new Callback<ApplicationResponse>() {

                            @Override
                            public void onResponse(
                                    Call<ApplicationResponse> call,
                                    Response<ApplicationResponse> response
                            ) {

                                resetSubmitButton();

                                if (response.isSuccessful()
                                        && response.body() != null) {

                                    Toast.makeText(
                                            ApplyJobActivity.this,
                                            "Application submitted successfully",
                                            Toast.LENGTH_LONG
                                    ).show();

                                    finish();

                                    return;
                                }

                                // =================================================
                                // DUPLICATE APPLICATION
                                // =================================================

                                if (response.code() == 400
                                        || response.code() == 409) {

                                    Toast.makeText(
                                            ApplyJobActivity.this,
                                            "You have already applied for this job",
                                            Toast.LENGTH_LONG
                                    ).show();

                                    return;
                                }

                                Toast.makeText(
                                        ApplyJobActivity.this,
                                        "Unable to submit application",
                                        Toast.LENGTH_LONG
                                ).show();
                            }

                            @Override
                            public void onFailure(
                                    Call<ApplicationResponse> call,
                                    Throwable t
                            ) {

                                resetSubmitButton();

                                Toast.makeText(
                                        ApplyJobActivity.this,
                                        "Unable to connect to server",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );
    }

    // =========================================================
    // READ FILE
    // =========================================================

    private byte[] readInputStream(
            InputStream inputStream
    ) throws IOException {

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        byte[] buffer =
                new byte[8192];

        int bytesRead;

        while ((bytesRead =
                inputStream.read(buffer)) != -1) {

            outputStream.write(
                    buffer,
                    0,
                    bytesRead
            );

            // Prevent unnecessarily reading
            // very large files into memory.
            if (outputStream.size()
                    > MAX_FILE_SIZE) {

                break;
            }
        }

        return outputStream.toByteArray();
    }

    // =========================================================
    // GET FILE NAME
    // =========================================================

    private String getFileName(Uri uri) {

        String result = null;

        Cursor cursor =
                getContentResolver()
                        .query(
                                uri,
                                null,
                                null,
                                null,
                                null
                        );

        if (cursor != null) {

            try {

                int nameIndex =
                        cursor.getColumnIndex(
                                OpenableColumns.DISPLAY_NAME
                        );

                if (nameIndex >= 0
                        && cursor.moveToFirst()) {

                    result =
                            cursor.getString(
                                    nameIndex
                            );
                }

            } finally {

                cursor.close();
            }
        }

        if (result == null) {

            result =
                    uri.getLastPathSegment();
        }

        return result;
    }

    // =========================================================
    // GET FILE SIZE
    // =========================================================

    private long getFileSize(Uri uri) {

        Cursor cursor =
                getContentResolver()
                        .query(
                                uri,
                                null,
                                null,
                                null,
                                null
                        );

        if (cursor != null) {

            try {

                int sizeIndex =
                        cursor.getColumnIndex(
                                OpenableColumns.SIZE
                        );

                if (sizeIndex >= 0
                        && cursor.moveToFirst()
                        && !cursor.isNull(sizeIndex)) {

                    return cursor.getLong(
                            sizeIndex
                    );
                }

            } finally {

                cursor.close();
            }
        }

        return 0;
    }

    // =========================================================
    // RESET SUBMIT BUTTON
    // =========================================================

    private void resetSubmitButton() {

        btnSubmitApplication.setEnabled(true);

        btnSubmitApplication.setText(
                "Submit Application"
        );
    }
}