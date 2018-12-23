package com.ajman.ded.ae.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RegisterLookups extends RealmObject {
    @PrimaryKey
    private int key = 0;
    @SerializedName("StockholderType")
    @Expose
    private RealmList<StockholderType> stockholderType = new RealmList<>();
    @SerializedName("Language")
    @Expose
    private RealmList<Language> language = new RealmList<>();
    @SerializedName("Gender")
    @Expose
    private RealmList<Gender> gender = new RealmList<>();
    @SerializedName("Country")
    @Expose
    private RealmList<Country> country = new RealmList<>();


    public RegisterLookups() {
    }

    public RegisterLookups(RegisterLookups date) {
        this.setStockholderType(date.getStockholderType());
        this.setLanguage(date.getLanguage());
        this.setCountry(date.getCountry());
        this.setGender(date.getGender());
    }

    public RealmList<StockholderType> getStockholderType() {
        return stockholderType;
    }

    public void setStockholderType(RealmList<StockholderType> stockholderType) {
        this.stockholderType = stockholderType;
    }

    public RealmList<Language> getLanguage() {
        return language;
    }

    public void setLanguage(RealmList<Language> language) {
        this.language = language;
    }

    public RealmList<Gender> getGender() {
        return gender;
    }

    public void setGender(RealmList<Gender> gender) {
        this.gender = gender;
    }

    public RealmList<Country> getCountry() {
        return country;
    }

    public void setCountry(RealmList<Country> country) {
        this.country = country;
    }

}
