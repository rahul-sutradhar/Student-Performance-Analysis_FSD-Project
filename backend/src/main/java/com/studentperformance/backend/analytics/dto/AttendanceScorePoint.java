package com.studentperformance.backend.analytics.dto;

public class AttendanceScorePoint {

    private final Long studentId;
    private final String studentName;
    private final int attendance;
    private final double averageMarks;

    public AttendanceScorePoint(Long studentId, String studentName, int attendance, double averageMarks) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.attendance = attendance;
        this.averageMarks = averageMarks;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getAttendance() {
        return attendance;
    }

    public double getAverageMarks() {
        return averageMarks;
    }
}
