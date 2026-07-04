package com.pm.patientservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.patientservice.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PatientEventProducer {

    private static final Logger log =
            LoggerFactory.getLogger(PatientEventProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String patientCreatedTopic;

    public PatientEventProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper,
            @Value("${app.kafka.topic.patient-created}") String patientCreatedTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.patientCreatedTopic = patientCreatedTopic;
    }

    public void sendPatientCreatedEvent(Patient patient) {
        PatientCreatedEvent event = new PatientCreatedEvent(
                patient.getId().toString(),
                patient.getName(),
                patient.getEmail());

        String payload;
        try {
            payload = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Unable to serialize patient event", ex);
        }

        kafkaTemplate.send(patientCreatedTopic, event.patientId(), payload)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.warn("Failed to publish patient created event for {}",
                                event.patientId(), ex);
                    }
                });
    }
}
