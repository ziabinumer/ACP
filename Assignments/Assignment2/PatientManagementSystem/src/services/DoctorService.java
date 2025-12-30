package services;

import models.Doctor;
import utils.ValidationUtils;
import models.Disease;
import dao.interfaces.DoctorDAO;
import dao.impl.DoctorDAOImpl;
import logging.AppLogger;

import java.util.List;

public class DoctorService {
    private DoctorDAO doctorDAO;
    private DiseaseService diseaseService;
    
    public DoctorService() {
        this.doctorDAO = new DoctorDAOImpl();
        this.diseaseService = new DiseaseService();
    }
    
    /*
        Adds a new doctor with validation to db
    */
    public int addDoctor(Doctor doctor) {
        // validate doctor
        if (ValidationUtils.validateDoctor(doctor) == -1) return -1;
        
        // Verify that the disease exists
        Disease disease = diseaseService.getDiseaseById(doctor.getdiseaseId());
        if (disease == null) {
            AppLogger.error("Selected specialization does not exist");
            return -1;
        }
        
        return doctorDAO.add(doctor);
    }
    
    public Doctor getDoctorById(int id) {
        if (id <= 0) {
            AppLogger.error("Invalid doctor ID");
            return null;
        }
        return doctorDAO.findById(id);
    }
    
    public List<Doctor> getDoctorByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        return doctorDAO.findByName(name.trim());
    }
    
    public List<Doctor> getAllDoctors() {
        return doctorDAO.getAll();
    }
    
    public List<Doctor> getDoctorsBySpecialization(int diseaseId) {
        if (diseaseId <= 0) {
            AppLogger.error("Invalid disease ID");
            return null;
        }
        return doctorDAO.findBySpecialisation(diseaseId);
    }

    // allow name and disease updation
    public boolean updateDoctor(Doctor doctor) {
        if (ValidationUtils.validateDoctor(doctor) == -1) return false;

        // Verify disease exists
        Disease disease = diseaseService.getDiseaseById(doctor.getdiseaseId());
        if (disease == null) {
            AppLogger.error("Selected specialization does not exist");
            return false;
        }
        
        return doctorDAO.update(doctor);
    }
    
    public boolean deleteDoctor(int id) {
        if (id <= 0) {
            AppLogger.error("Invalid doctor ID");
            return false;
        }
        
        return doctorDAO.delete(id);
    }
}