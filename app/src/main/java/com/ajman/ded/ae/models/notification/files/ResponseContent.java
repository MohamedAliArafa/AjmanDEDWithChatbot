
package com.ajman.ded.ae.models.notification.files;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseContent {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("FileId")
    @Expose
    private String fileId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

}
