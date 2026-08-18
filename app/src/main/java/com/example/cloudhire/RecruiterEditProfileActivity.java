package com.example.cloudhire;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class RecruiterEditProfileActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private ImageView editCompanyLogo;
    private TextView btnChangeLogo;
    private TextView btnSaveProfile;
    private ScrollView editProfileScrollView;
    private EditText etCompanyName;
    private EditText etIndustry;
    private EditText etCompanySize;
    private EditText etLocation;
    private EditText etWebsite;
    private EditText etRecruiterName;
    private EditText etRecruiterPhone;
    private EditText etDesignation;
    private Uri selectedLogoUri;
    private final ActivityResultLauncher<String> logoPicker =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {
                            selectedLogoUri = uri;
                            editCompanyLogo.setImageTintList(null);
                            editCompanyLogo.setImageURI(uri);
                        }
                    }
            );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_edit_profile);
        initializeViews();
        loadExistingProfile();
        setupClicks();
        setupKeyboardHandling();
    }
    private void initializeViews() {
        btnBack = findViewById(R.id.btnEditBack);
        editCompanyLogo = findViewById(R.id.editCompanyLogo);
        btnChangeLogo = findViewById(R.id.btnChangeLogo);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        editProfileScrollView =
                findViewById(R.id.editProfileScrollView);
        etCompanyName = findViewById(R.id.etCompanyName);
        etIndustry = findViewById(R.id.etIndustry);
        etCompanySize = findViewById(R.id.etCompanySize);
        etLocation = findViewById(R.id.etLocation);
        etWebsite = findViewById(R.id.etWebsite);
        etRecruiterName = findViewById(R.id.etRecruiterName);
        etRecruiterPhone = findViewById(R.id.etRecruiterPhone);
        etDesignation = findViewById(R.id.etDesignation);
    }
    private void loadExistingProfile() {
        etCompanyName.setText("NexTech Solutions");
        etIndustry.setText("Information Technology");
        etCompanySize.setText("51 - 200 Employees");
        etLocation.setText("Mangalore, Karnataka");
        etWebsite.setText("www.nextechsolutions.com");
        etRecruiterName.setText("Recruiter");
        etRecruiterPhone.setText("");
        etDesignation.setText("HR Manager");
    }
    private void setupClicks() {
        btnBack.setOnClickListener(v -> finish());
        btnChangeLogo.setOnClickListener(v ->
                logoPicker.launch("image/*")
        );
        editCompanyLogo.setOnClickListener(v ->
                logoPicker.launch("image/*")
        );
        btnSaveProfile.setOnClickListener(v -> saveProfile());
    }
    private void setupKeyboardHandling() {
        ViewCompat.setOnApplyWindowInsetsListener(
                editProfileScrollView,
                (view, windowInsets) -> {
                    Insets imeInsets =
                            windowInsets.getInsets(
                                    WindowInsetsCompat.Type.ime()
                            );
                    Insets systemInsets =
                            windowInsets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );
                    int bottomPadding =
                            Math.max(
                                    imeInsets.bottom,
                                    systemInsets.bottom
                            ) + 40;
                    view.setPadding(
                            view.getPaddingLeft(),
                            view.getPaddingTop(),
                            view.getPaddingRight(),
                            bottomPadding
                    );
                    return windowInsets;
                }
        );
        View.OnFocusChangeListener focusListener =
                (view, hasFocus) -> {
                    if (hasFocus && editProfileScrollView != null) {
                        view.postDelayed(() ->
                                editProfileScrollView.smoothScrollTo(
                                        0,
                                        Math.max(0, view.getTop() - 80)
                                ), 250);
                    }
                };
        etCompanyName.setOnFocusChangeListener(focusListener);
        etIndustry.setOnFocusChangeListener(focusListener);
        etCompanySize.setOnFocusChangeListener(focusListener);
        etLocation.setOnFocusChangeListener(focusListener);
        etWebsite.setOnFocusChangeListener(focusListener);
        etRecruiterName.setOnFocusChangeListener(focusListener);
        etRecruiterPhone.setOnFocusChangeListener(focusListener);
        etDesignation.setOnFocusChangeListener(focusListener);
    }
    private void saveProfile() {
        hideKeyboard();
        String companyName = getText(etCompanyName);
        String industry = getText(etIndustry);
        String companySize = getText(etCompanySize);
        String location = getText(etLocation);
        String website = getText(etWebsite);
        String recruiterName = getText(etRecruiterName);
        String phone = getText(etRecruiterPhone);
        String designation = getText(etDesignation);
        if (TextUtils.isEmpty(companyName)) {
            showError(etCompanyName, "Enter company name");
            return;
        }
        if (TextUtils.isEmpty(industry)) {
            showError(etIndustry, "Enter industry");
            return;
        }
        if (TextUtils.isEmpty(companySize)) {
            showError(etCompanySize, "Enter company size");
            return;
        }
        if (TextUtils.isEmpty(location)) {
            showError(etLocation, "Enter company location");
            return;
        }
        if (TextUtils.isEmpty(website)) {
            showError(etWebsite, "Enter website");
            return;
        }
        if (!isValidWebsite(website)) {
            showError(etWebsite, "Enter a valid website");
            return;
        }
        if (TextUtils.isEmpty(recruiterName)) {
            showError(etRecruiterName, "Enter recruiter name");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showError(etRecruiterPhone, "Enter phone number");
            return;
        }
        if (!isValidPhone(phone)) {
            showError(
                    etRecruiterPhone,
                    "Enter a valid 10-digit phone number"
            );
            return;
        }
        if (TextUtils.isEmpty(designation)) {
            showError(etDesignation, "Enter designation");
            return;
        }
// FRONTEND ONLY: return edited data to RecruiterProfileActivity.
        Intent resultIntent = new Intent();
        resultIntent.putExtra("companyName", companyName);
        resultIntent.putExtra("industry", industry);
        resultIntent.putExtra("companySize", companySize);
        resultIntent.putExtra("location", location);
        resultIntent.putExtra("website", website);
        resultIntent.putExtra("recruiterName", recruiterName);
        resultIntent.putExtra("phone", phone);
        resultIntent.putExtra("designation", designation);
        if (selectedLogoUri != null) {
            resultIntent.putExtra(
                    "logoUri",
                    selectedLogoUri.toString()
            );
        }
        setResult(RESULT_OK, resultIntent);
        Toast.makeText(
                this,
                "Profile updated successfully",
                Toast.LENGTH_SHORT
        ).show();
        finish();
    }
    private String getText(EditText editText) {
        if (editText == null) {
            return "";
        }
        return editText.getText().toString().trim();
    }
    private void showError(EditText editText, String message) {
        if (editText == null) {
            return;
        }
        editText.setError(message);
        editText.requestFocus();
        if (editProfileScrollView != null) {
            editProfileScrollView.postDelayed(
                    () -> editProfileScrollView.smoothScrollTo(
                            0,
                            Math.max(0, editText.getTop() - 80)
                    ),
                    200
            );
        }
    }
    private boolean isValidPhone(String phone) {
        String cleanedPhone =
                phone.replaceAll("[\\s-]", "");
        return cleanedPhone.matches(
                "^(\\+91)?[6-9][0-9]{9}$"
        );
    }
    private boolean isValidWebsite(String website) {
        String url = website;
        if (!url.startsWith("http://")
                && !url.startsWith("https://")) {
            url = "https://" + url;
        }
        return Patterns.WEB_URL.matcher(url).matches();
    }
    private void hideKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus == null) {
            return;
        }
        InputMethodManager imm =
                (InputMethodManager)
                        getSystemService(
                                Context.INPUT_METHOD_SERVICE
                        );
        if (imm != null) {
            imm.hideSoftInputFromWindow(
                    currentFocus.getWindowToken(),
                    0
            );
        }
        currentFocus.clearFocus();
    }}