package gui.forms;

import javax.swing.*;
import services.DoctorService;
import services.DiseaseService;
import models.Doctor;
import models.Disease;
import java.awt.*;
import java.util.List;

public class AddDoctorForm extends JPanel {

    private JTextField nameField;
    private JComboBox<Disease> diseaseCombo;
    private DoctorService doctorService;
    private DiseaseService diseaseService;

    public AddDoctorForm() {
        doctorService = new DoctorService();
        diseaseService = new DiseaseService();

        setLayout(new BorderLayout(10, 10));
        JLabel titleLabel = new JLabel("Add New Doctor", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Doctor Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Doctor Name:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // Disease Selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Specialization:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        diseaseCombo = new JComboBox<>();
        populateDiseases();
        formPanel.add(diseaseCombo, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> saveDoctor());
        cancelBtn.addActionListener(e -> clearForm());

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void populateDiseases() {
        List<Disease> diseases = diseaseService.getAllDiseases();
        diseaseCombo.removeAllItems();
        
        if (diseases == null || diseases.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No diseases found. Please add a disease first.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        for (Disease d : diseases) {
            diseaseCombo.addItem(d);
        }
        
        // above shows something like models.address 
        // to get proper name we need to do this
        diseaseCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Disease) {
                    setText(((Disease) value).getName());
                }
                return this;
            }
        });
    }

    private void saveDoctor() {
        String name = nameField.getText().trim();
        Disease selectedDisease = (Disease) diseaseCombo.getSelectedItem();
        selectedDisease.setId(diseaseService.getDiseaseByName(selectedDisease.getName()).getId());

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Doctor name is required!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (selectedDisease == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a specialization! Add a disease first if none exist.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (selectedDisease.getId() == null || selectedDisease.getId() <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Invalid disease selected. Please try again.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Doctor doctor = new Doctor(name, selectedDisease.getId());
        int id = doctorService.addDoctor(doctor);

        if (id > 0) {
            JOptionPane.showMessageDialog(this,
                    "Doctor added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to add doctor. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        nameField.setText("");
        if (diseaseCombo.getItemCount() > 0)
            diseaseCombo.setSelectedIndex(0);
    }
}