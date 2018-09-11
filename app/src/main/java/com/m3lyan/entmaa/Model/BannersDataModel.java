
package com.m3lyan.entmaa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannersDataModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("text")
    @Expose
    private String text;

    public BannersDataModel(Integer id, String link, String image, String text) {
        this.id = id;
        this.link = link;
        this.image = image;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
