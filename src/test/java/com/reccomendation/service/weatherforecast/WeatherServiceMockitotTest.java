package com.reccomendation.service.weatherforecast;

import com.recommendation.cache.weatherforecast.CacheWeatherManager;
import com.recommendation.properties.WeatherProperties;
import com.recommendation.service.weatherforecast.WeatherService;
import com.recommendation.service.weatherforecast.model.WeatherForecastJsonResponse;
import org.mockito.Mock;

public class WeatherServiceMockitotTest {
    @Mock
    private WeatherService weatherService;
    @Mock
    private WeatherProperties weatherProps;
    @Mock
    private CacheWeatherManager weatherCache;

    public static interface OpenWeatherServiceInterface {
        WeatherForecastJsonResponse getWeatherForecastByCity(String city);
    }

/*
    @Path("myresource")
    public static class WeatherService {

        private WeatherService weatherService;

        @GET
        @Consumes(MediaType.TEXT_PLAIN)
        @Produces(MediaType.APPLICATION_XML)
        public double retrieveWeatherResponse(String city) {

            return weatherService.retrieveWeatherResponse(city);
        }

    }*/
}
