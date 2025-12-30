package services;

import models.Patient;
import models.Doctor;
import dao.interfaces.PatientDAO;
import dao.impl.PatientDAOImpl;
import utils.DateUtils;
import utils.ValidationUtils;

import java.util.List;

import logging.AppLogger;

public class PatientService {
    private PatientDAO patientDAO;
    private DoctorService doctorService;
    
    public PatientService() {
        this.patientDAO = new PatientDAOImpl();
        this.doctorService = new DoctorService();
    }
    
    /*
      Adds a new patient with validation
    */
    public int addPatient(Patient patient) {
        
        // Validate
        if (ValidationUtils.validatePatient(patient) == -1) {
            return -1;
        }
        
        // Verify doctor exists
        Doctor doctor = doctorService.getDoctorById(patient.getDoctorId());
        if (doctor == null) {
            AppLogger.error("Selected doctor does not exist");
            return -1;
        }
        
        // Disease history and prescription can be empty initially
        if (patient.getDiseaseHistory() != null && patient.getDiseaseHistory().length() > 1000) {
            AppLogger.error("Disease history is too long (max 1000 characters)");
            return -1;
        }
        
        if (patient.getPrescription() != null && patient.getPrescription().length() > 1000) {
            AppLogger.error("Prescription is too long (max 1000 characters)");
            return -1;
        }
        
        return patientDAO.add(patient);
    }
    
    public Patient getPatientById(int id) {
        if (id <= 0) {
            AppLogger.error("Invalid patient ID");
            return null;
        }
        return patientDAO.findById(id);
    }
    
    public List<Patient> searchPatientsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            AppLogger.error("Please enter a name to search");
            return null;
        }
        return patientDAO.findByName(name.trim());
    }
    public List<Patient> searchPatientsByAge(int age) {
        if (age < 0 || age > 150) {
            AppLogger.error("Please enter a valid age (0-150)");
            return null;
        }
        return patientDAO.findByAge(age);
    }
    
    public List<Patient> searchPatientsByDisease(String diseaseName) {
        if (diseaseName == null || diseaseName.trim().isEmpty()) {
            AppLogger.error("Please enter a disease name to search");
            return null;
        }
        return patientDAO.findByDisease(diseaseName.trim());
    }
    
    public List<Patient> searchPatientsByDoctor(int doctorId) {
        if (doctorId <= 0) {
            AppLogger.error("Invalid doctor ID");
            return null;
        }
        
        // Verify doctor exists
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) {
            AppLogger.error("Doctor does not exist");
            return null;
        }
        
        return patientDAO.findByDoctor(doctorId);
    }

    public List<Patient> getAllPatients() {
        return patientDAO.getAll();
    }

    public boolean updatePatient(Patient patient) {
        if (patient.getId() <= 0) {
            AppLogger.error("Invalid patient ID");
            return false;
        }
        
        // Verify patient exists
        Patient existing = patientDAO.findById(patient.getId());
        if (existing == null) {
            AppLogger.error("Patient does not exist");
            return false;
        }
        
        // Validate updated fields
        if (ValidationUtils.validatePatientUpdateFields(patient) == -1) {return false;}
        
        return patientDAO.update(patient);
    }
    
    public boolean deletePatient(int id) {
        if (id <= 0) {
            AppLogger.error("Invalid patient ID");
            return false;
        }
        
        // Verify patient exists
        Patient patient = patientDAO.findById(id);
        if (patient == null) {
            AppLogger.error("Patient does not exist");
            return false;
        }
        
        return patientDAO.delete(id);
    }
    
    // calculate current age of patient
    public int calculatePatientAge(Patient patient) {
        if (patient == null || patient.getDateOfBirth() == null) {
            return -1;
        }
        return DateUtils.calculateAge(patient.getDateOfBirth());
    }
}