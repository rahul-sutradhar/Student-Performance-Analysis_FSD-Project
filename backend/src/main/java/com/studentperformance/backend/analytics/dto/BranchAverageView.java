package com.studentperformance.backend.analytics.dto;

import com.studentperformance.backend.student.AcademicBranch;

public class BranchAverageView {

    private final AcademicBranch department;
    private final double averageMarks;

    public BranchAverageView(AcademicBranch department, double averageMarks) {
        this.department = department;
        this.averageMarks = averageMarks;
    }

    public AcademicBranch getDepartment() {
        return department;
    }

    public double getAverageMarks() {
        return averageMarks;
    }
}
