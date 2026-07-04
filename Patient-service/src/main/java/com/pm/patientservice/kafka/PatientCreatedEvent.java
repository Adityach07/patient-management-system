package com.pm.patientservice.kafka;

public record PatientCreatedEvent(
        String patientId,
        String name,
        String email) {
}
