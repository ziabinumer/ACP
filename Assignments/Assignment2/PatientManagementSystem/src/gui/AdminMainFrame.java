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
         
        JMenu menu = new JMenu("Add");

        JMenuItem addPatient = new JMenuItem("Add Patient");
        JMenuItem addDoctor = new JMenuItem("Add Patient");
        JMenuItem addDisease = new JMenuItem("Add Patient");

        menu.add(addPatient);
        menu.add(addDoctor);
        menu.add(addDisease);

        menuBar.add(menu);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(menuBar, BorderLayout.CENTER);
        return panel;
    }

    @Override
    protected void initializeContent() {
        JLabel welcomeLabel = new JLabel("Welcome");
        contentArea.add(welcomeLabel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminMainFrame());
    }
}