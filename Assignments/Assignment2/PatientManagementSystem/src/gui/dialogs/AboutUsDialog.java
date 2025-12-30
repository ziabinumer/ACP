package gui.dialogs;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AboutUsDialog {
    public void dialog(JFrame parent) {
        JOptionPane.showMessageDialog(parent, "Hospital Management System\nVersion 1.0\nDeveloped By Zia Gondal");
    }
}