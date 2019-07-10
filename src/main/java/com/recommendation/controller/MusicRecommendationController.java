package com.recommendation.controller;

import com.recommendation.cache.musicPlaylist.CachePlaylistManagerImpl;
import com.recommendation.cache.weatherforecast.CacheWeatherManager;
import com.recommendation.cache.weatherforecast.CacheWeatherManagerImpl;
import com.recommendation.properties.AuthorizationProperties;
import com.recommendation.properties.MusicRecommendationProperties;
import com.recommendation.properties.WeatherProperties;
import com.recommendation.service.MusicRecommendationService;
import com.recommendation.service.musicplaylist.model.PlaylistJsonResponse;
import com.recommendation.service.musicplaylist.model.PlaylistResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private CacheWeatherManagerImpl weatherCache;
    @Autowired
    private CachePlaylistManagerImpl playlistCache;

    @RequestMapping(value = "/city")
    public ResponseEntity<PlaylistResponse> recommendationByCity(String city) {
        MusicRecommendationService musicRecommendationService = new MusicRecommendationService(authorizationProperties, musicRecommendationProp, weatherProp, weatherCache,playlistCache);
        ResponseEntity<PlaylistResponse> recommendedPlaylist = musicRecommendationService.getRecommendedPlaylist(city);
        return recommendedPlaylist;
    }

    @RequestMapping(value = {"/lat", "/lon"})
    public ResponseEntity<PlaylistResponse> recommendationByCoordinates(String lat, String lon) {
        MusicRecommendationService musicRecommendationService = new MusicRecommendationService(authorizationProperties, musicRecommendationProp, weatherProp, weatherCache, playlistCache);
        ResponseEntity<PlaylistResponse> recommendedPlaylist = musicRecommendationService.getRecommendedPlaylist(lat, lon);
        return recommendedPlaylist;
    }

}