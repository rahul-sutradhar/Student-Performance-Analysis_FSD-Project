package com.studentperformance.backend.student.dto;

import com.studentperformance.backend.student.AcademicBranch;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StudentPayload {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Department is required")
    private AcademicBranch department;

    @NotNull(message = "Math marks are required")
    @Min(value = 0, message = "Math marks cannot be less than 0")
    @Max(value = 100, message = "Math marks cannot be more than 100")
    private Integer math;

    @NotNull(message = "Science marks are required")
    @Min(value = 0, message = "Science marks cannot be less than 0")
    @Max(value = 100, message = "Science marks cannot be more than 100")
    private Integer science;

    @NotNull(message = "Programming marks are required")
    @Min(value = 0, message = "Programming marks cannot be less than 0")
    @Max(value = 100, message = "Programming marks cannot be more than 100")
    private Integer programming;

    @NotNull(message = "Attendance is required")
    @Min(value = 0, message = "Attendance cannot be less than 0")
    @Max(value = 100, message = "Attendance cannot be more than 100")
    private Integer attendance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AcademicBranch getDepartment() {
        return department;
    }

    public void setDepartment(AcademicBranch department) {
        this.department = department;
    }

    public Integer getMath() {
        return math;
    }

    public void setMath(Integer math) {
        this.math = math;
    }

    public Integer getScience() {
        return science;
    }

    public void setScience(Integer science) {
        this.science = science;
    }

    public Integer getProgramming() {
        return programming;
    }

    public void setProgramming(Integer programming) {
        this.programming = programming;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }
}
