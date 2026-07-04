package com.pm.patientservice.service;

import com.pm.patientservice.Exceptionhandler.PatientNotFoundException;
import com.pm.patientservice.billing.grpc.BillingResponse;
import com.pm.patientservice.client.BillingServiceClient;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.kafka.PatientEventProducer;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private static final Logger log =
            LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;
    private final BillingServiceClient billingServiceClient;
    private final PatientEventProducer patientEventProducer;

    public PatientService(
            PatientRepository patientRepository,
            BillingServiceClient billingServiceClient,
            PatientEventProducer patientEventProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceClient = billingServiceClient;
        this.patientEventProducer = patientEventProducer;
    }

    public List<PatientResponseDTO> getPatients() {

        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
                .map(PatientMapper::toDto)
                .toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

        Patient patient = PatientMapper.toModel(patientRequestDTO);

        Patient savedPatient = patientRepository.save(patient);

        BillingResponse billingResponse =
                billingServiceClient.createBillingAccount(
                        savedPatient.getId().toString(),
                        savedPatient.getName(),
                        savedPatient.getEmail());

        log.info("Created billing account {} with status {} for patient {}",
                billingResponse.getAccountId(),
                billingResponse.getStatus(),
                savedPatient.getId());

        patientEventProducer.sendPatientCreatedEvent(savedPatient);

        return PatientMapper.toDto(savedPatient);
    }

    public PatientResponseDTO updatePatient(
            UUID id,
            PatientRequestDTO patientRequestDTO) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new PatientNotFoundException(
                                "Patient not found with id: " + id));

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDate_of_birth(
                patientRequestDTO.getDate_of_birth());

        Patient updatedPatient =
                patientRepository.save(patient);

        return PatientMapper.toDto(updatedPatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
