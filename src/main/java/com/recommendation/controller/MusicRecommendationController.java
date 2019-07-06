package com.recommendation.controller;

import com.recommendation.service.musicplaylist.MusicPlaylistService;
import com.recommendation.service.weatherforecast.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

@RestController
public class MusicRecommendationController {

    @RequestMapping(value = "/city")
    public List<String> recommendationByCity(String city) throws IOException {
        WeatherService weatherService = new WeatherService();
        MusicPlaylistService musicPlaylistService = new MusicPlaylistService();
        Double currentTemp = weatherService.retrieveTemperatureByCity(city);
        List<String> playlist = musicPlaylistService.retrieveRecommendation(currentTemp);
        return playlist;    }

    @RequestMapping(value = {"/lat", "/lon"})
    public List<String> recommendationByCoordinates(String lat, String lon) throws IOException {
        WeatherService weatherService = new WeatherService();
        MusicPlaylistService musicPlaylistService = new MusicPlaylistService();
        Double currentTemp = weatherService.retrieveTemperatureByCoordinates(lat, lon);
        List<String> playlist = musicPlaylistService.retrieveRecommendation(currentTemp);
        return playlist;
    }
}