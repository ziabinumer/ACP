package models;

import enums.UserRole;

class User {
    public static Integer idCounter = 0;

    Integer id;
    String username, password;
    UserRole role;
User() {
        this.id = null;
        this.username = null;
        this.password = null;
        this.role = null;
    }

    public User(String username, String password, UserRole role) {
        this.id = idCounter++;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    // Setters

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}