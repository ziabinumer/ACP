package services;

import models.Doctor;
import models.Disease;
import dao.interfaces.DoctorDAO;
import dao.impl.DoctorDAOImpl;

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
        // Validate doctor name
        if (doctor.getName() == null || doctor.getName().trim().isEmpty()) {
            System.out.println("Doctor name cannot be empty");
            return -1;
        }
        
        if (doctor.getName().length() > 100) {
            System.out.println("Doctor name is too long (max 100 characters)");
            return -1;
        }
        
        // Validate disease ID (specialization is basically diseaseId)
        if (doctor.getdiseaseId() <= 0) {
            System.out.println("Please select a valid specialization");
            return -1;
        }
        
        // Verify that the disease exists
        Disease disease = diseaseService.getDiseaseById(doctor.getdiseaseId());
        if (disease == null) {
            System.out.println("Selected specialization does not exist");
            return -1;
        }
        
        return doctorDAO.add(doctor);
    }
    
    public Doctor getDoctorById(int id) {
        if (id <= 0) {
            System.out.println("Invalid doctor ID");
            return null;
        }
        return doctorDAO.findById(id);
    }
    
    public Doctor getDoctorByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        return doctorDAO.findByName(name.trim()).get(0);
    }
    
    public List<Doctor> getAllDoctors() {
        return doctorDAO.getAll();
    }
    
    public List<Doctor> getDoctorsBySpecialization(int diseaseId) {
        if (diseaseId <= 0) {
            System.out.println("Invalid disease ID");
            return null;
        }
        return doctorDAO.findBySpecialisation(diseaseId);
    }

    public boolean updateDoctor(Doctor doctor) {
        if (doctor.getId() <= 0) {
            System.out.println("Invalid doctor ID");
            return false;
        }
        
        if (doctor.getName() == null || doctor.getName().trim().isEmpty()) {
            System.out.println("Doctor name cannot be empty");
            return false;
        }
        
        // Verify disease exists
        Disease disease = diseaseService.getDiseaseById(doctor.getdiseaseId());
        if (disease == null) {
            System.out.println("Selected specialization does not exist");
            return false;
        }
        
        return doctorDAO.update(doctor);
    }
    
    public boolean deleteDoctor(int id) {
        if (id <= 0) {
            System.out.println("Invalid doctor ID");
            return false;
        }
        
        return doctorDAO.delete(id);
    }
}