package com.ajman.ded.ae.models.notification.tybe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseContent {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("NameAR")
    @Expose
    private String nameAR;
    @SerializedName("NameEN")
    @Expose
    private String nameEN;
    @SerializedName("DescriptionAR")
    @Expose
    private String descriptionAR;
    @SerializedName("DescriptionEN")
    @Expose
    private String descriptionEN;
    @SerializedName("DepartmentId")
    @Expose
    private String departmentId;
    @SerializedName("DepartmentNameAR")
    @Expose
    private String departmentNameAR;
    @SerializedName("DepartmentNameEN")
    @Expose
    private String departmentNameEN;
    @SerializedName("NotificationStatusId")
    @Expose
    private String notificationStatusId;
    @SerializedName("NotificationStatusNameAR")
    @Expose
    private String notificationStatusNameAR;
    @SerializedName("NotificationStatusNameEN")
    @Expose
    private String notificationStatusNameEN;
    @SerializedName("PeriodInDays")
    @Expose
    private String periodInDays;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getNameAR() {
        return nameAR;
    }

    public void setNameAR(String nameAR) {
        this.nameAR = nameAR;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public String getDescriptionAR() {
        return descriptionAR;
    }

    public void setDescriptionAR(String descriptionAR) {
        this.descriptionAR = descriptionAR;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentNameAR() {
        return departmentNameAR;
    }

    public void setDepartmentNameAR(String departmentNameAR) {
        this.departmentNameAR = departmentNameAR;
    }

    public String getDepartmentNameEN() {
        return departmentNameEN;
    }

    public void setDepartmentNameEN(String departmentNameEN) {
        this.departmentNameEN = departmentNameEN;
    }

    public String getNotificationStatusId() {
        return notificationStatusId;
    }

    public void setNotificationStatusId(String notificationStatusId) {
        this.notificationStatusId = notificationStatusId;
    }

    public String getNotificationStatusNameAR() {
        return notificationStatusNameAR;
    }

    public void setNotificationStatusNameAR(String notificationStatusNameAR) {
        this.notificationStatusNameAR = notificationStatusNameAR;
    }

    public String getNotificationStatusNameEN() {
        return notificationStatusNameEN;
    }

    public void setNotificationStatusNameEN(String notificationStatusNameEN) {
        this.notificationStatusNameEN = notificationStatusNameEN;
    }

    public String getPeriodInDays() {
        return periodInDays;
    }

    public void setPeriodInDays(String periodInDays) {
        this.periodInDays = periodInDays;
    }

}
