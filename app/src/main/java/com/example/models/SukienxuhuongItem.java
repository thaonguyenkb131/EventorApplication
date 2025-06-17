package com.example.models;

public class SukienxuhuongItem {
    private int posterResId;
    private String ten;
    private String daBan;
    private String gia;
    private String diaDiem;
    private String top;

    public SukienxuhuongItem(int posterResId, String ten, String daBan, String gia, String diaDiem, String top) {
        this.posterResId = posterResId;
        this.ten = ten;
        this.daBan = daBan;
        this.gia = gia;
        this.diaDiem = diaDiem;
        this.top = top;
    }

    public int getPosterResId() {
        return posterResId;
    }

    public String getTen() {
        return ten;
    }

    public String getDaBan() {
        return daBan;
    }

    public String getGia() {
        return gia;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public String getTop() {
        return top;
    }
}
