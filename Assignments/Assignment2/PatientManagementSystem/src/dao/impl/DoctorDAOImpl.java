package dao.impl;

import models.Doctor;
import dao.interfaces.DoctorDAO;

import java.sql.*;
import database.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;

public class DoctorDAOImpl implements DoctorDAO {

    @Override
    public int add(Doctor doctor) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "INSERT INTO Doctor (Doctor_Name, Disease_ID) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, doctor.getName());
            pstmt.setInt(2, doctor.getdiseaseId());

            // execute statement
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);  // Return generated Doctor_ID
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    } 

    // Retrieve
    @Override
    public Doctor findById(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "SELECT * FROM Doctor WHERE Doctor_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setInt(1, id);

            // execute 
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Doctor newDoc = new Doctor(
                    rs.getString("Doctor_Name"), 
                    rs.getInt("Disease_ID")
                );
                newDoc.setId(rs.getInt("Doctor_ID"));
                return newDoc;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Doctor> findByName(String name) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "SELECT * FROM Doctor WHERE Doctor_Name Like ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setString(1, "%" + name + "%");

            // list to store diseases
            List<Doctor> list = new ArrayList<>();

            // execute 
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Doctor newDoc = new Doctor(
                    rs.getString("Doctor_Name"), 
                    rs.getInt("Disease_ID")
                );
                newDoc.setId(rs.getInt("Doctor_ID"));
                list.add(newDoc);
            }
            return list;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Doctor> findBySpecialisation(int diseaseId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "SELECT d.*, dis.Disease_Name FROM Doctor d " +
                     "JOIN Disease dis ON d.Disease_ID = dis.Disease_ID " +
                     "WHERE d.Disease_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setInt(1, diseaseId);

            // list to store diseases
            List<Doctor> list = new ArrayList<>();
            // execute 
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Doctor newDoc = new Doctor(
                    rs.getString("Doctor_Name"), 
                    rs.getInt("Disease_ID")
                );
                newDoc.setId(rs.getInt("Doctor_ID"));
                list.add(newDoc);
            }
            return list;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Doctor> getAll() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "SELECT * from Doctor";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);

            // list to store diseases
            List<Doctor> list = new ArrayList<>();
            // execute 
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Doctor newDoc = new Doctor(
                    rs.getString("Doctor_Name"), 
                    rs.getInt("Disease_ID")
                );
                newDoc.setId(rs.getInt("Doctor_ID"));
                list.add(newDoc);
            }
            return list;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update
    @Override
    public boolean update(Doctor doctor) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "Update Doctor SET Doctor_Name = ?, Disease_ID = ? WHERE Doctor_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);

            // set values
            pstmt.setString(1, doctor.getName());
            pstmt.setInt(2, doctor.getdiseaseId());
            pstmt.setInt(3, doctor.getId());

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
            String sqlStatement = "delete from Doctor WHERE Doctor_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);

            // set values
            pstmt.setInt(1, id);

            // execute update
            return pstmt.executeUpdate() > 0;
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}