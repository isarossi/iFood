package com.recommendation.service.musicplaylist;

import retrofit2.Call;
import retrofit2.http.*;

public interface MusicPlaylistServiceInterface {

    @POST("/api/token")
    @FormUrlEncoded
    Call<TokenResponse> getAccessToken(@Field("grant_type") String grantType, @Header("Authorization") String authorization);

    @GET("/api/recommendations")
    Call<PlaylistResponse> getRecommendation(@Query("seed_genres") String genre, @Query("limit") String limit, @Header("Authorization") String authorization);
}
