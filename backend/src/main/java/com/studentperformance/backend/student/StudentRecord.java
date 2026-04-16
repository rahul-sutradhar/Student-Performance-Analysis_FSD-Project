package com.studentperformance.backend.student;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class StudentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @Column(name = "name", nullable = false, length = 120)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "department", nullable = false, length = 30)
    private AcademicBranch branch;

    @Column(name = "math", nullable = false)
    private Integer mathematicsScore;

    @Column(name = "science", nullable = false)
    private Integer scienceScore;

    @Column(name = "programming", nullable = false)
    private Integer programmingScore;

    @Column(name = "attendance", nullable = false)
    private Integer attendancePercent;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public AcademicBranch getBranch() {
        return branch;
    }

    public void setBranch(AcademicBranch branch) {
        this.branch = branch;
    }

    public Integer getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(Integer mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public Integer getScienceScore() {
        return scienceScore;
    }

    public void setScienceScore(Integer scienceScore) {
        this.scienceScore = scienceScore;
    }

    public Integer getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(Integer programmingScore) {
        this.programmingScore = programmingScore;
    }

    public Integer getAttendancePercent() {
        return attendancePercent;
    }

    public void setAttendancePercent(Integer attendancePercent) {
        this.attendancePercent = attendancePercent;
    }

    public double averageScore() {
        return (mathematicsScore + scienceScore + programmingScore) / 3.0;
    }

    public boolean fallsBelow(double threshold) {
        return averageScore() < threshold;
    }
}
