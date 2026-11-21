package com.student.repository;

import com.student.entity.StudentSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentSummaryRepository extends JpaRepository<StudentSummary, Long>, JpaSpecificationExecutor<StudentSummary> {
}
