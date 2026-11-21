package com.student.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeRequestDTO {

    @NotNull(message = "studentId không được để trống")
    private Long studentId;

    @NotBlank(message = "Tên môn học không được để trống")
    private String subjectName;

    @NotNull(message = "Điểm số không được để trống")
    @DecimalMin(value = "0.0", message = "Điểm số tối thiểu là 0.0")
    @DecimalMax(value = "10.0", message = "Điểm số tối đa là 10.0")
    private Double score;

    @NotNull(message = "Số tín chỉ không được để trống")
    @Min(value = 1, message = "Số tín chỉ tối thiểu là 1")
    private Integer credits;
}
