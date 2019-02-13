package com.ajman.ded.ae.models;

/**
 * Created by root on 10/2/17.
 */

public class UserModel {
    private String userName;
    private String password;
    private String userId;
    private String nameAr;
    private String nameEn;

    public UserModel(String userName, String password, String userId, String nameAr, String nameEn) {
        this.userName = userName;
        this.password = password;
        this.userId = userId;
        this.nameAr = nameAr;
        this.nameEn = nameEn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
}
