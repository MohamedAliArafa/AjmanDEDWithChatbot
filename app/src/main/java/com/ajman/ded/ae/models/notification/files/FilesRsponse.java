
package com.ajman.ded.ae.models.notification.files;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilesRsponse {

    private List<ResponseContent> responseContent = null;
    private Integer responseCode;
    private String responseDescription;
    private String errorMessage;

    public List<ResponseContent> getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(List<ResponseContent> responseContent) {
        this.responseContent = responseContent;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
