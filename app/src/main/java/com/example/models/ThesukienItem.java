package com.example.models;

public class ThesukienItem {
    public int imageResId;
    public String title;
    public String price;
    public String location;
    public String date;

//    Contructor

    public ThesukienItem(int imageResId, String title, String price, String location, String date) {
        this.imageResId = imageResId;
        this.title = title;
        this.price = price;
        this.location = location;
        this.date = date;
    }

//    Getter and Setter

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
