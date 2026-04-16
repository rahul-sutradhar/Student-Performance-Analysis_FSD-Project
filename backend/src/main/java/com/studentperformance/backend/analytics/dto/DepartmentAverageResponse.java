package com.studentperformance.backend.analytics.dto;

import com.studentperformance.backend.student.Department;

public record DepartmentAverageResponse(
        Department department,
        double averageMarks
) {
}
