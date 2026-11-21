package com.student.service;

import com.student.dto.StudentRequestDTO;
import com.student.dto.StudentResponseDTO;
import com.student.entity.Student;
import com.student.exception.DuplicateResourceException;
import com.student.exception.ResourceNotFoundException;
import com.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO request) {
        if (studentRepository.existsByStudentCode(request.getStudentCode())) {
            throw new DuplicateResourceException("Mã sinh viên đã tồn tại: " + request.getStudentCode());
        }

        Student student = Student.builder()
                .studentCode(request.getStudentCode())
                .fullName(request.getFullName())
                .birthYear(request.getBirthYear())
                .email(request.getEmail())
                .major(request.getMajor())
                .build();

        Student saved = studentRepository.save(student);
        return mapToDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sinh viên có ID: " + id));
        return mapToDTO(student);
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentByCode(String studentCode) {
        Student student = studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sinh viên có mã: " + studentCode));
        return mapToDTO(student);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> searchStudents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllStudents();
        }
        return studentRepository.searchByKeyword(keyword.trim())
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public StudentResponseDTO mapToDTO(Student student) {
        return StudentResponseDTO.builder()
                .id(student.getId())
                .studentCode(student.getStudentCode())
                .fullName(student.getFullName())
                .birthYear(student.getBirthYear())
                .email(student.getEmail())
                .major(student.getMajor())
                .build();
    }
}
