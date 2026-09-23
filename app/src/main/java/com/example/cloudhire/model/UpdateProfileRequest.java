package com.example.cloudhire.model;

public class UpdateProfileRequest {

    private String name;
    private String phone;
    private String location;
    private String jobTitle;
    private String about;
    private String skills;
    private String totalExperience;
    private String profileImageUrl;

    public UpdateProfileRequest(
            String name,
            String phone,
            String location,
            String jobTitle,
            String about,
            String skills,
            String totalExperience,
            String profileImageUrl
    ) {
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.jobTitle = jobTitle;
        this.about = about;
        this.skills = skills;
        this.totalExperience = totalExperience;
        this.profileImageUrl = profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getAbout() {
        return about;
    }

    public String getSkills() {
        return skills;
    }

    public String getTotalExperience() {
        return totalExperience;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}