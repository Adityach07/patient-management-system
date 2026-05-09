package com.pm.patientservice.dto;

import java.time.LocalDate;

public class PatientResponseDTO {

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getRegistered_date() {
        return registered_date;
    }

    public void setRegistered_date(LocalDate registered_date) {
        this.registered_date = registered_date;
    }

    private String name;
    private  String address;
    private  String dateOfBirth;
    private  String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private  String gender;
    private LocalDate registered_date;



}
