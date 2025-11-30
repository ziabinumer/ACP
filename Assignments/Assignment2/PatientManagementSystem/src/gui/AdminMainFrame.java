package gui;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class AdminMainFrame extends Layout {
    public AdminMainFrame() {
        super("Patient Management System: Admin");
        setSize(1000, 600);
        setVisible(true);
    }

    @Override
    protected JPanel createMenu() {
        /*
            Add Patient - Add Doctor
        */
        JMenuBar menuBar = new JMenuBar();
         
        JMenu manageRecordMenu = new JMenu("Manage Record");
        JMenu searchRecordMenu =  new JMenu("Search Record");
        JMenu helpMenu =  new JMenu("Help");

        /*
            Add new Patient,
             Add New Doctor,
              Add New Disease, 
              Delete Patient Record 
              and Update Record
        */

        JMenuItem addPatient = new JMenuItem("Add Patient");
        JMenuItem addDoctor = new JMenuItem("Add Patient");
        JMenuItem addDisease = new JMenuItem("Add Patient");
        JMenuItem deletePatient = new JMenuItem("Delete Patient Record");
        JMenuItem updateRecord = new JMenuItem("Update Patient Record");

        manageRecordMenu.add(addPatient);
        manageRecordMenu.add(addDoctor);
        manageRecordMenu.add(addDisease);
        manageRecordMenu.add(deletePatient);
        manageRecordMenu.add(updateRecord);


        /*
            search Patient by Name, Search Patient by ID, Search Patient by age, Search Patient by Disease, 
            Search Patient by Doctor, Search Doctor by Name and Search Doctor by Disease Specialization
        */

        JMenuItem searchByName = new JMenuItem("Search Patient by Name");
        JMenuItem searchByID = new JMenuItem("Search Patient by ID");
        JMenuItem searchByAge = new JMenuItem("Search Patient by Age");
        JMenuItem searchByDisease = new JMenuItem("Search Patient by Disease");
        JMenuItem searchByDoctor = new JMenuItem("Search Patient by Doctor");
        JMenuItem searchDoctorByName = new JMenuItem("Search Doctor by Name");
        JMenuItem searchDoctorBySpecialization = new JMenuItem("Search Doctor by Disease Specialization");

        // Add items to the menu
        searchRecordMenu.add(searchByName);
        searchRecordMenu.add(searchByID);
        searchRecordMenu.add(searchByAge);
        searchRecordMenu.add(searchByDisease);
        searchRecordMenu.add(searchByDoctor);
        searchRecordMenu.add(searchDoctorByName);
        searchRecordMenu.add(searchDoctorBySpecialization);


        /*
            Help Menu
        */

        JMenuItem aboutUs = new JMenuItem("About Us");
        JMenuItem changePassword = new JMenuItem("Change Password");

        helpMenu.add(aboutUs);
        helpMenu.add(changePassword);


        // add to menu bar
        menuBar.add(manageRecordMenu);
        menuBar.add(searchRecordMenu);
        menuBar.add(helpMenu);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(menuBar, BorderLayout.CENTER);
        return panel;
    }

    @Override
    protected void initializeContent() {
        JLabel welcomeLabel = new JLabel("Welcome", SwingConstants.CENTER);
        contentArea.add(welcomeLabel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminMainFrame());
    }
}