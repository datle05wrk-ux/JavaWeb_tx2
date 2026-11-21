package com.student.repository;

import com.student.entity.StudentSummary;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentSummarySpecification {

    public static Specification<StudentSummary> filterStudents(Integer birthYear, Double minGpa, Double maxGpa, String status) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (birthYear != null) {
                predicates.add(criteriaBuilder.equal(root.get("birthYear"), birthYear));
            }

            if (minGpa != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("gpa"), minGpa));
            }

            if (maxGpa != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("gpa"), maxGpa));
            }

            if (status != null && !status.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("status")),
                        status.trim().toLowerCase()
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
