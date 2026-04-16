package com.studentperformance.backend.student.dto;

import com.studentperformance.backend.student.Department;

public record StudentResponse(
        Long id,
        String name,
        Department department,
        Integer math,
        Integer science,
        Integer programming,
        Integer attendance,
        double averageMarks,
        boolean atRisk
) {
}
