package com.student.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GradeEventProducer {

    public static final String TOPIC = "student-grade-events";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishStudentGradeEvent(StudentGradeEvent event) {
        log.info("Publishing StudentGradeEvent to Kafka topic '{}': studentId={}, GPA={}, status={}",
                TOPIC, event.getStudentId(), event.getGpa(), event.getStatus());
        kafkaTemplate.send(TOPIC, String.valueOf(event.getStudentId()), event);
    }
}
