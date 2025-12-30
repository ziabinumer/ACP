package gui.forms;

import javax.swing.*;
import services.DiseaseService;
import models.Disease;
import java.awt.*;

public class AddDiseaseForm extends JPanel {
    
    private JTextField nameField;
    private JTextArea descriptionArea;
    private DiseaseService service;
    
    public AddDiseaseForm() {
        service = new DiseaseService();
        setLayout(new BorderLayout(10, 10));
        JLabel titleLabel = new JLabel("Add New Disease", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Disease Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Disease Name:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);
        
        // Description
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Description:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        formPanel.add(scrollPane, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> saveDisease());
        cancelBtn.addActionListener(e -> clearForm());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void saveDisease() {
        String name = nameField.getText().trim();
        String description = descriptionArea.getText().trim();
        
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Disease name is required!", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Disease disease = new Disease(name, description);
        int id = service.addDisease(disease);
        
        if (id > 0) {
            JOptionPane.showMessageDialog(this, 
                "Disease added successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Failed to add disease. It may already exist.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    private void clearForm() {
        nameField.setText("");
        descriptionArea.setText("");
    }

}