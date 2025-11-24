package dao.interfaces;

import models.Disease;
import java.util.List;

public interface DiseaseDAO {
    int add(Disease disease); // Create

    // Retrieve
    Disease findById(int id); 
    List<Disease> findByName(String name);
    List<Disease> getAll();

    // Update
    boolean update(Disease disease);

    // Delete
    boolean delete(int id);
}