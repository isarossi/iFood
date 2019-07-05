package com.recommendation.controller;

import com.recommendation.service.musicplaylist.MusicPlaylistAPI;
import com.recommendation.service.weatherforecast.WeatherAPI;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

@RestController
public class MusicRecommendationController {

    @RequestMapping(value = "/city")
    public List<String> recommendationByCity(String city) throws IOException {
        WeatherAPI weatherAPI = new WeatherAPI();
        Double currentTemp = weatherAPI.retrieveTemperatureByCity(city);
        MusicPlaylistAPI musicApi = new MusicPlaylistAPI();
        List<String> playlist = musicApi.retrieveRecommendation(currentTemp);
        return playlist;
    }

    @RequestMapping(value = {"/lat", "/lon"})
    public List<String> recommendationByCoordinates(String lat, String lon) throws IOException {
        WeatherAPI weatherAPI = new WeatherAPI();
        Double currentTemp = weatherAPI.retrieveTemperatureByCoordinates(lat,lon);
        MusicPlaylistAPI musicApi = new MusicPlaylistAPI();
        List<String> playlist = musicApi.retrieveRecommendation(currentTemp);
        return playlist;
    }
}