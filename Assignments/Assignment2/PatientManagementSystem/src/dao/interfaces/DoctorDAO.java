package dao.interfaces;

import models.Doctor;
import java.util.List;

public interface DoctorDAO {
    int add(Doctor doctor); // Create

    // Retrieve
    Doctor findById(int id); 
    List<Doctor> findByName(String name);
    List<Doctor> findBySpecialisation(int diseaseId);
    List<Doctor> getAll();

    // Update
    boolean update(Doctor doctor);

    // Delete
    boolean delete(int id);
}