
package com.m3lyan.entmaa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TypesDetailsModel {

    @SerializedName("value")
    @Expose
    private Boolean value;
    @SerializedName("data")
    @Expose
    private TypesDataDetailsModel data;

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public TypesDataDetailsModel getData() {
        return data;
    }

    public void setData(TypesDataDetailsModel data) {
        this.data = data;
    }

}
