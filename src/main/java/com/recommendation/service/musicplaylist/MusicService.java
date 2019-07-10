package com.recommendation.service.musicplaylist;

import com.recommendation.Constants;
import com.recommendation.cache.musicPlaylist.CachePlaylistManager;
import com.recommendation.cache.musicPlaylist.model.GenrePlaylist;
import com.recommendation.error.RestException;
import com.recommendation.properties.AuthorizationProperties;
import com.recommendation.properties.MusicRecommendationProperties;
import com.recommendation.service.musicplaylist.model.PlaylistJsonResponse;
import com.recommendation.service.musicplaylist.model.PlaylistResponse;
import com.recommendation.service.musicplaylist.model.TokenJsonResponse;
import com.recommendation.service.musicplaylist.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
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

    public PlaylistResponse retrievePlaylistRecommendation(CachePlaylistManager cachePlaylist, String genre) throws IOException {
        List<String> playlist;
        if (genre != null && cachePlaylist.get(genre.toLowerCase()) != null) {
            playlist = retrieveFromCache(cachePlaylist, genre);
        } else {
            AuthorizationService authorizationService = new AuthorizationService(authorizationProperties);
            TokenJsonResponse tokenJsonResponse = authorizationService.retrieveToken();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(musicRecConfig.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
            MusicPlaylistServiceInterface musicPlaylistService = retrofit.create(MusicPlaylistServiceInterface.class);
            Call<PlaylistJsonResponse> call = musicPlaylistService.getRecommendation(genre, musicRecConfig.getLimit(), tokenJsonResponse.getTokenType() + Constants.SPACE + tokenJsonResponse.getAccessToken());
            Response<PlaylistJsonResponse> playlistAPIResponse = executeWeatherForecastService(call);
            playlist = mappingTracks (playlistAPIResponse.body());
            saveInCache(cachePlaylist, genre, playlist);
        }
        PlaylistResponse playlistResponse = new PlaylistResponse(playlist);
        return playlistResponse;
    }
    private List<String> mappingTracks(PlaylistJsonResponse playlistResponse){
        List<String> playlist = new ArrayList<String>();
        List<Track> trackList = playlistResponse.getTracks();
        trackList.forEach(item -> {
            playlist.add(item.getName());
        });
        return playlist;
    }

    private void saveInCache(CachePlaylistManager playlistCache, String genre, List<String> playlist) {
        GenrePlaylist genrePlaylist = new GenrePlaylist(genre, playlist);
        playlistCache.save(genrePlaylist);
    }

    private List<String> retrieveFromCache(CachePlaylistManager playlistCache, String genre) {
        GenrePlaylist genrePlaylist = (GenrePlaylist) playlistCache.get(genre);
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

}
