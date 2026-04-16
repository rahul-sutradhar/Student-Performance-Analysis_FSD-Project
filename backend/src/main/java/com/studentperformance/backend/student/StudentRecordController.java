package com.studentperformance.backend.student;

import com.studentperformance.backend.student.dto.StudentPayload;
import com.studentperformance.backend.student.dto.StudentView;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentRecordController {

    private final StudentCatalogService studentCatalogService;

    public StudentRecordController(StudentCatalogService studentCatalogService) {
        this.studentCatalogService = studentCatalogService;
    }

    @GetMapping
    @Operation(summary = "Browse student entries with optional branch and name filters")
    public List<StudentView> browseStudents(
            @RequestParam(required = false) AcademicBranch department,
            @RequestParam(required = false) String search
    ) {
        return studentCatalogService.browse(department, search);
    }

    @GetMapping("/{studentId}")
    @Operation(summary = "Read one student entry by id")
    public StudentView readStudent(@PathVariable Long studentId) {
        return studentCatalogService.readOne(studentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new student entry")
    public StudentView createStudent(@Valid @RequestBody StudentPayload payload) {
        return studentCatalogService.create(payload);
    }

    @PutMapping("/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Revise an existing student entry")
    public StudentView reviseStudent(@PathVariable Long studentId, @Valid @RequestBody StudentPayload payload) {
        return studentCatalogService.revise(studentId, payload);
    }

    @DeleteMapping("/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove a student entry")
    public void removeStudent(@PathVariable Long studentId) {
        studentCatalogService.remove(studentId);
    }
}
