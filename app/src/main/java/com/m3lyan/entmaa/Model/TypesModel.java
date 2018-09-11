
package com.m3lyan.entmaa.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TypesModel {

    @SerializedName("value")
    @Expose
    private Boolean value;
    @SerializedName("data")
    @Expose
    private List<TypesDataModel> data = null;

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public List<TypesDataModel> getData() {
        return data;
    }

    public void setData(List<TypesDataModel> data) {
        this.data = data;
    }

}
