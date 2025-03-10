package com.qa.model;

public class UserModel {
    private int id;
    private String name;
    private String email;

    public UserModel(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User ID: " + id + ", Name: " + name + ", Email: " + email;
    }
}
