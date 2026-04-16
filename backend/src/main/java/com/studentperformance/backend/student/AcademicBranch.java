package com.studentperformance.backend.student;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AcademicBranch {
    CS("Computer Science"),
    IT("Information Technology"),
    ENTC("Electronics and Telecommunication"),
    MECHANICAL("Mechanical Engineering"),
    CIVIL("Civil Engineering");

    private final String title;

    AcademicBranch(String title) {
        this.title = title;
    }

    @JsonValue
    public String code() {
        return name();
    }

    public String title() {
        return title;
    }

    @JsonCreator
    public static AcademicBranch fromText(String rawValue) {
        if (rawValue == null) {
            return null;
        }

        String normalized = rawValue.trim().toUpperCase();
        for (AcademicBranch branch : values()) {
            if (branch.name().equals(normalized) || branch.title.toUpperCase().equals(normalized)) {
                return branch;
            }
        }

        throw new IllegalArgumentException("Unknown branch: " + rawValue);
    }
}
