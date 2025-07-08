package com.example.models;

public class UserModel {
    public String lastname;
    public String name;
    public String email;
    public String phone;
    public String password;

    public UserModel() {}

    public UserModel(String lastname, String name, String email, String phone, String password) {
        this.lastname = lastname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
