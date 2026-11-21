package com.student.controller;

import com.student.dto.StudentSummaryResponseDTO;
import com.student.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/filter")
    public ResponseEntity<List<StudentSummaryResponseDTO>> filterStudents(
            @RequestParam(value = "birthYear", required = false) Integer birthYear,
            @RequestParam(value = "minGpa", required = false) Double minGpa,
            @RequestParam(value = "maxGpa", required = false) Double maxGpa,
            @RequestParam(value = "status", required = false) String status) {

        List<StudentSummaryResponseDTO> results = analyticsService.filterStudents(birthYear, minGpa, maxGpa, status);
        return ResponseEntity.ok(results);
    }
}
