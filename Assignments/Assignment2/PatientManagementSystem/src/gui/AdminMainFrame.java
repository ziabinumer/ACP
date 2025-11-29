package gui;

import javax.swing.*;
import java.awt.GridLayout;

public class AdminMainFrame {
    JFrame frame;
    public AdminMainFrame() {
        frame = new JFrame("Admin Dashboard");
        frame.setTitle("Patient Management System: Login");
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new AdminMainFrame();
    }
}
