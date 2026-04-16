package com.studentperformance.backend.analytics.dto;

public record AttendancePerformancePoint(
        Long studentId,
        String studentName,
        int attendance,
        double averageMarks
) {
}
