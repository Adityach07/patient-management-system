package com.pm.analyticsservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PatientEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(PatientEventConsumer.class);
    private final ObjectMapper objectMapper;

    public PatientEventConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

        @KafkaListener(topics = "${app.kafka.topic.patient-created}", groupId = "analytics-group")
    public void consumePatientCreated(String message) {
        try {
            // 1. Convert JSON payload back to our Java Record
            PatientCreatedEvent event = objectMapper.readValue(message, PatientCreatedEvent.class);

            // 2. Pop / log message with parameters
            log.info("==================================================");
            log.info("🔔 [Analytics Service] NEW PATIENT EVENT RECEIVED!");
            log.info("   ID:    {}", event.patientId());
            log.info("   Name:  {}", event.name());
            log.info("   Email: {}", event.email());
            log.info("==================================================");

        } catch (JsonProcessingException ex) {
            log.error("Failed to parse incoming Kafka message: {}", message, ex);
        }
    }
}