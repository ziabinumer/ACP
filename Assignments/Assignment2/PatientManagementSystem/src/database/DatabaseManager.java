package database;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import java.io.File;

import logging.AppLogger;


public class DatabaseManager {
    public static void start() {
        AppLogger.info("Attempting connection to db");
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                AppLogger.success("Connected to db");
                conn.createStatement().execute("PRAGMA foreign_keys = ON");

                Statement sqlStmt = conn.createStatement();

                String createDisease = """
                    Create Table if not exists Disease (
                        Disease_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                        Disease_Name TEXT NOT NULL, 
                        Disease_Description TEXT
                    );
                """;
                String createDoctor = """
                    CREATE TABLE IF NOT EXISTS Doctor (
                        Doctor_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
                        Doctor_Name TEXT NOT NULL, Disease_ID INTEGER, 
                        FOREIGN KEY(Disease_ID) 
                        REFERENCES Disease(Disease_ID)
                    )
                """;
                String createPatient = """
                        CREATE TABLE IF NOT EXISTS Patient (
                            PATIENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                            Patient_Name TEXT NOT NULL,
                            PF_Name TEXT,
                            Sex TEXT CHECK(Sex IN ('MALE', 'FEMALE')),
                            DOB TEXT,
                            Doctor_ID INTEGER, 
                            Disease_History TEXT,
                            Prescription TEXT,
                            FOREIGN KEY (Doctor_ID) REFERENCES Doctor(Doctor_ID)
                        )
                        """;

                String createUser = """
                        CREATE TABLE IF NOT EXISTS User (
                            UserID INTEGER PRIMARY KEY AUTOINCREMENT,
                            Username TEXT NOT NULL UNIQUE,
                            Password TEXT NOT NULL,
                            Role TEXT CHECK(Role IN ('ADMINISTRATOR', 'GUEST')) NOT NULL
                        )
                        """;

                sqlStmt.executeUpdate(createDisease);
                sqlStmt.executeUpdate(createDoctor);
                sqlStmt.executeUpdate(createPatient);
                sqlStmt.executeUpdate(createUser);

                AppLogger.success("Database ready for operations");

            }
            else {
                AppLogger.error("Couldnt estalish connection");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int deleteDBFile() {
        File dbFile = new File("resources/config/hospital.db");

        if (dbFile.exists()) {
            if (dbFile.delete()) {
                AppLogger.success("Deleted db file");
            } else {
                AppLogger.error("Failed to delete file");
                return -1;
            }
        } else {
            AppLogger.error("Db file doesnt exist");        
        }
    
        return 1;
    }
}