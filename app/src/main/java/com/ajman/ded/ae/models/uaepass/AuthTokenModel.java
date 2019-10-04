package com.ajman.ded.ae.models.uaepass;

import com.google.gson.annotations.SerializedName;

public class AuthTokenModel {
    @SerializedName("scope")
    private String mScope;
    @SerializedName("access_token")
    private String mAccessToken;
    @SerializedName("token_type")
    private String mTokenType;
    @SerializedName("expires_in")
    private int mExpiresIn;

    public AuthTokenModel() {
    }

    public String getScope() {
        return this.mScope;
    }

    public void setScope(String scope) {
        this.mScope = scope;
    }

    public String getAccessToken() {
        return this.mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        this.mAccessToken = accessToken;
    }

    public String getTokenType() {
        return this.mTokenType;
    }

    public void setTokenType(String tokenType) {
        this.mTokenType = tokenType;
    }

    public int getExpiresIn() {
        return this.mExpiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.mExpiresIn = expiresIn;
    }
}

