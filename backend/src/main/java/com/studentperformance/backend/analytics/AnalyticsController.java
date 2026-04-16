package com.studentperformance.backend.analytics;

import com.studentperformance.backend.analytics.dto.AnalyticsSummaryResponse;
import com.studentperformance.backend.analytics.dto.AttendancePerformancePoint;
import com.studentperformance.backend.analytics.dto.DepartmentAverageResponse;
import com.studentperformance.backend.student.dto.StudentResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/summary")
    @Operation(summary = "Get dashboard summary metrics")
    public AnalyticsSummaryResponse getSummary() {
        return analyticsService.getSummary();
    }

    @GetMapping("/topper")
    @Operation(summary = "Get the top-performing student")
    public StudentResponse getTopPerformer() {
        return analyticsService.getTopPerformer();
    }

    @GetMapping("/at-risk")
    @Operation(summary = "Get all at-risk students")
    public List<StudentResponse> getAtRiskStudents() {
        return analyticsService.getAtRiskStudents();
    }

    @GetMapping("/department-averages")
    @Operation(summary = "Get department-wise average marks")
    public List<DepartmentAverageResponse> getDepartmentAverages() {
        return analyticsService.getDepartmentAverages();
    }

    @GetMapping("/attendance-performance")
    @Operation(summary = "Get attendance vs marks data points")
    public List<AttendancePerformancePoint> getAttendancePerformance() {
        return analyticsService.getAttendancePerformance();
    }
}
