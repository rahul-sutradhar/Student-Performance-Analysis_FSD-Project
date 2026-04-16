package com.studentperformance.backend.student;

import com.studentperformance.backend.student.dto.StudentPayload;
import com.studentperformance.backend.student.dto.StudentView;

public final class StudentRecordAssembler {

    private StudentRecordAssembler() {
    }

    public static StudentRecord createRecord(StudentPayload payload) {
        StudentRecord record = new StudentRecord();
        applyChanges(record, payload);
        return record;
    }

    public static void applyChanges(StudentRecord record, StudentPayload payload) {
        record.setFullName(payload.getName().trim());
        record.setBranch(payload.getDepartment());
        record.setMathematicsScore(payload.getMath());
        record.setScienceScore(payload.getScience());
        record.setProgrammingScore(payload.getProgramming());
        record.setAttendancePercent(payload.getAttendance());
    }

    public static StudentView toView(StudentRecord record) {
        double average = round(record.averageScore());
        return new StudentView(
                record.getRecordId(),
                record.getFullName(),
                record.getBranch(),
                record.getMathematicsScore(),
                record.getScienceScore(),
                record.getProgrammingScore(),
                record.getAttendancePercent(),
                average,
                record.fallsBelow(60.0)
        );
    }

    private static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
