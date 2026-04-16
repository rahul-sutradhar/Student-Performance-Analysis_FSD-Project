package com.studentperformance.backend.student;

import com.studentperformance.backend.student.dto.StudentRequest;
import com.studentperformance.backend.student.dto.StudentResponse;
import com.studentperformance.backend.support.ResourceNotFoundException;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents(Department department, String search) {
        String normalizedSearch = search == null ? "" : search.trim();
        List<Student> students;

        if (department != null && !normalizedSearch.isEmpty()) {
            students = studentRepository.findAllByDepartmentAndNameContainingIgnoreCase(department, normalizedSearch);
        } else if (department != null) {
            students = studentRepository.findAllByDepartment(department);
        } else if (!normalizedSearch.isEmpty()) {
            students = studentRepository.findAllByNameContainingIgnoreCase(normalizedSearch);
        } else {
            students = studentRepository.findAll();
        }

        return students.stream()
                .map(StudentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .map(StudentMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));
    }

    public StudentResponse createStudent(StudentRequest request) {
        Student student = StudentMapper.toEntity(request);
        return StudentMapper.toResponse(studentRepository.save(student));
    }

    public StudentResponse updateStudent(Long studentId, StudentRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));

        StudentMapper.updateEntity(student, request);
        return StudentMapper.toResponse(studentRepository.save(student));
    }

    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with id " + studentId);
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional(readOnly = true)
    public StudentResponse getTopStudent() {
        return studentRepository.findTopPerformers(PageRequest.of(0, 1)).stream()
                .findFirst()
                .map(StudentMapper::toResponse)
                .orElse(null);
    }
}
