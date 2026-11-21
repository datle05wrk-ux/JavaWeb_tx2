package com.student.kafka;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentGradeEvent {
    private Long studentId;
    private String studentCode;
    private String fullName;
    private Integer birthYear;
    private Double gpa;
    private String status;
}
