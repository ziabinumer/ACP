package models;

public class Disease {
    Integer id;
    String name;
    String description;

    public Disease() {
        this.id = null;
        this.name = null;
        this.description = null;
    }
    public Disease(String name, String des) {
        this.name = name;
        this.description = des;
    }

    // getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String des) {
        this.description = des;
    }
    
}