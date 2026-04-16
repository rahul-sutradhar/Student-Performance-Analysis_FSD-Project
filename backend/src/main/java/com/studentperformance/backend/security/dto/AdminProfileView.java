package com.studentperformance.backend.security.dto;

public class AdminProfileView {

    private final String username;
    private final String role;

    public AdminProfileView(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
