package gui.forms;

import javax.swing.*;
import services.PatientService;
import services.DoctorService;
import models.Patient;
import models.Doctor;
import enums.Sex;
import java.awt.*;
import java.util.List;

public class AddPatientForm extends JPanel {

    private JTextField nameField;
    private JTextField fatherNameField;
    private JComboBox<Sex> sexCombo;
    private JTextField dobField;
    private JTextArea diseaseHistoryArea;
    private JTextArea prescriptionArea;
    private JComboBox<Doctor> doctorCombo;

    private PatientService patientService;
    private DoctorService doctorService;

    public AddPatientForm() {
        patientService = new PatientService();
        doctorService = new DoctorService();

        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Add New Patient", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Patient Name:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // Father Name
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Father Name:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        fatherNameField = new JTextField(20);
        formPanel.add(fatherNameField, gbc);

        // Sex
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Sex:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        sexCombo = new JComboBox<>(Sex.values());
        formPanel.add(sexCombo, gbc);

        // Date of Birth
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        dobField = new JTextField(15);
        formPanel.add(dobField, gbc);

        // Disease History
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Disease History:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.3;
        diseaseHistoryArea = new JTextArea(3, 20);
        diseaseHistoryArea.setLineWrap(true);
        diseaseHistoryArea.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(diseaseHistoryArea), gbc);

        // Prescription
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0;
        formPanel.add(new JLabel("Prescription:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.3;
        prescriptionArea = new JTextArea(3, 20);
        prescriptionArea.setLineWrap(true);
        prescriptionArea.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(prescriptionArea), gbc);

        // Doctor Selection
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0;
        formPanel.add(new JLabel("Doctor:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        doctorCombo = new JComboBox<>();
        populateDoctors();
        formPanel.add(doctorCombo, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> savePatient());
        cancelBtn.addActionListener(e -> clearForm());

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void populateDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        doctorCombo.removeAllItems();
        for (Doctor d : doctors) {
            doctorCombo.addItem(d);
        }
        doctorCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Doctor) {
                    setText(((Doctor) value).getName());
                }
                return this;
            }
        });
    }

    private void savePatient() {
        String name = nameField.getText().trim();
        String fatherName = fatherNameField.getText().trim();
        Sex sex = (Sex) sexCombo.getSelectedItem();
        String dob = dobField.getText().trim();
        String history = diseaseHistoryArea.getText().trim();
        String prescription = prescriptionArea.getText().trim();
        Doctor doctor = (Doctor) doctorCombo.getSelectedItem();

        if (name.isEmpty() || fatherName.isEmpty() || sex == null || dob.isEmpty() || doctor == null) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all required fields!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Patient patient = new Patient(name, fatherName, sex, dob, history, prescription, doctor.getId());
        int id = patientService.addPatient(patient);

        if (id > 0) {
            JOptionPane.showMessageDialog(this,
                    "Patient added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to add patient.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        nameField.setText("");
        fatherNameField.setText("");
        sexCombo.setSelectedIndex(0);
        dobField.setText("");
        diseaseHistoryArea.setText("");
        prescriptionArea.setText("");
        if (doctorCombo.getItemCount() > 0)
            doctorCombo.setSelectedIndex(0);
    }
}
