package com.example.cloudhire.model;

public class JobResponse {

    private Long id;
    private Long recruiterId;

    private String title;
    private String description;
    private String companyName;
    private String location;
    private String employmentType;
    private String experienceRequired;

    private Double salaryMin;
    private Double salaryMax;

    private String skills;
    private String status;

    private String createdAt;
    private String updatedAt;

    private String applicationMethod;
    private String applicationUrl;

    public Long getId() {
        return id;
    }

    public Long getRecruiterId() {
        return recruiterId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
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

    public String getExperienceRequired() {
        return experienceRequired;
    }

    public Double getSalaryMin() {
        return salaryMin;
    }

    public Double getSalaryMax() {
        return salaryMax;
    }

    public String getSkills() {
        return skills;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getApplicationMethod() {
        return applicationMethod;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }
}