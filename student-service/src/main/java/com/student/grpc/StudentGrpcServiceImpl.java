package com.student.grpc;

import com.student.entity.Student;
import com.student.repository.StudentRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class StudentGrpcServiceImpl extends StudentGrpcServiceGrpc.StudentGrpcServiceImplBase {

    private final StudentRepository studentRepository;

    @Override
    public void getStudentById(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        long studentId = request.getId();
        log.info("gRPC call received: GetStudentById with ID = {}", studentId);

        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        StudentResponse response;
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            response = StudentResponse.newBuilder()
                    .setId(student.getId())
                    .setStudentCode(student.getStudentCode())
                    .setFullName(student.getFullName())
                    .setBirthYear(student.getBirthYear() != null ? student.getBirthYear() : 0)
                    .setMajor(student.getMajor() != null ? student.getMajor() : "")
                    .setExists(true)
                    .build();
        } else {
            log.warn("Student not found via gRPC with ID = {}", studentId);
            response = StudentResponse.newBuilder()
                    .setId(studentId)
                    .setExists(false)
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
