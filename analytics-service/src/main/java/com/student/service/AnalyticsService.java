package com.student.service;

import com.student.dto.StudentSummaryResponseDTO;
import com.student.entity.StudentSummary;
import com.student.kafka.StudentGradeEvent;
import com.student.repository.StudentSummaryRepository;
import com.student.repository.StudentSummarySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final StudentSummaryRepository studentSummaryRepository;

    @Transactional
    public void processStudentGradeEvent(StudentGradeEvent event) {
        log.info("Processing StudentGradeEvent: studentId={}, studentCode={}, GPA={}, status={}",
                event.getStudentId(), event.getStudentCode(), event.getGpa(), event.getStatus());

        StudentSummary summary = studentSummaryRepository.findById(event.getStudentId())
                .orElse(StudentSummary.builder()
                        .studentId(event.getStudentId())
                        .build());

        summary.setStudentCode(event.getStudentCode());
        summary.setFullName(event.getFullName());
        summary.setBirthYear(event.getBirthYear());
        summary.setGpa(event.getGpa());
        summary.setStatus(event.getStatus());
        summary.setUpdatedAt(LocalDateTime.now());

        studentSummaryRepository.save(summary);
        log.info("StudentSummary saved/updated successfully for studentId={}", event.getStudentId());
    }

    @Transactional(readOnly = true)
    public List<StudentSummaryResponseDTO> filterStudents(Integer birthYear, Double minGpa, Double maxGpa, String status) {
        Specification<StudentSummary> spec = StudentSummarySpecification.filterStudents(birthYear, minGpa, maxGpa, status);

        return studentSummaryRepository.findAll(spec)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private StudentSummaryResponseDTO mapToDTO(StudentSummary summary) {
        return StudentSummaryResponseDTO.builder()
                .studentId(summary.getStudentId())
                .studentCode(summary.getStudentCode())
                .fullName(summary.getFullName())
                .birthYear(summary.getBirthYear())
                .gpa(summary.getGpa())
                .status(summary.getStatus())
                .updatedAt(summary.getUpdatedAt())
                .build();
    }
}
