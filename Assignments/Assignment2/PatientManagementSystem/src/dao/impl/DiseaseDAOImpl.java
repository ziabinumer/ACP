package dao.impl;

import models.Disease;
import dao.interfaces.DiseaseDAO;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.List;


public class DiseaseDAOImpl implements DiseaseDAO {
    @Override
    public int add(Disease disease) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sqlStatement = "INSERT INTO Disease (Disease_Name, Disease_Description) VALUES(?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, disease.getName());
            pstmt.setString(2, disease.getDescription());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected;
        } catch (SQLException e) {

        }

        return 1;
    }

    @Override
    public Disease findById(int id) {return null;}; 

    @Override
    public Disease findByName(String name) {return null;};

    @Override
    public List<Disease> getAll() {return null;};

    // Update
    @Override
    public boolean update(Disease disease) {return true;};

    // Delete
    @Override
    public boolean delete(int id) {return true;};
}