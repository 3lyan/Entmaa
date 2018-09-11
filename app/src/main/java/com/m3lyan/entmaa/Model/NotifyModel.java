package com.m3lyan.entmaa.Model;

public class NotifyModel {
    private String postBy;
    private String details;

    public NotifyModel(String postBy, String details) {
        this.postBy = postBy;
        this.details = details;
    }

    public String getPostBy() {
        return postBy;
    }

    public void setPostBy(String postBy) {
        this.postBy = postBy;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
