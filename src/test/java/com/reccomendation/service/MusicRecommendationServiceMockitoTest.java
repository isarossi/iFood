package com.reccomendation.service;

import com.recommendation.cache.musicPlaylist.CachePlaylistManagerImpl;
import com.recommendation.cache.weatherforecast.CacheWeatherManager;
import com.recommendation.properties.AuthorizationProperties;
import com.recommendation.properties.MusicRecommendationProperties;
import com.recommendation.properties.WeatherProperties;
import com.recommendation.service.MusicRecommendationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MusicRecommendationServiceMockitoTest {
    @Mock
    MusicRecommendationService musicRecommendationService;
    @Mock
    private AuthorizationProperties authorizationProp;
    @Mock
    private MusicRecommendationProperties musicRecommendationProp;
    @Mock
    private WeatherProperties weatherProp;
    @Mock
    private CacheWeatherManager weatherCache;
    @Mock
    private CachePlaylistManagerImpl playlistCache;

    @Test
    public void testClassicalRecommendation() {
         musicRecommendationService = new MusicRecommendationService(authorizationProp, musicRecommendationProp, weatherProp, weatherCache, playlistCache);
        Assert.assertEquals("classical",musicRecommendationService.retrieveGenreByTemperature(0));
    }

    @Test
    public void testRockRecommendation() {
        musicRecommendationService = new MusicRecommendationService(authorizationProp, musicRecommendationProp, weatherProp, weatherCache, playlistCache);
        Assert.assertEquals("rock",musicRecommendationService.retrieveGenreByTemperature(10));
        Assert.assertEquals("rock",musicRecommendationService.retrieveGenreByTemperature(14.9));
    }

    @Test
    public void testPopRecommendation() {
        musicRecommendationService = new MusicRecommendationService(authorizationProp, musicRecommendationProp, weatherProp, weatherCache, playlistCache);
        Assert.assertEquals("pop",musicRecommendationService.retrieveGenreByTemperature(15));
        Assert.assertEquals("pop",musicRecommendationService.retrieveGenreByTemperature(30));
    }

    @Test
    public void testPartyRecommendation() {
        musicRecommendationService = new MusicRecommendationService(authorizationProp, musicRecommendationProp, weatherProp, weatherCache, playlistCache);
        Assert.assertEquals("party",musicRecommendationService.retrieveGenreByTemperature(31));
        Assert.assertEquals("party",musicRecommendationService.retrieveGenreByTemperature(100));
    }

}
