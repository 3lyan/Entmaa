
package com.m3lyan.entmaa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReplyQDataModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("answer")
    @Expose
    private String answer;

    private int pos;

    public ReplyQDataModel(Integer id, String answer, int pos) {
        this.id = id;
        this.answer = answer;
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
