package com.studentperformance.backend.security.dto;

public class LoginTokenView {

    private final String token;
    private final String username;
    private final String role;

    public LoginTokenView(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
