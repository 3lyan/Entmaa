
package com.m3lyan.entmaa.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReplyQModel {

    @SerializedName("value")
    @Expose
    private Boolean value;
    @SerializedName("data")
    @Expose
    private List<ReplyQDataModel> data = null;

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public List<ReplyQDataModel> getData() {
        return data;
    }

    public void setData(List<ReplyQDataModel> data) {
        this.data = data;
    }

}
