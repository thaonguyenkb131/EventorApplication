package com.example.models;

public class ListtinnhanItem {
    int avatar;
    String sender;
    String message;
    String time;

    //Constructor

    public ListtinnhanItem(int avatar, String sender, String message, String time) {
        this.avatar = avatar;
        this.sender = sender;
        this.message = message;
        this.time = time;
    }

    //Getter and Setter

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
