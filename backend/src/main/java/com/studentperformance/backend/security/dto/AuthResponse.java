package com.studentperformance.backend.security.dto;

public record AuthResponse(
        String token,
        String username,
        String role
) {
}
