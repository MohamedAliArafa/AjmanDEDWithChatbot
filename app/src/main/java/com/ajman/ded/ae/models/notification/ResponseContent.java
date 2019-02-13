package com.ajman.ded.ae.models.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ResponseContent {

    @SerializedName("ID")
    @Expose
    private String id;
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
    @SerializedName("IsClosed")
    @Expose
    private String isClosed;
    @SerializedName("PeriodInDays")
    @Expose
    private String periodInDays;
    @SerializedName("AttachmentsCount")
    @Expose
    private String attachmentsCount;

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

    public String getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(String isClosed) {
        this.isClosed = isClosed;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPeriodInDays() {
        return periodInDays;
    }

    public void setPeriodInDays(String periodInDays) {
        this.periodInDays = periodInDays;
    }

    public String getAttachmentsCount() {
        return attachmentsCount;
    }

    public void setAttachmentsCount(String attachmentsCount) {
        this.attachmentsCount = attachmentsCount;
    }
}
