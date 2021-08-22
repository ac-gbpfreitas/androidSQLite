package com.example.sqlitegustavofreitas_300309391.model;

public class UserContact {

    private String name;
    private String contact;
    private String dateOfBirth;

    public UserContact(String name, String contact, String dateOfBirth) {
        this.name = name;
        this.contact = contact;
        this.dateOfBirth = dateOfBirth;
    }

    public UserContact() {
        this.name = "";
        this.contact = "";
        this.dateOfBirth = "";
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
