package com.example.cloudhire;

public class Candidate {

    private String fullName;
    private String email;
    private String phone;
    private String jobTitle;
    private String location;

    public Candidate() {
    }

    public Candidate(String fullName,
                     String email,
                     String phone,
                     String jobTitle,
                     String location) {

        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.jobTitle = jobTitle;
        this.location = location;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}