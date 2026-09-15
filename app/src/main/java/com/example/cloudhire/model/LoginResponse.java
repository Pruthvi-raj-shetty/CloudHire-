package com.example.cloudhire.model;

public class LoginResponse {

    private String email;
    private Long id;
    private String name;
    private String role;
    private String token;

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }
}