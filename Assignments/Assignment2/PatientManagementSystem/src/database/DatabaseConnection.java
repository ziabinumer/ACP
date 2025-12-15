package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:resources/config/hospital.db";

    // static {
    //     try {
    //         Class.forName("org.sqlite.JDBC"); // load driver
    //     } catch (ClassNotFoundException e) {
    //         e.printStackTrace();
    //     }
    // }

    // modern method. service provider auto loads the driver
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}