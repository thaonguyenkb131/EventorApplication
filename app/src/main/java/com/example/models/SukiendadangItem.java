package com.example.models;

public class SukiendadangItem {
    private int hinhAnh;
    private String ten, daBan, gia, diaDiem;

    public SukiendadangItem(int hinhAnh, String ten, String daBan, String gia, String diaDiem) {
        this.hinhAnh = hinhAnh;
        this.ten = ten;
        this.daBan = daBan;
        this.gia = gia;
        this.diaDiem = diaDiem;
    }

    public int getHinhAnh() { return hinhAnh; }
    public String getTen() { return ten; }
    public String getDaBan() { return daBan; }
    public String getGia() { return gia; }
    public String getDiaDiem() { return diaDiem; }
}
