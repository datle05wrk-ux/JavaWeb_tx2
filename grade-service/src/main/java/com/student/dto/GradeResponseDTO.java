package com.student.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeResponseDTO {
    private Long id;
    private Long studentId;
    private String subjectName;
    private Double score;
    private Integer credits;
}
