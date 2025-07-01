package com.example.models;

public class BinhluanItem {
    private String name;
    private String comment;
    private int rating;
    private int avatarResId;

//    Contructor

    public BinhluanItem(String name, String comment, int rating, int avatarResId) {
        this.name = name;
        this.comment = comment;
        this.rating = rating;
        this.avatarResId = avatarResId;
    }

//    Getter and setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getAvatarResId() {
        return avatarResId;
    }

    public void setAvatarResId(int avatarResId) {
        this.avatarResId = avatarResId;
    }
}
