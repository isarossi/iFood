package com.recommendation.service;

import com.recommendation.cache.CacheManager;
import com.recommendation.error.RestException;
import com.recommendation.properties.AuthorizationProperties;
import com.recommendation.properties.CacheProperties;
import com.recommendation.properties.MusicRecommendationProperties;
import com.recommendation.properties.WeatherProperties;
import com.recommendation.service.musicplaylist.MusicService;
import com.recommendation.service.musicplaylist.model.PlaylistJsonResponse;
import com.recommendation.service.weatherforecast.WeatherService;
import com.recommendation.service.weatherforecast.model.WeatherForecastJsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MusicRecommendationService {
    private final AuthorizationProperties authorizationProp;
    private final MusicRecommendationProperties musicRecommendationProp;
    private final WeatherProperties weatherProp;



    @Autowired
    public MusicRecommendationService(AuthorizationProperties authorizationProperties, MusicRecommendationProperties musicRecommendationProp, WeatherProperties weatherProp) {
        this.authorizationProp = authorizationProperties;
        this.musicRecommendationProp = musicRecommendationProp;
        this.weatherProp = weatherProp;

    }

    public ResponseEntity<PlaylistJsonResponse> getRecommendedPlaylist(String city) {
        WeatherService weatherService = new WeatherService(weatherProp);
        MusicService musicService = new MusicService(authorizationProp, musicRecommendationProp);
        WeatherForecastJsonResponse weatherForecastJsonResponse = null;
        PlaylistJsonResponse playlistJsonResponse = null;
        try {
            weatherForecastJsonResponse = weatherService.retrieveWeatherResponse(city);
            String genre = musicService.retrieveGenreByTemperature(weatherForecastJsonResponse);
            playlistJsonResponse = musicService.retrievePlaylistRecommendation(genre);
        } catch (RestException ex) {
            throw ex;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<PlaylistJsonResponse>(playlistJsonResponse, HttpStatus.OK);
    }

    public ResponseEntity<PlaylistJsonResponse> getRecommendedPlaylist(String lat, String lon) {
        WeatherService weatherService = new WeatherService(weatherProp);
        MusicService musicService = new MusicService(authorizationProp, musicRecommendationProp);
        WeatherForecastJsonResponse weatherForecastJsonResponse = null;
        PlaylistJsonResponse playlistJsonResponse = null;
        try {
            weatherForecastJsonResponse = weatherService.retrieveWeatherResponse(lat, lon);
            String genre = musicService.retrieveGenreByTemperature(weatherForecastJsonResponse);
            playlistJsonResponse = musicService.retrievePlaylistRecommendation(genre);
        } catch (RestException ex) {
            throw ex;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<PlaylistJsonResponse>(playlistJsonResponse, HttpStatus.OK);

    }
}
