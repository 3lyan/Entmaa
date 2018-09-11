
package com.m3lyan.entmaa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInModel {

    @SerializedName("value")
    @Expose
    private Boolean value;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private SignInDataModel data;

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }

    public SignInDataModel getData() {
        return data;
    }

    public void setData(SignInDataModel data) {
        this.data = data;
    }

}
