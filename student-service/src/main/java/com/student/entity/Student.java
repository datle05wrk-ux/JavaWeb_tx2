package com.student.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_code", unique = true, nullable = false, length = 50)
    private String studentCode;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(name = "birth_year", nullable = false)
    private Integer birthYear;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "major", length = 100)
    private String major;
}
