package com.recommendation.controller;

import com.recommendation.service.MusicRecommendationService;
import com.recommendation.service.musicplaylist.model.Track;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/recommendation")
public class MusicRecommendationController {

    @RequestMapping(value = "/city")
    public List<Track> recommendationByCity(String city) throws IOException {
        MusicRecommendationService musicRecommendationService = new MusicRecommendationService();
        List<Track> recommendedPlaylist = musicRecommendationService.getRecommendedPlaylist(city);
        return recommendedPlaylist;
    }
/*
    @RequestMapping(value = {"/lat", "/lon"})
    public List<Track> recommendationByCoordinates(String lat, String lon) throws IOException {

        // WeatherService weatherService = new WeatherService(weatherConfig);
        WeatherService weatherService = new WeatherService();
        MusicService musicService = new MusicService(authorizationConfig, musicRecConfig);
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
    }*/


}