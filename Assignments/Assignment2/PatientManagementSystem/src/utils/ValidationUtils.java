package utils;

import logging.AppLogger;
import models.Patient;

import logging.AppLogger;

public class ValidationUtils {
    public static int validatePatient(Patient patient) {

        if (patient.getName() == null || patient.getName().trim().isEmpty()) {
            AppLogger.error("Patient name cannot be empty");
            return -1;
        }
        
        if (patient.getName().length() > 100) {
            AppLogger.error("Patient name is too long (max 100 characters)");
            return -1;
        }
        
        if (patient.getFatherName() == null || patient.getFatherName().trim().isEmpty()) {
            AppLogger.error("Father name cannot be empty");
            return -1;
        }
        
        if (patient.getSex() == null) {
            AppLogger.error("Please select gender");
            return -1;
        }
        
        if (patient.getDateOfBirth() == null || patient.getDateOfBirth().trim().isEmpty()) {
            AppLogger.error("Date of birth cannot be empty");
            return -1;
        }
        
        if (!DateUtils.isValidDate(patient.getDateOfBirth())) {
            AppLogger.error("Invalid date format. Use YYYY-MM-DD");
            return -1;
        }
        
        int age = DateUtils.calculateAge(patient.getDateOfBirth());
        if (age < 0 || age > 150) {
            AppLogger.error("Invalid date of birth. Age must be between 0-150 years");
            return -1;
        }
        
        if (patient.getDoctorId() <= 0) {
            AppLogger.error("Please select a doctor");
            return -1;
        }

        return 1;
    }

    public static int validatePatientUpdateFields(Patient patient) {
        if (patient.getDiseaseHistory() != null && patient.getDiseaseHistory().length() > 1000) {
            AppLogger.error("Disease history is too long (max 1000 characters)");
            return -1;
        }
        
        if (patient.getPrescription() != null && patient.getPrescription().length() > 1000) {
            AppLogger.error("Prescription is too long (max 1000 characters)");
            return -1;
        }
        return 1;
    }
};