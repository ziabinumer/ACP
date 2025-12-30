package gui.search;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import services.PatientService;
import services.DoctorService;
import models.Patient;
import models.Doctor;
import enums.Sex;

import java.awt.*;
import java.util.List;

public class SearchDashPanel extends JPanel {

    private JComboBox<String> searchTypeCombo;
    private JTextField searchField;
    private JButton searchBtn;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    private PatientService patientService;
    private DoctorService doctorService;

    public SearchDashPanel(String selected) {
        patientService = new PatientService();
        doctorService = new DoctorService();

        setLayout(new BorderLayout(10, 10));

        // --- Top Panel for Search Input ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        searchTypeCombo = new JComboBox<>(new String[] {
                "Patient by Name",
                "Patient by ID",
                "Patient by Age",
                "Patient by Disease",
                "Patient by Doctor",
                "Doctor by Name",
                "Doctor by Disease Specialization"
        });
        

        searchField = new JTextField(20);
        searchBtn = new JButton("Search");

        searchBtn.addActionListener(e -> performSearch());

        topPanel.add(new JLabel("Search Type:"));
        topPanel.add(searchTypeCombo);
        topPanel.add(new JLabel("Search Term:"));
        topPanel.add(searchField);
        topPanel.add(searchBtn);

        add(topPanel, BorderLayout.NORTH);

        // --- Table to display results ---
        tableModel = new DefaultTableModel();
        resultTable = new JTable(tableModel);
        resultTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);
        if (selected != null) {
            searchTypeCombo.setSelectedItem(selected);
        }
    }

    private void performSearch() {
        String searchType = (String) searchTypeCombo.getSelectedItem();
        String term = searchField.getText().trim();

        if (term.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a search term.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (searchType) {
            case "Patient by Name":
                displayPatients(patientService.searchPatientsByName(term));
                break;
            case "Patient by ID":
                try {
                    int id = Integer.parseInt(term);
                    Patient p = patientService.getPatientById(id);
                    displayPatients(p != null ? List.of(p) : List.of());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a valid numeric ID.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Patient by Age":
                try {
                    int age = Integer.parseInt(term);
                    displayPatients(patientService.searchPatientsByAge(age));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a valid numeric age.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Patient by Disease":
                displayPatients(patientService.searchPatientsByDisease(term));
                break;
            case "Patient by Doctor":
                displayPatients(patientService.searchPatientsByDoctor(Integer.parseInt(term)));
                break;
            case "Doctor by Name":
                displayDoctors(doctorService.getDoctorByName(term));
                break;
            case "Doctor by Disease Specialization":
                displayDoctors(doctorService.getDoctorsBySpecialization(Integer.parseInt(term)));
                break;
            default:
                JOptionPane.showMessageDialog(this, "Unknown search type!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayPatients(List<Patient> patients) {
        String[] columns = {"ID", "Name", "Father Name", "Sex", "DOB", "Disease History", "Prescription", "Doctor ID"};
        tableModel.setDataVector(new Object[0][0], columns);

        for (Patient p : patients) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getFatherName(),
                    p.getSex(),
                    p.getDateOfBirth(),
                    p.getDiseaseHistory(),
                    p.getPrescription(),
                    p.getDoctorId()
            });
        }
    }

    private void displayDoctors(List<Doctor> doctors) {
        String[] columns = {"ID", "Name", "Disease ID"};
        tableModel.setDataVector(new Object[0][0], columns);

        for (Doctor d : doctors) {
            tableModel.addRow(new Object[]{
                    d.getId(),
                    d.getName(),
                    d.getdiseaseId()
            });
        }
    }
}
