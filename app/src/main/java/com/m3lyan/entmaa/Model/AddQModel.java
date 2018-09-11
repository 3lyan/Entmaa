package com.m3lyan.entmaa.Model;

public class AddQModel {
    private String question;
    private int pos;

    public AddQModel(String question, int pos) {
        this.question = question;
        this.pos = pos;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
