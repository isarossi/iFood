package com.recommendation.controller;

import com.recommendation.cache.CacheManagerImpl;
import com.recommendation.properties.AuthorizationProperties;
import com.recommendation.properties.MusicRecommendationProperties;
import com.recommendation.properties.CacheProperties;
import com.recommendation.properties.WeatherProperties;
import com.recommendation.service.MusicRecommendationService;
import com.recommendation.service.musicplaylist.model.PlaylistJsonResponse;
import com.recommendation.service.musicplaylist.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
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
    private CacheProperties redisProperties;
    @Autowired
    private CacheManager cacheManager;

    @RequestMapping(value = "/city")
    public ResponseEntity<PlaylistJsonResponse> recommendationByCity(String city) {
        MusicRecommendationService musicRecommendationService = new MusicRecommendationService(authorizationProperties, musicRecommendationProp, weatherProp, redisProperties, cacheManager);
        ResponseEntity<PlaylistJsonResponse> recommendedPlaylist = musicRecommendationService.getRecommendedPlaylist(city);
        return recommendedPlaylist;
    }

    @RequestMapping(value = {"/lat", "/lon"})
    public ResponseEntity<PlaylistJsonResponse> recommendationByCoordinates(String lat, String lon){
        MusicRecommendationService musicRecommendationService = new MusicRecommendationService(authorizationProperties, musicRecommendationProp, weatherProp, redisProperties, cacheManager);
        ResponseEntity<PlaylistJsonResponse> recommendedPlaylist = musicRecommendationService.getRecommendedPlaylist(lat,lon);
        return recommendedPlaylist;
    }

}