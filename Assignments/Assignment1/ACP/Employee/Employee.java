package ACP.Employee;
import java.time.LocalDate;

public class Employee {
    static int counter = 9000;
    String name;
    String fatherName;
    int empID;
    LocalDate dob;
    int payScale;
    String nic;

    public enum JobCategory {
        Teacher, Officer, Staff, Labour
    }

    public enum Education {
        Matric, FSC, BS, MS, PHD
    }

    private JobCategory jobCategory;
    private Education education;

    public Employee() {
        this.empID = counter++;
        this.name = this.fatherName = this.nic = "";
        this.dob = null;
        this.payScale = 0;
        this.jobCategory = null;
        this.education = null;
    }

    public Employee(
        String name, String fatherName, LocalDate dob,
        int scale, String nic, JobCategory jc, Education edu
    ) {
        this.name = name;
        this.fatherName = fatherName;
        this.dob = dob;
        this.payScale = scale;
        this.nic = nic;
        this.jobCategory = jc;
        this.education = edu;
    }

    // Getter and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }


    public LocalDate getDOB() {
        return dob;
    }

    public void setDOB(LocalDate dob) {
        this.dob = dob;
    }

    public int getPayScale() {
        return payScale;
    }

    public void setPayScale(int payScale) {
        this.payScale = payScale;
    }

    public String getNIC() {
        return nic;
    }

    public void setNIC(String nic) {
        this.nic = nic;
    }

    public JobCategory getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(JobCategory jobCategory) {
        this.jobCategory = jobCategory;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public void updateEmployee(Employee newEmp) {
        this.payScale = newEmp.payScale;
        this.education = newEmp.education;
        this.jobCategory = newEmp.jobCategory;
    }
}
