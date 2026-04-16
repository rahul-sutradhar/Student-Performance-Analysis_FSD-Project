package com.studentperformance.backend.security.dto;

public record CurrentUserResponse(
        String username,
        String role
) {
}
