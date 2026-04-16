package com.studentperformance.backend.security;

public enum AccessTier {
    ADMIN("ROLE_ADMIN", "Administrator");

    private final String authorityName;
    private final String label;

    AccessTier(String authorityName, String label) {
        this.authorityName = authorityName;
        this.label = label;
    }

    public String authorityName() {
        return authorityName;
    }

    public String label() {
        return label;
    }
}
