package com.studentperformance.backend.analytics;

import com.studentperformance.backend.analytics.dto.AnalyticsSummaryResponse;
import com.studentperformance.backend.analytics.dto.AttendancePerformancePoint;
import com.studentperformance.backend.analytics.dto.DepartmentAverageResponse;
import com.studentperformance.backend.student.Student;
import com.studentperformance.backend.student.StudentMapper;
import com.studentperformance.backend.student.StudentRepository;
import com.studentperformance.backend.student.dto.StudentResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AnalyticsService {

    private static final double RISK_THRESHOLD = 60.0;

    private final StudentRepository studentRepository;

    public AnalyticsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public AnalyticsSummaryResponse getSummary() {
        List<Student> students = studentRepository.findAll();
        double classAverage = round(studentRepository.findClassAverage() == null ? 0.0 : studentRepository.findClassAverage());
        long atRiskCount = studentRepository.countAtRiskStudents(RISK_THRESHOLD);

        return new AnalyticsSummaryResponse(students.size(), classAverage, atRiskCount);
    }

    public StudentResponse getTopPerformer() {
        return studentRepository.findTopPerformers(org.springframework.data.domain.PageRequest.of(0, 1)).stream()
                .findFirst()
                .map(StudentMapper::toResponse)
                .orElse(null);
    }

    public List<StudentResponse> getAtRiskStudents() {
        return studentRepository.findAtRiskStudents(RISK_THRESHOLD).stream()
                .map(StudentMapper::toResponse)
                .toList();
    }

    public List<DepartmentAverageResponse> getDepartmentAverages() {
        return studentRepository.findDepartmentAverages().stream()
                .map(entry -> new DepartmentAverageResponse(entry.getDepartment(), round(entry.getAverageMarks())))
                .toList();
    }

    public List<AttendancePerformancePoint> getAttendancePerformance() {
        return studentRepository.findAll().stream()
                .map(student -> new AttendancePerformancePoint(
                        student.getId(),
                        student.getName(),
                        student.getAttendance(),
                        round(StudentMapper.calculateAverage(student))
                ))
                .toList();
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
