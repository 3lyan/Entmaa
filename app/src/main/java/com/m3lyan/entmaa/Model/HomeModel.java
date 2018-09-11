
package com.m3lyan.entmaa.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeModel {

    @SerializedName("value")
    @Expose
    private Boolean value;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("data")
    @Expose
    private List<HomeDataModel> data = null;

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<HomeDataModel> getData() {
        return data;
    }

    public void setData(List<HomeDataModel> data) {
        this.data = data;
    }

}
