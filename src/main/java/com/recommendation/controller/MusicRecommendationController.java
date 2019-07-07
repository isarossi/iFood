package com.recommendation.controller;

import com.recommendation.properties.AuthorizationConfig;
import com.recommendation.properties.MusicRecommendationConfig;
import com.recommendation.service.RestException;
import com.recommendation.service.RestExceptionHandler;
import com.recommendation.service.musicplaylist.MusicService;
import com.recommendation.service.musicplaylist.model.PlaylistResponse;
import com.recommendation.service.musicplaylist.model.Track;
import com.recommendation.properties.WeatherConfig;
import com.recommendation.service.weatherforecast.WeatherService;
import com.recommendation.service.weatherforecast.model.WeatherForecastJsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recommendation")
public class MusicRecommendationController {
    @Autowired
    private AuthorizationConfig authorizationConfig;
    @Autowired
    private MusicRecommendationConfig musicRecConfig;
    @Autowired
    private WeatherConfig weatherConfig;

    @RequestMapping(value = "/city")
    public List<Track> recommendationByCity(String city)throws IOException{
        List<Track> t = new ArrayList<>();
        WeatherService weatherService = new WeatherService(weatherConfig);
        MusicService musicService = new MusicService(authorizationConfig,musicRecConfig);
        WeatherForecastJsonResponse weatherForecastJsonResponse = null;
        try {
            weatherForecastJsonResponse = weatherService.retrieveWeatherResponse(city);
        } catch (RestException ex) {
          throw ex;
        }
        String genre = musicService.retrieveGenreByTemperature(weatherForecastJsonResponse);
        PlaylistResponse playlistResponse = musicService.retrievePlaylistRecommendation(genre);
        return playlistResponse.getTracks();
    }

    @RequestMapping(value = {"/lat", "/lon"})
    public List<Track> recommendationByCoordinates(String lat, String lon) throws IOException{
        WeatherService weatherService = new WeatherService(weatherConfig);
        MusicService musicService = new MusicService(authorizationConfig,musicRecConfig);
        WeatherForecastJsonResponse weatherForecastJsonResponse = weatherService.retrieveWeatherResponse(lat, lon);
        String genre = musicService.retrieveGenreByTemperature(weatherForecastJsonResponse);
        PlaylistResponse playlistResponse = musicService.retrievePlaylistRecommendation(genre);
        return playlistResponse.getTracks();
    }


}