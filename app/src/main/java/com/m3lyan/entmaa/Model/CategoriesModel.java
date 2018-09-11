package com.m3lyan.entmaa.Model;

public class CategoriesModel {
    private String img;
    private String txtTitle;

    public CategoriesModel(String img, String txtTitle) {
        this.img = img;
        this.txtTitle = txtTitle;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTxtTitle() {
        return txtTitle;
    }

    public void setTxtTitle(String txtTitle) {
        this.txtTitle = txtTitle;
    }
}
