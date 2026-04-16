package com.studentperformance.backend.student;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRecordRepository extends JpaRepository<StudentRecord, Long> {

    List<StudentRecord> findAllByBranch(AcademicBranch branch);

    List<StudentRecord> findAllByFullNameContainingIgnoreCase(String fragment);

    List<StudentRecord> findAllByBranchAndFullNameContainingIgnoreCase(AcademicBranch branch, String fragment);

    @Query("select entry from StudentRecord entry order by ((entry.mathematicsScore + entry.scienceScore + entry.programmingScore) / 3.0) desc")
    List<StudentRecord> findLeaderboard(Pageable pageRequest);

    @Query("""
            select entry
            from StudentRecord entry
            where ((entry.mathematicsScore + entry.scienceScore + entry.programmingScore) / 3.0) < :cutoff
            order by ((entry.mathematicsScore + entry.scienceScore + entry.programmingScore) / 3.0) asc
            """)
    List<StudentRecord> findSupportWatchlist(@Param("cutoff") double cutoff);

    @Query("select avg((entry.mathematicsScore + entry.scienceScore + entry.programmingScore) / 3.0) from StudentRecord entry")
    Double readOverallAverage();

    @Query("select count(entry) from StudentRecord entry where ((entry.mathematicsScore + entry.scienceScore + entry.programmingScore) / 3.0) < :cutoff")
    long countSupportWatchlist(@Param("cutoff") double cutoff);

    @Query("""
            select entry.branch as branch, avg((entry.mathematicsScore + entry.scienceScore + entry.programmingScore) / 3.0) as meanScore
            from StudentRecord entry
            group by entry.branch
            order by entry.branch
            """)
    List<BranchMeanProjection> readBranchMeans();

    interface BranchMeanProjection {
        AcademicBranch getBranch();

        Double getMeanScore();
    }
}
