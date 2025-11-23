package models;

public class Doctor {
    Integer id;
    String name;
    Integer diseaseId;

    public Doctor() {
        this.id = null;
        this.name = null;
        this.diseaseId = null;
    }
    public Doctor(String name, Integer did) {
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