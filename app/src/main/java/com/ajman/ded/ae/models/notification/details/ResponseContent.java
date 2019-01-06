
package com.ajman.ded.ae.models.notification.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ResponseContent {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("EstablishmentNameAR")
    @Expose
    private String establishmentNameAR;
    @SerializedName("EstablishmentNameEN")
    @Expose
    private String establishmentNameEN;
    @SerializedName("RequestDate")
    @Expose
    private Date requestDate;
    @SerializedName("RequestNumber")
    @Expose
    private String requestNumber;
    @SerializedName("LicenseNumber")
    @Expose
    private String licenseNumber;
    @SerializedName("NotificationDetails")
    @Expose
    private String notificationDetails;
    @SerializedName("NotificationDetails_Voice")
    @Expose
    private Object notificationDetailsVoice;
    @SerializedName("NotificationTypeId")
    @Expose
    private String notificationTypeId;
    @SerializedName("NotificationTypeAR")
    @Expose
    private String notificationTypeAR;
    @SerializedName("NotificationTypeEN")
    @Expose
    private String notificationTypeEN;
    @SerializedName("NotificationTypeDescriptionAR")
    @Expose
    private String notificationTypeDescriptionAR;
    @SerializedName("NotificationTypeDescriptionEN")
    @Expose
    private String notificationTypeDescriptionEN;
    @SerializedName("AreaId")
    @Expose
    private String areaId;
    @SerializedName("AreaNameAR")
    @Expose
    private String areaNameAR;
    @SerializedName("AreaNameEN")
    @Expose
    private String areaNameEN;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("IsClosed")
    @Expose
    private Boolean isClosed;
    @SerializedName("ll")
    @Expose
    private String ll;
    @SerializedName("lg")
    @Expose
    private String lg;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getEstablishmentNameAR() {
        return establishmentNameAR;
    }

    public void setEstablishmentNameAR(String establishmentNameAR) {
        this.establishmentNameAR = establishmentNameAR;
    }

    public String getEstablishmentNameEN() {
        return establishmentNameEN;
    }

    public void setEstablishmentNameEN(String establishmentNameEN) {
        this.establishmentNameEN = establishmentNameEN;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getNotificationDetails() {
        return notificationDetails;
    }

    public void setNotificationDetails(String notificationDetails) {
        this.notificationDetails = notificationDetails;
    }

    public Object getNotificationDetailsVoice() {
        return notificationDetailsVoice;
    }

    public void setNotificationDetailsVoice(Object notificationDetailsVoice) {
        this.notificationDetailsVoice = notificationDetailsVoice;
    }

    public String getNotificationTypeId() {
        return notificationTypeId;
    }

    public void setNotificationTypeId(String notificationTypeId) {
        this.notificationTypeId = notificationTypeId;
    }

    public String getNotificationTypeAR() {
        return notificationTypeAR;
    }

    public void setNotificationTypeAR(String notificationTypeAR) {
        this.notificationTypeAR = notificationTypeAR;
    }

    public String getNotificationTypeEN() {
        return notificationTypeEN;
    }

    public void setNotificationTypeEN(String notificationTypeEN) {
        this.notificationTypeEN = notificationTypeEN;
    }

    public String getNotificationTypeDescriptionAR() {
        return notificationTypeDescriptionAR;
    }

    public void setNotificationTypeDescriptionAR(String notificationTypeDescriptionAR) {
        this.notificationTypeDescriptionAR = notificationTypeDescriptionAR;
    }

    public String getNotificationTypeDescriptionEN() {
        return notificationTypeDescriptionEN;
    }

    public void setNotificationTypeDescriptionEN(String notificationTypeDescriptionEN) {
        this.notificationTypeDescriptionEN = notificationTypeDescriptionEN;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaNameAR() {
        return areaNameAR;
    }

    public void setAreaNameAR(String areaNameAR) {
        this.areaNameAR = areaNameAR;
    }

    public String getAreaNameEN() {
        return areaNameEN;
    }

    public void setAreaNameEN(String areaNameEN) {
        this.areaNameEN = areaNameEN;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public String getLl() {
        return ll;
    }

    public void setLl(String ll) {
        this.ll = ll;
    }

    public String getLg() {
        return lg;
    }

    public void setLg(String lg) {
        this.lg = lg;
    }

}
