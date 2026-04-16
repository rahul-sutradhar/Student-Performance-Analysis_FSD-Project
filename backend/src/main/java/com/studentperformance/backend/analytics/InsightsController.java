package com.studentperformance.backend.analytics;

import com.studentperformance.backend.analytics.dto.AttendanceScorePoint;
import com.studentperformance.backend.analytics.dto.BranchAverageView;
import com.studentperformance.backend.analytics.dto.OverviewStats;
import com.studentperformance.backend.student.dto.StudentView;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class InsightsController {

    private final InsightsService insightsService;

    public InsightsController(InsightsService insightsService) {
        this.insightsService = insightsService;
    }

    @GetMapping("/summary")
    @Operation(summary = "Read the figures displayed on the overview screen")
    public OverviewStats readOverview() {
        return insightsService.readOverview();
    }

    @GetMapping("/topper")
    @Operation(summary = "Read the student currently leading the class")
    public StudentView readHighestScorer() {
        return insightsService.readHighestScorer();
    }

    @GetMapping("/at-risk")
    @Operation(summary = "Read the watchlist of students below the review line")
    public List<StudentView> readSupportWatchlist() {
        return insightsService.readSupportWatchlist();
    }

    @GetMapping("/department-averages")
    @Operation(summary = "Read mean scores for each academic branch")
    public List<BranchAverageView> readBranchAverages() {
        return insightsService.readBranchAverages();
    }

    @GetMapping("/attendance-performance")
    @Operation(summary = "Read plot data for attendance and score patterns")
    public List<AttendanceScorePoint> readAttendanceScatter() {
        return insightsService.readAttendanceScatter();
    }
}
