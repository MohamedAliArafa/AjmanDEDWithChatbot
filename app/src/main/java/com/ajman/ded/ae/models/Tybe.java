
package com.ajman.ded.ae.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
