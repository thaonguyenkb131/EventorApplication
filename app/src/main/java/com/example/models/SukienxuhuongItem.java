package com.example.models;

public class SukienxuhuongItem {
    private int posterResId;
    private String ten;
    private String daBan;
    private String gia;
    private String diaDiem;

    public SukienxuhuongItem(int posterResId, String ten, String daBan, String gia, String diaDiem) {
        this.posterResId = posterResId;
        this.ten = ten;
        this.daBan = daBan;
        this.gia = gia;
        this.diaDiem = diaDiem;
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


}
