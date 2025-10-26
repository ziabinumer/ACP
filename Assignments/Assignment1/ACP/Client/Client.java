package ACP.Client;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

import ACP.Employee.Employee;
import ACP.Employee.Employee.JobCategory;
import ACP.Employee.Employee.Education;

public class Client {
    final int MAX_EMP = 50;
    int EmpCounter = 0;

    Employee[] employees = new Employee[MAX_EMP];

    private int getChoice() {
        String[] options = {
        "Add New Employee",
        "Update Empployee Info",
        "Delete Employee Info",
        "Show All Employees",
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

    private void showSearchDialog() {
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

        if (result == JOptionPane.OK_OPTION) {
            String idText = idField.getText().trim();
            String nameText = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            JobCategory job = (JobCategory) jobBox.getSelectedItem();

            if (!idText.isEmpty()) {
                try {
                    Employee emp = findEmpById(Integer.parseInt(idText));
                    if (emp != null) {
                        showData(emp, "Found employee with id: " + Integer.toString(emp.getEmpID()));
                        return;
                    }
                    JOptionPane.showMessageDialog(null, "ID \"" + idText + "\" not found");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ID can only be a number");
                }

                return;
            }
            else if (!nameText.isEmpty()) {
                Employee[] emps = findEmpByName(nameText);
                int len = countLength();
                if (len == 0) {
                    JOptionPane.showMessageDialog(null, "Found 0 matching employees");
                    return;
                }
                showData();
                return;
            }
            else if (!ageText.isEmpty()) {
                try {
                    Employee[] emps = findEmpByAge(Integer.parseInt(ageText));
                    int len = countLength();
                    if (len == 0) {
                        JOptionPane.showMessageDialog(null, "Found 0 matching employees");
                        return;
                    }
                    showData();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Age can only be a number");
                }
            }
            else if (job != null) {
                Employee[] emps = findEmpByJobCategory(job);
                int len = countLength();
                if (len == 0) {
                    JOptionPane.showMessageDialog(null, "No employees found in job category: " + job);
                    return;
                }
                showData();
                return;
            } 
            else {
                JOptionPane.showMessageDialog(null, "Please enter or select at least one search criteria.");
            }
            
        }
    }

    private void showData(Employee emp, String message) {
        if (emp == null) return;
        
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel(message)); panel.add(new JLabel(""));

        panel.add(new JLabel("<html><b>Personal Information</b></html>"));
        panel.add(new JLabel("")); 

        panel.add(new JLabel("Name:"));
        panel.add(new JLabel(emp.getName()));

        panel.add(new JLabel("Father Name:"));
        panel.add(new JLabel(emp.getFatherName()));

        panel.add(new JLabel("DOB:"));
        panel.add(new JLabel(emp.getDOB().toString()));

        panel.add(new JLabel("NIC:"));
        panel.add(new JLabel(emp.getNIC()));

        panel.add(new JLabel("<html><b>Professional Information</b></html>"));
        panel.add(new JLabel(""));

        panel.add(new JLabel("Education:"));
        panel.add(new JLabel(emp.getEducation().toString()));

        panel.add(new JLabel("Profession:"));
        panel.add(new JLabel(emp.getJobCategory().toString()));

        panel.add(new JLabel("Pay Scale:"));
        panel.add(new JLabel(Integer.toString(emp.getPayScale())));

        String[] options = {"Next / Ok", "Cancel"};
        int result = JOptionPane.showOptionDialog(
                    null,
                    panel,
                    "Search Employee",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
                );



    }
    private void showData() {
        int len = countLength();
    if (employees == null || len == 0) {
        JOptionPane.showMessageDialog(null, "No matching employees found.");
        return;
    }

    for (int i = 0; i < len; i++) {
        showData(employees[i], "Employee " + (i + 1) + " of " + len);
    }
}

    private void addDisable(JTextField main, JTextField... others) {
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

    private Employee getEmployee(Employee em) {
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
            try {
                int payScale = Integer.parseInt(pscaleField.getText().trim());
                if (payScale <= 0) {
                    JOptionPane.showMessageDialog(null, "Pay scale must be positive!");
                    return null;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Pay scale must be a number!");
                return null;
            }
            JobCategory newJob = (JobCategory) jobBox.getSelectedItem();
            Education newEdu = (Education) eduBox.getSelectedItem();

            if (newName.isEmpty() || newFather.isEmpty() || newNic.isEmpty() || dobField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Personal detail is required");
                return null;
            }


            if (findEmpIdByNIC(newNic) != -1) {
                JOptionPane.showMessageDialog(null, "NIC already present");
                return null;
            }
            

            

            return new Employee(newName, newFather, newDob, newpScale, newNic, newJob, newEdu);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Invalid input: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        }
        return null;
    }

    private int countLength() {
        int i;
        for (i = 0; i < MAX_EMP; i++) {
            if (employees[i] == null) break;
        }
        return i;
    }

    private Employee findEmpById(int id) {
        if (EmpCounter == 0) return null;
        for (Employee emp : employees) {
            if (emp.getEmpID() == id) {
                return emp;
            }
        }
        return null;
    }

    private int findEmpIdByNIC(String nic) {
        if (EmpCounter == 0) return -1;
        for (Employee emp : employees) {
            if (emp == null) return -1;
            if (emp.getNIC() == nic) {
                return emp.getEmpID();
            }
        }
        return -1;
    }

    private Employee[] findEmpByName(String name) {
        Employee[] foundEmps = new Employee[MAX_EMP]; int c = 0;
        
        for (int i = 0; i < EmpCounter; i++) {
            if (employees[i].getName().equalsIgnoreCase(name)) {
                foundEmps[c++] = employees[i];
            }
        }
        return foundEmps;
    }

    private Employee[] findEmpByAge(int age) {
        Employee[] foundEmps = new Employee[MAX_EMP]; int c = 0;
        
        for (int i = 0; i < EmpCounter; i++) {
            if (Period.between(employees[i].getDOB(), LocalDate.now()).getYears() == age) {
                foundEmps[c++] = employees[i];
            }
        }
        return foundEmps;
    }

    private Employee[] findEmpByJobCategory(JobCategory jc) {
        Employee[] foundEmps = new Employee[MAX_EMP]; int c = 0;
        
        for (int i = 0; i < EmpCounter; i++) {
            if (employees[i].getJobCategory() == jc) {
                foundEmps[c++] = employees[i];
            }
        }
        return foundEmps;
    }

    private int findIndexById(int id) {
        int index = 0;
        for (Employee employee : employees) {
            if (employee.getEmpID() == id) return index;
            index++;
        }
        return -1;
    }


    private Boolean deleteEmployee(int id) {
        int index = findIndexById(id);
        for (int i = index; i < MAX_EMP - 1; i++) {
            employees[i] = employees[i + 1];
        }
        employees[--EmpCounter] = null;
        return true;
    }

    private void saveAllToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employees.dat"))) {
            for (Employee emp : employees) {
                out.writeObject(emp);
            }
            JOptionPane.showMessageDialog(null, "Data saved successfully to employees.dat!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error saving file: ");
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("employees.dat"))) {
            int i = 0;
            while (true) {
                employees[i++] = (Employee) in.readObject();
                EmpCounter = i;
            }
        } catch (EOFException e) { // file ends (its a normal behavior)
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading file: " + e.getMessage());
        }
    }

    private Boolean checkValidity(Employee emp) {
        if (!emp.isAgeValid()) {
            JOptionPane.showMessageDialog(null, "Age must be minimum 18");
            return false;
        }
        else if (!emp.isProfessionValid()) {
            JOptionPane.showMessageDialog(null, "Education or PayScale not valid");
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        // initialize the client class
        Client app = new Client();

        // load data
        app.loadFromFile();
        app.EmpCounter = app.countLength();

        // app interface
        int choiceFlag = 0;
        int choice = 0;
        while (true) {
            if (choiceFlag != 1) {
                choice = app.getChoice();
            }
            else choiceFlag = 0;
            

            if (choice == JOptionPane.CLOSED_OPTION) {
            JOptionPane.showMessageDialog(null, "Qutting Program");
            return;
            }   
        
            System.out.println(choice);
            switch(choice) {
            
                case 0:
                    if (app.EmpCounter >= app.MAX_EMP) {
                        JOptionPane.showMessageDialog(null, "Employee limit exceeded");
                        break;
                    }
                    Employee newEmp = app.getEmployee(null);
                    if (!app.checkValidity(newEmp)) {
                        choice = 0; choiceFlag = 1;
                        break;
                    }
                    if (newEmp != null) {
                        
                        app.employees[app.EmpCounter++] = newEmp;
                        app.saveAllToFile();
                        System.out.println("added employee with id " + Integer.toString(app.employees[app.EmpCounter - 1].getEmpID()));
                    } 
                    else System.out.println("Could not add");
                    break;
                case 1:
                    String toUpdateId = JOptionPane.showInputDialog("Enter employee id to search: ");
                    try {
                        int id = Integer.parseInt(toUpdateId);
                        Employee emp = app.findEmpById(id);
                        if (emp == null) {
                            JOptionPane.showMessageDialog(null, "Employee not found.");
                        } else {
                            Employee updated = app.getEmployee(emp);
                            if (!app.checkValidity(updated)) {
                                choice = 1; choiceFlag = 1;
                                break;
                            }
                            if (updated != null) {
                                app.employees[app.findIndexById(id)] = updated;
                                JOptionPane.showMessageDialog(null, "Updated successfully.");
                            }
                    }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Invalid ID input or missing data.");
                    }
                    break;
                case 2:
                    String toDeleteId = JOptionPane.showInputDialog("Enter employee id to delete: ");
                    try {
                        int id = Integer.parseInt(toDeleteId);
                        if (app.deleteEmployee(id)) {
                            JOptionPane.showMessageDialog(null, "Deleted employee with id " + id);
                        }
                        else JOptionPane.showMessageDialog(null, "Id was not found");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Invalid ID input or missing data.");
                    }
                    break;
                case 3:
                    app.showData();
                    break;
                case 4:
                    app.showSearchDialog();
                    break;
                    
            }
        }
    

    }
}
