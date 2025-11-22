package models;

public class Disease {
    Integer id;
    String name;
    String description;

    Disease() {
        this.id = null;
        this.name = null;
        this.description = null;
    }
    Disease(Integer id, String name, String des) {
        this.id = id;
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