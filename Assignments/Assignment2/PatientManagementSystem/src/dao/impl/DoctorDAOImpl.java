package dao.impl;

import models.Doctor;
import dao.interfaces.DoctorDAO;

import java.sql.*;
import database.DatabaseConnection;

import java.util.List;

class DoctorDAOImpl implements DoctorDAO {

    @Override
    public int add(Doctor doctor) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "INSERT INTO Doctor (Doctor_Name, Disease_ID) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setString(1, doctor.getName());
            pstmt.setInt(2, doctor.getdiseaseId());

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
    public Doctor findById(int id) {

        return null;
    }

    @Override
    public Doctor findByName(String name) {

        return null;
    }

    @Override
    public List<Doctor> findBySpecialisation(int diseaseId) {

        return null;
    }

    @Override
    public List<Doctor> getAll() {

        return null;
    }

    // Update
    @Override
    public boolean update(Doctor doctor) {

        return true;
    }

    // Delete
    @Override
    public boolean delete(int id) {
        return true;
    }
};