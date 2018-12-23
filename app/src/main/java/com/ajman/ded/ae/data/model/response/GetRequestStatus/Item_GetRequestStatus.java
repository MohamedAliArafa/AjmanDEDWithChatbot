package com.ajman.ded.ae.data.model.response.GetRequestStatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item_GetRequestStatus implements Serializable {

    @SerializedName("LastUpdate")
    @Expose
    private String lastUpdate;
    @SerializedName("TaskTitle")
    @Expose
    private String taskTitle;
    @SerializedName("TaskStatus")
    @Expose
    private String taskStatus;

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
