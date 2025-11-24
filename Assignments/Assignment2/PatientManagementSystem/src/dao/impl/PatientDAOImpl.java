package dao.impl;

import models.Patient;
import dao.interfaces.PatientDAO;
import database.DatabaseConnection;
import enums.Sex;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements PatientDAO{
    // create
    @Override
    public int add(Patient patient) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "INSERT INTO Patient (Patient_Name, PF_Name, Sex, DOB, Doctor_ID, Disease_History, Prescription) " +
             "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            // set values
            pstmt.setString(1, patient.getName());
            pstmt.setString(2, patient.getFatherName());
            pstmt.setString(3, patient.getSex().name());
            pstmt.setString(4, patient.getDateOfBirth());  // String DOB
            pstmt.setInt(5, patient.getDoctorId());
            pstmt.setString(6, patient.getDiseaseHistory());
            pstmt.setString(7, patient.getPrescription());

            // execute statement
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);  // Return generated Patient_ID
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    } 

    // Retrieve by ID
    @Override
    public Patient findById(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "SELECT * FROM Patient WHERE Patient_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setInt(1, id);

            // execute 
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Patient patient = new Patient(
                    rs.getString("Patient_Name"),
                    rs.getString("PF_Name"),
                    Sex.valueOf(rs.getString("Sex")),
                    rs.getString("DOB"),  // String DOB
                    rs.getString("Disease_History"),
                    rs.getString("Prescription"),
                    rs.getInt("Doctor_ID")
                );
                patient.setId(rs.getInt("Patient_ID"));
                return patient;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Patient> findByName(String name) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "SELECT * FROM Patient WHERE Patient_Name LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setString(1, "%" + name + "%");

            List<Patient> list = new ArrayList<>();
            // execute 
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient(
                    rs.getString("Patient_Name"),
                    rs.getString("PF_Name"),
                    Sex.valueOf(rs.getString("Sex")),
                    rs.getString("DOB"),  // String DOB
                    rs.getString("Disease_History"),
                    rs.getString("Prescription"),
                    rs.getInt("Doctor_ID")
                );
                patient.setId(rs.getInt("Patient_ID"));
                list.add(patient);
            }
            return list;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    @Override
    public List<Patient> getAll() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "SELECT * FROM Patient ORDER BY Patient_Name";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);

            List<Patient> list = new ArrayList<>();
            // execute 
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient(
                    rs.getString("Patient_Name"),
                    rs.getString("PF_Name"),
                    Sex.valueOf(rs.getString("Sex")),
                    rs.getString("DOB"),  // String DOB
                    rs.getString("Disease_History"),
                    rs.getString("Prescription"),
                    rs.getInt("Doctor_ID")
                );
                patient.setId(rs.getInt("Patient_ID"));
                list.add(patient);
            }
            return list;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Patient> findByDisease(String diseaseName) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            // Search in Disease_History text field
            String sqlStatement = "SELECT * FROM Patient WHERE Disease_History LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setString(1, "%" + diseaseName + "%");

            List<Patient> list = new ArrayList<>();
            // execute 
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient(
                    rs.getString("Patient_Name"),
                    rs.getString("PF_Name"),
                    Sex.valueOf(rs.getString("Sex")),
                    rs.getString("DOB"),  // String DOB
                    rs.getString("Disease_History"),
                    rs.getString("Prescription"),
                    rs.getInt("Doctor_ID")
                );
                patient.setId(rs.getInt("Patient_ID"));
                list.add(patient);
            }
            return list;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Patient> findByDoctor(int doctorId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution with JOIN to get doctor details
            String sqlStatement = "SELECT p.*, d.Doctor_Name FROM Patient p " +
                "JOIN Doctor d ON p.Doctor_ID = d.Doctor_ID " +
                "WHERE p.Doctor_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setInt(1, doctorId);

            List<Patient> list = new ArrayList<>();
            // execute 
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient(
                    rs.getString("Patient_Name"),
                    rs.getString("PF_Name"),
                    Sex.valueOf(rs.getString("Sex")),
                    rs.getString("DOB"),  // String DOB
                    rs.getString("Disease_History"),
                    rs.getString("Prescription"),
                    rs.getInt("Doctor_ID")
                );
                patient.setId(rs.getInt("Patient_ID"));
                list.add(patient);
            }
            return list;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update
    @Override
    public boolean update(Patient patient) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            // Only Disease_History and Prescription should be updatable as per requirements
            String sqlStatement = "UPDATE Patient SET Disease_History = ?, Prescription = ? WHERE Patient_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);

            // set values
            pstmt.setString(1, patient.getDiseaseHistory());
            pstmt.setString(2, patient.getPrescription());
            pstmt.setInt(3, patient.getId());

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
            String sqlStatement = "DELETE FROM Patient WHERE Patient_ID = ?";
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