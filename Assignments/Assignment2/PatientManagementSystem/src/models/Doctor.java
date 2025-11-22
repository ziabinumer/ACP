package models;

public class Doctor {
    Integer id;
    String name;
    Integer diseaseId;

    Doctor() {
        this.id = null;
        this.name = null;
        this.diseaseId = null;
    }
    Doctor(Integer id, String name, Integer did) {
        this.id = id;
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