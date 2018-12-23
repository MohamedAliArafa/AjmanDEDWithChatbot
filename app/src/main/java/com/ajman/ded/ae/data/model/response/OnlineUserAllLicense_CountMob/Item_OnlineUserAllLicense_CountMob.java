package com.ajman.ded.ae.data.model.response.OnlineUserAllLicense_CountMob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item_OnlineUserAllLicense_CountMob implements Serializable {

    @SerializedName("Total")
    @Expose
    private int Total;
    @SerializedName("Active")
    @Expose
    private int Active;
    @SerializedName("expiry")
    @Expose
    private int expiry;
    @SerializedName("Abrogated_Cancelled_Revoked")
    @Expose
    private int Abrogated_Cancelled_Revoked;
    @SerializedName("Administrative_Cancellation_")
    @Expose
    private int Administrative_Cancellation_;


    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int active) {
        Active = active;
    }

    public int getExpiry() {
        return expiry;
    }

    public void setExpiry(int expiry) {
        this.expiry = expiry;
    }

    public int getAbrogated_Cancelled_Revoked() {
        return Abrogated_Cancelled_Revoked;
    }

    public void setAbrogated_Cancelled_Revoked(int abrogated_Cancelled_Revoked) {
        Abrogated_Cancelled_Revoked = abrogated_Cancelled_Revoked;
    }

    public int getAdministrative_Cancellation_() {
        return Administrative_Cancellation_;
    }

    public void setAdministrative_Cancellation_(int administrative_Cancellation_) {
        Administrative_Cancellation_ = administrative_Cancellation_;
    }
}
