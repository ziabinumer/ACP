package services;

import models.Disease;
import dao.interfaces.DiseaseDAO;
import dao.impl.DiseaseDAOImpl;

import java.util.List;

public class DiseaseService {
    private DiseaseDAO diseaseDAO;
    
    public DiseaseService() {
        this.diseaseDAO = new DiseaseDAOImpl();
    }
    
    /*
        function:
            add disease to database
        Requirements:
            Disease name validation (cant be empty OR too long)
            Duplication (No two diseases may have same name)
            Description can not be too long (500 char limit)
    */
    public int addDisease(Disease disease) {
        // Validate disease name
        if (disease.getName() == null || disease.getName().trim().isEmpty()) {
            System.out.println("Disease name cannot be empty");
            return -1;
        }
        
        if (disease.getName().length() > 100) {
            System.out.println("Disease name is too long (max 100 characters)");
            return -1;
        }
        
        // Check for duplicate disease name
        Disease existing = diseaseDAO.findByName(disease.getName()).get(0);
        if (existing != null) {
            System.out.println("Disease with this name already exists");
            return -1;
        }
        
        // Validate description
        if (disease.getDescription() != null && disease.getDescription().length() > 500) {
            System.out.println("Description is too long (max 500 characters)");
            return -1;
        }
        
        return diseaseDAO.add(disease);
    }
    
    /*
        function:
            returns disease with given id fby calling diseaseDAO method
    */
    public Disease getDiseaseById(int id) {
        if (id <= 0) {
            System.out.println("Invalid disease ID");
            return null;
        }
        return diseaseDAO.findById(id);
    }
    
    public Disease getDiseaseByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        return diseaseDAO.findByName(name.trim()).get(0);
    }
    
    public List<Disease> getAllDiseases() {
        return diseaseDAO.getAll();
    }
    
    public boolean updateDisease(Disease disease) {
        if (disease.getId() <= 0) {
            System.out.println("Invalid disease ID");
            return false;
        }
        
        if (disease.getName() == null || disease.getName().trim().isEmpty()) {
            System.out.println("Disease name cannot be empty");
            return false;
        }
        
        return diseaseDAO.update(disease);
    }
    
    public boolean deleteDisease(int id) {
        if (id <= 0) {
            System.out.println("Invalid disease ID");
            return false;
        }
        
        return diseaseDAO.delete(id);
    }
}