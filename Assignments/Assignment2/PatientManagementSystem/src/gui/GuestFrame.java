package gui;

import gui.dialogs.AboutUsDialog;
import gui.search.SearchPanelGuest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GuestFrame extends JFrame {

    private JPanel contentContainer;

    public GuestFrame(String guestName) {
        setTitle("Patient Management System - Guest: " + guestName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // --- Menu Bar ---
        JMenuBar menuBar = new JMenuBar();

        // Search Record Menu
        JMenu searchMenu = new JMenu("Search Record");
        JMenuItem searchByName = new JMenuItem("Search by Name");
        JMenuItem searchByID = new JMenuItem("Search by ID");
        JMenuItem searchByAge = new JMenuItem("Search by Age");
        searchMenu.add(searchByName);
        searchMenu.add(searchByID);
        searchMenu.add(searchByAge);

        // Print Menu
        JMenu printMenu = new JMenu("Print");
        JMenuItem printItem = new JMenuItem("Print");
        printMenu.add(printItem);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About Us");
        JMenuItem changePassItem = new JMenuItem("Change Password");
        helpMenu.add(aboutItem);
        helpMenu.add(changePassItem);

        menuBar.add(searchMenu);
        menuBar.add(printMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        // --- Tool Bar ---
        JToolBar toolBar = new JToolBar();
        JButton searchBtn = new JButton("Search");
        JButton printBtn = new JButton("Print");
        toolBar.add(searchBtn);
        toolBar.add(printBtn);
        add(toolBar, BorderLayout.NORTH);

        // --- Content Area ---
        contentContainer = new JPanel(new BorderLayout());
        add(contentContainer, BorderLayout.CENTER);

        // --- Action Listeners ---
        ActionListener searchAction = e -> showSearchPanel(e.getActionCommand());
        searchByName.addActionListener(searchAction);
        searchByID.addActionListener(searchAction);
        searchByAge.addActionListener(searchAction);
        searchBtn.addActionListener(e -> showSearchPanel("Search by Name")); // default

        // Print button (you can implement actual printing later)
        printBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Print clicked"));

        aboutItem.addActionListener(e -> showAboutDialog());
        changePassItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Change Password clicked"));

        setVisible(true);
    }

    // --- Show Search Panel in content container ---
    private void showSearchPanel(String selection) {
        contentContainer.removeAll();
        SearchPanelGuest panel = null;

        switch (selection) {
            case "Search by Name":
                panel = new SearchPanelGuest("Patient by Name");
                break;
            case "Search by ID":
                panel = new SearchPanelGuest("Patient by ID");
                break;
            case "Search by Age":
                panel = new SearchPanelGuest("Patient by Age");
                break;
        }

        if (panel != null) {
            contentContainer.add(panel, BorderLayout.CENTER);
            contentContainer.revalidate();
            contentContainer.repaint();
        }
    }
    private void showAboutDialog() {
        new AboutUsDialog().dialog(this);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GuestFrame("Guest"));
    }
}
