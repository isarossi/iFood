package com.recommendation.controller;

import com.recommendation.properties.PlaylistApiProperties;
import com.recommendation.properties.WeatherApiProperties;
import com.recommendation.service.musicplaylist.AuthorizationResponse;
import com.recommendation.service.musicplaylist.MusicPlaylistService;
import com.recommendation.service.weatherforecast.OpenWeatherService;
import com.recommendation.service.weatherforecast.WeatherForecastResponse;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.Base64;

@RestController
public class MusicRecommendationController {
    @Autowired
    private WeatherApiProperties weatherProps;
    @Autowired
    private PlaylistApiProperties playListApiProps;

    @RequestMapping(value = "/city")
    public double recommendationByCity(String city) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        OpenWeatherService openWeatherService = retrofit.create(OpenWeatherService.class);
        Call<WeatherForecastResponse> call = openWeatherService.getWeatherForecastByCity(weatherProps.getAppid(), weatherProps.getUnits(), city);
        WeatherForecastResponse weatherForecast = call.execute().body();



        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(playListApiProps.getUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        MusicPlaylistService musicPlaylistService = retrofit2.create(MusicPlaylistService.class);
        Call<AuthorizationResponse> call2 = musicPlaylistService.getAuthorization(playListApiProps.getClientId(),playListApiProps.getResponseType(),playListApiProps.getResponseType(),playListApiProps.getScope());
        AuthorizationResponse authorizationResponse = call2.execute().body();

        return weatherForecast.getMain().getTemp();

    }

    @RequestMapping(value = {"/lat", "/lon"})
    public double recommendationByCoordinates(String lat, String lon) throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        OpenWeatherService openWeatherService = retrofit.create(OpenWeatherService.class);
        Call<WeatherForecastResponse> call = openWeatherService.getWeatherForecastByCoordinates(weatherProps.getAppid(), weatherProps.getUnits(), lat, lon);
        WeatherForecastResponse weatherForecast = call.execute().body();
        return weatherForecast.getMain().getTemp();
    }
}