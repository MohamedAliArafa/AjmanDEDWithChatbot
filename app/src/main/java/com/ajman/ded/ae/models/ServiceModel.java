package com.ajman.ded.ae.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 10/17/17.
 */

public class ServiceModel extends RealmObject {

    @Expose
    @SerializedName("id")
    @PrimaryKey
    private int id;
    @Expose
    @Index
    @SerializedName("name")
    private String name;
    @Expose
    @Index
    @SerializedName("nameAr")
    private String nameAr;
    @Expose
    @SerializedName("icon")
    private String icon;
    @Expose
    @SerializedName("color")
    private int color;
    @Expose
    @SerializedName("link")
    private String link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }
}
