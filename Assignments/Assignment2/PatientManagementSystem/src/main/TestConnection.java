package main;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Connection established successfully!");
            } else {
                System.out.println("❌ Connection failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
