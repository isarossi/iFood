package com.recommendation.service;

import com.recommendation.cache.musicPlaylist.CachePlaylistManager;
import com.recommendation.cache.musicPlaylist.CachePlaylistManagerImpl;
import com.recommendation.cache.weatherforecast.CacheWeatherManager;
import com.recommendation.error.RestException;
import com.recommendation.properties.AuthorizationProperties;
import com.recommendation.properties.MusicRecommendationProperties;
import com.recommendation.properties.WeatherProperties;
import com.recommendation.service.musicplaylist.MusicService;
import com.recommendation.service.musicplaylist.model.PlaylistJsonResponse;
import com.recommendation.service.musicplaylist.model.PlaylistResponse;
import com.recommendation.service.weatherforecast.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MusicRecommendationService {
    private final AuthorizationProperties authorizationProp;
    private final MusicRecommendationProperties musicRecommendationProp;
    private final WeatherProperties weatherProp;
    private CacheWeatherManager weatherCache;
    private CachePlaylistManager playlistCache;

    @Autowired
    public MusicRecommendationService(AuthorizationProperties authorizationProperties, MusicRecommendationProperties musicRecommendationProp, WeatherProperties weatherProp, CacheWeatherManager weatherCache, CachePlaylistManagerImpl playlistCache) {
        this.authorizationProp = authorizationProperties;
        this.musicRecommendationProp = musicRecommendationProp;
        this.weatherProp = weatherProp;
        this.weatherCache = weatherCache;
        this.playlistCache = playlistCache;
    }

    public ResponseEntity<PlaylistResponse> getRecommendedPlaylist(String city) {
        WeatherService weatherService = new WeatherService(weatherProp, weatherCache);
        MusicService musicService = new MusicService(authorizationProp, musicRecommendationProp);
        PlaylistResponse playlistResponse = null;
        double temp;
        try {
            temp = weatherService.retrieveWeatherResponse(city);
            String genre = musicService.retrieveGenreByTemperature(temp);
            playlistResponse = musicService.retrievePlaylistRecommendation(playlistCache, genre);
        } catch (RestException ex) {
            throw ex;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<PlaylistResponse>(playlistResponse, HttpStatus.OK);
    }

    public ResponseEntity<PlaylistResponse> getRecommendedPlaylist(String lat, String lon) {
        WeatherService weatherService = new WeatherService(weatherProp, weatherCache);
        MusicService musicService = new MusicService(authorizationProp, musicRecommendationProp);
        double temp;
        PlaylistResponse playlistResponse = null;
        try {
            temp = weatherService.retrieveWeatherResponse(lat, lon);
            String genre = musicService.retrieveGenreByTemperature(temp);
            playlistResponse = musicService.retrievePlaylistRecommendation(playlistCache, genre);
        } catch (RestException ex) {
            throw ex;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<PlaylistResponse>(playlistResponse, HttpStatus.OK);

    }
}
