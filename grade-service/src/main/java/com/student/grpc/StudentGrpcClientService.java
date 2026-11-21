package com.student.grpc;

import com.student.exception.ResourceNotFoundException;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StudentGrpcClientService {

    @GrpcClient("student-service")
    private StudentGrpcServiceGrpc.StudentGrpcServiceBlockingStub studentStub;

    public StudentResponse getStudentById(Long studentId) {
        log.info("Calling gRPC student-service to verify student ID = {}", studentId);
        try {
            StudentRequest request = StudentRequest.newBuilder()
                    .setId(studentId)
                    .build();

            StudentResponse response = studentStub.getStudentById(request);

            if (!response.getExists()) {
                log.warn("Student ID = {} does not exist according to gRPC student-service", studentId);
                throw new ResourceNotFoundException("Không tìm thấy sinh viên có ID: " + studentId + " (Xác thực gRPC thất bại)");
            }

            log.info("gRPC call successful: Student found: {} ({})", response.getFullName(), response.getStudentCode());
            return response;
        } catch (StatusRuntimeException e) {
            log.error("gRPC communication error with student-service: {}", e.getMessage());
            throw new RuntimeException("Lỗi kết nối gRPC tới student-service: " + e.getStatus().getDescription(), e);
        }
    }
}
