package com.studentperformance.backend.analytics.dto;

public class OverviewStats {

    private final long totalStudents;
    private final double classAverage;
    private final long atRiskCount;

    public OverviewStats(long totalStudents, double classAverage, long atRiskCount) {
        this.totalStudents = totalStudents;
        this.classAverage = classAverage;
        this.atRiskCount = atRiskCount;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public double getClassAverage() {
        return classAverage;
    }

    public long getAtRiskCount() {
        return atRiskCount;
    }
}
