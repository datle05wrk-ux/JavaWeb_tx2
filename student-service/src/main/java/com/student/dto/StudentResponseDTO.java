package com.student.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponseDTO {
    private Long id;
    private String studentCode;
    private String fullName;
    private Integer birthYear;
    private String email;
    private String major;
}
