
package com.m3lyan.entmaa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionsDataModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("post")
    @Expose
    private String post;
    private int pos;

    public QuestionsDataModel(Integer id, String post,int pos) {
        this.id = id;
        this.post = post;
        this.pos = pos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
