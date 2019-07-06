package com.recommendation.controller;

import com.recommendation.properties.AuthorizationConfig;
import com.recommendation.properties.MusicRecommendationConfig;
import com.recommendation.properties.WeatherConfig;
import com.recommendation.service.Constants;
import com.recommendation.service.musicplaylist.AuthorizationServiceInterface;
import com.recommendation.service.musicplaylist.MusicPlaylistServiceInterface;
import com.recommendation.service.musicplaylist.model.PlaylistResponse;
import com.recommendation.service.musicplaylist.model.TokenResponse;
import com.recommendation.service.musicplaylist.model.Track;
import com.recommendation.service.weatherforecast.OpenWeatherServiceInterface;
import com.recommendation.service.weatherforecast.model.WeatherForecastResponse;
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
    private WeatherConfig weatherProps;
    @Autowired
    private AuthorizationConfig authorizationConfig;
    @Autowired
    private MusicRecommendationConfig musicRecConfig;

    @RequestMapping(value = "/city")
    public List<Track> recommendationByCity(String city) throws IOException {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        OpenWeatherServiceInterface openWeatherService = retrofit.create(OpenWeatherServiceInterface.class);
        Call<WeatherForecastResponse> call = openWeatherService.getWeatherForecastByCity(weatherProps.getAppid(), weatherProps.getUnits(), city);
        WeatherForecastResponse weatherForecast = call.execute().body();

        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(authorizationConfig.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        AuthorizationServiceInterface musicPlaylistService = retrofit2.create(AuthorizationServiceInterface.class);
        Call<TokenResponse> call2 = musicPlaylistService.getAccessToken(authorizationConfig.getGrantType(), authorizationConfig.getAuthorizationPrefix()+Constants.SPACE+retrieveAuthorizationEncoded());
        TokenResponse tokenResponse = call2.execute().body();

        Retrofit retrofit3 = new Retrofit.Builder().baseUrl(musicRecConfig.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        MusicPlaylistServiceInterface musicPlaylistService2 = retrofit3.create(MusicPlaylistServiceInterface.class);
        Call<PlaylistResponse> call3 = musicPlaylistService2.getRecommendation(Constants.MUSIC_GENRE_PARTY,musicRecConfig.getLimit(),tokenResponse.getTokenType()+Constants.SPACE+tokenResponse.getAccessToken());
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
        String credentials = authorizationConfig.getClientId() + Constants.COLON + authorizationConfig.getClientSecret();
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}