package com.studentperformance.backend.student;

import com.studentperformance.backend.student.dto.StudentPayload;
import com.studentperformance.backend.student.dto.StudentView;
import com.studentperformance.backend.support.ResourceNotFoundException;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentCatalogService {

    private final StudentRecordRepository studentRecordRepository;

    public StudentCatalogService(StudentRecordRepository studentRecordRepository) {
        this.studentRecordRepository = studentRecordRepository;
    }

    @Transactional(readOnly = true)
    public List<StudentView> browse(AcademicBranch branch, String searchText) {
        String filterText = searchText == null ? "" : searchText.trim();
        List<StudentRecord> records;

        if (branch != null && !filterText.isEmpty()) {
            records = studentRecordRepository.findAllByBranchAndFullNameContainingIgnoreCase(branch, filterText);
        } else if (branch != null) {
            records = studentRecordRepository.findAllByBranch(branch);
        } else if (!filterText.isEmpty()) {
            records = studentRecordRepository.findAllByFullNameContainingIgnoreCase(filterText);
        } else {
            records = studentRecordRepository.findAll();
        }

        return records.stream()
                .map(StudentRecordAssembler::toView)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudentView readOne(Long recordId) {
        return studentRecordRepository.findById(recordId)
                .map(StudentRecordAssembler::toView)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + recordId));
    }

    public StudentView create(StudentPayload payload) {
        StudentRecord record = StudentRecordAssembler.createRecord(payload);
        return StudentRecordAssembler.toView(studentRecordRepository.save(record));
    }

    public StudentView revise(Long recordId, StudentPayload payload) {
        StudentRecord record = studentRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + recordId));

        StudentRecordAssembler.applyChanges(record, payload);
        return StudentRecordAssembler.toView(studentRecordRepository.save(record));
    }

    public void remove(Long recordId) {
        if (!studentRecordRepository.existsById(recordId)) {
            throw new ResourceNotFoundException("Student not found with id " + recordId);
        }
        studentRecordRepository.deleteById(recordId);
    }

    @Transactional(readOnly = true)
    public StudentView readLeader() {
        return studentRecordRepository.findLeaderboard(PageRequest.of(0, 1)).stream()
                .findFirst()
                .map(StudentRecordAssembler::toView)
                .orElse(null);
    }
}
