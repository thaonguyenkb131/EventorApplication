package com.example.models;

public class KetquatimkiemItem {
    private int image;
    private String title;
    private String price;
    private String location;
    private String date;

    public KetquatimkiemItem(int image, String title, String price, String location, String date) {
        this.image = image;
        this.title = title;
        this.price = price;
        this.location = location;
        this.date = date;
    }

    public int getImage() {
        return image; }
    public String getTitle() {
        return title; }
    public String getPrice() {
        return price; }
    public String getLocation() {
        return location; }
    public String getDate() {
        return date; }

}
