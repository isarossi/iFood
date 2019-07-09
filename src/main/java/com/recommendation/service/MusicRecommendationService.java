package com.recommendation.service;

import com.recommendation.error.RestException;
import com.recommendation.properties.AuthorizationProperties;
import com.recommendation.properties.MusicRecommendationProperties;
import com.recommendation.properties.RedisProperties;
import com.recommendation.properties.WeatherProperties;
import com.recommendation.service.musicplaylist.MusicService;
import com.recommendation.service.musicplaylist.model.PlaylistJsonResponse;
import com.recommendation.service.musicplaylist.model.Track;
import com.recommendation.service.weatherforecast.WeatherService;
import com.recommendation.service.weatherforecast.model.WeatherForecastJsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MusicRecommendationService {
    private final AuthorizationProperties authorizationProp;
    private final MusicRecommendationProperties musicRecommendationProp;
    private final WeatherProperties weatherProp;
    private final RedisProperties redisProp;

    @Autowired
    public MusicRecommendationService(AuthorizationProperties authorizationProperties, MusicRecommendationProperties musicRecommendationProp, WeatherProperties weatherProp, RedisProperties redisProp) {
        this.authorizationProp = authorizationProperties;
        this.musicRecommendationProp = musicRecommendationProp;
        this.weatherProp = weatherProp;
        this.redisProp = redisProp;
    }

    public List<Track> getRecommendedPlaylist(String city) throws IOException {
        WeatherService weatherService = new WeatherService(weatherProp, redisProp);
        MusicService musicService = new MusicService(authorizationProp, musicRecommendationProp, redisProp);
        WeatherForecastJsonResponse weatherForecastJsonResponse = null;
        PlaylistJsonResponse playlistJsonResponse = null;
        try {
            weatherForecastJsonResponse = weatherService.retrieveWeatherResponse(city);
            String genre = musicService.retrieveGenreByTemperature(weatherForecastJsonResponse);
            playlistJsonResponse = musicService.retrievePlaylistRecommendation(genre);
        } catch (RestException ex) {
            throw ex;
        }
        return playlistJsonResponse.getTracks();
    }

    public List<Track> getRecommendedPlaylist(String lat, String lon) throws IOException {
        WeatherService weatherService = new WeatherService(weatherProp, redisProp);
        MusicService musicService = new MusicService(authorizationProp, musicRecommendationProp, redisProp);
        WeatherForecastJsonResponse weatherForecastJsonResponse = null;
        PlaylistJsonResponse playlistJsonResponse = null;
        try {
            weatherForecastJsonResponse = weatherService.retrieveWeatherResponse(lat, lon);
            String genre = musicService.retrieveGenreByTemperature(weatherForecastJsonResponse);
            playlistJsonResponse = musicService.retrievePlaylistRecommendation(genre);
        } catch (RestException ex) {
            throw ex;
        }
        return playlistJsonResponse.getTracks();
    }
}
