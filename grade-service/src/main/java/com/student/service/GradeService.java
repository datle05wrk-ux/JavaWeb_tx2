package com.student.service;

import com.student.dto.GradeRequestDTO;
import com.student.dto.GradeResponseDTO;
import com.student.dto.StudentGradeSummaryResponseDTO;
import com.student.entity.Grade;
import com.student.grpc.StudentGrpcClientService;
import com.student.grpc.StudentResponse;
import com.student.kafka.GradeEventProducer;
import com.student.kafka.StudentGradeEvent;
import com.student.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentGrpcClientService studentGrpcClientService;
    private final GradeEventProducer gradeEventProducer;

    @Transactional
    public GradeResponseDTO addGrade(GradeRequestDTO request) {
        // Bước 1: Gọi gRPC Client đồng bộ sang student-service để xác thực sinh viên
        StudentResponse student = studentGrpcClientService.getStudentById(request.getStudentId());

        // Bước 2: Lưu điểm vào grade_db
        Grade grade = Grade.builder()
                .studentId(request.getStudentId())
                .subjectName(request.getSubjectName())
                .score(request.getScore())
                .credits(request.getCredits())
                .build();
        Grade savedGrade = gradeRepository.save(grade);

        // Bước 3: Tính điểm trung bình tích lũy (GPA) hiện tại của sinh viên
        List<Grade> allGrades = gradeRepository.findByStudentId(request.getStudentId());
        double gpa = calculateGpa(allGrades);
        String status = gpa >= 5.0 ? "ĐỖ" : "TRƯỢT";

        // Bước 4: Bắn sự kiện bất đồng bộ qua Kafka topic student-grade-events
        StudentGradeEvent event = StudentGradeEvent.builder()
                .studentId(student.getId())
                .studentCode(student.getStudentCode())
                .fullName(student.getFullName())
                .birthYear(student.getBirthYear())
                .gpa(gpa)
                .status(status)
                .build();

        gradeEventProducer.publishStudentGradeEvent(event);

        return mapToDTO(savedGrade);
    }

    @Transactional(readOnly = true)
    public StudentGradeSummaryResponseDTO getStudentGradeSummary(Long studentId) {
        // Lấy thông tin sinh viên từ gRPC
        StudentResponse student = studentGrpcClientService.getStudentById(studentId);

        // Lấy bảng điểm
        List<Grade> grades = gradeRepository.findByStudentId(studentId);
        double gpa = calculateGpa(grades);
        String status = grades.isEmpty() ? "CHƯA_CÓ_ĐIỂM" : (gpa >= 5.0 ? "ĐỖ" : "TRƯỢT");

        List<GradeResponseDTO> gradeDTOs = grades.stream()
                .map(this::mapToDTO)
                .toList();

        return StudentGradeSummaryResponseDTO.builder()
                .studentId(student.getId())
                .studentCode(student.getStudentCode())
                .fullName(student.getFullName())
                .birthYear(student.getBirthYear())
                .gpa(gpa)
                .status(status)
                .grades(gradeDTOs)
                .build();
    }

    public double calculateGpa(List<Grade> grades) {
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }

        double totalWeightedScore = 0.0;
        int totalCredits = 0;

        for (Grade g : grades) {
            int credits = (g.getCredits() != null && g.getCredits() > 0) ? g.getCredits() : 1;
            totalWeightedScore += g.getScore() * credits;
            totalCredits += credits;
        }

        if (totalCredits == 0) {
            return 0.0;
        }

        double rawGpa = totalWeightedScore / totalCredits;
        return Math.round(rawGpa * 100.0) / 100.0;
    }

    private GradeResponseDTO mapToDTO(Grade grade) {
        return GradeResponseDTO.builder()
                .id(grade.getId())
                .studentId(grade.getStudentId())
                .subjectName(grade.getSubjectName())
                .score(grade.getScore())
                .credits(grade.getCredits())
                .build();
    }
}
