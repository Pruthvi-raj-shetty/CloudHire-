package com.example.cloudhire;

public class AdminApplication {
    private String applicationId;
    private String jobId;
    private String candidateName;
    private String candidateEmail;
    private String candidatePhone;
    private String candidateTitle;
    private String jobTitle;
    private String companyName;
    private String location;
    private String employmentType;
    private String status;
    private String appliedAt;
    private String updatedAt;

    public AdminApplication(String applicationId, String jobId, String candidateName, String candidateEmail, String candidatePhone, String candidateTitle, String jobTitle, String companyName, String location, String employmentType, String status, String appliedAt, String updatedAt) {
        this.applicationId = applicationId;
        this.jobId = jobId;
        this.candidateName = candidateName;
        this.candidateEmail = candidateEmail;
        this.candidatePhone = candidatePhone;
        this.candidateTitle = candidateTitle;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.location = location;
        this.employmentType = employmentType;
        this.status = status;
        this.appliedAt = appliedAt;
        this.updatedAt = updatedAt;
    }

    public String getApplicationId() { return applicationId; }
    public String getCandidateName() { return candidateName; }
    public String getCandidateEmail() { return candidateEmail; }
    public String getCandidatePhone() { return candidatePhone; }
    public String getCandidateTitle() { return candidateTitle; }
    public String getJobTitle() { return jobTitle; }
    public String getCompanyName() { return companyName; }
    public String getLocation() { return location; }
    public String getEmploymentType() { return employmentType; }
    public String getStatus() { return status; }
    public String getAppliedAt() { return appliedAt; }
    public String getUpdatedAt() { return updatedAt; }
}
