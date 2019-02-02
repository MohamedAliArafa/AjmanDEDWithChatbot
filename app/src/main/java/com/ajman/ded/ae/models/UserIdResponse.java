package com.ajman.ded.ae.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserIdResponse {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("NameAR")
    @Expose
    private String nameAR;
    @SerializedName("NameEN")
    @Expose
    private String nameEN;
    @SerializedName("MobileConfirmed")
    @Expose
    private Boolean mobileConfirmed;
    @SerializedName("MobileConfirmationCode")
    @Expose
    private String mobileConfirmationCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    public Boolean getMobileConfirmed() {
        return mobileConfirmed;
    }

    public void setMobileConfirmed(Boolean mobileConfirmed) {
        this.mobileConfirmed = mobileConfirmed;
    }

    public String getMobileConfirmationCode() {
        return mobileConfirmationCode;
    }

    public void setMobileConfirmationCode(String mobileConfirmationCode) {
        this.mobileConfirmationCode = mobileConfirmationCode;
    }

}
