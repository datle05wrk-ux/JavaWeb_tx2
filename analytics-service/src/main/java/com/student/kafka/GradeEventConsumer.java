package com.student.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GradeEventConsumer {

    private final AnalyticsService analyticsService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "student-grade-events", groupId = "analytics-group")
    public void consumeStudentGradeEvent(Object payload) {
        try {
            StudentGradeEvent event;
            if (payload instanceof String jsonStr) {
                event = objectMapper.readValue(jsonStr, StudentGradeEvent.class);
            } else if (payload instanceof StudentGradeEvent typedEvent) {
                event = typedEvent;
            } else {
                event = objectMapper.convertValue(payload, StudentGradeEvent.class);
            }

            log.info("Kafka consumer received event for student ID = {}", event.getStudentId());
            analyticsService.processStudentGradeEvent(event);
        } catch (Exception e) {
            log.error("Failed to parse and process student-grade-event payload: {}", payload, e);
        }
    }
}
