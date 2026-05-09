package com.pm.patientservice.dto;

import jakarta.validation.constraints.*;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;

import java.time.LocalDate;

public class PatientRequestDTO {


    @NotBlank(message = "name is required")
    @Size(message = "name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message =  " email should be valid ")
    private String email;

    @NotNull(message = "addres is required")
    private  String address;

    @NotNull(message = "date of birth required")
    private LocalDate date_of_birth;


    @NotNull(message = "registartion date is required")
    private LocalDate registered_date;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public LocalDate getRegistered_date() {
        return registered_date;
    }

    public void setRegistered_date(LocalDate registered_date) {
        this.registered_date = registered_date;
    }
}
