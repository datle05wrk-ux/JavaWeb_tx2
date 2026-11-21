package com.student.controller;

import com.student.dto.GradeRequestDTO;
import com.student.dto.GradeResponseDTO;
import com.student.dto.StudentGradeSummaryResponseDTO;
import com.student.service.GradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public ResponseEntity<GradeResponseDTO> addGrade(@Valid @RequestBody GradeRequestDTO request) {
        GradeResponseDTO savedGrade = gradeService.addGrade(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGrade);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<StudentGradeSummaryResponseDTO> getStudentGrades(@PathVariable Long studentId) {
        StudentGradeSummaryResponseDTO summary = gradeService.getStudentGradeSummary(studentId);
        return ResponseEntity.ok(summary);
    }
}
