package com.ajman.ded.ae.data.model.response.OnlineUserAllPermits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item_OnlineUserAllPermits implements Serializable {

    @SerializedName("ValidPermits")
    @Expose
    private int validPermits;
    @SerializedName("ExpiryPermits")
    @Expose
    private int expiryPermits;

    public int getValidPermits() {
        return validPermits;
    }

    public void setValidPermits(int validPermits) {
        this.validPermits = validPermits;
    }

    public int getExpiryPermits() {
        return expiryPermits;
    }

    public void setExpiryPermits(int expiryPermits) {
        this.expiryPermits = expiryPermits;
    }

}
