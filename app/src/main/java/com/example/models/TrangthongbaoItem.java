package com.example.models;

public class TrangthongbaoItem {
    int imgThumb;
    String txtTieude;
    String txtThoigian;
    String txtNoidung;

    //Constructor

    public TrangthongbaoItem(int imgThumb, String txtTieude, String txtThoigian, String txtNoidung) {
        this.imgThumb = imgThumb;
        this.txtTieude = txtTieude;
        this.txtThoigian = txtThoigian;
        this.txtNoidung = txtNoidung;
    }

    //Setter and getter

    public int getImgThumb() {
        return imgThumb;
    }

    public void setImgThumb(int imgThumb) {
        this.imgThumb = imgThumb;
    }

    public String getTxtTieude() {
        return txtTieude;
    }

    public void setTxtTieude(String txtTieude) {
        this.txtTieude = txtTieude;
    }

    public String getTxtThoigian() {
        return txtThoigian;
    }

    public void setTxtThoigian(String txtThoigian) {
        this.txtThoigian = txtThoigian;
    }

    public String getTxtNoidung() {
        return txtNoidung;
    }

    public void setTxtNoidung(String txtNoidung) {
        this.txtNoidung = txtNoidung;
    }
}
