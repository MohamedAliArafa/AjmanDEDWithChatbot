package com.ajman.ded.ae.data.model.response.ApplicationListByEmail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item_ApplicationListByEmail implements Serializable {

    @SerializedName("onlineStatusAr")
    @Expose
    private String onlineStatusAr;
    @SerializedName("onlineStatusEn")
    @Expose
    private String onlineStatusEn;
    @SerializedName("COUNTA")
    @Expose
    private int COUNTA;

    public String getOnlineStatusAr() {
        return onlineStatusAr;
    }

    public void setOnlineStatusAr(String onlineStatusAr) {
        this.onlineStatusAr = onlineStatusAr;
    }

    public String getOnlineStatusEn() {
        return onlineStatusEn;
    }

    public void setOnlineStatusEn(String onlineStatusEn) {
        this.onlineStatusEn = onlineStatusEn;
    }

    public int getCOUNTA() {
        return COUNTA;
    }

    public void setCOUNTA(int COUNTA) {
        this.COUNTA = COUNTA;
    }
}
