package com.studentperformance.backend.analytics.dto;

public record AnalyticsSummaryResponse(
        long totalStudents,
        double classAverage,
        long atRiskCount
) {
}
