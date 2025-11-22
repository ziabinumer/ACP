package dao.interfaces;

import models.Patient;
import java.util.List;

public interface PatientDAO {
    int add(Patient patient); // Create

    // Retrieve
    Patient findById(int id); 
    Patient findByName(String name);
    List<Patient> getAll();

    List<Patient> findByDisease(String diseaseName);
    List<Patient> findByDoctor(int doctorId);

    // Update
    boolean update(Patient patient);

    // Delete
    boolean delete(int id);
}