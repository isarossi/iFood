package com.recommendation.controller;

import com.recommendation.properties.PlaylistApiProperties;
import com.recommendation.properties.WeatherApiProperties;
import com.recommendation.service.Constants;
import com.recommendation.service.musicplaylist.MusicPlaylistServiceInterface;
import com.recommendation.service.musicplaylist.PlaylistResponse;
import com.recommendation.service.musicplaylist.TokenResponse;
import com.recommendation.service.musicplaylist.Track;
import com.recommendation.service.weatherforecast.OpenWeatherServiceInterface;
import com.recommendation.service.weatherforecast.WeatherForecastResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
public class MusicRecommendationController {
    @Autowired
    private WeatherApiProperties weatherProps;
    @Autowired
    private PlaylistApiProperties playListApiProps;

    @RequestMapping(value = "/city")
    public List<Track> recommendationByCity(String city) throws IOException {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        OpenWeatherServiceInterface openWeatherService = retrofit.create(OpenWeatherServiceInterface.class);
        Call<WeatherForecastResponse> call = openWeatherService.getWeatherForecastByCity(weatherProps.getAppid(), weatherProps.getUnits(), city);
        WeatherForecastResponse weatherForecast = call.execute().body();

        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(playListApiProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        MusicPlaylistServiceInterface musicPlaylistService = retrofit2.create(MusicPlaylistServiceInterface.class);
        Call<TokenResponse> call2 = musicPlaylistService.getAccessToken(playListApiProps.getGrantType(),playListApiProps.getAuthorizationPrefix()+Constants.SPACE+retrieveAuthorizationEncoded());
        TokenResponse tokenResponse = call2.execute().body();

        Retrofit retrofit3 = new Retrofit.Builder().baseUrl(playListApiProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        MusicPlaylistServiceInterface musicPlaylistService2 = retrofit3.create(MusicPlaylistServiceInterface.class);
        Call<PlaylistResponse> call3 = musicPlaylistService2.getRecommendation("pop","5",tokenResponse.getTokenType()+Constants.SPACE+tokenResponse.getAccessToken());
        PlaylistResponse playlistResponse = call3.execute().body();

        return playlistResponse.getTracks();

    }

    @RequestMapping(value = {"/lat", "/lon"})
    public double recommendationByCoordinates(String lat, String lon) throws IOException {

        /*
        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        OpenWeatherService openWeatherService = retrofit.create(OpenWeatherService.class);
        Call<WeatherForecastResponse> call = openWeatherService.getWeatherForecastByCoordinates(weatherProps.getAppid(), weatherProps.getUnits(), lat, lon);
        WeatherForecastResponse weatherForecast = call.execute().body();
        return weatherForecast.getMain().getTemp();*/
        return 1.0;
    }

    private String retrieveAuthorizationEncoded() {
        String credentials = playListApiProps.getClientId() + Constants.COLON + playListApiProps.getClientSecret();
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}