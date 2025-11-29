package gui;

import javax.swing.*;
import java.awt.*;
import enums.UserRole;
import logging.AppLogger;
import services.AuthenticationService;
import models.User;

public class LoginFrame {
    JFrame frame;
    public LoginFrame() {
        AuthenticationService authService = new AuthenticationService();

        frame = new JFrame();
        frame.setTitle("Patient Management System: Login");
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel outerPanel = new JPanel(new GridBagLayout());

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

         JLabel titleLabel = new JLabel("Patient Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        innerPanel.add(titleLabel, 0);
        innerPanel.add(Box.createVerticalStrut(20));

        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JTextField usernameField = new JTextField(20);
        usernameField.setMaximumSize(usernameField.getPreferredSize());

        usernamePanel.add(new JLabel("Username: "));
        usernamePanel.add(usernameField);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(passwordField.getPreferredSize());

        passwordPanel.add(new JLabel("Password: "));
        passwordPanel.add(passwordField);

        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JRadioButton adminRadioBtn = new JRadioButton(UserRole.ADMINISTRATOR.toString());
        JRadioButton guestRadioBtn = new JRadioButton(UserRole.GUEST.toString());

        ButtonGroup group = new ButtonGroup();
        group.add(adminRadioBtn);
        group.add(guestRadioBtn);

        rolePanel.add(new JLabel("Role: "));
        rolePanel.add(adminRadioBtn);
        rolePanel.add(guestRadioBtn);

        JLabel message = new JLabel();
        message.setVisible(false);
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel actionPanel = new JPanel();

        JButton loginBtn = new JButton("Login");
        JButton exitBtn = new JButton("Exit");

        actionPanel.add(loginBtn);
        actionPanel.add(exitBtn);

        innerPanel.add(usernamePanel);
        innerPanel.add(passwordPanel);
        innerPanel.add(rolePanel);
        innerPanel.add(Box.createVerticalStrut(10));
        innerPanel.add(message);
        innerPanel.add(Box.createVerticalStrut(10));
        innerPanel.add(actionPanel);

        outerPanel.add(innerPanel);
        frame.add(outerPanel);

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            UserRole role;
            if (adminRadioBtn.isSelected()) role = UserRole.ADMINISTRATOR;
            else if (guestRadioBtn.isSelected()) role = UserRole.GUEST;
            else role = null;

            User user = authService.login(username, password, role);
            if (user == null) {
                message.setText("User not found. Please try again");
                message.setVisible(true);
                usernameField.setText("");
                passwordField.setText("");
                usernameField.requestFocus();
            }
            else if (user.getRole() == UserRole.ADMINISTRATOR) {
                frame.dispose();
                new AdminMainFrame();
            }
            else if (user.getRole() == UserRole.GUEST) {
                frame.dispose();
                new GuestMainFrame();
            }
        });

        exitBtn.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
