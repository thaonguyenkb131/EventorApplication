package com.example.models;

public class UserModel {
    public String Lastname;
    public String Name;
    public String Email;
    public String Phone;
    public String Password;

    public UserModel() {}

    public UserModel(String lastname, String name, String email, String phone, String password) {
        Lastname = lastname;
        Name = name;
        Email = email;
        Phone = phone;
        Password = password;
    }
}
