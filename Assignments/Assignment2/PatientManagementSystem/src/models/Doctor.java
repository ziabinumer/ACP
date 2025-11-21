package models;

public class Doctor {
    public static int idCounter = 0;
    Integer id;
    String name;
    Integer diseaseId;

    Doctor() {
        this.id = null;
        this.name = null;
        this.diseaseId = null;
    }
    Doctor(String name, Integer did) {
        this.id = idCounter++;
        this.name = name;
        this.diseaseId = did;
    }

    // getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getdiseaseId() {
        return diseaseId;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setdiseaseId(Integer did) {
        this.diseaseId = did;
    }
    
}