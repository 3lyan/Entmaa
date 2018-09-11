
package com.m3lyan.entmaa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpModel {

    @SerializedName("value")
    @Expose
    private Boolean value;
    @SerializedName("data")
    @Expose
    private SignUpDataModel data;

    @SerializedName("msg")
    @Expose
    private String msg;

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public SignUpDataModel getData() {
        return data;
    }

    public void setData(SignUpDataModel data) {
        this.data = data;
    }



}
