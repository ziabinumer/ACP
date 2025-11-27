package services;

import models.User;
import database.DatabaseConnection;
import enums.UserRole;

import java.sql.*;

import logging.AppLogger;

public class AuthenticationService {

    // validate
    public User login(String username, String password, UserRole role) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return null;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            // prepare statement for execution
            String sqlStatement = "SELECT * FROM User WHERE username = ? AND password = ? AND role = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, username.trim());
            pstmt.setString(2, password);
            pstmt.setString(3, role.name());

            // execute 
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getString("Username"), rs.getString("password");
                                UserRole.valueOf(rs.getString("role")));
                                user.setId(rs.getInt("UserID"));
                return user;
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        
        return null;
    }

    // change password
    public boolean changePassword(String username, String oldPass, String newPass) {
        if (username == null || oldPass == null || newPass == null) {
        return false;
        }
    
        if (newPass.length() < 4) {
            AppLogger.error("Password must be at least 4 characters long");
            return false;
        }

        // to verify pass from db query
        String verifyPassSql = "Select * from User where Username = ? and Password = ?";

        // to update pass query
        String updatePassSql = "update User set Password = ? where Username = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement verifyStmt = conn.prepareStatement(verifyPassSql);
            verifyStmt.setString(1, username);
            verifyStmt.setString(2, oldPass);

            PreparedStatement updateStmt = conn.prepareStatement(updatePassSql);
            

            if (!verifyStmt.executeQuery().next()) {
                return false;
            }

            // update
            updateStmt.setString(1, newPass);
            updateStmt.setString(2, username);

            return updateStmt.executeUpdate() > 0;

        } catch (SQLException e) {e.printStackTrace();}
        return false; 
    }

    public void createDefaultUsers() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if admin exists
            String checkSql = "SELECT COUNT(*) FROM User WHERE role = 'ADMINISTRATOR'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(checkSql);
            
            if (rs.next() && rs.getInt(1) == 0) {
                // Create default admin
                String insertSql = "INSERT INTO User (username, password, role) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(insertSql);
                
                pstmt.setString(1, "lokamu");
                pstmt.setString(2, "lokamu123");
                pstmt.setString(3, UserRole.ADMINISTRATOR.name());
                pstmt.executeUpdate();
                
                AppLogger.info("Default admin created: username=admin, password=admin123");
            }
            
            // Check if guest exists
            checkSql = "SELECT COUNT(*) FROM User WHERE role = 'GUEST'";
            rs = stmt.executeQuery(checkSql);
            
            if (rs.next() && rs.getInt(1) == 0) {
                // Create default guest
                String insertSql = "INSERT INTO User (username, password, role) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(insertSql);
                
                pstmt.setString(1, "guest");
                pstmt.setString(2, "guest123");
                pstmt.setString(3, UserRole.GUEST.name());
                pstmt.executeUpdate();
                
                AppLogger.info("Default guest created: username=guest, password=guest123");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}