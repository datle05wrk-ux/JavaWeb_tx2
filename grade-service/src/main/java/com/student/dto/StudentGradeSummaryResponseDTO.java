package com.student.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentGradeSummaryResponseDTO {
    private Long studentId;
    private String studentCode;
    private String fullName;
    private Integer birthYear;
    private Double gpa;
    private String status;
    private List<GradeResponseDTO> grades;
}
