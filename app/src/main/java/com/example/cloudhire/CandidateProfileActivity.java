package com.example.cloudhire;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudhire.api.ApiService;
import com.example.cloudhire.api.RetrofitClient;
import com.example.cloudhire.model.UserProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidateProfileActivity extends AppCompatActivity {

    private static final int PICK_RESUME_REQUEST = 1001;

    private ImageButton btnBack;
    private Button btnEditProfile;
    private Button btnAddExperience;
    private Button btnAddEducation;
    private Button btnAddResume;
    private Button btnReplaceResume;
    private Button btnLogout;

    private TextView btnEditSkills;

    private TextView txtCandidateName;
    private TextView txtJobTitle;
    private TextView txtFullName;
    private TextView txtEmail;
    private TextView txtPhone;
    private TextView txtLocation;
    private TextView txtAbout;
    private TextView txtProfessionalTitle;
    private TextView txtSkills;
    private TextView txtExperience;

    private TextView txtResumeName;

    private LinearLayout workExperienceContainer;
    private LinearLayout educationContainer;
    private LinearLayout skillsChipContainer;

    private String currentSkillsText = "";

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile);

        // -----------------------------
        // Find Views
        // -----------------------------

        btnBack = findViewById(R.id.btnBack);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnAddExperience = findViewById(R.id.btnAddExperience);
        btnAddEducation = findViewById(R.id.btnAddEducation);
        btnAddResume = findViewById(R.id.btnAddResume);
        btnReplaceResume = findViewById(R.id.btnReplaceResume);
        btnLogout = findViewById(R.id.btnLogout);

        btnEditSkills = findViewById(R.id.btnEditSkills);

        txtCandidateName = findViewById(R.id.txtCandidateName);
        txtJobTitle = findViewById(R.id.txtJobTitle);
        txtFullName = findViewById(R.id.txtFullName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        txtLocation = findViewById(R.id.txtLocation);
        txtAbout = findViewById(R.id.txtAbout);
        txtProfessionalTitle = findViewById(R.id.txtProfessionalTitle);
        txtSkills = findViewById(R.id.txtSkills);
        txtExperience = findViewById(R.id.txtExperience);

        txtResumeName = findViewById(R.id.txtResumeName);

        workExperienceContainer =
                findViewById(R.id.workExperienceContainer);

        educationContainer =
                findViewById(R.id.educationContainer);

        skillsChipContainer =
                findViewById(R.id.skillsChipContainer);

        apiService = RetrofitClient.getApiService(this);

        // -----------------------------
        // Back Button
        // -----------------------------

        btnBack.setOnClickListener(v -> finish());

        // -----------------------------
        // Edit Profile
        // -----------------------------

        btnEditProfile.setOnClickListener(v -> {

            Intent intent = new Intent(
                    CandidateProfileActivity.this,
                    CandidateEditProfileActivity.class
            );

            startActivity(intent);
        });

        // -----------------------------
        // Add Experience
        // -----------------------------

        btnAddExperience.setOnClickListener(
                v -> showAddExperienceDialog()
        );

        // -----------------------------
        // Edit Skills
        // -----------------------------

        if (btnEditSkills != null) {

            btnEditSkills.setOnClickListener(
                    v -> showEditSkillsDialog()
            );
        }

        // -----------------------------
        // Add Education
        // -----------------------------

        btnAddEducation.setOnClickListener(
                v -> showAddEducationDialog()
        );

        // -----------------------------
        // Resume
        // -----------------------------

        btnAddResume.setOnClickListener(
                v -> openResumePicker()
        );

        btnReplaceResume.setOnClickListener(
                v -> openResumePicker()
        );

        // -----------------------------
        // Logout
        // -----------------------------

        btnLogout.setOnClickListener(v -> {

            SessionManager sessionManager =
                    new SessionManager(CandidateProfileActivity.this);

            sessionManager.logout();

            Intent intent = new Intent(
                    CandidateProfileActivity.this,
                    MainActivity.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);

            Toast.makeText(
                    CandidateProfileActivity.this,
                    "Logged out successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        });

        // -----------------------------
        // Load Profile
        // -----------------------------

        loadProfile();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh profile after returning
        // from Edit Profile.
        if (apiService != null) {
            loadProfile();
        }
    }

    // =========================================================
    // LOAD PROFILE FROM BACKEND
    // =========================================================

    private void loadProfile() {

        apiService.getProfile().enqueue(
                new Callback<UserProfileResponse>() {

                    @Override
                    public void onResponse(
                            Call<UserProfileResponse> call,
                            Response<UserProfileResponse> response
                    ) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            UserProfileResponse profile =
                                    response.body();

                            displayProfile(profile);

                        } else {

                            Toast.makeText(
                                    CandidateProfileActivity.this,
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

                        Toast.makeText(
                                CandidateProfileActivity.this,
                                "Network error: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }

    // =========================================================
    // DISPLAY PROFILE DATA
    // =========================================================

    private void displayProfile(UserProfileResponse profile) {

        String name = safe(profile.getName());
        String jobTitle = safe(profile.getJobTitle());
        String email = safe(profile.getEmail());
        String phone = safe(profile.getPhone());
        String location = safe(profile.getLocation());
        String about = safe(profile.getAbout());
        String skills = safe(profile.getSkills());
        String experience = safe(profile.getTotalExperience());

        // Header
        txtCandidateName.setText(
                isEmpty(name) ? "Your Name" : name
        );

        txtJobTitle.setText(
                isEmpty(jobTitle) ? "Add your job title" : jobTitle
        );

        // Personal Information
        txtFullName.setText(
                isEmpty(name) ? "Not provided" : name
        );

        txtEmail.setText(
                isEmpty(email) ? "Not provided" : email
        );

        txtPhone.setText(
                isEmpty(phone) ? "Not provided" : phone
        );

        txtLocation.setText(
                isEmpty(location) ? "Not provided" : location
        );

        // About
        txtAbout.setText(
                isEmpty(about)
                        ? "Tell recruiters about yourself."
                        : about
        );

        // Professional Information
        txtProfessionalTitle.setText(
                isEmpty(jobTitle)
                        ? "Not provided"
                        : jobTitle
        );

        txtSkills.setText(
                isEmpty(skills)
                        ? "No skills added"
                        : skills
        );

        txtExperience.setText(
                isEmpty(experience)
                        ? "Not provided"
                        : experience
        );

        // Skills
        currentSkillsText = skills;

        if (!isEmpty(skills)) {
            updateSkillsChips(skills);
        } else {
            skillsChipContainer.removeAllViews();
        }
    }

    // =========================================================
    // ADD EXPERIENCE DIALOG
    // =========================================================

    private void showAddExperienceDialog() {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle("Add Experience");

        LinearLayout layout =
                new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setPadding(
                50,
                30,
                50,
                10
        );

        final EditText etTitle =
                new EditText(this);

        etTitle.setHint(
                "Job Title (e.g. Full Stack Engineer)"
        );

        layout.addView(etTitle);

        final EditText etCompany =
                new EditText(this);

        etCompany.setHint(
                "Company Name (e.g. Google)"
        );

        layout.addView(etCompany);

        final EditText etDuration =
                new EditText(this);

        etDuration.setHint(
                "Duration (e.g. Jan 2022 - Present)"
        );

        layout.addView(etDuration);

        final EditText etDescription =
                new EditText(this);

        etDescription.setHint(
                "Description / Key Responsibilities"
        );

        layout.addView(etDescription);

        builder.setView(layout);

        builder.setPositiveButton(
                "Add",
                (dialog, which) -> {

                    String title =
                            etTitle.getText()
                                    .toString()
                                    .trim();

                    String company =
                            etCompany.getText()
                                    .toString()
                                    .trim();

                    String duration =
                            etDuration.getText()
                                    .toString()
                                    .trim();

                    String description =
                            etDescription.getText()
                                    .toString()
                                    .trim();

                    if (TextUtils.isEmpty(title)
                            || TextUtils.isEmpty(company)) {

                        Toast.makeText(
                                CandidateProfileActivity.this,
                                "Please enter Job Title and Company",
                                Toast.LENGTH_SHORT
                        ).show();

                        return;
                    }

                    addExperienceToLayout(
                            title,
                            company,
                            duration,
                            description
                    );

                    Toast.makeText(
                            CandidateProfileActivity.this,
                            "Experience added successfully",
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );

        builder.setNegativeButton(
                "Cancel",
                (dialog, which) -> dialog.dismiss()
        );

        builder.show();
    }

    private void addExperienceToLayout(
            String title,
            String company,
            String duration,
            String description
    ) {

        if (workExperienceContainer == null) {
            return;
        }

        LinearLayout entryLayout =
                new LinearLayout(this);

        entryLayout.setOrientation(
                LinearLayout.VERTICAL
        );

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(
                0,
                24,
                0,
                0
        );

        entryLayout.setLayoutParams(params);

        View divider = new View(this);

        LinearLayout.LayoutParams divParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        2
                );

        divParams.setMargins(
                0,
                0,
                0,
                16
        );

        divider.setLayoutParams(divParams);

        divider.setBackgroundColor(
                Color.parseColor("#E5E7EB")
        );

        entryLayout.addView(divider);

        TextView txtTitle =
                new TextView(this);

        txtTitle.setText(title);
        txtTitle.setTextSize(16);
        txtTitle.setTypeface(
                null,
                Typeface.BOLD
        );

        txtTitle.setTextColor(
                Color.parseColor("#111827")
        );

        entryLayout.addView(txtTitle);

        TextView txtComp =
                new TextView(this);

        txtComp.setText(company);
        txtComp.setTextSize(14);

        txtComp.setTextColor(
                Color.parseColor("#475569")
        );

        LinearLayout.LayoutParams compParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        compParams.setMargins(
                0,
                6,
                0,
                0
        );

        txtComp.setLayoutParams(compParams);

        entryLayout.addView(txtComp);

        if (!TextUtils.isEmpty(duration)) {

            TextView txtDur =
                    new TextView(this);

            txtDur.setText(duration);
            txtDur.setTextSize(12);

            txtDur.setTextColor(
                    Color.parseColor("#64748B")
            );

            LinearLayout.LayoutParams durParams =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            durParams.setMargins(
                    0,
                    4,
                    0,
                    0
            );

            txtDur.setLayoutParams(durParams);

            entryLayout.addView(txtDur);
        }

        if (!TextUtils.isEmpty(description)) {

            TextView txtDesc =
                    new TextView(this);

            txtDesc.setText(description);
            txtDesc.setTextSize(13);

            txtDesc.setTextColor(
                    Color.parseColor("#334155")
            );

            LinearLayout.LayoutParams descParams =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            descParams.setMargins(
                    0,
                    10,
                    0,
                    0
            );

            txtDesc.setLayoutParams(descParams);

            entryLayout.addView(txtDesc);
        }

        int count =
                workExperienceContainer.getChildCount();

        int insertIndex =
                count > 0 ? count - 1 : 0;

        workExperienceContainer.addView(
                entryLayout,
                insertIndex
        );
    }

    // =========================================================
    // EDIT SKILLS
    // =========================================================

    private void showEditSkillsDialog() {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle("Edit Skills");

        LinearLayout layout =
                new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setPadding(
                50,
                30,
                50,
                10
        );

        final EditText etSkills =
                new EditText(this);

        etSkills.setText(currentSkillsText);

        etSkills.setHint(
                "Enter skills separated by commas"
        );

        layout.addView(etSkills);

        builder.setView(layout);

        builder.setPositiveButton(
                "Save",
                (dialog, which) -> {

                    String newSkills =
                            etSkills.getText()
                                    .toString()
                                    .trim();

                    if (!TextUtils.isEmpty(newSkills)) {

                        currentSkillsText =
                                newSkills;

                        txtSkills.setText(
                                newSkills
                        );

                        updateSkillsChips(
                                newSkills
                        );

                        Toast.makeText(
                                CandidateProfileActivity.this,
                                "Skills updated on this screen",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );

        builder.setNegativeButton(
                "Cancel",
                (dialog, which) -> dialog.dismiss()
        );

        builder.show();
    }

    private void updateSkillsChips(
            String skillsStr
    ) {

        if (skillsChipContainer == null) {
            return;
        }

        skillsChipContainer.removeAllViews();

        String[] skills =
                skillsStr.split(",");

        LinearLayout row = null;

        int countInRow = 0;

        for (String skill : skills) {

            String trimmed =
                    skill.trim();

            if (trimmed.isEmpty()) {
                continue;
            }

            if (row == null
                    || countInRow >= 3) {

                row =
                        new LinearLayout(this);

                row.setOrientation(
                        LinearLayout.HORIZONTAL
                );

                LinearLayout.LayoutParams rowParams =
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );

                if (skillsChipContainer.getChildCount() > 0) {

                    rowParams.setMargins(
                            0,
                            16,
                            0,
                            0
                    );
                }

                row.setLayoutParams(rowParams);

                skillsChipContainer.addView(row);

                countInRow = 0;
            }

            TextView chip =
                    new TextView(this);

            chip.setText(trimmed);
            chip.setTextSize(12);

            chip.setTextColor(
                    Color.parseColor("#2563EB")
            );

            chip.setBackgroundColor(
                    Color.parseColor("#EFF6FF")
            );

            chip.setGravity(
                    Gravity.CENTER
            );

            int pHorizontal =
                    (int) (
                            14 *
                                    getResources()
                                            .getDisplayMetrics()
                                            .density
                    );

            int pVertical =
                    (int) (
                            8 *
                                    getResources()
                                            .getDisplayMetrics()
                                            .density
                    );

            chip.setPadding(
                    pHorizontal,
                    pVertical,
                    pHorizontal,
                    pVertical
            );

            LinearLayout.LayoutParams chipParams =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            chipParams.setMargins(
                    0,
                    0,
                    (int) (
                            8 *
                                    getResources()
                                            .getDisplayMetrics()
                                            .density
                    ),
                    0
            );

            chip.setLayoutParams(chipParams);

            row.addView(chip);

            countInRow++;
        }
    }

    // =========================================================
    // ADD EDUCATION
    // =========================================================

    private void showAddEducationDialog() {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle("Add Education");

        LinearLayout layout =
                new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setPadding(
                50,
                30,
                50,
                10
        );

        final EditText etDegree =
                new EditText(this);

        etDegree.setHint(
                "Degree / Qualification (e.g. BCA)"
        );

        layout.addView(etDegree);

        final EditText etCollege =
                new EditText(this);

        etCollege.setHint(
                "College / University Name"
        );

        layout.addView(etCollege);

        final EditText etYear =
                new EditText(this);

        etYear.setHint(
                "Year / Duration (e.g. 2020 - 2023)"
        );

        layout.addView(etYear);

        builder.setView(layout);

        builder.setPositiveButton(
                "Add",
                (dialog, which) -> {

                    String degree =
                            etDegree.getText()
                                    .toString()
                                    .trim();

                    String college =
                            etCollege.getText()
                                    .toString()
                                    .trim();

                    String year =
                            etYear.getText()
                                    .toString()
                                    .trim();

                    if (TextUtils.isEmpty(degree)
                            || TextUtils.isEmpty(college)) {

                        Toast.makeText(
                                CandidateProfileActivity.this,
                                "Please enter Degree and College",
                                Toast.LENGTH_SHORT
                        ).show();

                        return;
                    }

                    addEducationToLayout(
                            degree,
                            college,
                            year
                    );

                    Toast.makeText(
                            CandidateProfileActivity.this,
                            "Education added successfully",
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );

        builder.setNegativeButton(
                "Cancel",
                (dialog, which) -> dialog.dismiss()
        );

        builder.show();
    }

    private void addEducationToLayout(
            String degree,
            String college,
            String year
    ) {

        if (educationContainer == null) {
            return;
        }

        LinearLayout entryLayout =
                new LinearLayout(this);

        entryLayout.setOrientation(
                LinearLayout.VERTICAL
        );

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(
                0,
                24,
                0,
                0
        );

        entryLayout.setLayoutParams(params);

        View divider = new View(this);

        LinearLayout.LayoutParams divParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        2
                );

        divParams.setMargins(
                0,
                0,
                0,
                16
        );

        divider.setLayoutParams(divParams);

        divider.setBackgroundColor(
                Color.parseColor("#E5E7EB")
        );

        entryLayout.addView(divider);

        TextView txtDeg =
                new TextView(this);

        txtDeg.setText(degree);
        txtDeg.setTextSize(16);

        txtDeg.setTypeface(
                null,
                Typeface.BOLD
        );

        txtDeg.setTextColor(
                Color.parseColor("#111827")
        );

        entryLayout.addView(txtDeg);

        TextView txtCol =
                new TextView(this);

        txtCol.setText(companyOrCollege(college));
        txtCol.setTextSize(14);

        txtCol.setTextColor(
                Color.parseColor("#475569")
        );

        LinearLayout.LayoutParams colParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        colParams.setMargins(
                0,
                6,
                0,
                0
        );

        txtCol.setLayoutParams(colParams);

        entryLayout.addView(txtCol);

        if (!TextUtils.isEmpty(year)) {

            TextView txtYr =
                    new TextView(this);

            txtYr.setText(year);
            txtYr.setTextSize(12);

            txtYr.setTextColor(
                    Color.parseColor("#64748B")
            );

            LinearLayout.LayoutParams yrParams =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            yrParams.setMargins(
                    0,
                    4,
                    0,
                    0
            );

            txtYr.setLayoutParams(yrParams);

            entryLayout.addView(txtYr);
        }

        int count =
                educationContainer.getChildCount();

        int insertIndex =
                count > 0 ? count - 1 : 0;

        educationContainer.addView(
                entryLayout,
                insertIndex
        );
    }

    private String companyOrCollege(
            String college
    ) {
        return college;
    }

    // =========================================================
    // RESUME PICKER
    // =========================================================

    private void openResumePicker() {

        Intent intent =
                new Intent(
                        Intent.ACTION_OPEN_DOCUMENT
                );

        intent.addCategory(
                Intent.CATEGORY_OPENABLE
        );

        intent.setType(
                "application/pdf"
        );

        startActivityForResult(
                intent,
                PICK_RESUME_REQUEST
        );
    }

    // =========================================================
    // RESUME RESULT
    // =========================================================

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data
    ) {

        super.onActivityResult(
                requestCode,
                resultCode,
                data
        );

        if (requestCode == PICK_RESUME_REQUEST
                && resultCode == RESULT_OK
                && data != null) {

            Uri resumeUri =
                    data.getData();

            if (resumeUri != null) {

                Toast.makeText(
                        CandidateProfileActivity.this,
                        "Resume selected",
                        Toast.LENGTH_SHORT
                ).show();

                try {

                    getContentResolver()
                            .takePersistableUriPermission(
                                    resumeUri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            );

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }
    }

    // =========================================================
    // HELPERS
    // =========================================================

    private String safe(String value) {

        return value == null
                ? ""
                : value;
    }

    private boolean isEmpty(String value) {

        return value == null
                || value.trim().isEmpty();
    }
}