package com.recommendation.service.musicplaylist;

import com.recommendation.Constants;
import com.recommendation.cache.musicPlaylist.CachePlaylistManager;
import com.recommendation.cache.musicPlaylist.model.cacheGenrePlaylist;
import com.recommendation.error.RestException;
import com.recommendation.properties.AuthorizationProperties;
import com.recommendation.properties.MusicRecommendationProperties;
import com.recommendation.service.musicplaylist.model.PlaylistJsonResponse;
import com.recommendation.service.musicplaylist.model.TokenJsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Service
public class MusicService {

    private final MusicRecommendationProperties musicRecConfig;
    private final AuthorizationProperties authorizationProperties;

    @Autowired
    public MusicService(AuthorizationProperties authorizationProperties, MusicRecommendationProperties musicRecConfig) {
        this.musicRecConfig = musicRecConfig;
        this.authorizationProperties = authorizationProperties;
    }

    public ResponseEntity<Object> retrievePlaylistRecommendation(CachePlaylistManager cachePlaylist, String genre) throws IOException {
        PlaylistJsonResponse playlistResponse = null;
        Response<PlaylistJsonResponse> playlistApiResponse = null;
        if (genre != null && cachePlaylist.get(genre.toLowerCase()) != null) {
            playlistResponse = retrieveFromCache(cachePlaylist, genre);

        } else {
            AuthorizationService authorizationService = new AuthorizationService(authorizationProperties);
            TokenJsonResponse tokenJsonResponse = authorizationService.retrieveToken();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(musicRecConfig.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
            MusicPlaylistServiceInterface musicPlaylistService = retrofit.create(MusicPlaylistServiceInterface.class);
            Call<PlaylistJsonResponse> call = musicPlaylistService.getRecommendation(genre, musicRecConfig.getLimit(), tokenJsonResponse.getTokenType() + Constants.SPACE + tokenJsonResponse.getAccessToken());
            playlistApiResponse = executeWeatherForecastService(call);
            playlistResponse = playlistApiResponse.body();
            saveInCache(cachePlaylist, genre, playlistResponse);
        }
        return ResponseEntity<Object>;
    }

    private PlaylistJsonResponse retrieveFromCache(CachePlaylistManager playlistCache, String genre) {
        PlaylistJsonResponse playlistResponse = new PlaylistJsonResponse();
        cacheGenrePlaylist cacheGenrePlaylist = (cacheGenrePlaylist) playlistCache.get(genre.toLowerCase());
        Playlist playlist = cacheGenrePlaylist.getPlaylist();
        playlistResponse.setTracks(playlist.getTracks());
        return playlistResponse;
    }

    private void saveInCache(CachePlaylistManager playlistCache, String genre, PlaylistJsonResponse playlistResponse) {
        Playlist playlist = new Playlist(playlistResponse.getTracks());
        cacheGenrePlaylist cacheGenrePlaylist = new cacheGenrePlaylist(genre, playlist);
        playlistCache.save(cacheGenrePlaylist);
    }

    private Response<PlaylistJsonResponse> executeWeatherForecastService(Call<PlaylistJsonResponse> call) throws IOException {
        Response<PlaylistJsonResponse> playlistResponse = null;
        try {
            playlistResponse = call.execute();
            if (!playlistResponse.isSuccessful()) {
                throw new RestException(playlistResponse.errorBody().string());
            }
        } catch (IOException e) {
            throw e;
        }
        return playlistResponse;
    }


    public String retrieveGenreByTemperature(double doubleTemp) {
        String recommendation = null;
        int temp = (int) doubleTemp;
        if (temp > Constants.MAX_TEMPERATURE) {
            recommendation = Constants.MUSIC_GENRE_PARTY;
        } else if (temp < Constants.MIN_TEMPERATURE) {
            recommendation = Constants.MUSIC_GENRE_CLASSICAL;
        } else if (temp >= Constants.FIFTEEN_CELSIUS_TEMPERATURE && temp <= Constants.MAX_TEMPERATURE) {
            recommendation = Constants.MUSIC_GENRE_POP;
        } else if (temp >= Constants.MIN_TEMPERATURE && temp < Constants.FIFTEEN_CELSIUS_TEMPERATURE) {
            recommendation = Constants.MUSIC_GENRE_ROCK;
        }
        return recommendation;
    }
}
