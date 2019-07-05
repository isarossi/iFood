package com.recommendation.service.musicplaylist;

import com.recommendation.service.weatherforecast.WeatherForecastResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface MusicPlaylistService {
    @GET("authorize/")
    Call<AuthorizationResponse> getAuthorization(@Query("client_id") String clientId,
                                                 @Query("response_type") String resType,
                                                 @Query("redirect_uri") String redirectURL,
                                                 @Query("scope") String scope);

    @POST("/api/token")
    @FormUrlEncoded
    @Headers({"Accept: application/json" })
    TokenResponse getAccessToken(@Field("grant_type") String grantType,
                                       @Field("username") String username,
                                       @Field("password") String password,
                                       @Header("Authorization") String authorization);

}
