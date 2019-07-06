package com.recommendation.service.musicplaylist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenResponse {
    @SerializedName("access_token")
    @Expose
    private Double accessToken;
    @SerializedName("token_type")
    @Expose
    private Integer tokenType;
    @SerializedName("expires_in")
    @Expose
    private Integer expiresIn;

    public Double getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(Double accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getTokenType() {
        return tokenType;
    }

    public void setTokenType(Integer tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
}
