package dao.impl;

import models.Disease;
import dao.interfaces.DiseaseDAO;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;


public class DiseaseDAOImpl implements DiseaseDAO {
    @Override
    public int add(Disease disease) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "INSERT INTO Disease (Disease_Name, Disease_Description) VALUES(?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, disease.getName());
            pstmt.setString(2, disease.getDescription());

            // execute 
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public Disease findById(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "SELECT * FROM Disease WHERE Disease_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setInt(1, id);

            // execute 
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                String name = rs.getString("Disease_Name");
                String des = rs.getString("Disease_Description");
                Disease disease = new Disease(name, des);
                return disease;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

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