package com.recommendation.service.musicplaylist;

import com.recommendation.service.weatherforecast.WeatherForecastResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface MusicPlaylistService {

    @POST("/api/token")
    @FormUrlEncoded
    @Headers({"Accept: application/json" })
    Call<TokenResponse> getAccessToken(@Field("grant_type") String grantType, @Header("Authorization") String authorization);

}
