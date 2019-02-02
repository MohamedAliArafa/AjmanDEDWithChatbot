package com.ajman.ded.ae.models.notification;

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
    @SerializedName("IsClosed")
    @Expose
    private String isClosed;

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

    public String getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(String isClosed) {
        this.isClosed = isClosed;
    }

}
