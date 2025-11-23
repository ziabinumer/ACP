package dao.impl;

import models.Patient;
import dao.interfaces.PatientDAO;
import database.DatabaseConnection;

import java.sql.*;

import java.util.List;

class PatientDAOImpl implements PatientDAO{
    // create
    @Override
    public int add(Patient patient) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "INSERT INTO Patient (Patient_Name, PF_Name, Sex, DOB, Doctor_ID, Disease_History, Prescription) " +
             "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);

            // set values
            pstmt.setString(1, patient.getName());
            pstmt.setString(2, patient.getFatherName());
            pstmt.setString(3, patient.getSex().name());
            pstmt.setInt(5, patient.getDoctorId());
            pstmt.setString(6, patient.getDiseaseHistory());
            pstmt.setString(7, patient.getPrescription());

            // execute statement
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    } 

    // Retrieve
    @Override
    public Patient findById(int id) {

        return null;
    }

    @Override
    public Patient findByName(String name) {

        return null;
    }
    
    @Override
    public List<Patient> getAll() {

        return null;
    }

    @Override
    public List<Patient> findByDisease(String diseaseName) {

        return null;
    }

    @Override
    public List<Patient> findByDoctor(int doctorId) {

        return null;
    }

    // Update
    @Override
    public boolean update(Patient patient) {

        return true;
    }

    // Delete
    @Override
    public boolean delete(int id) {
        return true;
    }
};