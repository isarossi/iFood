package com.recommendation.service;

import com.recommendation.error.RestException;
import com.recommendation.properties.AuthorizationConfig;
import com.recommendation.properties.MusicRecommendationConfig;
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
    @Autowired
    private AuthorizationConfig authorizationConfig;
    @Autowired
    private MusicRecommendationConfig musicRecConfig;
    // @Autowired
    // private WeatherConfig weatherConfig;

    public List<Track> getRecommendedPlaylist(String city)throws IOException {
        // WeatherService weatherService = new WeatherService(weatherConfig);
        WeatherService weatherService = new WeatherService();
        MusicService musicService = new MusicService(authorizationConfig, musicRecConfig);
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
}
