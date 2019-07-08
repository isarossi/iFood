package com.recommendation.service.musicplaylist;

import com.recommendation.service.musicplaylist.model.PlaylistJsonResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MusicPlaylistServiceInterface {

    @GET("/v1/recommendations")
    Call<PlaylistJsonResponse> getRecommendation(@Query("seed_genres") String genre, @Query("limit") String limit, @Header("Authorization") String authorization);
}
