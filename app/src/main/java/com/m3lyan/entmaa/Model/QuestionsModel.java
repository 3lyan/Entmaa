
package com.m3lyan.entmaa.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionsModel {

    @SerializedName("value")
    @Expose
    private Boolean value;
    @SerializedName("data")
    @Expose
    private List<QuestionsDataModel> data = null;

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public List<QuestionsDataModel> getData() {
        return data;
    }

    public void setData(List<QuestionsDataModel> data) {
        this.data = data;
    }

}
