package com.student.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_summary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentSummary {

    @Id
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "student_code", nullable = false, length = 50)
    private String studentCode;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(name = "gpa", nullable = false)
    private Double gpa;

    @Column(name = "status", nullable = false, length = 50)
    private String status; // "ĐỖ" hoặc "TRƯỢT"

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
