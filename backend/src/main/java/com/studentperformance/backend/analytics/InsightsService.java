package com.studentperformance.backend.analytics;

import com.studentperformance.backend.analytics.dto.AttendanceScorePoint;
import com.studentperformance.backend.analytics.dto.BranchAverageView;
import com.studentperformance.backend.analytics.dto.OverviewStats;
import com.studentperformance.backend.student.StudentCatalogService;
import com.studentperformance.backend.student.StudentRecord;
import com.studentperformance.backend.student.StudentRecordAssembler;
import com.studentperformance.backend.student.StudentRecordRepository;
import com.studentperformance.backend.student.dto.StudentView;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InsightsService {

    private static final double REVIEW_LINE = 60.0;

    private final StudentRecordRepository studentRecordRepository;

    public InsightsService(StudentRecordRepository studentRecordRepository) {
        this.studentRecordRepository = studentRecordRepository;
    }

    public OverviewStats readOverview() {
        List<StudentRecord> records = studentRecordRepository.findAll();
        Double overallAverage = studentRecordRepository.readOverallAverage();
        double roundedAverage = round(overallAverage == null ? 0.0 : overallAverage);
        long flaggedCount = studentRecordRepository.countSupportWatchlist(REVIEW_LINE);
        return new OverviewStats(records.size(), roundedAverage, flaggedCount);
    }

    public StudentView readHighestScorer() {
        return studentRecordRepository.findLeaderboard(PageRequest.of(0, 1)).stream()
                .findFirst()
                .map(StudentRecordAssembler::toView)
                .orElse(null);
    }

    public List<StudentView> readSupportWatchlist() {
        return studentRecordRepository.findSupportWatchlist(REVIEW_LINE).stream()
                .map(StudentRecordAssembler::toView)
                .toList();
    }

    public List<BranchAverageView> readBranchAverages() {
        return studentRecordRepository.readBranchMeans().stream()
                .map(projection -> new BranchAverageView(projection.getBranch(), round(projection.getMeanScore())))
                .toList();
    }

    public List<AttendanceScorePoint> readAttendanceScatter() {
        return studentRecordRepository.findAll().stream()
                .map(record -> new AttendanceScorePoint(
                        record.getRecordId(),
                        record.getFullName(),
                        record.getAttendancePercent(),
                        round(record.averageScore())
                ))
                .toList();
    }

    private double round(double rawValue) {
        return Math.round(rawValue * 100.0) / 100.0;
    }
}
