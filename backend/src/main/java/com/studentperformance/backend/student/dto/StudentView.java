package com.studentperformance.backend.student.dto;

import com.studentperformance.backend.student.AcademicBranch;

public class StudentView {

    private final Long id;
    private final String name;
    private final AcademicBranch department;
    private final Integer math;
    private final Integer science;
    private final Integer programming;
    private final Integer attendance;
    private final double averageMarks;
    private final boolean atRisk;

    public StudentView(
            Long id,
            String name,
            AcademicBranch department,
            Integer math,
            Integer science,
            Integer programming,
            Integer attendance,
            double averageMarks,
            boolean atRisk
    ) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.math = math;
        this.science = science;
        this.programming = programming;
        this.attendance = attendance;
        this.averageMarks = averageMarks;
        this.atRisk = atRisk;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AcademicBranch getDepartment() {
        return department;
    }

    public Integer getMath() {
        return math;
    }

    public Integer getScience() {
        return science;
    }

    public Integer getProgramming() {
        return programming;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public double getAverageMarks() {
        return averageMarks;
    }

    public boolean isAtRisk() {
        return atRisk;
    }
}
