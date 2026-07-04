package com.pm.analyticsservice.kafka;

public record PatientCreatedEvent(
        String patientId,
        String name,
        String email) {
}