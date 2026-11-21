package com.student.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "grades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "subject_name", nullable = false, length = 150)
    private String subjectName;

    @Column(name = "score", nullable = false)
    private Double score;

    @Column(name = "credits", nullable = false)
    private Integer credits;
}
