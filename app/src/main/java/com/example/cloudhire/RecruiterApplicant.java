package com.example.cloudhire;

public class RecruiterApplicant {

    private String applicationId;
    private String candidateId;

    private String candidateName;
    private String professionalTitle;

    private String jobTitle;
    private String companyName;

    private String location;
    private String appliedDate;

    private String email;
    private String phone;

    private String resumeUrl;
    private String profileImage;

    private String status;

    public RecruiterApplicant() {
    }

    public RecruiterApplicant(
            String applicationId,
            String candidateId,
            String candidateName,
            String professionalTitle,
            String jobTitle,
            String companyName,
            String location,
            String appliedDate,
            String email,
            String phone,
            String resumeUrl,
            String profileImage,
            String status
    ) {
        this.applicationId = applicationId;
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.professionalTitle = professionalTitle;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.location = location;
        this.appliedDate = appliedDate;
        this.email = email;
        this.phone = phone;
        this.resumeUrl = resumeUrl;
        this.profileImage = profileImage;
        this.status = status;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public String getProfessionalTitle() {
        return professionalTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLocation() {
        return location;
    }

    public String getAppliedDate() {
        return appliedDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}