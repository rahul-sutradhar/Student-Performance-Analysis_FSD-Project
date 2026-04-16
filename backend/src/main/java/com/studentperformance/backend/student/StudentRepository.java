package com.studentperformance.backend.student;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByDepartment(Department department);

    List<Student> findAllByNameContainingIgnoreCase(String search);

    List<Student> findAllByDepartmentAndNameContainingIgnoreCase(Department department, String search);

    @Query("select s from Student s order by ((s.math + s.science + s.programming) / 3.0) desc")
    List<Student> findTopPerformers(Pageable pageable);

    @Query("select s from Student s where ((s.math + s.science + s.programming) / 3.0) < :threshold order by ((s.math + s.science + s.programming) / 3.0) asc")
    List<Student> findAtRiskStudents(@Param("threshold") double threshold);

    @Query("select avg((s.math + s.science + s.programming) / 3.0) from Student s")
    Double findClassAverage();

    @Query("select count(s) from Student s where ((s.math + s.science + s.programming) / 3.0) < :threshold")
    long countAtRiskStudents(@Param("threshold") double threshold);

    @Query("select s.department as department, avg((s.math + s.science + s.programming) / 3.0) as averageMarks from Student s group by s.department order by s.department")
    List<DepartmentAverageView> findDepartmentAverages();

    interface DepartmentAverageView {
        Department getDepartment();

        Double getAverageMarks();
    }
}
