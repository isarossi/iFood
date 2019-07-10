package com.recommendation.service.musicplaylist;

import com.recommendation.Constants;
import com.recommendation.cache.musicPlaylist.CachePlaylistManager;
import com.recommendation.cache.musicPlaylist.model.GenrePlaylist;
import com.recommendation.error.RestException;
import com.recommendation.properties.AuthorizationProperties;
import com.recommendation.properties.MusicRecommendationProperties;
import com.recommendation.service.musicplaylist.model.PlaylistJsonResponse;
import com.recommendation.service.musicplaylist.model.TokenJsonResponse;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MusicService {

    private final MusicRecommendationProperties musicRecConfig;
    private final AuthorizationProperties authorizationProperties;

    @Autowired
    public MusicService(AuthorizationProperties authorizationProperties, MusicRecommendationProperties musicRecConfig) {
        this.musicRecConfig = musicRecConfig;
        this.authorizationProperties = authorizationProperties;
    }

    public List<String> retrievePlaylistRecommendation(CachePlaylistManager cachePlaylist, String genre) throws IOException {
        List<String> playlist = new ArrayList<>();
        if (genre != null && cachePlaylist.get(genre.toLowerCase()) != null) {
            playlist = retrieveFromCache(cachePlaylist, genre);
        } else {
            AuthorizationService authorizationService = new AuthorizationService(authorizationProperties);
            TokenJsonResponse tokenJsonResponse = authorizationService.retrieveToken();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(musicRecConfig.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
            MusicPlaylistServiceInterface musicPlaylistService = retrofit.create(MusicPlaylistServiceInterface.class);
            Call<PlaylistJsonResponse> call = musicPlaylistService.getRecommendation(genre, musicRecConfig.getLimit(), tokenJsonResponse.getTokenType() + Constants.SPACE + tokenJsonResponse.getAccessToken());
            Response<PlaylistJsonResponse> playlistAPIResponse = executeWeatherForecastService(call);
            PlaylistJsonResponse playlistJsonResponse = playlistAPIResponse.body();
            saveInCache(cachePlaylist, genre, playlistJsonResponse);
        }
        return playlist;
    }

    private void saveInCache(CachePlaylistManager playlistCache, String genre, PlaylistJsonResponse playlistResponse) {
        GenrePlaylist genrePlaylist = new GenrePlaylist(genre, playlistResponse.setTracks());
        playlistCache.save(genrePlaylist);
    }

    private List<String> retrieveFromCache(CachePlaylistManager playlistCache, String genre) {
        List<String> playlist = new ArrayList<>();
        GenrePlaylist genrePlaylist = playlistCache.get(genre);
        return genrePlaylist.getPlaylist();
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
