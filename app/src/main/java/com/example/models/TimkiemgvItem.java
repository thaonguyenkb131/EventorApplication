package com.example.models;

public class TimkiemgvItem {
    private int imageResId;
    private String title;
    private String price;
    private String location;
    private String date;

    public TimkiemgvItem(int imageResId, String title, String price, String location, String date) {
        this.imageResId = imageResId;
        this.title = title;
        this.price = price;
        this.location = location;
        this.date = date;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

}
