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
import java.util.ArrayList;


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
                Disease newDisease = new Disease(name, des);
                newDisease.setId(rs.getInt("Disease_ID"));
                return newDisease;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Disease findByName(String name) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "SELECT * FROM Disease WHERE Disease_Name Like ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setString(1, name);


            // execute 
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                String dName = rs.getString("Disease_Name");
                String des = rs.getString("Disease_Description");
                Disease disease = new Disease(dName, des);
                disease.setId(rs.getInt("Disease_ID"));
                return disease;
            }
            return null;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Disease> getAll() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "SELECT * FROM Disease";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);

            // list to store diseases
            List<Disease> list = new ArrayList<>();
            // execute 
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                String dName = rs.getString("Disease_Name");
                String des = rs.getString("Disease_Description");
                Disease disease = new Disease(dName, des);
                list.add(disease);
            }
            return list;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update
    @Override
    public boolean update(Disease disease) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "Update Disease SET Disease_Name = ?, Disease_Description = ? WHERE Disease_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);

            // set values
            pstmt.setString(1, disease.getName());
            pstmt.setString(2, disease.getDescription());
            pstmt.setInt(3, disease.getId());

            // execute update
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete
    @Override
    public boolean delete(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "delete from disease where Disease_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setInt(1, id);

           int rowsAffected = pstmt.executeUpdate();
           return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}