package com.ajman.ded.ae.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tybe {

    @SerializedName("ResponseContent")
    @Expose
    private List<ResponseContent> responseContent = null;

    public List<ResponseContent> getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(List<ResponseContent> responseContent) {
        this.responseContent = responseContent;
    }

}
