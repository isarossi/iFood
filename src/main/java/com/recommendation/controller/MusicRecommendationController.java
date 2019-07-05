package com.recommendation.controller;

import com.recommendation.properties.WeatherProperties;
import com.recommendation.service.weatherforecast.OpenWeather;
import com.recommendation.service.weatherforecast.WeatherForecastResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;

@RestController
public class MusicRecommendationController {
    @Autowired
    private WeatherProperties weatherProps;

    @RequestMapping(value = "/city")
    public double recommendationByCity(String city) throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        OpenWeather openWeatherService = retrofit.create(OpenWeather.class);
        Call<WeatherForecastResponse> call = openWeatherService.getWeatherForecastByCity(weatherProps.getAppid(), weatherProps.getUnits(), city);
        WeatherForecastResponse weatherForecast = call.execute().body();
        return weatherForecast.getMain().getTemp();
    }

    @RequestMapping(value = {"/lat", "/lon"})
    public double recommendationByCoordinates(String lat, String lon) throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        OpenWeather openWeatherService = retrofit.create(OpenWeather.class);
        Call<WeatherForecastResponse> call = openWeatherService.getWeatherForecastByCoordinates(weatherProps.getAppid(), weatherProps.getUnits(), lat, lon);
        WeatherForecastResponse weatherForecast = call.execute().body();
        return weatherForecast.getMain().getTemp();
    }
}