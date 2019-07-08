package com.recommendation.service.musicplaylist;

import com.recommendation.service.musicplaylist.model.TokenJsonResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthorizationServiceInterface {
    @POST("/api/token")
    @FormUrlEncoded
    Call<TokenJsonResponse> getAccessToken(@Field("grant_type") String grantType, @Header("Authorization") String authorization);

}
