package services;

import models.Disease;
import utils.ValidationUtils;
import dao.interfaces.DiseaseDAO;
import dao.impl.DiseaseDAOImpl;
import logging.AppLogger;

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
        if (ValidationUtils.validateDisease(disease) == -1) return -1;
        
        // Check for duplicate disease name
        Disease existing = diseaseDAO.findByName(disease.getName()).get(0);
        if (existing != null) {
            AppLogger.error("Disease with this name already exists");
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
            AppLogger.error("Invalid disease ID");
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
            AppLogger.error("Invalid disease ID");
            return false;
        }
        
        if (disease.getName() == null || disease.getName().trim().isEmpty()) {
            AppLogger.error("Disease name cannot be empty");
            return false;
        }
        
        return diseaseDAO.update(disease);
    }
    
    public boolean deleteDisease(int id) {
        if (id <= 0) {
            AppLogger.error("Invalid disease ID");
            return false;
        }
        
        return diseaseDAO.delete(id);
    }
}