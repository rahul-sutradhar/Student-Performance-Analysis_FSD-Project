package com.studentperformance.backend.student.dto;

import com.studentperformance.backend.student.Department;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Department is required")
        Department department,

        @NotNull(message = "Math marks are required")
        @Min(value = 0, message = "Math marks cannot be less than 0")
        @Max(value = 100, message = "Math marks cannot be more than 100")
        Integer math,

        @NotNull(message = "Science marks are required")
        @Min(value = 0, message = "Science marks cannot be less than 0")
        @Max(value = 100, message = "Science marks cannot be more than 100")
        Integer science,

        @NotNull(message = "Programming marks are required")
        @Min(value = 0, message = "Programming marks cannot be less than 0")
        @Max(value = 100, message = "Programming marks cannot be more than 100")
        Integer programming,

        @NotNull(message = "Attendance is required")
        @Min(value = 0, message = "Attendance cannot be less than 0")
        @Max(value = 100, message = "Attendance cannot be more than 100")
        Integer attendance
) {
}
