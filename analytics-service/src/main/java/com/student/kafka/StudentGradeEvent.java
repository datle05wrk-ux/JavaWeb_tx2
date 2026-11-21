package com.student.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentGradeEvent {
    private Long studentId;
    private String studentCode;
    private String fullName;
    private Integer birthYear;
    private Double gpa;
    private String status;
}
