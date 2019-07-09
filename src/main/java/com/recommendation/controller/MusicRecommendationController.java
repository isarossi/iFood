package com.recommendation.controller;

import com.recommendation.properties.AuthorizationProperties;
import com.recommendation.properties.MusicRecommendationProperties;
import com.recommendation.properties.RedisProperties;
import com.recommendation.properties.WeatherProperties;
import com.recommendation.service.MusicRecommendationService;
import com.recommendation.service.musicplaylist.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/recommendation")
public class MusicRecommendationController {
    @Autowired
    private AuthorizationProperties authorizationProperties;
    @Autowired
    private MusicRecommendationProperties musicRecommendationProp;
    @Autowired
    private WeatherProperties weatherProp;
    @Autowired
    private RedisProperties redisProperties;

    @RequestMapping(value = "/city")
    public List<Track> recommendationByCity(String city) throws IOException {
        MusicRecommendationService musicRecommendationService = new MusicRecommendationService(authorizationProperties, musicRecommendationProp, weatherProp, redisProperties);
        List<Track> recommendedPlaylist = musicRecommendationService.getRecommendedPlaylist(city);
        return recommendedPlaylist;
    }

    @RequestMapping(value = {"/lat", "/lon"})
    public List<Track> recommendationByCoordinates(String lat, String lon) throws IOException {
        MusicRecommendationService musicRecommendationService = new MusicRecommendationService(authorizationProperties, musicRecommendationProp, weatherProp, redisProperties);
        List<Track> recommendedPlaylist = musicRecommendationService.getRecommendedPlaylist(lat,lon);
        return recommendedPlaylist;
    }

}