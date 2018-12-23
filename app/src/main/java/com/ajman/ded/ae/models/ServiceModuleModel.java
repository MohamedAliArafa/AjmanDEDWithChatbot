package com.ajman.ded.ae.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 10/17/17.
 */

public class ServiceModuleModel extends RealmObject {

    @Expose
    @SerializedName("id")
    @PrimaryKey
    private int id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("nameAr")
    private String nameAr;
    @Expose
    @SerializedName("services")
    private RealmList<ServiceModel> list;

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

    public RealmList<ServiceModel> getList() {
        return list;
    }

    public void setList(RealmList<ServiceModel> list) {
        this.list = list;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }
}
