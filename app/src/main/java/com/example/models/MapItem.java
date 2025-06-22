package com.example.models;

public class MapItem {
    String txtTendiadiem;
    String txtDiachi;

    //Constructor

    public MapItem(String txtTendiadiem, String txtDiachi) {
        this.txtTendiadiem = txtTendiadiem;
        this.txtDiachi = txtDiachi;
    }

    //Getter & Setter

    public String getTxtTendiadiem() {
        return txtTendiadiem;
    }

    public void setTxtTendiadiem(String txtTendiadiem) {
        this.txtTendiadiem = txtTendiadiem;
    }

    public String getTxtDiachi() {
        return txtDiachi;
    }

    public void setTxtDiachi(String txtDiachi) {
        this.txtDiachi = txtDiachi;
    }
}
