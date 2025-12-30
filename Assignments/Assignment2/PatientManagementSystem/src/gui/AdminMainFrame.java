package gui;

import javax.swing.*;
import javax.swing.border.Border;
import gui.dialogs.AboutUsDialog;
import java.awt.*;
import gui.forms.AddDiseaseForm;
import gui.forms.AddDoctorForm;
import gui.forms.AddPatientForm;
import gui.search.SearchDashPanel;

public class AdminMainFrame extends Layout {
    public AdminMainFrame() {
        super("Patient Management System: Admin");
        setSize(1000, 600);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    @Override
    protected JPanel createMenu() {
        JMenuBar menuBar = new JMenuBar();
         
        JMenu manageRecordMenu = new JMenu("Manage Record");
        JMenu searchRecordMenu =  new JMenu("Search Record");
        JMenu helpMenu =  new JMenu("Help");

        /*
            Manage Record Menu
            Add new Patient,
             Add New Doctor,
              Add New Disease, 
              Delete Patient Record 
              and Update Record
        */

        // create items
        JMenuItem addPatient = new JMenuItem("Add Patient");
        JMenuItem addDoctor = new JMenuItem("Add Doctor");
        JMenuItem addDisease = new JMenuItem("Add Disease");
        JMenuItem deletePatient = new JMenuItem("Delete Patient Record");
        JMenuItem updateRecord = new JMenuItem("Update Patient Record");

        // add listeners
        addPatient.addActionListener(e -> showAddPatientForm());
        addDoctor.addActionListener(e -> showAddDoctorForm());
        addDisease.addActionListener(e -> showAddDiseaseForm());
        deletePatient.addActionListener(e -> showAddPatientForm());
        updateRecord.addActionListener(e -> System.out.println("Update Patient Record clicked"));

        manageRecordMenu.add(addPatient);
        manageRecordMenu.add(addDoctor);
        manageRecordMenu.add(addDisease);
        manageRecordMenu.add(deletePatient);
        manageRecordMenu.add(updateRecord);


        /*
            Search Menu
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


        // add listeners
        searchByName.addActionListener(e -> showSearchPanel("Patient by Name"));
        searchByID.addActionListener(e -> showSearchPanel("Patient by ID"));
        searchByAge.addActionListener(e -> showSearchPanel("Patient by Age"));
        searchByDisease.addActionListener(e -> showSearchPanel("Patient by Disease"));
        searchByDoctor.addActionListener(e -> showSearchPanel("Patient by Doctor"));
        searchDoctorByName.addActionListener(e -> showSearchPanel("Doctor by Name"));
        searchDoctorBySpecialization.addActionListener(e -> showSearchPanel("Doctor by Disease Specialization"));

 

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

        // add listeners
        aboutUs.addActionListener(e -> showAboutDialog());
        changePassword.addActionListener(e -> System.out.println("Change Password clicked"));

        helpMenu.add(aboutUs);
        helpMenu.add(changePassword);


        // add to menu bar
        menuBar.add(manageRecordMenu);
        menuBar.add(searchRecordMenu);
        menuBar.add(helpMenu);


        // toolbar
        JToolBar toolBar = new JToolBar();
        
        
        // buttons
        JButton addPatientBtn = new JButton("Add Patient");
        JButton searchBtn = new JButton("Search");
        JButton addDoctorBtn = new JButton("Add Doctor");
        JButton printBtn = new JButton("Print");
        
        // Add listeners
        addPatientBtn.addActionListener(e -> showAddPatientForm());
        searchBtn.addActionListener(e -> showSearchPanel(null));
        addDoctorBtn.addActionListener(e -> showAddDoctorForm());
        printBtn.addActionListener(e -> System.out.println("Toolbar: Print clicked"));
        
        // Add spacing and buttons to toolbar
        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(addPatientBtn);
        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(searchBtn);
        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(addDoctorBtn);
        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(printBtn);

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.add(menuBar, BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(menuPanel, BorderLayout.NORTH);
        panel.add(toolBar, BorderLayout.SOUTH);
        return panel;
    }

    private void showAboutDialog() {
        new AboutUsDialog().dialog(this);;
    }

    private void showAddDiseaseForm() {
        showInContentArea(new AddDiseaseForm());
    }

    private void showAddDoctorForm() {
        showInContentArea(new AddDoctorForm());
    }

    private void showAddPatientForm() {
        showInContentArea(new AddPatientForm());
    }

    private void showSearchPanel(String selection) {
        showInContentArea(new SearchDashPanel(selection));
    }

    @Override
    protected void showInContentArea(JPanel panel) {
        contentArea.removeAll();
        contentArea.add(panel, BorderLayout.CENTER);
        contentArea.revalidate();         
        contentArea.repaint(); 
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