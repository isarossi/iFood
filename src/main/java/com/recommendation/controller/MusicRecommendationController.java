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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sun.util.resources.cldr.ext.TimeZoneNames_en_MP;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/recommendation")
public class MusicRecommendationController {
    @Autowired
    private WeatherConfig weatherProps;
    @Autowired
    private AuthorizationConfig authorizationConfig;
    @Autowired
    private MusicRecommendationConfig musicRecConfig;

    @RequestMapping(value = "/city")
     public List<Track> recommendationByCity(String city) throws IOException {
        WeatherForecastResponse weatherForecastResponse =  retrieveWeatherResponse(city);
       String genre = retrieveGenreByTemperature(weatherForecastResponse);
        PlaylistResponse playlistResponse = retrievePlaylistRecommendation();
        return playlistResponse.getTracks();
    }

    @RequestMapping(value = {"/lat", "/lon"})
    public List<Track> recommendationByCoordinates(String lat, String lon) throws IOException {
        WeatherForecastResponse weatherForecastResponse =  retrieveWeatherResponse(lat,lon);
        String genre = retrieveGenreByTemperature(weatherForecastResponse);
        PlaylistResponse playlistResponse = retrievePlaylistRecommendation();
        return playlistResponse.getTracks();
    }

    private PlaylistResponse retrievePlaylistRecommendation() throws IOException {
        TokenResponse tokenResponse = retrieveToken();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(musicRecConfig.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        MusicPlaylistServiceInterface musicPlaylistService2 = retrofit.create(MusicPlaylistServiceInterface.class);
        Call<PlaylistResponse> call = musicPlaylistService2.getRecommendation(Constants.MUSIC_GENRE_PARTY, musicRecConfig.getLimit(), tokenResponse.getTokenType() + Constants.SPACE + tokenResponse.getAccessToken());
        return call.execute().body();
    }

    private TokenResponse retrieveToken() throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(authorizationConfig.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        AuthorizationServiceInterface musicPlaylistService = retrofit.create(AuthorizationServiceInterface.class);
        Call<TokenResponse> call = musicPlaylistService.getAccessToken(authorizationConfig.getGrantType(), authorizationConfig.getAuthorizationPrefix() + Constants.SPACE + retrieveAuthorizationEncoded());
        return call.execute().body();
    }

    private WeatherForecastResponse retrieveWeatherResponse(String city) throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        OpenWeatherServiceInterface openWeatherService = retrofit.create(OpenWeatherServiceInterface.class);
        Call<WeatherForecastResponse> call = openWeatherService.getWeatherForecastByCity(weatherProps.getAppid(), weatherProps.getUnits(), city);
        WeatherForecastResponse weatherForecast = call.execute().body();
        return weatherForecast;
    }

    private WeatherForecastResponse retrieveWeatherResponse(String lat, String lon) throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        OpenWeatherServiceInterface openWeatherService = retrofit.create(OpenWeatherServiceInterface.class);
        Call<WeatherForecastResponse> call = openWeatherService.getWeatherForecastByCoordinates(weatherProps.getAppid(), weatherProps.getUnits(), lat, lon);
        WeatherForecastResponse weatherForecast = call.execute().body();
        return weatherForecast;
    }

    private String retrieveAuthorizationEncoded() {
        String credentials = authorizationConfig.getClientId() + Constants.COLON + authorizationConfig.getClientSecret();
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }


    private String retrieveGenreByTemperature(WeatherForecastResponse weatherForecastResponse) {
        Double temp = weatherForecastResponse.getMain().getTemp();
        /*if (temp >= 10 && temp <=0 14){

        }

        if(temp > 30){
            return Constants.MUSIC_GENRE_PARTY;
        }

        if(temp >= 15 && temp <= 30)

        if(temp < 10)*/
        return "pop";

    }
}