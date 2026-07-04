package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

public class PatientMapper {

    // Entity -> Response DTO
    public static PatientResponseDTO toDto(Patient patient) {

        PatientResponseDTO patientDTO = new PatientResponseDTO();

        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setDateOfBirth(patient.getDate_of_birth().toString());
        patientDTO.setRegistered_date(patient.getRegistered_date());

        return patientDTO;
    }

    // Request DTO -> Entity
    public static Patient toModel(PatientRequestDTO patientRequestDTO) {

        Patient patient = new Patient();

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDate_of_birth(patientRequestDTO.getDate_of_birth());
        patient.setRegistered_date(patientRequestDTO.getRegistered_date());

        return patient;
    }
}