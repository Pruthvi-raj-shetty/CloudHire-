package com.example.cloudhire;

public class AdminUser {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
    private String status; // Active / Inactive

    public AdminUser(String userId, String name, String email, String phoneNumber, String role, String status) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
