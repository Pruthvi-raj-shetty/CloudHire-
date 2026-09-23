package com.example.cloudhire.model;

public class UserProfileResponse {

    private Long id;
    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String role;
    private String location;
    private String jobTitle;
    private String about;
    private String skills;
    private String totalExperience;
    private String profileImageUrl;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
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