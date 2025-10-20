package ACP.Client;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

import ACP.Employee.Employee;
import ACP.Employee.Employee.JobCategory;
import ACP.Employee.Employee.Education;

public class Client {
    final static int MAX_EMP = 50;
    static int EmpCounter = 0;

    private static int getChoice() {
        String[] options = {
        "Add New Employee",
        "Update Empployee Info",
        "Delete Employee Info",
        "Search"
        };
        return JOptionPane.showOptionDialog(
            null,
            "What do you want to do? ",
            "",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
            );
    }

    private static void showSearchDialog() {
        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(25);
        JTextField ageField = new JTextField(3);
        JComboBox<JobCategory> jobBox = new JComboBox<>(JobCategory.values());

        addDisable(idField, nameField, ageField);
        addDisable(nameField, idField, ageField);
        addDisable(ageField, idField, nameField);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Enter ID: "));
        panel.add(idField);

        panel.add(new JLabel("Enter name: "));
        panel.add(nameField);

        panel.add(new JLabel("Enter age: "));
        panel.add(ageField);

        panel.add(new JLabel("Select Job: "));
        panel.add(jobBox);

        int result = JOptionPane.showConfirmDialog(
            null,
            panel,
            "Search Employee",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

    }

    private static void addDisable(JTextField main, JTextField... others) {
        main.getDocument().addDocumentListener(new DocumentListener(){
            private void update() {
                boolean hasValue = !main.getText().trim().isEmpty();
                for (JTextField other : others) {
                    other.setEnabled(!hasValue);
                }
            }
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }
        });
    }

    private static Employee getEmployee(Employee em) {
        String name, fname, nic;
        name = fname = nic = "";
        int pscale = 0;
        LocalDate dob = null;
        JobCategory job = null; Education edu = null;
        if (em != null) {
            name = em.getName();
            fname = em.getFatherName();
            nic = em.getNIC();
            pscale = em.getPayScale();
            job = em.getJobCategory();
            edu = em.getEducation();
            dob = em.getDOB();
        }
        JTextField nameField = new JTextField(name, 25);
        JTextField fnameField = new JTextField(fname, 25);
        JTextField nicField = new JTextField(nic, 13);
        JTextField dobField = new JTextField(
            dob != null ? dob.toString() : "", 
            13);
        JTextField pscaleField = new JTextField(pscale != 0 ? Integer.toString(pscale) : "", 5);

        JComboBox<JobCategory> jobBox = new JComboBox<>(JobCategory.values());
        if (job != null) {
            jobBox.setSelectedItem(job);
        }
        JComboBox<Education> eduBox = new JComboBox<>(Education.values());
        if (eduBox != null) {
            eduBox.setSelectedItem(edu);
        }

        if (em != null) {
            nameField.setEditable(false);
            fnameField.setEditable(false);
            nicField.setEditable(false);
            dobField.setEditable(false);
        }

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Name: "));
        panel.add(nameField);
        
        panel.add(new JLabel("Father Name: "));
        panel.add(fnameField);

        panel.add(new JLabel("NIC: "));
        panel.add(nicField);

        panel.add(new JLabel("DOB: "));
        panel.add(dobField);

        panel.add(new JLabel("Pay Scale: "));
        panel.add(pscaleField);

        panel.add(new JLabel("Job Category: "));
        panel.add(jobBox);

        panel.add(new Label("Education: "));
        panel.add(eduBox);

        int result = JOptionPane.showConfirmDialog(null, panel, 
                    "Enter Employee Details", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
        try {
            String newName = nameField.getText().trim();
            String newFather = fnameField.getText().trim();
            String newNic = nicField.getText().trim();
            LocalDate newDob = LocalDate.parse(dobField.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE);
            int newpScale = Integer.parseInt(pscaleField.getText().trim());
            JobCategory newJob = (JobCategory) jobBox.getSelectedItem();
            Education newEdu = (Education) eduBox.getSelectedItem();

            return new Employee(newName, newFather, newDob, newpScale, newNic, newJob, newEdu);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Invalid input: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        }
        return null;
    }

    private static Employee findEmpById(Employee[] all, int id) {
        if (EmpCounter == 0) return null;
        for (Employee emp : all) {
            if (emp.getEmpID() == id) {
                return emp;
            }
        }
        return null;
    }

    private static Employee[] findEmpByName(Employee[] all, String name) {
        Employee[] foundEmps = new Employee[MAX_EMP]; int c = 0;
        
        for (int i = 0; i < EmpCounter; i++) {
            if (all[i].getName().equalsIgnoreCase(name)) {
                foundEmps[c++] = all[i];
            }
        }
        return foundEmps;
    }

    private static Employee[] findEmpByAge(Employee[] all, int age) {
        Employee[] foundEmps = new Employee[MAX_EMP]; int c = 0;
        
        for (int i = 0; i < EmpCounter; i++) {
            if (Period.between(all[i].getDOB(), LocalDate.now()).getYears() == age) {
                foundEmps[c++] = all[i];
            }
        }
        return foundEmps;
    }

    private static Employee[] findEmpByJobCategory(Employee[] all, JobCategory jc) {
        Employee[] foundEmps = new Employee[MAX_EMP]; int c = 0;
        
        for (int i = 0; i < EmpCounter; i++) {
            if (all[i].getJobCategory() == jc) {
                foundEmps[c++] = all[i];
            }
        }
        return foundEmps;
    }
    public static void main(String[] args) {
        Employee[] employees = new Employee[MAX_EMP];
        
        while (true) {
            int choice = getChoice();

            if (choice == JOptionPane.CLOSED_OPTION) {
            JOptionPane.showMessageDialog(null, "You didnt select anything. Qutting Program");
            return;
            }   
        
            System.out.println(choice);
            switch(choice) {
            
                case 0:
                    if (EmpCounter >= MAX_EMP) {
                        JOptionPane.showMessageDialog(null, "Employee limit exceeded");
                        break;
                    }
                    employees[EmpCounter++] = getEmployee(null);
                    break;
                case 1:
                    employees[0] = getEmployee(employees[0]);
                    JOptionPane.showMessageDialog(null, "Updated successfully");
                    break;
                case 2:
                    break;
                case 3:
                    showSearchDialog();
                    break;
                    
            }
        }
    

    }
}
