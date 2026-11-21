package com.student.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentSummaryResponseDTO {
    private Long studentId;
    private String studentCode;
    private String fullName;
    private Integer birthYear;
    private Double gpa;
    private String status;
    private LocalDateTime updatedAt;
}
