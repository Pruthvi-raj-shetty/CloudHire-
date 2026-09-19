package com.example.cloudhire.model;

public class ApplicationResponse {

    private Long id;
    private Long jobId;

    private String jobTitle;
    private String companyName;
    private String location;
    private String employmentType;

    private String status;
    private String appliedAt;
    private String updatedAt;

    public Long getId() {
        return id;
    }

    public Long getJobId() {
        return jobId;
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

    public String getEmploymentType() {
        return employmentType;
    }

    public String getStatus() {
        return status;
    }

    public String getAppliedAt() {
        return appliedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}