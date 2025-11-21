package models;

import enums.Sex;

public class Patient {
    public static Integer idCounter = 0;

    Integer id;
    String name, fatherName;
    Sex sex; 
    String dateOfBirth, diseaseHistory, prescription;
    Integer doctorId;

    Patient() {
        this.id = null;
        this.name = null;
        this.fatherName = null;
        this.sex = null;
        this.dateOfBirth = null;
        this.diseaseHistory = null;
        this.prescription = null;
        this.doctorId = null;
    }

    public Patient(String name, String fatherName, Sex sex, String dateOfBirth, String diseaseHistory, String prescription, Integer doctorId) {
        this.id = idCounter++;
        this.name = name;
        this.fatherName = fatherName;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.diseaseHistory = diseaseHistory;
        this.prescription = prescription;
        this.doctorId = doctorId;
    }

    // Getters

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public Sex getSex() {
        return sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDiseaseHistory() {
        return diseaseHistory;
    }

    public String getPrescription() {
        return prescription;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDiseaseHistory(String diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }
}