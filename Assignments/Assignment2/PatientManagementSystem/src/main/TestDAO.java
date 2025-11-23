package main;

import dao.impl.*;
import enums.Sex;

import models.*;

public class TestDAO {
    public static void main(String[] args) {
        DiseaseDAOImpl dao = new DiseaseDAOImpl();
        Disease disease = new Disease("Cholera", "Some disease");

        int result = dao.add(disease);
        System.out.println("Insert returned: " + result);

        DoctorDAOImpl dao2 = new DoctorDAOImpl();
        Doctor d1 = new Doctor("Ali", 1);

        result = dao2.add(d1);
        System.out.println("Insert returned: " + result);

        PatientDAOImpl dao3 = new PatientDAOImpl();
        Patient p1 = new Patient("Mohammad", "Ali", Sex.MALE, "12-01-2000", "", "", 1);

        result = dao3.add(p1);

        System.out.println("Insert returned: " + result);
    }
}