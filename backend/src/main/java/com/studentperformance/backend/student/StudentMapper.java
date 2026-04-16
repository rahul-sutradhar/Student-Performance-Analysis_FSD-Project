package com.studentperformance.backend.student;

import com.studentperformance.backend.student.dto.StudentRequest;
import com.studentperformance.backend.student.dto.StudentResponse;

public final class StudentMapper {

    private StudentMapper() {
    }

    public static Student toEntity(StudentRequest request) {
        Student student = new Student();
        updateEntity(student, request);
        return student;
    }

    public static void updateEntity(Student student, StudentRequest request) {
        student.setName(request.name().trim());
        student.setDepartment(request.department());
        student.setMath(request.math());
        student.setScience(request.science());
        student.setProgramming(request.programming());
        student.setAttendance(request.attendance());
    }

    public static StudentResponse toResponse(Student student) {
        double averageMarks = calculateAverage(student);
        boolean atRisk = isAtRisk(student);

        return new StudentResponse(
                student.getId(),
                student.getName(),
                student.getDepartment(),
                student.getMath(),
                student.getScience(),
                student.getProgramming(),
                student.getAttendance(),
                Math.round(averageMarks * 100.0) / 100.0,
                atRisk
        );
    }

    public static double calculateAverage(Student student) {
        return (student.getMath() + student.getScience() + student.getProgramming()) / 3.0;
    }

    public static boolean isAtRisk(Student student) {
        return calculateAverage(student) < 60.0;
    }
}
