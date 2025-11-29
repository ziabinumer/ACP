package main;

import javax.swing.SwingUtilities;

import database.DatabaseManager;
import gui.LoginFrame;
import services.AuthenticationService;


class Main {
    public static void main(String[] args) {
        // initiliaze the db
        DatabaseManager.start();

        // setup users if not already
        AuthenticationService authService = new AuthenticationService();
        authService.createDefaultUsers();
       
        // login window
        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}